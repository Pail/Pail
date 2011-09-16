
package me.escapeNT.pail.scheduler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.logging.Level;

import me.escapeNT.pail.Util.Util;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;

/**
 * Registers and executes all scheduled tasks.
 * @author escapeNT
 */
public class Scheduler {

    private static HashMap<ScheduledTask, Boolean> tasks = new HashMap<ScheduledTask, Boolean>();
    private static HashMap<ScheduledTask, Integer> taskIDs = new HashMap<ScheduledTask, Integer>();
    private static final BukkitScheduler bs = Bukkit.getServer().getScheduler();
    private static final File file = new File(Util.getPlugin().getDataFolder(), "tasks.dat");

    /**
     * Registers a task to be executed.
     * If the task has already been registered, the method simply returns.
     * @param task The task to register.
     */
    public static void registerTask(final ScheduledTask task) {
        if(isTaskRegistered(task)) {
            return;
        }
        tasks.put(task, Boolean.FALSE);
        if(!task.isEnabled()) {
            return;
        }
        
        int id;
        if(task.isRepeating()) {
            id = bs.scheduleAsyncRepeatingTask(Util.getPlugin(), new Runnable() {
                public void run() {
                    task.execute();
                    tasks.put(task, Boolean.TRUE);
                }
            }, task.getInterval(), task.getInterval());
        } else {
            id = bs.scheduleAsyncDelayedTask(Util.getPlugin(), new Runnable() {
                public void run() {
                    task.execute();
                    tasks.put(task, Boolean.TRUE);
                }
            }, task.getInterval());
        }
        taskIDs.put(task, id);
    }

    /**
     * Determines if a task has already been registered with the scheduler.
     * @param task The task to check.
     * @return True if it has been registered, false otherwise.
     */
    public static boolean isTaskRegistered(ScheduledTask task) {
        return tasks.containsKey(task);
    }

    /**
     * Gets the map of all currently registered tasks and whether they have been executed yet.
     * @return The map of tasks to weather or not they have been executed at least once.
     */
    public static HashMap<ScheduledTask, Boolean> getTasks() {
        return tasks;
    }

    /**
     * Saves the current list of tasks to file.
     */
    public static void saveTasks() {
        Util.getPlugin().getDataFolder().mkdir();
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(tasks);
            oos.close();
        } catch (IOException ex) {
            Util.log(Level.SEVERE, ex.toString());
        }
    }

    /**
     * Loads the saved list of tasks.
     */
    public static void loadTasks() {
        if(!file.exists()) {
            saveTasks();
        }
        for(Integer i : taskIDs.values()) {
            bs.cancelTask(i);
        }
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            tasks = (HashMap<ScheduledTask, Boolean>)ois.readObject();
            ois.close();
        } catch (Exception ex) {
            Util.log(Level.SEVERE, ex.toString());
        }
        for(final ScheduledTask task : tasks.keySet()) {
            if(!task.isEnabled()) {
                continue;
            }
            int id;
            if(task.isRepeating()) {
                id = bs.scheduleAsyncRepeatingTask(Util.getPlugin(), new Runnable() {
                    public void run() {
                        task.execute();
                        tasks.put(task, Boolean.TRUE);
                    }
                }, task.getInterval(), task.getInterval());
            } else {
                id = bs.scheduleAsyncDelayedTask(Util.getPlugin(), new Runnable() {
                    public void run() {
                        task.execute();
                        tasks.put(task, Boolean.TRUE);
                    }
                }, task.getInterval());
            }
            taskIDs.put(task, id);
        }
    }

    /**
     * Gets a task matching a specified name, or null if it isn't found.
     * @param name The name of the task.
     * @return The task with the given name, or null if not found.
     */
    public ScheduledTask getTask(String name) {
        for(ScheduledTask t : tasks.keySet()) {
            if(t.getName().equals(name)) {
                return t;
            }
        }
        return null;
    }

    /**
     * Removes a task from the list.
     * @param name
     */
    public void removeTask(String name) {
        for(ScheduledTask t : tasks.keySet()) {
            if(t.getName().equals(name)) {
                bs.cancelTask(taskIDs.get(t));
                taskIDs.remove(t);
                tasks.remove(t);
                return;
            }
        }
    }
}
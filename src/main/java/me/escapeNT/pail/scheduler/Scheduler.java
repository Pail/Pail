
package me.escapeNT.pail.scheduler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private static List<Integer> taskIDs = new ArrayList<Integer>();
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
        taskIDs.add(id);
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
        for(Integer i : taskIDs) {
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
            taskIDs.add(id);
        }
    }
}
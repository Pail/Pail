
package me.escapeNT.pail.scheduler;
import me.escapeNT.pail.Util.Util;

import org.bukkit.Bukkit;

/**
 * Class representing a console command task.
 * @author escapeNT
 */
public class ConsoleCommandTask implements ScheduledTask {

    private String command;
    private String name;
    private boolean repeating;
    private boolean enabled;
    private long interval;

    public ConsoleCommandTask(String command, boolean repeating, long interval, String name) {
        this.command = command;
        this.interval = interval;
        this.repeating = repeating;
        this.name = name;
    }

    public long getInterval() {
        return interval;
    }

    public boolean isRepeating() {
        return repeating;
    }

    /**
     * Gets the command to be executed.
     * @return the command The command String.
     */
    public String getCommand() {
        return command;
    }

    /**
     * Executes the task by dispatching the command.
     */
    public void execute() {
        Bukkit.getServer().dispatchCommand(Util.getConsoleSender(), command);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
}
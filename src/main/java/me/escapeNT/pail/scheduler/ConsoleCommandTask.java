
package me.escapeNT.pail.scheduler;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;

/**
 * Class representing a console command task.
 * @author escapeNT
 */
public class ConsoleCommandTask implements ScheduledTask {

    private String command;
    private boolean repeating;
    private long interval;

    public ConsoleCommandTask(String command, boolean repeating, long interval) {
        this.command = command;
        this.interval = interval;
        this.repeating = repeating;
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
        Bukkit.getServer().dispatchCommand(new ConsoleCommandSender(Bukkit.getServer()), command);
    }
}
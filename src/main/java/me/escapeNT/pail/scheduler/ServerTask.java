
package me.escapeNT.pail.scheduler;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;

/**
 * Class representing a scheduled server task.
 * @author escapeNT
 */
public class ServerTask implements ScheduledTask {

    private Type type;
    private boolean repeating;
    private long interval;

    /**
     * Constructs a new Server task of the given type.
     * @param type The Type of server task.
     * @param repeating True if the task is repeating.
     * @param interval The interval (or delay) between executions.
     */
    public ServerTask(Type type, boolean repeating, long interval) {
        this.type = type;
        this.repeating = repeating;
        this.interval = interval;
    }

    public long getInterval() {
        return interval;
    }

    public boolean isRepeating() {
        return repeating;
    }

    public void execute() {
        Server s = Bukkit.getServer();
        ConsoleCommandSender sender = new ConsoleCommandSender(Bukkit.getServer());
        s.dispatchCommand(sender, type.getCommand());
    }

    /**
     * The type of server task to execute.
     */
    public static enum Type {

        RELOAD("reload"),
        STOP("stop"),
        SAVE_ALL("Save-all");

        private String command;

        private Type(String command) {
            this.command = command;
        }

        /**
         * Gets the server command to execute this action.
         * @return the server command.
         */
        public String getCommand() {
            return command;
        }
    }
}
package me.escapeNT.pail.scheduler;

import me.escapeNT.pail.Util.Util;
import org.bukkit.Bukkit;
import org.bukkit.Server;

/**
 * Class representing a scheduled server task.
 * @author escapeNT
 */
public class ServerTask implements ScheduledTask {

    private Type type;
    private boolean repeating;
    private boolean enabled;
    private long interval;
    private String name;

    /**
     * Constructs a new Server task of the given type.
     * @param type The Type of server task.
     * @param repeating True if the task is repeating.
     * @param interval The interval (or delay) between executions.
     */
    public ServerTask(Type type, boolean repeating, long interval, String name) {
        this.type = type;
        this.repeating = repeating;
        this.interval = interval;
        this.name = name;
    }

    public long getInterval() {
        return interval;
    }

    public boolean isRepeating() {
        return repeating;
    }

    public void execute() {
        Bukkit.getServer().dispatchCommand(Util.getConsoleSender(), type.getCommand());
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

    /**
     * The type of server task to execute.
     */
    public static enum Type {

        RELOAD("Reload"),
        STOP("Stop"),
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
        
        @Override
        public String toString() {
            return getCommand();
        }
    }
}
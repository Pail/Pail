
package me.escapeNT.pail.scheduler;

import java.io.Serializable;

/**
 * Interface containing the basic methods of a schedulable
 * task.
 * @author escapeNT
 */
public interface ScheduledTask extends Serializable {

    /**
     * Gets the time interval between executions of this task.
     * @return The interval in milliseconds between task executions.
     */
    public long getInterval();

    /**
     * Returns true if the task should be repeated indefinitely.
     * @return True if the task is repeating, false if not.
     */
    public boolean isRepeating();

    /**
     * Gets whether the task is enabled and running.
     * @return True if the task is enabled.
     */
    public boolean isEnabled();

    /**
     * Sets whether the task is enabled and running.
     * @param enabled True if the task will be enabled, false if disabled.
     */
    public void setEnabled(boolean enabled);

    /**
     * Gets the name of the specific task.
     * @return The task name.
     */
    public String getName();

    /**
     * Sets the task name.
     * @param name The name of the task.
     */
    public void setName(String name);

    /**
     * Executes this task.
     */
    public void execute();
}
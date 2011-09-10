
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
     * Executes this task.
     */
    public void execute();
}

package me.escapeNT.pail.easygui.event;

import me.escapeNT.pail.easygui.PailComponent;

/**
 * Class containing general information about an event.
 * @author escapeNT
 */
public abstract class Event {

    protected PailComponent sender;

    /**
     * Constructs a new event from the given sender.
     *
     * @param sender The sender component
     */
    protected Event(PailComponent sender) {
        this.sender = sender;
    }

    /**
     * Gets the component which sent this event.
     *
     * @return The  sender component
     */
    public PailComponent getSender() {
        return sender;
    }
}
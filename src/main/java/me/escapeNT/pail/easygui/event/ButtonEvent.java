
package me.escapeNT.pail.easygui.event;

import me.escapeNT.pail.easygui.PailComponent;

/**
 * Event containing information about a button press.
 * @author escapeNT
 */
public class ButtonEvent extends Event {

    /**
     * Constructs a new ButtonEvent with the given sender.
     *
     * @param sender The component sending the event
     */
    public ButtonEvent(PailComponent sender) {
        super(sender);
    }
}
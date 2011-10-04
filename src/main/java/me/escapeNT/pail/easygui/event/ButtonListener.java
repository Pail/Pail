
package me.escapeNT.pail.easygui.event;

/**
 * Provided to a button component to be notified of button events.
 * @author escapeNT
 */
public abstract class ButtonListener implements Listener {

    /**
     * Called when the button this listener is attached to is pressed.
     */
    public void onButtonPress(ButtonEvent event) {}
}

package me.escapeNT.pail.easygui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import me.escapeNT.pail.easygui.event.ButtonEvent;

import me.escapeNT.pail.easygui.event.ButtonListener;
import me.escapeNT.pail.easygui.event.Listener;

/**
 * Class representing a tab displayable in the Pail window.
 * @author escapeNT
 */
public class PailTab extends JPanel {

    private static final int COLS = 3;

    private LinkedHashMap<String, PailComponent> components;
    private Map<PailComponent, Set<Listener>> listeners;
    private String defaultComponent;

    /**
     * Creates a new tab interface with the given components and the name of
     * the default component (null if none). Called only by a TabBuilder.
     *
     * @param cps
     * @param defaultComponent
     */
    protected PailTab(final LinkedHashMap<String, PailComponent> cps,
            final Map<PailComponent, Set<Listener>> listeners, final String defaultComponent) {

        this.components = cps;
        this.listeners = listeners;
        this.defaultComponent = defaultComponent;

        setLayout(new BorderLayout());
        setupComponents();
    }

    /**
     * Adds the components to the panel and registers their listeners.
     */
    private void setupComponents() {
        JPanel main = new JPanel();
        GridLayout gr = new GridLayout(Math.max(components.size() / COLS, 15), COLS);
        main.setLayout(gr);

        JPanel bottom = new JPanel();

        add(BorderLayout.CENTER, main);
        add(BorderLayout.SOUTH, bottom);

        for(final PailComponent pc : components.values()) {
            if(defaultComponent != null && pc.getLabel().equals(defaultComponent)) {
                bottom.add(pc);
            } else {
                pc.setAlignmentX(LEFT_ALIGNMENT);
                main.add(pc);
            }

            // Register button listeners
            if(pc.getType() == PailComponent.Type.BUTTON && listeners.get(pc) != null) {
                JButton button = (JButton) pc.getRootComponent();
                button.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        for(Listener l : listeners.get(pc)) {
                            ((ButtonListener)l).onButtonPress(new ButtonEvent(pc));
                        }
                    }
                });
            }
        }
    }

    /**
     * Gets the current data from a component with the following format:
     * For check boxes: "true" if selected, "false" if deselected.
     * For text fields: The current contents.
     * For selection menus: The currently selected item (as represented by its toString() method).
     * For buttons: null
     * Throws IllegalArgumentException if no component with the specified name exists.
     *
     * @param name The name of the component
     * @return The data currently held in the component, depending on its type
     */
    public String getComponentValue(String name) {
        if(!components.containsKey(name)) {
            throw new IllegalArgumentException("No component with name " + name + " exists.");
        }
        PailComponent c = components.get(name);
        switch(c.getType()) {
            case CHECKBOX:
                return (((JCheckBox) c.getRootComponent()).isSelected()) ? "true" : "false";
            case TEXT_FIELD:
                return ((JTextField) c.getRootComponent()).getText();
            case SELECTION_MENU:
                return ((JComboBox) c.getRootComponent()).getSelectedItem().toString();
            case BUTTON:
            default:
                return null;
        }
    }
}
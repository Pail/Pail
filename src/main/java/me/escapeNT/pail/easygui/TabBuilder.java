
package me.escapeNT.pail.easygui;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import me.escapeNT.pail.easygui.event.ButtonListener;
import me.escapeNT.pail.easygui.event.Listener;

/**
 * Class designed for building a tab.
 * @author escapeNT
 */
public class TabBuilder {

    private LinkedHashMap<String, PailComponent> components;
    private Map<PailComponent, Set<Listener>> listeners;
    private String defaultComponent;

    /**
     * Constructs a new TabBuilder object.
     * Components will be placed in the form in the order they are added.
     */
    public TabBuilder() {
        components = new LinkedHashMap<String, PailComponent>();
        listeners = new HashMap<PailComponent, Set<Listener>>();
        defaultComponent = null;
    }

    /**
     * Adds a check box with the given name to the tab. Throws
     * IllegalArgumentException if the specified name has already been used on this form.
     *
     * @param name The name of this component. This will appear as the
     * component's label and is also used to refer to it for all purposes.
     * @return The TabBuilder the component was added to. (Additions may be
     * chained together)
     */
    public TabBuilder addCheckBox(String name) {
        if(components.keySet().contains(name)) {
            throw new IllegalArgumentException("This form already contains a component by the name " + name);
        }
        components.put(name, new PailComponent(name, new JCheckBox(name)));
        return this;
    }

    /**
     * Adds a check box with the given name to the tab.
     *
     * @param name The name of this component. This will appear as the
     * component's label and is also used to refer to it for all purposes.
     * Throws IllegalArgumentException if the specified name has already been used on this form.
     * @param initialValue The selected state of the check box upon loading the form
     * @return The TabBuilder the component was added to. (Additions may be
     * chained together)
     */
    public TabBuilder addCheckBox(String name, boolean initialValue) {
        if(components.keySet().contains(name)) {
            throw new IllegalArgumentException("This form already contains a component by the name " + name);
        }
        JCheckBox c = new JCheckBox(name);
        c.setSelected(initialValue);
        components.put(name, new PailComponent(name, c));
        return this;
    }

    /**
     * Adds a text field with the given name to the tab.
     *
     * @param name The name of this component. This will appear as the
     * component's label and is also used to refer to it for all purposes.
     * Throws IllegalArgumentException if the specified name has already been used on this form.
     * @return The TabBuilder the component was added to. (Additions may be
     * chained together)
     */
    public TabBuilder addTextField(String name) {
        if(components.keySet().contains(name)) {
            throw new IllegalArgumentException("This form already contains a component by the name " + name);
        }
        components.put(name, new PailComponent(name, new JTextField(20)));
        return this;
    }

    /**
     * Adds a text field with the given name to the tab.
     *
     * @param name The name of this component. This will appear as the
     * component's label and is also used to refer to it for all purposes.
     * Throws IllegalArgumentException if the specified name has already been used on this form.
     * @param initalValue The text placed in the field when the form is loaded.
     * @return The TabBuilder the component was added to. (Additions may be
     * chained together)
     */
    public TabBuilder addTextField(String name, String initialValue) {
        if(components.keySet().contains(name)) {
            throw new IllegalArgumentException("This form already contains a component by the name " + name);
        }
        components.put(name, new PailComponent(name, new JTextField(initialValue)));
        return this;
    }

    /**
     * Adds a text field with the given name to the tab. Throws
     * IllegalArgumentException if the specified name has already been used on this form.
     *
     * @param name The name of this component. This will appear as the
     * component's label and is also used to refer to it for all purposes.
     * @param values The values the user will be able to choose from.
     * @return The TabBuilder the component was added to. (Additions may be
     * chained together)
     */
    public TabBuilder addSelectionMenu(String name, Object[] values) {
        if(components.keySet().contains(name)) {
            throw new IllegalArgumentException("This form already contains a component by the name " + name);
        }
        components.put(name, new PailComponent(name, new JComboBox(values)));
        return this;
    }
    
    /**
     * Adds a text field with the given name to the tab. Throws
     * IllegalArgumentException if the specified name has already been used on this form.
     *
     * @param name The name of this component. This will appear as the
     * component's label and is also used to refer to it for all purposes.
     * @param values The values the user will be able to choose from.
     * @param initialSelection The Object to be selected when the form is loaded.
     * IllegalArgumentException will be thrown if the initial selection is not 
     * one of the values in the list.
     * @return The TabBuilder the component was added to. (Additions may be
     * chained together)
     */
    public TabBuilder addSelectionMenu(String name, Object[] values, Object initialSelection) {
        if(components.keySet().contains(name)) {
            throw new IllegalArgumentException("This form already contains a component by the name " + name);
        }
        if(!Arrays.asList(values).contains(initialSelection)) {
            throw new IllegalArgumentException("The inital selection is not one of the options in the list!");
        } else {
            JComboBox b = new JComboBox(values);
            b.setSelectedItem(initialSelection);
            components.put(name, new PailComponent(name, b));
        }
        return this;
    }

    /**
     * Adds a button with the given name to the tab. Throws
     * IllegalArgumentException if the specified name has already been used on this form.
     *
     * @param name The name of this component. This will appear as the
     * component's label and is also used to refer to it for all purposes.
     * @param values The values the user will be able to choose from.
     * @return The TabBuilder the component was added to. (Additions may be
     * chained together)
     */
    public TabBuilder addButton(String name) {
        if(components.keySet().contains(name)) {
            throw new IllegalArgumentException("This form already contains a component by the name " + name);
        }
        components.put(name, new PailComponent(name, new JButton(name)));
        return this;
    }

    /**
     * Creates a fully-fledged PailTab using the components added to this TabBuilder.
     * Components will be placed in the form in the order they were added.
     * 
     * @return The PailTab containing all the components in this TabBuilder
     */
    public PailTab createTab() {
        return new PailTab(components, listeners, defaultComponent);
    }

    /**
     * Sets the name of the default component of the form.
     * This component is placed at the bottom and is usually a prominent
     * feature like a submit button. 
     *
     * @param defaultComponent The name of the default component
     */
    public void setDefaultComponent(String defaultComponent) {
        this.defaultComponent = defaultComponent;
    }

    /**
     * Adds a {@link ButtonListener} to the button with the given name.
     * Throws IllegalArgumentException if the specified button
     * name doesn't exist or is not a button.
     * @param buttonName The name of the button to add the listener to
     * @param listener The {@link ButtonListener} to add to the button
     * @return The TabBuilder this method was called on. (Calls may be
     * chained together)
     */
    public TabBuilder addButtonListener(String buttonName, ButtonListener listener) {
        if(!components.containsKey(buttonName)) {
            throw new IllegalArgumentException("No component with name " + buttonName + " exists.");
        } else if(components.get(buttonName).getType() != PailComponent.Type.BUTTON) {
            throw new IllegalArgumentException("Component " + buttonName
                    + " cannot have a ButtonListener applied because it is not a button.");
        }

        PailComponent button = components.get(buttonName);
        if(listeners.get(button) == null) {
            listeners.put(button, new HashSet<Listener>());
        }

        listeners.get(button).add(listener);

        return this;
    }
}
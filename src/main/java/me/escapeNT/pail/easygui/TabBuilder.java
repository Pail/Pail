
package me.escapeNT.pail.easygui;

import java.util.Arrays;
import java.util.LinkedHashMap;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextField;

/**
 * Class designed for building a tab.
 * @author escapeNT
 */
public class TabBuilder {

    private LinkedHashMap<String, PailComponent> components;
    private String defaultComponent;

    /**
     * Constructs a new TabBuilder object.
     * Components will be placed in the form in the order they are added.
     */
    public TabBuilder() {
        components = new LinkedHashMap<String, PailComponent>();
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
        components.put(name, new PailComponent(name, new JTextField()));
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
        return new PailTab(components, defaultComponent);
    }

    /**
     * Sets the name of the default component of the form.
     * This component is placed at the bottom and is usually a prominent
     * feature like a submit button.
     *
     * @param defaultComponent The name of the default component
     * @return The TabBuilder the component was added to. (Additions may be
     * chained together)
     */
    public TabBuilder setDefaultComponent(String defaultComponent) {
        this.defaultComponent = defaultComponent;
        return this;
    }
}
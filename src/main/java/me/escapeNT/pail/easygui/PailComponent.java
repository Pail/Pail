
package me.escapeNT.pail.easygui;

import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Class to hold a component an its label in a single panel.
 * @author escapeNT
 */
public class PailComponent extends JPanel {

    private String label;
    private Type type;
    private JComponent rootComponent;

    /**
     * Constructs a new PailComponent panel.
     *
     * @param label The label for the component.
     * @param component The component to add.
     */
    protected PailComponent(String label, JComponent component) {
        if(component instanceof JCheckBox) {
            type = Type.CHECKBOX;
        } else if(component instanceof JTextField) {
            type = Type.TEXT_FIELD;
        } else if(component instanceof JComboBox) {
            type = Type.SELECTION_MENU;
        } else if(component instanceof JButton) {
            type = Type.BUTTON;
        }

        setLayout(new FlowLayout());

        if(type != Type.CHECKBOX && type != Type.BUTTON) {
            add(new JLabel(label));
        }
        add(component);
        
        this.label = label;
        this.rootComponent = component;
    }

    /**
     * Gets the name of this component.
     * 
     * @return the name The component name
     */
    public String getLabel() {
        return label;
    }

    /**
     * Gets the type of component contained within this field.
     *
     * @return the type The component type
     */
    public Type getType() {
        return type;
    }

    /**
     * Returns the root {@link JComponent} this PailComponent is attached to.
     * @return the rootComponent The root JComponent
     */
    public JComponent getRootComponent() {
        return rootComponent;
    }

    /**
     * Represents the type of component this object contains.
     */
    public enum Type {

        /**
         * A check box.
         */
        CHECKBOX,

        /**
         * A text field.
         */
        TEXT_FIELD,

        /**
         * A dropdown selection menu.
         */
        SELECTION_MENU,

        /**
         * A button.
         */
        BUTTON;
    }
}
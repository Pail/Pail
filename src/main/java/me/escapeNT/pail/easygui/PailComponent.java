
package me.escapeNT.pail.easygui;

import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Class to hold a component an its label in a single panel.
 * @author escapeNT
 */
public class PailComponent extends JPanel {

    private String label;

    /**
     * Constructs a new PailComponent panel.
     *
     * @param label The label for the component.
     * @param component The component to add.
     */
    protected PailComponent(String label, JComponent component) {
        setLayout(new FlowLayout());
        if(!(component instanceof JCheckBox) && !(component instanceof JButton)) {
            add(new JLabel(label));
        }
        add(component);
        this.label = label;
    }

    /**
     * Gets the name of this component.
     * 
     * @return the name The component name
     */
    public String getLabel() {
        return label;
    }
}
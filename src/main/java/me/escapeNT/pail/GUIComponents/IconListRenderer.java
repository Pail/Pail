
package me.escapeNT.pail.GUIComponents;

import java.awt.Component;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;

/**
 * List renderer to display icons mapped to objects.
 * @author escapeNT
 */
public class IconListRenderer extends DefaultListCellRenderer {

    private Map<Object, ImageIcon> icons = null;

    public IconListRenderer(Map<Object, ImageIcon> icons) {
        this.icons = icons;
    }

    @Override
    public Component getListCellRendererComponent(
        JList list, Object value, int index,
        boolean isSelected, boolean cellHasFocus) {

        JLabel label = (JLabel) super.getListCellRendererComponent(list,
                        value, index, isSelected, cellHasFocus);
        Icon icon = icons.get(value);
        label.setIcon(icon);
        return label;
    }
}

package me.escapeNT.pail.GUIComponents;

import java.awt.Color;
import java.awt.Component;
import java.util.Map;
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
    private boolean highlightrows;

    public IconListRenderer(Map<Object, ImageIcon> icons, boolean highlightrows) {
        this.icons = icons;
        this.highlightrows = highlightrows;
    }

    @Override
    public Component getListCellRendererComponent(
        JList list, Object value, int index,
        boolean isSelected, boolean cellHasFocus) {

        JLabel label = (JLabel) super.getListCellRendererComponent(list,
                        value, index, isSelected, cellHasFocus);
        Icon icon = icons.get(value);
        label.setIcon(icon);

        if(highlightrows) {
            Color c = ((index % 2 == 0) ? Color.WHITE : Color.LIGHT_GRAY);
            label.setBackground(c);

            if(isSelected) {
                label.setForeground(Color.BLACK);
                label.setBackground(new Color(163, 225, 255));
            }
        }
        return label;
    }
}
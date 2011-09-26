
package me.escapeNT.pail.easygui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.LinkedHashMap;
import javax.swing.JPanel;

/**
 * Class representing a tab displayable in the Pail window.
 * @author escapeNT
 */
public class PailTab extends JPanel {

    protected PailTab(LinkedHashMap<String, PailComponent> cps, String defaultComponent) {
        setLayout(new BorderLayout());

        JPanel main = new JPanel();
        GridLayout gr = new GridLayout(Math.max(cps.size() / 4, 15), 4);
        main.setLayout(gr);

        JPanel bottom = new JPanel();

        add(BorderLayout.CENTER, main);
        add(BorderLayout.SOUTH, bottom);

        for(PailComponent pc : cps.values()) {
            if(defaultComponent != null && pc.getLabel().equals(defaultComponent)) {
                bottom.add(pc);
            } else {
                main.add(pc);
            }
        }
    }
}
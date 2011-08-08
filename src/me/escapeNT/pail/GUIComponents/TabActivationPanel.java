
package me.escapeNT.pail.GUIComponents;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import me.escapeNT.pail.config.PanelConfig;

import me.escapeNT.pail.util.Util;

/**
 * Panel displaying the loaded components and their activation status.
 * @author escapeNT
 */
public class TabActivationPanel extends JPanel {

    private Map<String, JCheckBox> boxes = new HashMap<String, JCheckBox>();

    public TabActivationPanel() {
        initComponents();
    }

    private void initComponents() {

        // Setup layout
        int numComps = Util.getInterfaceComponents().keySet().size();
        setLayout(new GridLayout(numComps / 2, 2));

        // Get all panel titles
        List<String> titles = new ArrayList<String>();
        for(String title : Util.getInterfaceComponents().keySet()) {
            titles.add(title);
        }
        Collections.sort(titles, String.CASE_INSENSITIVE_ORDER);

        // Add checkboxes
        boxes = new HashMap<String, JCheckBox>();
        for(String s : titles) {
            if(s.equals("Settings") || s.equals("Server Control")) {
                break;
            }
            if(!PanelConfig.getPanelsActivated().containsKey(s)) {
                PanelConfig.getPanelsActivated().put(s, Boolean.TRUE);
            }
            if(PanelConfig.getPanelsActivated().get(s)) {
                getBoxes().put(s, new JCheckBox(s));
                getBoxes().get(s).setSelected(true);
                add(getBoxes().get(s));
            }
            else {
                getBoxes().put(s, new JCheckBox(s));
                getBoxes().get(s).setSelected(false);
                add(getBoxes().get(s));
            }
        }
    }

    /**
     * @return the boxes
     */
    public Map<String, JCheckBox> getBoxes() {
        return boxes;
    }
}
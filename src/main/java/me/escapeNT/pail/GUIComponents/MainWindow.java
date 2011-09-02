package me.escapeNT.pail.GUIComponents;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import me.escapeNT.pail.config.PanelConfig;
import me.escapeNT.pail.Util.Util;


/**
 * Class containing the main JFrame of the plugin.
 * @author escapeNT
 */
public class MainWindow extends JFrame {

    private JTabbedPane tabPane;
    private JMenuBar menuBar;
    private ServerControlPanel serverControls;

    /**
     * Called by the constructor to initialize the GUI components.
     */
    private void initComponents() { 
        tabPane = new JTabbedPane();
        menuBar = new JMenuBar();

        // Register server control panel
        serverControls = new ServerControlPanel();
        Util.getPlugin().loadInterfaceComponent("Server Control", getServerControls());

        // Load all registered GUI components
        loadPanels();
        loadMenus();
        
        add(getTabPane());
        setJMenuBar(menuBar);
    }

    /**
     * Loads all queued panels into the interface.
     */
    public void loadPanels() {
        PanelConfig.load();
        getTabPane().removeAll();
        List<String> t = new ArrayList<String>();
        List<JPanel> p = new ArrayList<JPanel>();
        getTabPane().add(Util.getInterfaceComponents().get("Server Control"), "Server Control");
        for (String title : Util.getInterfaceComponents().keySet()) {
            t.add(title);
            p.add(Util.getInterfaceComponents().get(title));
        }
        for(int i = t.size() - 1; i >= 0; i--) {
            if(!PanelConfig.getPanelsActivated().containsKey(t.get(i))) {
                PanelConfig.getPanelsActivated().put(t.get(i), true);
            }
            if(!t.get(i).equals("Server Control") && PanelConfig.getPanelsActivated().get(t.get(i))) {
                getTabPane().add(p.get(i),t.get(i));
            }
        }
        validate();
    }
    
    private void loadMenus() {
        menuBar.add(new FileMenu());
        menuBar.add(new EditMenu());
        menuBar.add(new ServerMenu());
        menuBar.add(new HelpMenu());
    }

    /**
     * Constructs a new main window for the GUI.
     */
    public MainWindow() {
        initComponents();
    }

    /**
     * Returns the server control panel.
     * @return the serverControls
     */
    public ServerControlPanel getServerControls() {
        return serverControls;
    }

    /**
     * @return the tabPane
     */
    public JTabbedPane getTabPane() {
        return tabPane;
    }
}
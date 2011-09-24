package me.escapeNT.pail.GUIComponents;

import java.awt.Dimension;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import me.escapeNT.pail.Util.Localizable;
import me.escapeNT.pail.config.PanelConfig;
import me.escapeNT.pail.Util.Util;


/**
 * Class containing the main JFrame of the plugin.
 * @author escapeNT
 */
public class MainWindow extends JFrame implements Localizable {

    private JTabbedPane tabPane;
    private JMenuBar menuBar;
    private ServerControlPanel serverControls;

    /**
     * Called by the constructor to initialize the GUI components.
     */
    private void initComponents() {
        tabPane = new JTabbedPane();
        menuBar = new JMenuBar();

        setIconImage(Util.getPlugin().PAIL_ICON);
        setTitle(Util.translate("Pail Server Manager"));
        setMinimumSize(new Dimension(990, 615));
        addWindowListener(Util.getPlugin().windowListener);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        //setLocationRelativeTo(null);
        //setFocusableWindowState(true);
        setFocusable(true);

        // Register server control panel
        serverControls = new ServerControlPanel();
        Util.getPlugin().loadInterfaceComponent("Server Control", getServerControls());

        // Load all registered GUI components
        loadPanels();
        loadMenus();

        add(getTabPane());
        setJMenuBar(menuBar);
        
        pack();
    }

    /**
     * Loads all queued panels into the interface.
     */
    public void loadPanels() {
        PanelConfig.load();
        getTabPane().removeAll();
        getTabPane().add(Util.getInterfaceComponents().get("Server Control"), Util.translate("Server Control"));
        getTabPane().add(Util.getInterfaceComponents().get("Settings"), Util.translate("Settings"));
        for(Map.Entry<String, JPanel> entry : Util.getInterfaceComponents().entrySet()) {
            if(!PanelConfig.getPanelsActivated().containsKey(entry.getKey())) {
                PanelConfig.getPanelsActivated().put(entry.getKey(), true);
            }
            if(!entry.getKey().equals("Server Control") && !entry.getKey().equals("Settings")
                    && PanelConfig.getPanelsActivated().get(entry.getKey())) {
                getTabPane().add(entry.getValue(), Util.translate(entry.getKey()));
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

    public void translateComponent() {}
}
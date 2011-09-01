package me.escapeNT.pail.GUIComponents;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;

import me.escapeNT.pail.config.PanelConfig;
import me.escapeNT.pail.Util.Util;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;

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
        loadMenu();
        
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
    
    public void loadMenu() {
        JMenu server = new JMenu("Server");
        server.setMnemonic('S');

        JMenuItem reload = new JMenuItem("Reload");
        reload.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK));
        reload.setIcon(new ImageIcon(getClass().getResource("images/reload.png")));
        reload.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Util.getPlugin().saveState();
                Bukkit.getServer().dispatchCommand(new ConsoleCommandSender(Bukkit.getServer()), "reload");
            }
        });
        
        JMenuItem stop = new JMenuItem("Stop");
        stop.setIcon(new ImageIcon(getClass().getResource("images/stop.png")));
        stop.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));
        stop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Bukkit.getServer().dispatchCommand(new ConsoleCommandSender(Bukkit.getServer()), "stop");
            }
        });
        
        JMenuItem save = new JMenuItem("Save All");
        save.setIcon(new ImageIcon(getClass().getResource("images/save.png")));
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Bukkit.getServer().dispatchCommand(new ConsoleCommandSender(Bukkit.getServer()), "save-all");
            }
        });

        server.add(save);
        server.add(reload);
        server.add(stop);
        
        menuBar.add(server);
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
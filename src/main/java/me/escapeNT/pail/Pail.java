package me.escapeNT.pail;

import java.awt.Color;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import me.escapeNT.pail.GUIComponents.MainWindow;
import me.escapeNT.pail.GUIComponents.SettingsPanel;
import me.escapeNT.pail.GUIComponents.UpdateView;
import me.escapeNT.pail.config.General;
import me.escapeNT.pail.config.PanelConfig;
import me.escapeNT.pail.Util.ScrollableTextArea;
import me.escapeNT.pail.Util.ServerReadyListener;
import me.escapeNT.pail.Util.UpdateHandler;
import me.escapeNT.pail.Util.Util;
import me.escapeNT.pail.Util.Waypoint;
import me.escapeNT.pail.config.WaypointConfig;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Independent, comprehensive, and extensible GUI for Bukkit.
 * @author escapeNT
 */
public class Pail extends JavaPlugin {
    
    private static final Logger log = Logger.getLogger("Minecraft");

    public static final String PLUGIN_NAME = "Pail";
    public static final ServerReadyListener handler = new ServerReadyListener();
    public static String PLUGIN_VERSION;

    private MainWindow main;

    @Override
    public void onEnable() {
        PLUGIN_VERSION = getDescription().getVersion();  
        Util.setPlugin(this);
        General.load();

        try {
            UIManager.setLookAndFeel(General.getLookAndFeel());
        } catch (Exception ex) {
            Logger.getLogger(Pail.class.getName()).log(Level.SEVERE, null, ex);
        }

        new Thread(new Runnable() {
            public void run() {
                main = new MainWindow();

                log.addHandler(handler);

                PailLogHandler mainHandler = new PailLogHandler(
                    Util.getServerControls().getServerConsolePanel().getConsoleOutput());
                mainHandler.setLevel(Level.ALL);
                log.addHandler(mainHandler);

                getMainWindow().setTitle("Pail Server Manager");
                getMainWindow().setResizable(false);
                getMainWindow().addWindowListener(new WindowCloseListener());
                getMainWindow().setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                getMainWindow().pack();
                getMainWindow().setSize(860, 555);
                getMainWindow().setLocationRelativeTo(null);
                loadState();
                getMainWindow().setFocusableWindowState(true);
                getMainWindow().setFocusable(true);
                getMainWindow().setVisible(true);
                getMainWindow().requestFocus();

                Util.setServerControls(getMainWindow().getServerControls());   

                for(Player p : getServer().getOnlinePlayers()) {
                    Util.getServerControls().getListModel().addElement(p.getName());
                }

                if(General.isAutoUpdate() && UpdateHandler.isUpToDate() != null
                        && !UpdateHandler.isUpToDate()) {
                    new UpdateView().setVisible(true);
                }
            }
        }, "Pail").start();

        PluginManager pm = this.getServer().getPluginManager();
        PailPlayerListener playerListener = new PailPlayerListener();
        pm.registerEvent(Type.PLAYER_JOIN, playerListener, Priority.Monitor, this);
        pm.registerEvent(Type.PLAYER_KICK, playerListener, Priority.Monitor, this);
        pm.registerEvent(Type.PLAYER_QUIT, playerListener, Priority.Monitor, this);
        pm.registerEvent(Type.SERVER_COMMAND, new PailServerListener(), Priority.Monitor, this);

        Util.log(PLUGIN_NAME + " " + PLUGIN_VERSION + " Enabled");
    }
    
    @Override
    public void onDisable() {
        if(getMainWindow() != null) {
            getMainWindow().getTabPane().removeAll();
            getMainWindow().dispose();
        }
        WaypointConfig.save();
        PanelConfig.save();

        for(LookAndFeelInfo laf : UIManager.getInstalledLookAndFeels()) {
            if(laf.getName().equals(((SettingsPanel)this.getInterfaceComponent("Settings")).getThemes().getSelectedItem())) {
                General.setLookAndFeel(laf.getClassName());
            }
        }
        General.save();
        Util.log(PLUGIN_NAME + " " + PLUGIN_VERSION + " Disabled");
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if(cmd.getName().equalsIgnoreCase("Pail") && args.length == 0 && sender instanceof ConsoleCommandSender) {
            getMainWindow().setVisible(true);
            getMainWindow().requestFocus();
            return true;
        }
        else if(sender instanceof Player) {
            return true;
        }
        return false;
    }

    /**
     * Saves the current plugin state to file.
     */
    public void saveState() {
        if(main.isVisible()) {
            new PailPersistance().save(getMainWindow().getLocationOnScreen(),
                Util.getServerControls().getServerConsolePanel().getConsoleOutput().getText());
        }
    }

    /**
     * Attempts to load a previous state from file (if it exists).
     */
    protected void loadState() {
        if(!PailPersistance.file.exists()) {
            return;
        }
        final PailPersistance prev = new PailPersistance().load();
        getMainWindow().setLocation(prev.getWindowLocation());

        final ScrollableTextArea output = Util.getServerControls().getServerConsolePanel().getConsoleOutput();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                BufferedReader reader = new BufferedReader(new StringReader(prev.getConsoleText()));

                String str;
                try {
                    while ((str = reader.readLine()) != null) {
                        try {
                            if (str.length() > 0) {
                                String[] s = str.split(" ");
                                output.append(Color.GRAY, true, s[0]);

                                Color color = Color.BLACK;
                                Level lv = Level.parse(s[1].substring(1, s[1].length() - 1));
                                if(lv == Level.INFO) {
                                    color = Color.BLUE;
                                } else if(lv == Level.WARNING) {
                                    color = Color.ORANGE;
                                } else if(lv == Level.SEVERE) {
                                    color = Color.RED;
                                }
                                output.append(color, " [" + lv.toString() + "] ");

                                StringBuilder sb = new StringBuilder();
                                for(int i = 2; i < s.length; i++) {
                                    if(s[i].startsWith("[") && s[i].contains("]")) {
                                        output.append(Color.BLACK, sb.toString());
                                        sb = new StringBuilder();
                                        output.append(Color.BLACK, true, s[i] + " ");
                                    } else {
                                        sb.append(s[i]);
                                        sb.append(" ");
                                    }
                                }
                                output.append(Color.BLACK, sb.toString() + "\n");
                            }
                        } catch(IllegalArgumentException ex) {
                            output.append(Color.BLACK, str);
                        } catch (IndexOutOfBoundsException e) {
                            output.append(Color.BLACK, str);
                        }
                    }
                } catch (Exception e) {
                    Util.log(Level.SEVERE, e.toString());
                }

                JScrollBar vertical = Util.getServerControls().getServerConsolePanel().getConsoleOutput().getScrollerPanel().getVerticalScrollBar();
                vertical.setValue(vertical.getMaximum());
            }
        });
    }

    /**
     * Returns the main window of this instance.
     * @return the main
     */
    public MainWindow getMainWindow() {
        return main;
    }

    private class WindowCloseListener implements WindowListener {
        public void windowClosing(WindowEvent e) {
            int confirm = JOptionPane.showConfirmDialog(getMainWindow(), "Are you sure you want to close the Pail window?",
                    "Confirm Close", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            if(confirm == JOptionPane.OK_OPTION) {
                getMainWindow().setVisible(false);
            }
        }
        public void windowOpened(WindowEvent e) {}
        public void windowClosed(WindowEvent e) {}
        public void windowIconified(WindowEvent e) {}
        public void windowDeiconified(WindowEvent e) {}
        public void windowActivated(WindowEvent e) {}
        public void windowDeactivated(WindowEvent e) {}
    }


    // Public API ------------------------------------------------------------

    /**
     * Loads the provided JPanel into the user interface under the given title.
     * If the title is null, it will be converted to the empty String.
     * @param panel The JPanel to load.
     * @param title The title of this panel.
     * @throws NullPointerException if the specified JPanel is null.
     * @throws IllegalArgumentException if the title is already taken.
     */
    public void loadInterfaceComponent(String title, JPanel panel) {
        if(panel == null) {
            throw new NullPointerException("Attempt to pass null panel to be loaded.");
        }
        else if(Util.getInterfaceComponents().containsKey(title)) {
            throw new IllegalArgumentException("That title is taken!");
        }
        else {
            if(title == null) {
                title = "";
            }
            Util.getInterfaceComponents().put(title, panel);
        }
    }

    /**
     * Gets the interface component by the specified title, or null if it isn't loaded.
     * @param title The title of the component.
     * @return The component loaded with the specified title, or null if it doesn't exist.
     */
    public JPanel getInterfaceComponent(String title) {
        if(Util.getInterfaceComponents().containsKey(title)) {
            return Util.getInterfaceComponents().get(title);
        }
        else {
            return null;
        }
    }

    /**
     * Sets the activated (visible) status of the specified tab. Note: this will
     * refresh the tab layout to reflect changes immediately.
     * @param title The title of the panel.
     * @param activated True if the tab will be visible.
     */
    public void setActivated(String title, boolean activated) {
        if(!PanelConfig.getPanelsActivated().containsKey(title)) {
            throw new IllegalArgumentException("Panel doen't exist.");
        }
        PanelConfig.getPanelsActivated().put(title, activated);
        PanelConfig.save();
        getMainWindow().loadPanels();
    }

    /**
     * Gets the waypoint with the specified name, or null if it doesn't exist.
     * @param name The name of the waypoint.
     * @return
     */
    public Waypoint getWaypoint(String name) {
        for(Waypoint w : WaypointConfig.getWaypoints()) {
            if(w.getName().equals(name)) {
                return w;
            }
        }
        return null;
    }

    /**
     * Add the provided Waypoint to the list.
     * @param waypoint The Waypoint to add.
     */
    public void addWaypoint(Waypoint waypoint) {
        WaypointConfig.getWaypoints().add(waypoint);
        WaypointConfig.save();
    }

    /**
     * Removes the waypoint with the given name from the list.
     * @param name The name of the Waypoint to remove.
     * @return True if a waypoint was indeed removed.
     */
    public boolean removeWaypoint(String name) {
        for(Waypoint w : WaypointConfig.getWaypoints()) {
            if(w.getName().equals(name)) {
                WaypointConfig.getWaypoints().remove(w);
                WaypointConfig.save();
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the 25x25 pixel image icon for the provided Material.
     * @param material The material to get the icon for.
     * @return The 25x25px ImageIcon of the provided material.
     * @throws IllegalArgumentException if the Material is AIR.
     */
    public ImageIcon getIcon(Material material) {
        if(material == Material.AIR) {
            throw new IllegalArgumentException("There is no image for air silly.");
        }
        return new ImageIcon(getClass().getResource("GUIComponents/images/" + material.toString() + ".png"));
    }
}
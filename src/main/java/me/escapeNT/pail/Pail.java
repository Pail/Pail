package me.escapeNT.pail;

import com.apple.eawt.AboutHandler;
import com.apple.eawt.AppEvent.AboutEvent;
import com.apple.eawt.Application;
import com.google.api.translate.Translate;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import me.escapeNT.pail.GUIComponents.AboutView;
import me.escapeNT.pail.GUIComponents.MainWindow;
import me.escapeNT.pail.GUIComponents.SettingsPanel;
import me.escapeNT.pail.config.General;
import me.escapeNT.pail.config.PanelConfig;
import me.escapeNT.pail.Util.ScrollableTextArea;
import me.escapeNT.pail.Util.ServerReadyListener;
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
public final class Pail extends JavaPlugin {
    
    private static final Logger log = Logger.getLogger("Minecraft");

    public static final String PLUGIN_NAME = "Pail";
    public static String PLUGIN_THREAD;
    public static String PLUGIN_VERSION;
    public final Image PAIL_ICON = Toolkit.getDefaultToolkit().createImage(getClass().getResource("GUIComponents/images/pailicon.png"));

    public static final ServerReadyListener handler = new ServerReadyListener();

    private MainWindow main;

    @Override
    @SuppressWarnings("LeakingThisInConstructor")
    public void onEnable() {

        if(System.getProperty("os.name").contains("Mac")) {
            Application app = Application.getApplication();
            System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Pail");
            System.setProperty("apple.laf.useScreenMenuBar", "true");
            app.setDockIconImage(PAIL_ICON);
            app.setAboutHandler(new AboutHandler() {
                public void handleAbout(AboutEvent ae) {
                    new AboutView().setVisible(true);
                }
            });
        }

        PLUGIN_THREAD = getDescription().getWebsite();  
        PLUGIN_VERSION = getDescription().getVersion();
        Translate.setHttpReferrer(PLUGIN_THREAD);
        Util.setPlugin(this);
        General.load();

        setupLookAndFeels();

        Thread t = new Thread(new InitMain(), "Pail");
        t.start();

        PluginManager pm = this.getServer().getPluginManager();
        PailPlayerListener playerListener = new PailPlayerListener();
        pm.registerEvent(Type.PLAYER_JOIN, playerListener, Priority.Monitor, this);
        pm.registerEvent(Type.PLAYER_KICK, playerListener, Priority.Monitor, this);
        pm.registerEvent(Type.PLAYER_QUIT, playerListener, Priority.Monitor, this);
        pm.registerEvent(Type.SERVER_COMMAND, new PailServerListener(), Priority.Monitor, this);

        try {
            if(t.isAlive()) {
                t.join();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Pail.class.getName()).log(Level.SEVERE, null, ex);
        }

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

    private void setupLookAndFeels() {
        HashMap<String, Boolean> installQueue = new HashMap<String, Boolean>();
        installQueue.put("com.jtattoo.plaf.acryl.AcrylLookAndFeel", Boolean.TRUE);
        installQueue.put("com.jtattoo.plaf.hifi.HiFiLookAndFeel", Boolean.TRUE);
        installQueue.put("com.jtattoo.plaf.graphite.GraphiteLookAndFeel", Boolean.TRUE);
        installQueue.put("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel", Boolean.TRUE);
        installQueue.put("com.jtattoo.plaf.aero.AeroLookAndFeel", Boolean.TRUE);
        installQueue.put("com.jtattoo.plaf.fast.FastLookAndFeel", Boolean.TRUE);
        for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if (installQueue.keySet().contains(info.getClassName())) {
                installQueue.put(info.getClassName(), Boolean.FALSE);
            }
        }
        for (String n : installQueue.keySet()) {
            if (installQueue.get(n)) {
                try {
                    UIManager.installLookAndFeel(((LookAndFeel) Class.forName(n).newInstance()).getName(), n);
                } catch (Exception ex) {
                    Logger.getLogger(Pail.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        try {
            LookAndFeel laf = (LookAndFeel) Class.forName(General.getLookAndFeel()).newInstance();
            UIManager.setLookAndFeel(laf);
            UIManager.getLookAndFeelDefaults().put("ClassLoader", getClass().getClassLoader());
        } catch (Exception ex) {
            Logger.getLogger(Pail.class.getName()).log(Level.SEVERE, null, ex);
        }
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
                    while((str = reader.readLine()) != null) {
                        try {
                            if(str.length() > 0) {
                                String[] s = str.split(" ");
                                output.append(Color.GRAY, true, s[0]);
                                output.append(Color.GRAY, true, " " + s[1]);

                                Color color = Color.BLACK;
                                Level lv = Level.parse(s[2].substring(1, s[2].length() - 1));
                                if(lv == Level.INFO) {
                                    color = Color.BLUE;
                                } else if(lv == Level.WARNING) {
                                    color = Color.ORANGE;
                                } else if(lv == Level.SEVERE) {
                                    color = Color.RED;
                                } else if(lv == Level.CONFIG) {
                                    color = Color.GREEN;
                                }
                                output.append(color, " [" + lv.toString() + "] ");

                                if(UIManager.getLookAndFeel().getName().equals("HiFi")) {
                                    color = Color.WHITE;
                                } else {
                                    color = Color.BLACK;
                                }
                                StringBuilder sb = new StringBuilder();
                                for(int i = 3; i < s.length; i++) {
                                    if(s[i].startsWith("[") && s[i].contains("]")) {
                                        output.append(color, sb.toString());
                                        sb = new StringBuilder();
                                        output.append(color, true, s[i] + " ");
                                    } else {
                                        sb.append(s[i]);
                                        sb.append(" ");
                                    }
                                }
                                output.append(color, sb.toString() + "\n");
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
            int confirm = JOptionPane.showConfirmDialog(getMainWindow(), Util.translate("Are you sure you want to close the Pail window?"),
                    Util.translate("Confirm Close"), JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
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

    public class InitMain implements Runnable {
        public void run() {
            main = new MainWindow();
            log.addHandler(handler);

            PailLogHandler mainHandler = new PailLogHandler(
                Util.getServerControls().getServerConsolePanel().getConsoleOutput());
            mainHandler.setLevel(Level.ALL);
            log.addHandler(mainHandler);

            getMainWindow().setIconImage(PAIL_ICON);
            getMainWindow().setTitle(Util.translate("Pail Server Manager"));
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
                Util.getServerControls().addPlayer(p.getName());
            }

            /*if(General.isAutoUpdate() && UpdateHandler.isUpToDate() != null
                    && !UpdateHandler.isUpToDate()) {
                new UpdateView().setVisible(true);
            }*/
        }
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
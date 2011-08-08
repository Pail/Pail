package me.escapeNT.pail;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.SwingUtilities;

import me.escapeNT.pail.GUIComponents.MainWindow;
import me.escapeNT.pail.util.PanelConfig;
import me.escapeNT.pail.util.ServerReadyListener;
import me.escapeNT.pail.util.Util;

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
    public static final String PLUGIN_VERSION = "0.2";

    private MainWindow main;

    @Override
    public void onEnable() {
        log.addHandler(new ServerReadyListener());
        Util.setPlugin(this);

        new Thread(new Runnable() {
            public void run() {
                main = new MainWindow();

                PailLogHandler handler = new PailLogHandler(
                    Util.getServerControls().getServerConsolePanel().getConsoleOutput());
                handler.setLevel(Level.ALL);
                log.addHandler(handler);

                getMainWindow().setTitle("Pail Server Manager");
                getMainWindow().setResizable(false);
                getMainWindow().addWindowListener(new WindowCloseListener());
                getMainWindow().setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                getMainWindow().pack();
                getMainWindow().setSize(860, 535);
                getMainWindow().setLocationRelativeTo(null);
                loadState();
                getMainWindow().setVisible(true);
                getMainWindow().requestFocus();

                Util.setServerControls(getMainWindow().getServerControls());   

                for(Player p : getServer().getOnlinePlayers()) {
                    Util.getServerControls().getListModel().addElement(p.getName());
                }       
            }
        }, "Pail").start();

        PluginManager pm = this.getServer().getPluginManager();
        PailPlayerListener playerListener = new PailPlayerListener();
        pm.registerEvent(Type.PLAYER_JOIN, playerListener, Priority.Monitor, this);
        pm.registerEvent(Type.PLAYER_KICK, playerListener, Priority.Monitor, this);
        pm.registerEvent(Type.PLAYER_QUIT, playerListener, Priority.Monitor, this);

        Util.log(PLUGIN_NAME + " " + PLUGIN_VERSION + " Enabled");
    }
    
    @Override
    public void onDisable() {
        getMainWindow().dispose();
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
        new PailPersistance().save(getMainWindow().getLocationOnScreen(),
                Util.getServerControls().getServerConsolePanel().getConsoleOutput().getText());
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
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Util.getServerControls().getServerConsolePanel().getConsoleOutput().setText(prev.getConsoleText());
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
}
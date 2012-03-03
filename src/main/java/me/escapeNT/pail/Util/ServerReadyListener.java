package me.escapeNT.pail.Util;

import java.util.logging.Handler;
import java.util.logging.LogRecord;
import javax.swing.SwingUtilities;

import me.escapeNT.pail.Pail;
import me.escapeNT.pail.GUIComponents.SettingsPanel;

import org.bukkit.Bukkit;

/**
 * Simple log handler to listen when the server is ready.
 * @author escapeNT
 */
public class ServerReadyListener extends Handler {
    public static SettingsPanel settings;

    @Override
    public void publish(LogRecord record) {
        if(record.getMessage().contains("Reload complete.")
                || (record.getMessage().contains("Done") && record.getMessage().contains("help"))) {
            settings = new SettingsPanel();

            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    Util.getPlugin().loadInterfaceComponent("Settings", settings);
                    Util.getPlugin().getMainWindow().loadPanels();
                    Util.getPlugin().getMainWindow().setVisible(true);
                }
            });
            Bukkit.getLogger().removeHandler(Pail.handler);
        }
    }

    @Override
    public void flush() {}

    @Override
    public void close() throws SecurityException {}

}
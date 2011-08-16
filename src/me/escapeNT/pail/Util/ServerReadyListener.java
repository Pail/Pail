package me.escapeNT.pail.util;

import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;

import me.escapeNT.pail.Pail;
import me.escapeNT.pail.GUIComponents.SettingsPanel;

/**
 * Simple log handler to listen when the server is ready.
 * @author escapeNT
 */
public class ServerReadyListener extends Handler {

    private SettingsPanel settings;

    @Override
    public void publish(LogRecord record) {
        if(record.getMessage().contains("Reload complete.")
                || (record.getMessage().contains("Done") && record.getMessage().contains("help"))) {
            settings = new SettingsPanel();

            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    Util.getPlugin().loadInterfaceComponent("Settings", getSettings());
                    Util.getPlugin().getMainWindow().loadPanels();
                }
            });
            Logger.getLogger("Minecraft").removeHandler(Pail.handler);
        }
    }

    @Override
    public void flush() {}

    @Override
    public void close() throws SecurityException {}

    /**
     * @return the settings
     */
    public SettingsPanel getSettings() {
        return settings;
    }
}
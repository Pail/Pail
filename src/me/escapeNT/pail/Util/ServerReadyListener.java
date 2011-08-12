package me.escapeNT.pail.util;

import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import me.escapeNT.pail.Pail;
import org.bukkit.Bukkit;

import me.escapeNT.pail.GUIComponents.SettingsPanel;

/**
 * Simple log handler to listen when the server is ready.
 * @author escapeNT
 */
public class ServerReadyListener extends Handler {

    @Override
    public void publish(LogRecord record) {
        if(record.getMessage().contains("Reload complete.")
                || (record.getMessage().contains("Done") && record.getMessage().contains("help"))) {
            SettingsPanel settings = new SettingsPanel();
            Util.getPlugin().loadInterfaceComponent("Settings", settings);
            Util.getPlugin().getMainWindow().loadPanels();
            Logger.getLogger("Minecraft").removeHandler(((Pail)Bukkit.getServer().getPluginManager().getPlugin("Pail")).handler);
        }
    }

    @Override
    public void flush() {}

    @Override
    public void close() throws SecurityException {}
}
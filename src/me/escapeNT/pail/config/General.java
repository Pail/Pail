
package me.escapeNT.pail.config;

import me.escapeNT.pail.util.Util;

import org.bukkit.util.config.Configuration;

/**
 * Class for handling general configuration.
 * @author escapeNT
 */
public class General {

    public static final Configuration config = Util.getPlugin().getConfiguration();

    private static boolean autoUpdate;

    /**
     * Loads the configuration.
     */
    public static void load() {
        setAutoUpdate(config.getBoolean("Autoupdate", true));
        config.save();
    }

    /**
     * Saves the configuration.
     */
    public static void save() {
        config.setProperty("Autoupdate", autoUpdate);
        config.save();
    }

    /**
     * @return the autoUpdate
     */
    public static boolean isAutoUpdate() {
        return autoUpdate;
    }

    /**
     * @param aAutoUpdate the autoUpdate to set
     */
    public static void setAutoUpdate(boolean aAutoUpdate) {
        autoUpdate = aAutoUpdate;
    }
}
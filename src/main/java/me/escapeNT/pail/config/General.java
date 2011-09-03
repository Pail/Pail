
package me.escapeNT.pail.config;

import com.google.api.translate.Language;

import javax.swing.UIManager;

import me.escapeNT.pail.Util.Util;

import org.bukkit.util.config.Configuration;

/**
 * Class for handling general configuration.
 * @author escapeNT
 */
public class General {

    public static final Configuration config = Util.getPlugin().getConfiguration();

    private static boolean autoUpdate;
    private static String lookAndFeel;
    private static Language lang;

    /**
     * Loads the configuration.
     */
    public static void load() {
        setAutoUpdate(config.getBoolean("Autoupdate", true));
        setLookAndFeel(config.getString("Skin", UIManager.getSystemLookAndFeelClassName()));
        setLang(Language.fromString(config.getString("Language", Language.ENGLISH.toString())));
    }

    /**
     * Saves the configuration.
     */
    public static void save() {
        config.setProperty("Autoupdate", autoUpdate);
        config.setProperty("Skin", lookAndFeel);
        config.setProperty("Language", lang.toString());
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

    /**
     * @return the lookAndFeel
     */
    public static String getLookAndFeel() {
        return lookAndFeel;
    }

    /**
     * @param aLookAndFeel the lookAndFeel to set
     */
    public static void setLookAndFeel(String aLookAndFeel) {
        lookAndFeel = aLookAndFeel;
    }

    /**
     * @return the language
     */
    public static Language getLang() {
        return lang;
    }

    /**
     * @param aLang the lang to set
     */
    public static void setLang(Language aLang) {
        lang = aLang;
    }
}
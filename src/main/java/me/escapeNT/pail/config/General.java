package me.escapeNT.pail.config;

import com.google.api.translate.Language;

import javax.swing.UIManager;

import me.escapeNT.pail.Util.Util;

/**
 * Class for handling general configuration.
 * @author escapeNT
 */
public class General {

    /**
     * Loads the configuration.
     */
    public static void load() {
        defaults();
        setAutoUpdate(isAutoUpdate());
        setLookAndFeel(getLookAndFeel());
        loadConfigLang();
    }

    private static void defaults() {
        Util.getPlugin().getConfig().addDefault("Autoupdate", true);
        Util.getPlugin().getConfig().addDefault("Skin", UIManager.getSystemLookAndFeelClassName());
        Util.getPlugin().getConfig().addDefault("Language", Language.ENGLISH.toString());
    }

    /**
     * Saves the configuration.
     */
    public static void save() {
        Util.getPlugin().saveConfig();
    }

    /**
     * @return the autoUpdate
     */
    public static boolean isAutoUpdate() {
        return Util.getPlugin().getConfig().getBoolean("Autoupdate");
    }

    /**
     * @param aAutoUpdate the autoUpdate to set
     */
    public static void setAutoUpdate(boolean autoUpdate) {
        Util.getPlugin().getConfig().set("Autoupdate", autoUpdate);
    }

    /**
     * @return the lookAndFeel
     */
    public static String getLookAndFeel() {
        return Util.getPlugin().getConfig().getString("Skin");
    }

    /**
     * @param aLookAndFeel the lookAndFeel to set
     */
    public static void setLookAndFeel(String lookAndFeel) {
        Util.getPlugin().getConfig().set("Skin", lookAndFeel);
    }

    /**
     * @return the language
     */
    private static Language lang;
    public static Language getLang() {
        return lang;
    }

    private static void loadConfigLang() {
        String value = Util.getPlugin().getConfig().getString("Language");
        Language lang = Language.fromString(value);
        if (lang == null) {
            lang = Language.ENGLISH;
        }
        setLang(lang);
    }

    /**
     * @param aLang the lang to set
     */
    public static void setLang(Language aLang) {
        if (aLang == null) return;
        Util.getPlugin().getConfig().set("Language", aLang.toString());
        lang = aLang;
    }
}
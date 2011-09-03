
package me.escapeNT.pail.Util;

import com.google.api.translate.Language;
import com.google.api.translate.Translate;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.StringReader;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import me.escapeNT.pail.GUIComponents.FileMenu;
import me.escapeNT.pail.GUIComponents.ServerControlPanel;
import me.escapeNT.pail.Pail;
import me.escapeNT.pail.config.General;

/**
 * Various static utility methods.
 * @author escapeNT
 */
public class Util {
    
    private static final Logger log = Logger.getLogger("Minecraft");

    private static Pail plugin;
    private static HashMap<String, JPanel> interfaceComponents = new HashMap<String, JPanel>();
    private static ServerControlPanel serverControls;
    private static FileMenu fileMenu;

    /**
     * Reads the last number of specified lines in a file.
     * @param file The file to read from.
     * @param numLines The number of lines to read from the end.
     * @return The last n lines from the file.
     */
    public static String[] readLastLines(File file, int numLines) {
        String[] lines = null;
        try {
            RandomAccessFile fileReader = new RandomAccessFile(file, "r");
            fileReader.seek(fileReader.length());
        } catch (IOException ex) {
            log.throwing("Util", "readLastLines", ex);
        }
        return lines;
    }

    /**
     * Logs an info message from the plugin to the console.
     * @param message The message to send.
     */
    public static void log(Object message) {
        StringBuilder s = new StringBuilder();
        s.append("[");
        s.append(Pail.PLUGIN_NAME);
        s.append("] ");
        s.append(message.toString());
        log.log(Level.INFO, s.toString());
    }

    /**
     * Logs a message from the plugin to the console with the specified level..
     * @param level The log level.
     * @param message The message to send.
     */
    public static void log(Level level, Object message) {
        StringBuilder s = new StringBuilder();
        s.append("[");
        s.append(Pail.PLUGIN_NAME);
        s.append("] ");
        s.append(message.toString());
        log.log(level, s.toString());
    }

    /**
     * Saves the specified text to a file.
     * @param text The text to save.
     * @param saveTo The file to write the text to.
     */
    public static void saveTextFile(String text, File saveTo) throws IOException {
        PrintWriter out = new PrintWriter(new FileOutputStream(saveTo));
        BufferedReader reader = new BufferedReader(new StringReader(text));
        String str;
        while ((str = reader.readLine()) != null) {
            out.println(str);
        }
        reader.close();
        out.close();
    }

    /**
     * Translates the text to the configured language.
     * @param text The text to translate.
     * @return The translated text.
     */
    public static String translate(String text) {
        if(General.getLang() == Language.ENGLISH) {
            return text;
        }
        try {
            return Translate.execute(text, Language.ENGLISH, General.getLang());
        } catch (Exception ex) {
            return text;
        }
    }

    /**
     * @return the plugin
     */
    public static Pail getPlugin() {
        return plugin;
    }

    /**
     * @param aPlugin the plugin to set
     */
    public static void setPlugin(Pail aPlugin) {
        plugin = aPlugin;
    }

    /**
     * @return the interfaceComponents
     */
    public static HashMap<String, JPanel> getInterfaceComponents() {
        return interfaceComponents;
    }

    /**
     * @return the serverControls
     */
    public static ServerControlPanel getServerControls() {
        return serverControls;
    }

    /**
     * @param aServerControls the serverControls to set
     */
    public static void setServerControls(ServerControlPanel aServerControls) {
        serverControls = aServerControls;
    }

    /**
     * @return the fileMenu
     */
    public static FileMenu getFileMenu() {
        return fileMenu;
    }

    /**
     * @param aFileMenu the fileMenu to set
     */
    public static void setFileMenu(FileMenu aFileMenu) {
        fileMenu = aFileMenu;
    }

    public static void translateTextComponent(JCheckBox c) {
        c.setText(Util.translate(c.getText()));
    }

    public static void translateTextComponent(JLabel c) {
        c.setText(Util.translate(c.getText()));
    }

    public static void translateTextComponent(JButton c) {
        c.setText(Util.translate(c.getText()));
    }
}
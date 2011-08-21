
package me.escapeNT.pail.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import me.escapeNT.pail.util.Util;

/**
 * Class to store the activation status of third party panels.
 * @author escapeNT
 */
public class PanelConfig {

    private static Map<String, Boolean> panelsActivated = new HashMap<String, Boolean>();

    public static final File file = new File(Util.getPlugin().getDataFolder().getPath() + File.separator + "panels.dat");

    public static void save() {
        Util.getPlugin().getDataFolder().mkdir();
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(panelsActivated);
            oos.close();
        } catch (IOException ex) {
            Util.log(Level.SEVERE, ex.toString());
        }
    }

    public static void load() {
        if(!file.exists()) {
            save();
        }
         try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            panelsActivated = (Map<String, Boolean>)ois.readObject();
            ois.close();
        } catch (Exception ex) {
            Util.log(Level.SEVERE, ex.toString());
        }
    }

    /**
     * @return the panels
     */
    public static Map<String, Boolean> getPanelsActivated() {
        return panelsActivated;
    }
}
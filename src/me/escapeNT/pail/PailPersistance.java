
package me.escapeNT.pail;

import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.logging.Level;

import me.escapeNT.pail.Util.Util;


/**
 * Saves data relating to the current state of the plugin between reloads.
 * @author escapeNT
 */
public class PailPersistance implements Serializable {

    private Point windowLocation;
    private String consoleText;

    public static final File file = new File("pailPersist.tmp");

    /**
     * Saves the given information to a temporary file.
     * @param loc The current window location.
     * @param console The full console text since the server was started.
     */
    public void save(Point loc, String console) {
        this.windowLocation = loc;
        this.consoleText = console;
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
            oos.close();
        } catch (IOException ex) {
            Util.log(Level.SEVERE, ex.toString());
        }
    }

    /**
     * Loads the saved data from file.
     */
    public PailPersistance load() {
        PailPersistance pp = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            pp = (PailPersistance)ois.readObject();
            ois.close();
            file.delete();
        } catch (Exception ex) {
            Util.log(Level.SEVERE, ex.toString());
        }
        return pp;
    }

    /**
     * @return the windowLocation
     */
    protected Point getWindowLocation() {
        return windowLocation;
    }

    /**
     * @return the consoleText
     */
    protected String getConsoleText() {
        return consoleText;
    }
}
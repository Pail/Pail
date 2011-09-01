package me.escapeNT.pail.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import me.escapeNT.pail.Util.Util;
import me.escapeNT.pail.Util.Waypoint;

/**
 * Class for handling saved waypoints.
 * @author escapeNT
 */
public class WaypointConfig {

    public static final File file = new File(Util.getPlugin().getDataFolder(), "waypoints.dat");

    private static List<Waypoint> waypoints = new ArrayList<Waypoint>();

    public static void save() {
        Util.getPlugin().getDataFolder().mkdir();
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(getWaypoints());
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
            waypoints = (List<Waypoint>)ois.readObject();
            ois.close();
        } catch (Exception ex) {
            Util.log(Level.SEVERE, ex.toString());
        }
    }

    /**
     * @return the waypoints
     */
    public static List<Waypoint> getWaypoints() {
        return waypoints;
    }
}


package me.escapeNT.pail.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import me.escapeNT.pail.util.Util;

/**
 * Class intended to handle the vanilla server configuration.
 * @author escapeNT
 */
public class ServerConfigHandler {

    public static final File file = new File("server.properties");

    private static HashMap<String, String> props = new HashMap<String, String>();

    /**
     * Loads the current server configuration.
     */
    public static void load() {
        try {
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);

            String line;
            while((line = reader.readLine()) != null) {
                if(!line.startsWith("#")) {
                    String[] s = line.split("=");
                    if(s.length == 1) {
                        props.put(s[0], "");
                    }
                    else {
                        props.put(s[0], s[1]);
                    }
                }
            }
            
            reader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Saves the given configuration to file.
     * @param properties The server properties.
     */
    public static void save(HashMap<String, String> properties) {
        file.delete();
        try {
            FileWriter fw = new FileWriter(file);
            PrintWriter writer = new PrintWriter(fw);

            writer.println("#Minecraft server properties");
            writer.println("#" + new Date(System.currentTimeMillis()).toString());

            for(String p : properties.keySet()) {
                writer.println(p + "=" + properties.get(p));
            }

            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Returns the server properties.
     * @return the props The server properties read from server.properties.
     */
    public static HashMap<String, String> getProperties() {
        return props;
    }
}
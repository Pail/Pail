
package me.escapeNT.pail.Util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import me.escapeNT.pail.GUIComponents.UpdateView;
import me.escapeNT.pail.Pail;

import org.bukkit.Bukkit;

/**
 * Class containing static methods for checking and downloading updates for pail.
 * @author escapeNT
 */
public class UpdateHandler {

    private static final String version = Pail.PLUGIN_VERSION;
    private static String currentVersion;

    public static final File updateFile = new File(new File(Bukkit.getServer().getUpdateFolder()).getPath(), "Pail.jar");

    /**
     * Checks if the current Pail version is up to date.
     * @return True if this Pail's version number is greater than or equal to the current version,
     * false if it is lower, or null if the server could not be contacted.
     */
    public static Boolean isUpToDate() {
        Boolean upToDate = true;
        try {
            URL url = new URL("http://pail.hostzi.com/");
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            currentVersion = in.readLine();
            in.close();

            if(compareVersions(version, currentVersion) == -1) {
                upToDate = false;
            }

        } catch (Exception ex) {
            upToDate = null;
        }
        return upToDate;
    }
    
    /**
     * Gets the list of changes in the latest version.
     * @return The change list.
     */
    public static List<String> getChanges() {
        List<String> changes = new ArrayList<String>();
        try {
            URL url = new URL("http://pail.hostzi.com/");
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            in.readLine();

            String ln;
            while((ln = in.readLine()) != null) {
                changes.add(ln);
            }

            in.close();
        } catch (Exception ex) {
        }
        return changes;
    }

    /**
     * Downloads the latest version to the updates folder.
     * @param dialog
     * @throws IOException
     */
    public static void downloadLatest(final UpdateView dialog) throws IOException {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                dialog.getProgressLabel().setText("Starting download...");
            }
        });

        dialog.getIgnore().setEnabled(false);
        dialog.getUpdate().setEnabled(false);

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                dialog.getProgress().setIndeterminate(true);
            }
        });

        URL url = new URL("http://bit.ly/pF51cW");
        touchLink(url);

        if (!updateFile.getParentFile().exists()) {
            updateFile.getParentFile().mkdir();
        }
        if (updateFile.exists()) {
            updateFile.delete();
        }
        updateFile.createNewFile();
        final int size = url.openConnection().getContentLength();

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                dialog.getProgressLabel().setText("Downloading " + updateFile.getName() + " (" + size / 1024 + "kb) ...");
            }
        });

        final InputStream in = url.openStream();
        final OutputStream out = new BufferedOutputStream(new FileOutputStream(updateFile));
        final byte[] buffer = new byte[1024];
        int len, downloaded = 0;

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                dialog.getProgress().setIndeterminate(false);
            }
        });
        
        while ((len = in.read(buffer)) >= 0) {
            out.write(buffer, 0, len);
            downloaded += len;
            final int d = downloaded;
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    dialog.getProgressLabel().setText((int)((double)d / (double)size * 100d) + "%");
                    dialog.getProgress().setValue((int)((double)d / (double)size * 100d));
                }
            });
        }
        in.close();
        out.close();

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                dialog.getProgress().setValue(dialog.getProgress().getMaximum());
                dialog.getProgressLabel().setText("Download complete");
            }
        });

        int reload = JOptionPane.showConfirmDialog(dialog, "Download complete. Reload now?",
                "Success", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
        dialog.dispose();
        
        if(reload == JOptionPane.YES_OPTION) {          
            Bukkit.getServer().dispatchCommand(Util.getConsoleSender(), "reload");
        }
    }

    private static int compareVersions(String str1, String str2) {
        String[] vals1 = str1.split("\\.");
        String[] vals2 = str2.split("\\.");
        int i = 0;
        while(i < vals1.length && i < vals2.length && vals1[i].equals(vals2[i])) {
            i++;
        }

        if(i < vals1.length && i < vals2.length) {
            int diff = new Integer(vals1[i]).compareTo(new Integer(vals2[i]));
            return diff<0?-1:diff==0?0:1;
        }
        return vals1.length<vals2.length?-1:vals1.length==vals2.length?0:1;
    }

    private static void touchLink(URL url) {
        try {
            HttpURLConnection con = (HttpURLConnection)(url.openConnection());
            System.setProperty("http.agent", "");
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/534.30 (KHTML, like Gecko) Chrome/12.0.742.100 Safari/534.30");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String str = "";
            while((str = in.readLine()) != null);
            in.close();
        }
        catch(Exception e) {}
    }

    /**
     * @return the currentVersion
     */
    public static String getCurrentVersion() {
        return currentVersion;
    }
}
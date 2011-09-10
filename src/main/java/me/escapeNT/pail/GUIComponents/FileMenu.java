
package me.escapeNT.pail.GUIComponents;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.text.Document;

import me.escapeNT.pail.Util.Localizable;
import me.escapeNT.pail.Util.Util;

import org.bukkit.Bukkit;
import org.bukkit.World;

/**
 * Class representing the File dropdown menu.
 * @author escapeNT
 */
public class FileMenu extends JMenu implements Localizable {

    private JMenuItem saveSelection;

    public FileMenu() {
        super(Util.translate("File"));
        setMnemonic('F');

        JMenuItem about = new JMenuItem(Util.translate("About"));
        about.setIcon(new ImageIcon(getClass().getResource("images/about.png")));
        about.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AboutView().setVisible(true);
            }
        });
        add(about);

        JMenuItem saveConsole = new JMenuItem(Util.translate("Save console text..."));
        saveConsole.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK + InputEvent.SHIFT_DOWN_MASK));
        saveConsole.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                fc.setSelectedFile(new File("console.log"));
                int res = fc.showSaveDialog(null);
                if(res == JFileChooser.APPROVE_OPTION) {
                    File saveTo = fc.getSelectedFile();
                    Document doc = Util.getServerControls().getServerConsolePanel().getConsoleOutput().getDocument();
                    try {
                        String text = doc.getText(0, doc.getLength());
                        Util.saveTextFile(text, saveTo);
                    } catch(Exception ex) {
                        Logger.getLogger(FileMenu.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        add(saveConsole);

        saveSelection = new JMenuItem(Util.translate("Save selected text..."));
        saveSelection.setEnabled(false);
        saveSelection.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                fc.setSelectedFile(new File("console.log"));
                String text = Util.getServerControls().getServerConsolePanel().getConsoleOutput().getSelectedText();             
                int res = fc.showSaveDialog(null);
                if(res == JFileChooser.APPROVE_OPTION && text != null) {
                    File saveTo = fc.getSelectedFile();       
                    try {
                        Util.saveTextFile(text, saveTo);
                    } catch(Exception ex) {
                        Logger.getLogger(FileMenu.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        add(saveSelection);

        JMenuItem backup = new JMenuItem(Util.translate("Backup world..."));
        backup.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<String> worlds = new ArrayList<String>();
                for(World w :  Bukkit.getServer().getWorlds()) {
                    worlds.add(w.getName());
                }

                final String worldName = (String)JOptionPane.showInputDialog(Util.getPlugin().getMainWindow(),
                        Util.translate("What world do you want to back up?"),
                        Util.translate("Choose world"), JOptionPane.QUESTION_MESSAGE, null,
                        worlds.toArray(), worlds.get(0));
                
                final File worldFolder = new File(worldName);
                final File backupFolder = new File(Util.getPlugin().getDataFolder(), "backups");
                final File backup = new File(backupFolder, worldName
                        + new SimpleDateFormat("'@'MM:dd:yy_hh.mm.ss").format(new Date(System.currentTimeMillis())) + ".zip");
                if(!backupFolder.exists()) {
                    backupFolder.mkdir();
                }
                Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(Util.getPlugin(), new Runnable() {
                    public void run() {
                        Util.log("Starting backup for " + worldName);
                        long start = System.currentTimeMillis();
                        Util.zipDir(worldFolder, backup);
                        int seconds = (int)(System.currentTimeMillis() - start) / 1000;
                        Util.log("Backup completed in " + seconds + (seconds==1?" second":" seconds") + " for " + worldName);
                    }
                });
            }
        });
        add(backup);

        Util.setFileMenu(this);
    }

    /**
     * @return the saveSelection
     */
    public JMenuItem getSaveSelection() {
        return saveSelection;
    }

    public void translateComponent() {}
}
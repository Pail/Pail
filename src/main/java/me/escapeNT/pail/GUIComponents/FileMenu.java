
package me.escapeNT.pail.GUIComponents;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.text.Document;

import me.escapeNT.pail.Util.Localizable;
import me.escapeNT.pail.Util.Util;

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

package me.escapeNT.pail.GUIComponents;

import java.awt.Desktop;
import java.awt.Desktop.Action;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.net.URI;
import java.util.logging.Level;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import me.escapeNT.pail.Pail;
import me.escapeNT.pail.Util.Localizable;
import me.escapeNT.pail.Util.Util;

/**
 * Class representing the Help dropdown menu.
 * @author escapeNT
 */
public class HelpMenu extends JMenu implements Localizable {

    public HelpMenu() {
        super(Util.translate("Help"));
        setMnemonic('H');

        JMenuItem thread = new JMenuItem(Util.translate("Plugin thread"));
        thread.setIcon(new ImageIcon(getClass().getResource("images/help.png")));
        thread.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_MASK));
        if(!Desktop.getDesktop().isSupported(Action.BROWSE)) {
            thread.setEnabled(false);
        }
        thread.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI(Pail.PLUGIN_THREAD));
                } catch(Exception ex) {
                    Util.log(Level.WARNING, Util.translate("Could not open plugin thread."));
                }
            }
        });
        add(thread);
    }

    public void translateComponent() {}
}
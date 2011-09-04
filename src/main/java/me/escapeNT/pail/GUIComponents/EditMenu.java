
package me.escapeNT.pail.GUIComponents;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import me.escapeNT.pail.Util.Localizable;
import me.escapeNT.pail.Util.ScrollableTextArea;
import me.escapeNT.pail.Util.Util;

/**
 * Class representing the Edit dropdown menu.
 * @author escapeNT
 */
public class EditMenu extends JMenu implements Localizable {

    public static boolean findOpen = false;

    public EditMenu() {
        super(Util.translate("Edit"));
        setMnemonic('E');

        JMenuItem selectall = new JMenuItem(Util.translate("Select all"));
        selectall.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
        selectall.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ScrollableTextArea a = Util.getServerControls().getServerConsolePanel().getConsoleOutput();
                a.selectAll();
            }
        });
        add(selectall);

        JMenuItem find = new JMenuItem(Util.translate("Find"));
        find.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK));
        find.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(!findOpen) {
                    new FindView().setVisible(true);
                    findOpen = true;
                }
            }
        });
        add(find);

        JMenuItem clear = new JMenuItem(Util.translate("Clear"));
        clear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
            }
        });
        add(clear);
    }

    public void translateComponent() {}
}

package me.escapeNT.pail.GUIComponents;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import me.escapeNT.pail.Util.Localizable;

import me.escapeNT.pail.Util.Util;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;

/**
 * Class representing the Server dropdown menu.
 * @author escapeNT
 */
public class ServerMenu extends JMenu implements Localizable {

    public ServerMenu() {
        super(Util.translate("Server"));
        setMnemonic('S');

        JMenuItem reload = new JMenuItem(Util.translate("Reload"));
        reload.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK));
        reload.setIcon(new ImageIcon(getClass().getResource("images/reload.png")));
        reload.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Util.getPlugin().saveState();
                Bukkit.getServer().dispatchCommand(new ConsoleCommandSender(Bukkit.getServer()), "reload");
            }
        });

        JMenuItem stop = new JMenuItem(Util.translate("Stop"));
        stop.setIcon(new ImageIcon(getClass().getResource("images/stop.png")));
        stop.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));
        stop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Bukkit.getServer().dispatchCommand(new ConsoleCommandSender(Bukkit.getServer()), "stop");
            }
        });

        JMenuItem save = new JMenuItem(Util.translate("Save All"));
        save.setIcon(new ImageIcon(getClass().getResource("images/save.png")));
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Bukkit.getServer().dispatchCommand(new ConsoleCommandSender(Bukkit.getServer()), "save-all");
            }
        });

        add(save);
        add(reload);
        add(stop);
    }

    public void translateComponent() {}
}
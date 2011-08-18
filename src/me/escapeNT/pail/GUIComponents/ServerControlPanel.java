
package me.escapeNT.pail.GUIComponents;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.border.TitledBorder;

import me.escapeNT.pail.util.Util;
import org.bukkit.Bukkit;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

/**
 * Panel containing the basic server controls.
 * @author escapeNT
 */
public final class ServerControlPanel extends javax.swing.JPanel {

    private DefaultListModel listModel = new DefaultListModel();
    private JPopupMenu playerMenu = null;

    /** Creates new form ServerControlPanel */
    public ServerControlPanel() {
        initComponents();
        Util.setServerControls(this);
        playerList.setModel(listModel);
        jScrollPane1.setBorder(new TitledBorder("Players"));

        // Load images
        ImageIcon plus = new ImageIcon(getClass().getResource("images/plus.png"));
        ImageIcon arrow = new ImageIcon(getClass().getResource("images/right.png"));
        ImageIcon kill = new ImageIcon(getClass().getResource("images/kill.png"));
        ImageIcon up = new ImageIcon(getClass().getResource("images/up.png"));
        ImageIcon down = new ImageIcon(getClass().getResource("images/down.png"));
        ImageIcon msg = new ImageIcon(getClass().getResource("images/msg.png"));
        ImageIcon kickico = new ImageIcon(getClass().getResource("images/kick.png"));
        ImageIcon banico = new ImageIcon(getClass().getResource("images/ban.png"));
        
        // Construct player popup menu
        playerMenu = new JPopupMenu();

        JMenuItem giveItem = new JMenuItem("Give Item", plus);
        giveItem.addActionListener(new GiveItemListener());
        playerMenu.add(giveItem);

        JMenuItem tele = new JMenuItem("Teleport", arrow);
        tele.addActionListener(new TeleportListener());
        playerMenu.add(tele);

        JMenuItem killPlayer = new JMenuItem("Kill", kill);
        killPlayer.addActionListener(new KillPlayerListener());
        playerMenu.add(killPlayer);

        JMenuItem op = new JMenuItem("Promote to OP", up);
        op.addActionListener(new OpPlayerListener());
        playerMenu.add(op);

        JMenuItem deop = new JMenuItem("Demote from OP", down);
        deop.addActionListener(new DeOpPlayerListener());
        playerMenu.add(deop);
        
        JMenuItem message = new JMenuItem("Message", msg);
        message.addActionListener(new MessagePlayerListener());
        playerMenu.add(message);

        JMenuItem kick = new JMenuItem("Kick", kickico);
        kick.addActionListener(new KickPlayerListener());
        playerMenu.add(kick);

        JMenuItem ban = new JMenuItem("Ban", banico);
        ban.addActionListener(new BanPlayerListener());
        playerMenu.add(ban);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();
        serverConsolePanel = new me.escapeNT.pail.GUIComponents.ServerConsolePanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        playerList = new javax.swing.JList();

        jScrollPane2.setViewportView(jEditorPane1);

        setLayout(null);

        serverConsolePanel.setPreferredSize(new java.awt.Dimension(640, 450));
        add(serverConsolePanel);
        serverConsolePanel.setBounds(10, 10, 640, 450);

        playerList.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        playerList.setFocusable(false);
        playerList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                playerListMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                playerListMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(playerList);

        add(jScrollPane1);
        jScrollPane1.setBounds(660, 10, 170, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void playerListMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_playerListMousePressed
        showPlayerMenu(evt);
    }//GEN-LAST:event_playerListMousePressed

    private void playerListMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_playerListMouseReleased
        showPlayerMenu(evt);
    }//GEN-LAST:event_playerListMouseReleased

    private void showPlayerMenu(MouseEvent e) {        
        if(e.isPopupTrigger()) {
            for(int i = 0; i < listModel.getSize(); i++) {
                Rectangle r = playerList.getCellBounds(i, i);
                if(r.contains(e.getPoint())) {
                    playerList.setSelectedIndex(i);
                    playerMenu.show(playerList, e.getX(), e.getY());
                    break;
                }
            }
        }
    }

    private class GiveItemListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            new GiveItemView((String)playerList.getSelectedValue()).setVisible(true);
        }
    }

    private class TeleportListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            new TeleportPlayerView(playerList.getSelectedValue().toString()).setVisible(true);
        }
    }

    private class KillPlayerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Player p = Bukkit.getServer().getPlayer(playerList.getSelectedValue().toString());
            p.setHealth(0);
        }
    }

    private class OpPlayerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Server s = Bukkit.getServer();
            s.dispatchCommand(new ConsoleCommandSender(s), "op " + playerList.getSelectedValue().toString());
        }
    }

    private class DeOpPlayerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Server s = Bukkit.getServer();
            s.dispatchCommand(new ConsoleCommandSender(s), "deop " + playerList.getSelectedValue().toString());
        }
    }

    private class MessagePlayerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String msg = JOptionPane.showInputDialog(Util.getPlugin().getMainWindow(), "Enter your message:",
                    "Message Player", JOptionPane.QUESTION_MESSAGE);
            if(msg == null) {
                return;
            }
            Bukkit.getServer().getPlayer(playerList.getSelectedValue().toString()).sendMessage(ChatColor.LIGHT_PURPLE
                    + "[Server]" + ChatColor.GRAY + " whispers: " + ChatColor.WHITE + msg);
        }
    }

    private class KickPlayerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Server s = Bukkit.getServer();
            String reason = JOptionPane.showInputDialog(Util.getPlugin().getMainWindow(), "Enter kick reason:",
                    "Kick Player", JOptionPane.QUESTION_MESSAGE);
            if(reason == null) {
                return;
            }
            reason = reason.replaceAll(" ", "_");
            s.dispatchCommand(new ConsoleCommandSender(s), "kick " + playerList.getSelectedValue().toString() + " " + reason);
        }
    }

    private class BanPlayerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Bukkit.getServer().dispatchCommand(new ConsoleCommandSender(
                    Bukkit.getServer()), "ban " + playerList.getSelectedValue().toString());
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList playerList;
    private me.escapeNT.pail.GUIComponents.ServerConsolePanel serverConsolePanel;
    // End of variables declaration//GEN-END:variables

    /**
     * Returns the panel containing the console controls.
     * @return the serverConsolePanel
     */
    public me.escapeNT.pail.GUIComponents.ServerConsolePanel getServerConsolePanel() {
        return serverConsolePanel;
    }

    /**
     * Returns the list model for the player selection list.
     * @return the listModel
     */
    public DefaultListModel getListModel() {
        return listModel;
    }
}
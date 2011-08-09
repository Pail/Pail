
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

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;

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
        sendMessage = new javax.swing.JButton();
        kick = new javax.swing.JButton();
        ban = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        reload = new javax.swing.JButton();
        stop = new javax.swing.JButton();

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
        jScrollPane1.setBounds(660, 10, 170, 270);

        sendMessage.setText("Message Player");
        sendMessage.setFocusable(false);
        sendMessage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendMessageActionPerformed(evt);
            }
        });
        add(sendMessage);
        sendMessage.setBounds(660, 290, 170, 29);

        kick.setText("Kick Player");
        kick.setFocusable(false);
        kick.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kickActionPerformed(evt);
            }
        });
        add(kick);
        kick.setBounds(660, 320, 170, 29);

        ban.setText("Ban Player");
        ban.setFocusable(false);
        ban.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                banActionPerformed(evt);
            }
        });
        add(ban);
        ban.setBounds(660, 350, 170, 29);
        add(jSeparator1);
        jSeparator1.setBounds(660, 380, 170, 12);

        reload.setIcon(new javax.swing.ImageIcon(getClass().getResource("/me/escapeNT/pail/GUIComponents/images/reload.png"))); // NOI18N
        reload.setText("Reload Server");
        reload.setFocusable(false);
        reload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reloadActionPerformed(evt);
            }
        });
        add(reload);
        reload.setBounds(660, 390, 170, 37);

        stop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/me/escapeNT/pail/GUIComponents/images/stop.png"))); // NOI18N
        stop.setText("Stop Server");
        stop.setFocusable(false);
        stop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopActionPerformed(evt);
            }
        });
        add(stop);
        stop.setBounds(660, 430, 170, 37);
    }// </editor-fold>//GEN-END:initComponents

    private void reloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reloadActionPerformed
        Util.getPlugin().saveState();
        Util.getPlugin().getServer().dispatchCommand(new ConsoleCommandSender(Util.getPlugin().getServer()), "reload");
    }//GEN-LAST:event_reloadActionPerformed

    private void stopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopActionPerformed
        Util.getPlugin().getServer().dispatchCommand(new ConsoleCommandSender(Util.getPlugin().getServer()), "stop");
    }//GEN-LAST:event_stopActionPerformed

    private void kickActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kickActionPerformed
        if(playerList.getSelectedValues().length == 0) {
            return;
        }
        String reason = JOptionPane.showInputDialog(this, "Enter kick reason:",
                "Kick Player", JOptionPane.QUESTION_MESSAGE);
        if(reason == null) {
            return;
        }
        reason = reason.replaceAll(" ", "_");
        Object[] players = playerList.getSelectedValues();
        for(Object p : players) {
            Util.getPlugin().getServer().dispatchCommand(new ConsoleCommandSender(
                    Util.getPlugin().getServer()), "kick " + p.toString() + " " + reason);
        }
    }//GEN-LAST:event_kickActionPerformed

    private void banActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_banActionPerformed
        if(playerList.getSelectedValues().length == 0) {
            return;
        }
        Object[] players = playerList.getSelectedValues();
        for(Object p : players) {
            Util.getPlugin().getServer().dispatchCommand(new ConsoleCommandSender(
                    Util.getPlugin().getServer()), "ban " + p.toString());
        }
    }//GEN-LAST:event_banActionPerformed

    private void sendMessageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendMessageActionPerformed
        if(playerList.getSelectedValues().length == 0) {
            return;
        }
        String msg = JOptionPane.showInputDialog(this, "Enter your message:",
                "Message Player", JOptionPane.QUESTION_MESSAGE);
        if(msg == null) {
            return;
        }
        Object[] players = playerList.getSelectedValues();
        for(Object p : players) {
            Util.getPlugin().getServer().getPlayer(p.toString()).sendMessage(ChatColor.LIGHT_PURPLE
                    + "[Server]" + ChatColor.GRAY + " whispers: " + ChatColor.WHITE + msg);
        }
    }//GEN-LAST:event_sendMessageActionPerformed

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
            Server serv = Util.getPlugin().getServer();
            serv.dispatchCommand(new ConsoleCommandSender(serv), "kill " + playerList.getSelectedValue());
        }
    }

    private class OpPlayerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Server s = Util.getPlugin().getServer();
            s.dispatchCommand(new ConsoleCommandSender(s), "op " + playerList.getSelectedValue().toString());
        }
    }

    private class DeOpPlayerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Server s = Util.getPlugin().getServer();
            s.dispatchCommand(new ConsoleCommandSender(s), "deop " + playerList.getSelectedValue().toString());
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ban;
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton kick;
    private javax.swing.JList playerList;
    private javax.swing.JButton reload;
    private javax.swing.JButton sendMessage;
    private me.escapeNT.pail.GUIComponents.ServerConsolePanel serverConsolePanel;
    private javax.swing.JButton stop;
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
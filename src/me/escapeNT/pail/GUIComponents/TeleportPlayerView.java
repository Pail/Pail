
package me.escapeNT.pail.GUIComponents;

import me.escapeNT.pail.util.Util;
import org.bukkit.entity.Player;

/**
 * Player teleport interface.
 * @author escapeNT
 */
public class TeleportPlayerView extends javax.swing.JDialog {

    private String player;

    /** Creates new form TeleportPlayerView */
    public TeleportPlayerView(String player) {
        super(Util.getPlugin().getMainWindow());
        this.player = player;

        for(Player p : Util.getPlugin().getServer().getOnlinePlayers()) {
            if(p != null && !p.getName().equals(player)) {
                locations.addItem(p.getName());
            }
        }

        pack();
        setLocationRelativeTo(Util.getPlugin().getMainWindow());
        setModal(true);
        initComponents();
        teleLabel.setText("Teleport " + player + " to:");
        getRootPane().setDefaultButton(teleport);
        setResizable(false);
        setSize(320, 150);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        teleport = new javax.swing.JButton();
        cancel = new javax.swing.JButton();
        teleLabel = new javax.swing.JLabel();
        locations = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);

        teleport.setText("Teleport");
        teleport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                teleportActionPerformed(evt);
            }
        });
        getContentPane().add(teleport);
        teleport.setBounds(220, 90, 96, 29);

        cancel.setText("Cancel");
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });
        getContentPane().add(cancel);
        cancel.setBounds(130, 90, 86, 29);

        teleLabel.setText("Teleport *** to");
        getContentPane().add(teleLabel);
        teleLabel.setBounds(20, 20, 280, 16);

        locations.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Spawn" }));
        getContentPane().add(locations);
        locations.setBounds(70, 50, 183, 27);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionPerformed
        dispose();
    }//GEN-LAST:event_cancelActionPerformed

    private void teleportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_teleportActionPerformed
        Player teleporter = Util.getPlugin().getServer().getPlayer(player);
        if(locations.getSelectedItem().toString().equals("Spawn")) {
            teleporter.teleport(teleporter.getWorld().getSpawnLocation());
        }
        else {
            Player teleportTo = Util.getPlugin().getServer().getPlayer(locations.getSelectedItem().toString());
            teleporter.teleport(teleportTo);
        }
        dispose();
    }//GEN-LAST:event_teleportActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancel;
    private javax.swing.JComboBox locations;
    private javax.swing.JLabel teleLabel;
    private javax.swing.JButton teleport;
    // End of variables declaration//GEN-END:variables
}
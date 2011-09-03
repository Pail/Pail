package me.escapeNT.pail.GUIComponents;

import me.escapeNT.pail.Util.Localizable;
import me.escapeNT.pail.config.WaypointConfig;
import me.escapeNT.pail.Util.Util;
import me.escapeNT.pail.Util.Waypoint;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Player teleport interface.
 * @author escapeNT
 */
public class TeleportPlayerView extends javax.swing.JDialog implements Localizable {

    private String player;

    /** Creates new form TeleportPlayerView */
    public TeleportPlayerView(String player) {
        super(Util.getPlugin().getMainWindow());
        this.player = player;

        pack();
        setLocationRelativeTo(Util.getPlugin().getMainWindow());
        setModal(true);
        initComponents();
        
        for(Player p : Bukkit.getServer().getOnlinePlayers()) {
            if(p != null && !p.getName().equals(player)) {
                locations.addItem(p.getName());
            }
        }
        
        teleLabel.setText(Util.translate("Teleport " + player + " to:"));
        getRootPane().setDefaultButton(teleport);
        setResizable(false);
        setSize(400, 260);

        for(Waypoint p : WaypointConfig.getWaypoints()) {
            if(p != null) {
                waypoints.addItem(p);
            }
        }

        waypoints.setEnabled(false);

        translateComponent();
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
        jSeparator1 = new javax.swing.JSeparator();
        waypoints = new javax.swing.JComboBox();
        toWaypoint = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);

        teleport.setText("Teleport");
        teleport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                teleportActionPerformed(evt);
            }
        });
        getContentPane().add(teleport);
        teleport.setBounds(290, 190, 96, 29);

        cancel.setText("Cancel");
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });
        getContentPane().add(cancel);
        cancel.setBounds(200, 190, 86, 29);

        teleLabel.setText("Teleport *** to");
        getContentPane().add(teleLabel);
        teleLabel.setBounds(20, 20, 280, 16);

        locations.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Spawn" }));
        getContentPane().add(locations);
        locations.setBounds(90, 50, 200, 27);
        getContentPane().add(jSeparator1);
        jSeparator1.setBounds(40, 90, 300, 10);

        getContentPane().add(waypoints);
        waypoints.setBounds(90, 140, 200, 27);

        toWaypoint.setText("Waypoint");
        toWaypoint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toWaypointActionPerformed(evt);
            }
        });
        getContentPane().add(toWaypoint);
        toWaypoint.setBounds(140, 110, 106, 23);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionPerformed
        dispose();
    }//GEN-LAST:event_cancelActionPerformed

    private void teleportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_teleportActionPerformed
        Player teleporter = Bukkit.getServer().getPlayer(player);
        if(toWaypoint.isSelected()) {
            Waypoint point = (Waypoint)waypoints.getSelectedItem();
            if(point == null) {
                return;
            }
            teleporter.teleport(point.getLocation());
        }
        else {
            if(locations.getSelectedItem().toString().equals("Spawn")) {
            teleporter.teleport(teleporter.getWorld().getSpawnLocation());
            }
            else {
                Player teleportTo = Bukkit.getServer().getPlayer(locations.getSelectedItem().toString());
                teleporter.teleport(teleportTo);
            }
        }
        dispose();
    }//GEN-LAST:event_teleportActionPerformed

    private void toWaypointActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toWaypointActionPerformed
        if(toWaypoint.isSelected()) {
            locations.setEnabled(false);
            waypoints.setEnabled(true);
        }
        else {
            locations.setEnabled(true);
            waypoints.setEnabled(false);
        }
    }//GEN-LAST:event_toWaypointActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancel;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JComboBox locations;
    private javax.swing.JLabel teleLabel;
    private javax.swing.JButton teleport;
    private javax.swing.JCheckBox toWaypoint;
    private javax.swing.JComboBox waypoints;
    // End of variables declaration//GEN-END:variables

    public final void translateComponent() {
        Util.translateTextComponent(cancel);
        Util.translateTextComponent(teleLabel);
        Util.translateTextComponent(teleport);
        Util.translateTextComponent(toWaypoint);
    }
}
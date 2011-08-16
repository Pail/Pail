
package me.escapeNT.pail.GUIComponents;

import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

import me.escapeNT.pail.config.WaypointConfig;
import me.escapeNT.pail.util.Waypoint;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

/**
 * Panel for editing teleport waypoints.
 * @author escapeNT
 */
public class WaypointEditPanel extends javax.swing.JPanel {

    /** Creates new form WaypointEditPanel */
    public WaypointEditPanel() {
        initComponents();
        WaypointConfig.load();

        for(Player p : Bukkit.getServer().getOnlinePlayers()) {
            players.addItem(p.getName());
        }

        for(World w : Bukkit.getServer().getWorlds()) {
            worlds.addItem(w.getName());
        }

        ((DefaultListModel)waypoints.getModel()).clear();
        for(Waypoint wp : WaypointConfig.getWaypoints()) {
            ((DefaultListModel)waypoints.getModel()).addElement(wp);
        }
        waypoints.setSelectedIndex(0);

        updateFields();
    }

    private void updateFields() {
        
        if(waypoints.getSelectedValue() == null) {
            x.setEnabled(false);
            y.setEnabled(false);
            z.setEnabled(false);
            name.setEnabled(false);
            worlds.setEnabled(false);
            save.setEnabled(false);
            players.setEnabled(false);
            playerSubmit.setEnabled(false);
        }
        else {
            x.setEnabled(true);
            y.setEnabled(true);
            z.setEnabled(true);
            name.setEnabled(true);
            worlds.setEnabled(true);
            save.setEnabled(true);
            players.setEnabled(true);
            playerSubmit.setEnabled(true);

            Waypoint p = (Waypoint)waypoints.getSelectedValue();
            Location l = p.getLocation();

            x.setValue(l.getBlockX());
            y.setValue(l.getBlockY());
            z.setValue(l.getBlockZ());
            name.setText(p.getName());
            worlds.setSelectedItem(l.getWorld().getName());
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        waypoints = new javax.swing.JList();
        addWaypoint = new javax.swing.JButton();
        removeWaypoint = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        z = new javax.swing.JSpinner();
        y = new javax.swing.JSpinner();
        jLabel2 = new javax.swing.JLabel();
        x = new javax.swing.JSpinner();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        worlds = new javax.swing.JComboBox();
        save = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        name = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        players = new javax.swing.JComboBox();
        playerSubmit = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();

        setLayout(null);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Waypoints"));
        jPanel1.setLayout(null);

        waypoints.setModel(new DefaultListModel());
        waypoints.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                waypointsValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(waypoints);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(10, 20, 360, 330);

        addWaypoint.setText("+");
        addWaypoint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addWaypointActionPerformed(evt);
            }
        });
        jPanel1.add(addWaypoint);
        addWaypoint.setBounds(320, 360, 50, 40);

        removeWaypoint.setText("-");
        removeWaypoint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeWaypointActionPerformed(evt);
            }
        });
        jPanel1.add(removeWaypoint);
        removeWaypoint.setBounds(260, 360, 50, 40);

        add(jPanel1);
        jPanel1.setBounds(20, 10, 380, 410);

        jLabel1.setText("Z");
        add(jLabel1);
        jLabel1.setBounds(700, 90, 20, 30);

        z.setModel(new javax.swing.SpinnerNumberModel());
        add(z);
        z.setBounds(720, 90, 100, 28);

        y.setModel(new javax.swing.SpinnerNumberModel());
        add(y);
        y.setBounds(580, 90, 100, 28);

        jLabel2.setText("Y");
        add(jLabel2);
        jLabel2.setBounds(560, 90, 20, 30);

        x.setModel(new javax.swing.SpinnerNumberModel());
        add(x);
        x.setBounds(440, 90, 100, 28);

        jLabel3.setText("X");
        add(jLabel3);
        jLabel3.setBounds(420, 90, 20, 30);

        jLabel4.setText("World");
        add(jLabel4);
        jLabel4.setBounds(420, 150, 36, 20);

        add(worlds);
        worlds.setBounds(460, 140, 360, 40);

        save.setText("Save Waypoint");
        save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveActionPerformed(evt);
            }
        });
        add(save);
        save.setBounds(690, 380, 130, 29);

        jLabel5.setText("Name");
        add(jLabel5);
        jLabel5.setBounds(420, 36, 36, 20);
        add(name);
        name.setBounds(470, 30, 350, 28);

        jLabel6.setText("Use player location:");
        add(jLabel6);
        jLabel6.setBounds(420, 250, 130, 16);

        add(players);
        players.setBounds(420, 270, 310, 30);

        playerSubmit.setText("Go");
        playerSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playerSubmitActionPerformed(evt);
            }
        });
        add(playerSubmit);
        playerSubmit.setBounds(740, 270, 75, 29);
        add(jSeparator1);
        jSeparator1.setBounds(430, 210, 380, 10);
    }// </editor-fold>//GEN-END:initComponents

    private void addWaypointActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addWaypointActionPerformed
        Waypoint point = new Waypoint("waypoint", new Location(Bukkit.getServer().getWorlds().get(0), 0, 0, 0));
        WaypointConfig.getWaypoints().add(point);
        ((DefaultListModel)waypoints.getModel()).addElement(point);
        waypoints.setSelectedValue(point, true);
        updateFields();
    }//GEN-LAST:event_addWaypointActionPerformed

    private void removeWaypointActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeWaypointActionPerformed
        if(waypoints.getSelectedValue() != null) {
            Waypoint point = (Waypoint)waypoints.getSelectedValue();
            ((DefaultListModel)waypoints.getModel()).removeElement(point);
            WaypointConfig.getWaypoints().remove(point);
            WaypointConfig.save();
            updateFields();
        }
    }//GEN-LAST:event_removeWaypointActionPerformed

    private void playerSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playerSubmitActionPerformed
        Player p = Bukkit.getServer().getPlayer(getPlayers().getSelectedItem().toString());
        if(p == null) {
            getPlayers().removeItem(getPlayers().getSelectedItem());
            return;
        }
        Location l = p.getLocation();
        name.setText(p.getName() + "'s location");
        x.setValue(l.getBlockX());
        y.setValue(l.getBlockY());
        z.setValue(l.getBlockZ());
        worlds.setSelectedItem(l.getWorld().getName());
    }//GEN-LAST:event_playerSubmitActionPerformed

    private void saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveActionPerformed
        Waypoint p = (Waypoint)waypoints.getSelectedValue();
        p.setName(name.getText());
        p.setWorld(worlds.getSelectedItem().toString());
        p.setX((Integer)x.getValue());
        p.setY((Integer)y.getValue());
        p.setZ((Integer)z.getValue());

        WaypointConfig.save();
        updateFields();

        JOptionPane.showMessageDialog(this, "Waypoint saved.", "Success", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_saveActionPerformed

    private void waypointsValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_waypointsValueChanged
        updateFields();
    }//GEN-LAST:event_waypointsValueChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addWaypoint;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField name;
    private javax.swing.JButton playerSubmit;
    private javax.swing.JComboBox players;
    private javax.swing.JButton removeWaypoint;
    private javax.swing.JButton save;
    private javax.swing.JList waypoints;
    private javax.swing.JComboBox worlds;
    private javax.swing.JSpinner x;
    private javax.swing.JSpinner y;
    private javax.swing.JSpinner z;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the players
     */
    public javax.swing.JComboBox getPlayers() {
        return players;
    }
}
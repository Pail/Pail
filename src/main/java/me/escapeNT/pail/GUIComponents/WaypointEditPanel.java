
package me.escapeNT.pail.GUIComponents;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import me.escapeNT.pail.Util.Localizable;
import me.escapeNT.pail.Util.Util;

import me.escapeNT.pail.config.WaypointConfig;
import me.escapeNT.pail.Util.Waypoint;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

/**
 * Panel for editing teleport waypoints.
 * @author escapeNT
 */
public class WaypointEditPanel extends javax.swing.JPanel implements Localizable {

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

        translateComponent();
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

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Waypoints"));

        waypoints.setModel(new DefaultListModel());
        waypoints.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                waypointsValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(waypoints);

        addWaypoint.setText("+");
        addWaypoint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addWaypointActionPerformed(evt);
            }
        });

        removeWaypoint.setText("-");
        removeWaypoint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeWaypointActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(8, 8, 8)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
                        .add(3, 3, 3))
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(257, 257, 257)
                        .add(removeWaypoint, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 50, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(addWaypoint, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 50, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .add(29, 29, 29))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
                .add(10, 10, 10)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(addWaypoint, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 40, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(removeWaypoint, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 40, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(2, 2, 2))
        );

        jLabel1.setText("Z");

        z.setModel(new javax.swing.SpinnerNumberModel());

        y.setModel(new javax.swing.SpinnerNumberModel());

        jLabel2.setText("Y");

        x.setModel(new javax.swing.SpinnerNumberModel());

        jLabel3.setText("X");

        jLabel4.setText("World");

        save.setText("Save Waypoint");
        save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveActionPerformed(evt);
            }
        });

        jLabel5.setText("Name");

        jLabel6.setText("Use player location:");

        playerSubmit.setText("Go");
        playerSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playerSubmitActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE)
                .add(18, 18, 18)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(4, 4, 4)
                        .add(jLabel5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 60, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(name, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE))
                    .add(layout.createSequentialGroup()
                        .add(4, 4, 4)
                        .add(jLabel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 30, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(x, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                        .add(20, 20, 20)
                        .add(jLabel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(y, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                        .add(20, 20, 20)
                        .add(jLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(z, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                    .add(jLabel4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 60, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(layout.createSequentialGroup()
                        .add(54, 54, 54)
                        .add(worlds, 0, 360, Short.MAX_VALUE))
                    .add(layout.createSequentialGroup()
                        .add(24, 24, 24)
                        .add(jSeparator1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 380, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(layout.createSequentialGroup()
                        .add(284, 284, 284)
                        .add(save, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 130, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(layout.createSequentialGroup()
                        .add(14, 14, 14)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel6, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 300, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(players, 0, 310, Short.MAX_VALUE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(playerSubmit, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(7, 7, 7)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                        .add(10, 10, 10)
                        .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE))
                    .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                        .add(30, 30, 30)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(name, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(layout.createSequentialGroup()
                                .add(6, 6, 6)
                                .add(jLabel5)))
                        .add(32, 32, 32)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 30, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(x, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 30, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(y, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 30, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(z, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(30, 30, 30)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(worlds, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(40, 40, 40)
                        .add(jSeparator1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(28, 28, 28)
                        .add(jLabel6)
                        .add(4, 4, 4)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(players, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 18, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(playerSubmit))
                        .add(92, 92, 92)
                        .add(save)))
                .addContainerGap())
        );
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
        if(getPlayers().getSelectedItem() == null) {
            return;
        }
        Player p = Bukkit.getServer().getPlayer(getPlayers().getSelectedItem().toString());
        if(p == null) {
            getPlayers().removeItem(getPlayers().getSelectedItem());
            return;
        }
        Location l = p.getLocation();
        name.setText(p.getName() + "'s " + Util.translate("location"));
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

        JOptionPane.showMessageDialog(this,  Util.translate("Waypoint saved."),  Util.translate("Success"),
                JOptionPane.INFORMATION_MESSAGE);
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

    public final void translateComponent() {
        Util.translateTextComponent(jLabel1);
        Util.translateTextComponent(jLabel2);
        Util.translateTextComponent(jLabel3);
        Util.translateTextComponent(jLabel4);
        Util.translateTextComponent(jLabel5);
        Util.translateTextComponent(jLabel6);
        Util.translateTextComponent(playerSubmit);
        Util.translateTextComponent(removeWaypoint);
        Util.translateTextComponent(save);
    }
}

package me.escapeNT.pail.GUIComponents;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;

import me.escapeNT.pail.util.Util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;

/**
 * Interface for giving items.
 * @author escapeNT
 */
public class GiveItemView extends javax.swing.JDialog {

    private String player;

    /** Creates new form GiveItemView */
    public GiveItemView(String player) {
        super(Util.getPlugin().getMainWindow());
        this.player = player;
        pack();
        setLocationRelativeTo(Util.getPlugin().getMainWindow());
        setModal(true);
        initComponents();
        getRootPane().setDefaultButton(give);
        setResizable(false);
        setSize(425, 175);

        item.setRenderer(new IconListRenderer(getMaterials()));
    }

    /**
     * Constructs a new HashMap for materials and their icons.
     * @return The Map of material names and their icons.
     */
    private HashMap<Object, ImageIcon> getMaterials() {
        HashMap<Object, ImageIcon> mats = new HashMap<Object, ImageIcon>();
        List<String> names = sortMatNames();

        for(String m : names) {
            if(m.equals("AIR")) {
                mats.put(m, new ImageIcon());
            }
            else {
                mats.put(m, new ImageIcon(getClass().getResource("images/" + m + ".png")));
            }
        }

        return mats;
    }

    private List<String> sortMatNames() {
        List<String> names = new ArrayList<String>();

        for(Material mat : Material.values()) {
            names.add(mat.toString());
        }
        Collections.sort(names, String.CASE_INSENSITIVE_ORDER);
        return names;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        idLabel = new javax.swing.JLabel();
        amountLabel = new javax.swing.JLabel();
        amount = new javax.swing.JSpinner();
        give = new javax.swing.JButton();
        cancel = new javax.swing.JButton();
        item = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Give Item");
        getContentPane().setLayout(null);

        idLabel.setText("Item");
        getContentPane().add(idLabel);
        idLabel.setBounds(10, 40, 28, 30);

        amountLabel.setText("Amount");
        getContentPane().add(amountLabel);
        amountLabel.setBounds(290, 40, 50, 30);

        amount.setModel(new javax.swing.SpinnerNumberModel(1, 1, 64, 1));
        getContentPane().add(amount);
        amount.setBounds(350, 40, 58, 28);

        give.setText("Give");
        give.setFocusable(false);
        give.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                giveActionPerformed(evt);
            }
        });
        getContentPane().add(give);
        give.setBounds(330, 110, 75, 29);

        cancel.setText("Cancel");
        cancel.setFocusable(false);
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });
        getContentPane().add(cancel);
        cancel.setBounds(240, 110, 86, 30);

        item.setModel(new DefaultComboBoxModel(sortMatNames().toArray()));
        getContentPane().add(item);
        item.setBounds(50, 30, 230, 50);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionPerformed
        this.dispose();
    }//GEN-LAST:event_cancelActionPerformed

    private void giveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_giveActionPerformed
        Server s = Bukkit.getServer();
        s.dispatchCommand(new ConsoleCommandSender(s), "give " + player + " "
                + Material.getMaterial(item.getSelectedItem().toString()).getId() + " " + amount.getValue().toString());
        dispose();
    }//GEN-LAST:event_giveActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSpinner amount;
    private javax.swing.JLabel amountLabel;
    private javax.swing.JButton cancel;
    private javax.swing.JButton give;
    private javax.swing.JLabel idLabel;
    private javax.swing.JComboBox item;
    // End of variables declaration//GEN-END:variables
}

package me.escapeNT.pail.GUIComponents;

import me.escapeNT.pail.util.Util;

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
        setSize(335, 130);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        idLabel = new javax.swing.JLabel();
        itemID = new javax.swing.JTextField();
        amountLabel = new javax.swing.JLabel();
        amount = new javax.swing.JSpinner();
        give = new javax.swing.JButton();
        cancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Give Item");
        getContentPane().setLayout(null);

        idLabel.setText("Item ID");
        getContentPane().add(idLabel);
        idLabel.setBounds(20, 26, 46, 16);
        getContentPane().add(itemID);
        itemID.setBounds(76, 20, 89, 28);

        amountLabel.setText("Amount");
        getContentPane().add(amountLabel);
        amountLabel.setBounds(183, 26, 50, 16);

        amount.setModel(new javax.swing.SpinnerNumberModel(1, 1, 64, 1));
        getContentPane().add(amount);
        amount.setBounds(243, 20, 58, 28);

        give.setText("Give");
        give.setFocusable(false);
        give.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                giveActionPerformed(evt);
            }
        });
        getContentPane().add(give);
        give.setBounds(226, 66, 75, 29);

        cancel.setText("Cancel");
        cancel.setFocusable(false);
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });
        getContentPane().add(cancel);
        cancel.setBounds(140, 66, 86, 29);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionPerformed
        this.dispose();
    }//GEN-LAST:event_cancelActionPerformed

    private void giveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_giveActionPerformed
        if(!itemID.getText().equals("")) {
            Server serv = Util.getPlugin().getServer();
            serv.dispatchCommand(new ConsoleCommandSender(serv), "give "
                    + player + " " + itemID.getText() + " " + amount.getValue());
            this.dispose();
        }
    }//GEN-LAST:event_giveActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSpinner amount;
    private javax.swing.JLabel amountLabel;
    private javax.swing.JButton cancel;
    private javax.swing.JButton give;
    private javax.swing.JLabel idLabel;
    private javax.swing.JTextField itemID;
    // End of variables declaration//GEN-END:variables
}
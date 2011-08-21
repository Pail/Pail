
package me.escapeNT.pail.GUIComponents;

import javax.swing.JOptionPane;

import me.escapeNT.pail.util.UpdateHandler;
import me.escapeNT.pail.util.Util;

/**
 * Dialog displaying update status and progress.
 * @author escapeNT
 */
public class UpdateView extends javax.swing.JDialog {

    private static UpdateView instance;

    /**
     * @return the instance
     */
    public static UpdateView getInstance() {
        return instance;
    }

    /**
     * @param aInstance the instance to set
     */
    public static void setInstance(UpdateView aInstance) {
        instance = aInstance;
    }

    /** Creates new form UpdateView */
    public UpdateView() {
        setInstance(this);
        pack();
        setLocationRelativeTo(Util.getPlugin().getMainWindow());
        setModal(true);      
        initComponents();
        setResizable(false);
        setSize(475, 310);
        getRootPane().setDefaultButton(update);

        changes.append("Version " + UpdateHandler.getCurrentVersion() + ":\n");
        for(String s : UpdateHandler.getChanges()) {
            changes.append("\n    • " + s);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        progress = new javax.swing.JProgressBar();
        progressLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        changes = new javax.swing.JTextArea();
        update = new javax.swing.JButton();
        Ignore = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Update Available");
        getContentPane().setLayout(null);
        getContentPane().add(progress);
        progress.setBounds(20, 240, 440, 20);

        progressLabel.setText(" ");
        getContentPane().add(progressLabel);
        progressLabel.setBounds(20, 260, 440, 20);

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 1, 14));
        jLabel1.setText("An update for Pail is available!");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(140, 10, 230, 17);

        jLabel2.setText("Changes:");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(20, 30, 70, 16);

        changes.setColumns(20);
        changes.setEditable(false);
        changes.setRows(5);
        changes.setFocusable(false);
        jScrollPane1.setViewportView(changes);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(20, 50, 440, 150);

        update.setText("Update");
        update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateActionPerformed(evt);
            }
        });
        getContentPane().add(update);
        update.setBounds(370, 210, 88, 29);

        Ignore.setText("Remind me later");
        Ignore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IgnoreActionPerformed(evt);
            }
        });
        getContentPane().add(Ignore);
        Ignore.setBounds(234, 210, 130, 29);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void IgnoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IgnoreActionPerformed
        dispose();
    }//GEN-LAST:event_IgnoreActionPerformed

    private void updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateActionPerformed
        new Thread(new Runnable() {
            public void run() {
                try {
                    UpdateHandler.downloadLatest(UpdateView.getInstance());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(getRootPane(), "Update failed."
                            + "\nThe latest version can be downloaded manually\nfrom the plugin thread.",
                            "Update Failed", JOptionPane.ERROR_MESSAGE);
                    UpdateHandler.updateFile.delete();
                    UpdateView.getInstance().dispose();
                }
            }
        }).start();
    }//GEN-LAST:event_updateActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Ignore;
    private javax.swing.JTextArea changes;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JProgressBar progress;
    private javax.swing.JLabel progressLabel;
    private javax.swing.JButton update;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the progress
     */
    public javax.swing.JProgressBar getProgress() {
        return progress;
    }

    /**
     * @return the progressLabel
     */
    public javax.swing.JLabel getProgressLabel() {
        return progressLabel;
    }

    /**
     * @return the Ignore
     */
    public javax.swing.JButton getIgnore() {
        return Ignore;
    }

    /**
     * @return the update
     */
    public javax.swing.JButton getUpdate() {
        return update;
    }
}
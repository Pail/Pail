
package me.escapeNT.pail.GUIComponents;

import me.escapeNT.pail.Pail;
import me.escapeNT.pail.util.Util;

/**
 * Pail about window.
 * @author escapeNT
 */
public class AboutView extends javax.swing.JDialog {

    /** Creates new form AboutView */
    public AboutView() {
        initComponents();
        setModal(true);
        pack();
        setSize(225, 230);
        setLocationRelativeTo(Util.getPlugin().getMainWindow());
        setResizable(false);
        version.setText("Version " + Pail.PLUGIN_VERSION + " by escape");
    }

    /** This method is called from within the constructor to
     * initialize the form.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        title = new javax.swing.JLabel();
        version = new javax.swing.JLabel();
        icon = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);

        title.setFont(new java.awt.Font("Lucida Grande", 1, 26)); // NOI18N
        title.setText("Pail");
        getContentPane().add(title);
        title.setBounds(90, 10, 47, 32);

        version.setText("Version ");
        getContentPane().add(version);
        version.setBounds(50, 50, 170, 16);

        icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/me/escapeNT/pail/GUIComponents/images/pail.png"))); // NOI18N
        getContentPane().add(icon);
        icon.setBounds(40, 70, 150, 130);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel icon;
    private javax.swing.JLabel title;
    private javax.swing.JLabel version;
    // End of variables declaration//GEN-END:variables
}

package me.escapeNT.pail.GUIComponents;

import me.escapeNT.pail.Pail;
import me.escapeNT.pail.Util.Util;

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

        title.setFont(new java.awt.Font("Lucida Grande", 1, 26));
        title.setText("Pail");

        version.setText("Version ");

        icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/me/escapeNT/pail/GUIComponents/images/pail.png"))); // NOI18N

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(87, 87, 87)
                .add(title, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 150, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .add(layout.createSequentialGroup()
                .add(50, 50, 50)
                .add(version, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 170, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .add(layout.createSequentialGroup()
                .add(40, 40, 40)
                .add(icon, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 150, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(10, 10, 10)
                .add(title)
                .add(8, 8, 8)
                .add(version)
                .add(4, 4, 4)
                .add(icon, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 130, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel icon;
    private javax.swing.JLabel title;
    private javax.swing.JLabel version;
    // End of variables declaration//GEN-END:variables
}
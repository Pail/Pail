
package me.escapeNT.pail.GUIComponents;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

import me.escapeNT.pail.Util.Localizable;
import me.escapeNT.pail.Util.ScrollableTextArea;
import me.escapeNT.pail.Util.Util;

/**
 * Dialog for searching the console.
 * @author escapeNT
 */
public class FindView extends javax.swing.JDialog implements Localizable {

    private static final ScrollableTextArea a = Util.getServerControls().getServerConsolePanel().getConsoleOutput();
    private static final Highlighter highlighter = a.getHighlighter();

    /** Creates new form FindView */
    public FindView() {
        initComponents();
        setResizable(false);
        setSize(425, 150);
        getRootPane().setDefaultButton(find);
        setLocationRelativeTo(Util.getPlugin().getMainWindow());
        addWindowListener(new WindowCloseListener());
        matches.setVisible(false);
        translateComponent();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new JLabel();
        search = new JTextField();
        find = new JButton();
        matches = new JLabel();
        matchCase = new JCheckBox();

        setTitle(Util.translate("Find"));
        setAlwaysOnTop(true);
        getContentPane().setLayout(null);

        jLabel1.setText("Search");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(20, 26, 70, 20);
        getContentPane().add(search);
        search.setBounds(90, 20, 310, 28);

        find.setText("Find");
        find.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                findActionPerformed(evt);
            }
        });
        getContentPane().add(find);
        find.setBounds(320, 80, 75, 29);

        matches.setForeground(new Color(204, 0, 0));
        matches.setText("0 matches");
        getContentPane().add(matches);
        matches.setBounds(20, 80, 190, 16);

        matchCase.setText("Match case");
        getContentPane().add(matchCase);
        matchCase.setBounds(210, 80, 110, 23);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void findActionPerformed(ActionEvent evt) {//GEN-FIRST:event_findActionPerformed
        if(search.getText().equals("")) {
            matches.setVisible(false);
            return;
        }
        boolean found = false;
        matches.setVisible(true);
        highlighter.removeAllHighlights();
        int nMatches = 0;

        Pattern p;
        if(matchCase.isSelected()) {
            p = Pattern.compile(search.getText());
        } else {
            p = Pattern.compile(search.getText(), Pattern.CASE_INSENSITIVE);
        }
        Matcher m = p.matcher(a.getText());
        while(m.find()) {
            try {
                highlighter.addHighlight(m.start(), m.end(), new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW));
            }
            catch (BadLocationException ex) {
                Logger.getLogger(FindView.class.getName()).log(Level.SEVERE, null, ex);
            }
            found = true;
            nMatches++;
        }

        if(!found) {
            matches.setForeground(Color.RED);
            matches.setText(Util.translate("No matches"));
            Toolkit.getDefaultToolkit().beep();
        } else {
            matches.setForeground(Color.BLACK);
            matches.setText(Util.translate(nMatches + " match" + (nMatches > 1 ? "es" : "")));
        }
    }//GEN-LAST:event_findActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton find;
    private JLabel jLabel1;
    private JCheckBox matchCase;
    private JLabel matches;
    private JTextField search;
    // End of variables declaration//GEN-END:variables

    public final void translateComponent() {
        Util.translateTextComponent(jLabel1);
        Util.translateTextComponent(find);
        Util.translateTextComponent(matchCase);
    }

    private class WindowCloseListener implements WindowListener {
        public void windowClosing(WindowEvent e) {
            highlighter.removeAllHighlights();
            EditMenu.findOpen = false;
        }
        public void windowOpened(WindowEvent e) {}
        public void windowClosed(WindowEvent e) {}
        public void windowIconified(WindowEvent e) {}
        public void windowDeiconified(WindowEvent e) {}
        public void windowActivated(WindowEvent e) {}
        public void windowDeactivated(WindowEvent e) {}
    }
}
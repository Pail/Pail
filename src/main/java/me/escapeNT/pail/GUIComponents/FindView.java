
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
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
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
        setSize(425, 125);
        setLocationRelativeTo(Util.getPlugin().getMainWindow());
        addWindowListener(new WindowCloseListener());
        matches.setVisible(false);
        translateComponent();

        search.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                search();
            }
            public void removeUpdate(DocumentEvent e) {
                search();
            }
            public void changedUpdate(DocumentEvent e) {
                search();
            }
        });
    }

    /** This method is called from within the constructor to
     * initialize the form.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new JLabel();
        search = new JTextField();
        matches = new JLabel();
        matchCase = new JCheckBox();

        setTitle(Util.translate("Find"));
        setAlwaysOnTop(true);
        getContentPane().setLayout(null);

        jLabel1.setText("Search");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(20, 26, 70, 20);

        search.setToolTipText(Util.translate("Text to match (supports regular expressions)"));
        getContentPane().add(search);
        search.setBounds(90, 20, 310, 28);

        matches.setForeground(new Color(204, 0, 0));
        matches.setText("0 matches");
        getContentPane().add(matches);
        matches.setBounds(20, 60, 230, 16);

        matchCase.setText("Match case");
        matchCase.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                matchCaseActionPerformed(evt);
            }
        });
        getContentPane().add(matchCase);
        matchCase.setBounds(300, 60, 110, 20);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void matchCaseActionPerformed(ActionEvent evt) {//GEN-FIRST:event_matchCaseActionPerformed
        search();
    }//GEN-LAST:event_matchCaseActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JLabel jLabel1;
    private JCheckBox matchCase;
    private JLabel matches;
    private JTextField search;
    // End of variables declaration//GEN-END:variables

    public final void translateComponent() {
        Util.translateTextComponent(jLabel1);
        Util.translateTextComponent(matchCase);
    }

    /**
     * Searches and highlights the console based on the current input.
     */
    public void search() {
        highlighter.removeAllHighlights();
        if (search.getText().equals("")) {
            matches.setVisible(false);
            return;
        }
        boolean found = false;
        matches.setVisible(true);
        int nMatches = 0;
        Pattern p;
        Matcher m;
        
        try {
            if (matchCase.isSelected()) {
                p = Pattern.compile(search.getText());
                m = p.matcher(a.getText());
            } else {
                p = Pattern.compile(search.getText(), Pattern.CASE_INSENSITIVE);
                m = p.matcher(a.getText());
            }

            while (m.find()) {
                try {
                    highlighter.addHighlight(m.start(), m.end(), new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW));
                } catch (BadLocationException ex) {
                    Logger.getLogger(FindView.class.getName()).log(Level.SEVERE, null, ex);
                }
                found = true;
                nMatches++;
            }
            if (!found) {
                matches.setForeground(Color.RED);
                matches.setText(Util.translate("No matches"));
                Toolkit.getDefaultToolkit().beep();
            } else {
                matches.setForeground(Color.BLACK);
                matches.setText(Util.translate(nMatches + " match" + ( nMatches > 1 ? "es" : "" )));
            }
        } catch(Exception ex) {}
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
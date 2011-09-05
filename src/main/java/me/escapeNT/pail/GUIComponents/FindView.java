
package me.escapeNT.pail.GUIComponents;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

import me.escapeNT.pail.Util.Localizable;
import me.escapeNT.pail.Util.ScrollableTextArea;
import me.escapeNT.pail.Util.TextLocation;
import me.escapeNT.pail.Util.Util;

/**
 * Dialog for searching the console.
 * @author escapeNT
 */
public class FindView extends javax.swing.JDialog implements Localizable {

    private static final ScrollableTextArea a = Util.getServerControls().getServerConsolePanel().getConsoleOutput();
    private static final Highlighter highlighter = a.getHighlighter();

    private List<TextLocation> textMatches = new ArrayList<TextLocation>();
    private int textMatchesIndex = 0;
    private int nMatches = 0;

    /** Creates new form FindView */
    public FindView() {
        initComponents();
        setResizable(false);
        setSize(425, 160);
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
        next = new JButton();
        back = new JButton();

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
        matches.setBounds(20, 60, 260, 16);

        matchCase.setText("Match case");
        matchCase.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                matchCaseActionPerformed(evt);
            }
        });
        getContentPane().add(matchCase);
        matchCase.setBounds(290, 60, 130, 20);

        next.setText("Next");
        next.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                nextActionPerformed(evt);
            }
        });
        getContentPane().add(next);
        next.setBounds(300, 90, 100, 29);

        back.setText("Back");
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                backActionPerformed(evt);
            }
        });
        getContentPane().add(back);
        back.setBounds(200, 90, 97, 29);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void matchCaseActionPerformed(ActionEvent evt) {//GEN-FIRST:event_matchCaseActionPerformed
        search();
    }//GEN-LAST:event_matchCaseActionPerformed

    private void nextActionPerformed(ActionEvent evt) {//GEN-FIRST:event_nextActionPerformed
            if(textMatches.isEmpty()) {
                return;
            }

            TextLocation l = textMatches.get(textMatchesIndex);
            DefaultCaret c = (DefaultCaret)a.getCaret();
            c.setDot(l.getStart());
            a.scrollRectToVisible(c);

            matches.setForeground(Color.BLACK);
            matches.setText(Util.translate("(" + (textMatchesIndex + 1) + "/"
                    + nMatches + ")" + " match" + ( nMatches > 1 ? "es" : "")));
            if(textMatchesIndex <= textMatches.size() - 1) {
                textMatchesIndex++;
            }
            if(textMatchesIndex > textMatches.size() - 1) {
                textMatchesIndex = 0;
            }
    }//GEN-LAST:event_nextActionPerformed

    private void backActionPerformed(ActionEvent evt) {//GEN-FIRST:event_backActionPerformed
        if(textMatches.isEmpty()) {
            return;
        }

        TextLocation l = textMatches.get(textMatchesIndex);
        DefaultCaret c = (DefaultCaret)a.getCaret();
        c.setDot(l.getStart());
        a.scrollRectToVisible(c);

        matches.setForeground(Color.BLACK);
        matches.setText(Util.translate("(" + (textMatchesIndex + 1) + "/"
                + nMatches + ")" + " match" + ( nMatches > 1 ? "es" : "")));
        if(textMatchesIndex >= 0) {
            textMatchesIndex--;
        }
        if(textMatchesIndex < 0) {
            textMatchesIndex = textMatches.size() - 1;
        }
    }//GEN-LAST:event_backActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton back;
    private JLabel jLabel1;
    private JCheckBox matchCase;
    private JLabel matches;
    private JButton next;
    private JTextField search;
    // End of variables declaration//GEN-END:variables

    public final void translateComponent() {
        Util.translateTextComponent(jLabel1);
        Util.translateTextComponent(matchCase);
        Util.translateTextComponent(back);
        Util.translateTextComponent(next);
    }

    /**
     * Searches and highlights the console based on the current input.
     */
    public void search() {
        highlighter.removeAllHighlights();
        textMatches.clear();
        textMatchesIndex = 0;
        
        if (search.getText().equals("")) {
            matches.setVisible(false);
            return;
        }
        boolean found = false;
        matches.setVisible(true);
        nMatches = 0;
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
                    textMatches.add(new TextLocation(m.start(), m.end()));
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
                matches.setText(Util.translate(nMatches + " match" + ( nMatches > 1 ? "es" : "")));
            }
        } catch(PatternSyntaxException ex) {}
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
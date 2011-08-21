

package me.escapeNT.pail.util;

import java.awt.Color;
import java.awt.Font;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

/**
 * A simple text area contained within a autoscrolling pane.
 * @author escapeNT
 */
public class ScrollableTextArea extends JTextPane {

    private JScrollPane scroller;

    public ScrollableTextArea() {
       scroller = new JScrollPane(this);
       scroller.setVerticalScrollBarPolicy(
               ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
       scroller.setHorizontalScrollBarPolicy(
               ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
       setFont(new Font("SansSerif", Font.PLAIN, 12));
       setEditable(false);
    }

    /**
     * Appends the specified text to the pane with the provided color.
     * @param color The color of the text.
     * @param text The text to append.
     */
    public void append(Color color, String text) {
        SimpleAttributeSet sas = new SimpleAttributeSet();
        StyleConstants.setForeground(sas, color);

        Document doc = getDocument();
        try {
            doc.insertString(doc.getLength(), text, sas);
        } catch (BadLocationException ex) {
            Logger.getLogger(ScrollableTextArea.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Appends the specified text to the pane with the provided color.
     * @param color The color of the text.
     * @param bold True if the text will be bold.
     * @param text The text to append.
     */
    public void append(Color color, boolean bold, String text) {
        SimpleAttributeSet sas = new SimpleAttributeSet();
        StyleConstants.setBold(sas, true);
        StyleConstants.setForeground(sas, color);

        Document doc = getDocument();
        try {
            doc.insertString(doc.getLength(), text, sas);
        } catch (BadLocationException ex) {
            Logger.getLogger(ScrollableTextArea.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns the text area contained within its scroll pane.
     * @return the panel with scrollbar
     */
    public JScrollPane getScrollerPanel() {
       return scroller;
    }
}
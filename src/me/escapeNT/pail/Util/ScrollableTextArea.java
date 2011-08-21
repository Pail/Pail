package me.escapeNT.pail.util;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Rectangle;
import java.util.logging.Level;
import javax.swing.BoundedRangeModel;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
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

        ((DefaultCaret)getCaret()).setUpdatePolicy(DefaultCaret.NEVER_UPDATE);

        getDocument().addDocumentListener(new ScrollingDocumentListener(this));
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
            Util.log(Level.SEVERE, ex.toString());
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
            Util.log(Level.SEVERE, ex.toString());
        }
    }

    /**
     * Returns the text area contained within its scroll pane.
     * @return the panel with scrollbar
     */
    public JScrollPane getScrollerPanel() {
       return scroller;
    }

    private class ScrollingDocumentListener implements DocumentListener {
        
        private ScrollableTextArea textArea;

        public ScrollingDocumentListener(ScrollableTextArea textArea) {
            this.textArea = textArea;
        }

        public void changedUpdate(DocumentEvent e) {
            maybeScrollToBottom();
        }

        public void insertUpdate(DocumentEvent e) {
            maybeScrollToBottom();
        }

        public void removeUpdate(DocumentEvent e) {
            maybeScrollToBottom();
        }

        private void maybeScrollToBottom() {
            JScrollBar scrollBar = scroller.getVerticalScrollBar();
            boolean scrollBarAtBottom = isScrollBarFullyExtended(scrollBar);
            if(scrollBarAtBottom) {
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        EventQueue.invokeLater(new Runnable() {
                            public void run() {
                                scrollToBottom(textArea);
                            }
                        });
                    }
                });
            }
        }
    }

    private static boolean isScrollBarFullyExtended(JScrollBar vScrollBar) {
        BoundedRangeModel model = vScrollBar.getModel();
        return (model.getExtent() + model.getValue()) == model.getMaximum();
    }

    private static void scrollToBottom(JComponent component) {
        Rectangle visibleRect = component.getVisibleRect();
        visibleRect.y = component.getHeight() - visibleRect.height;
        component.scrollRectToVisible(visibleRect);
    }
}
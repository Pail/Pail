

package me.escapeNT.pail.util;

import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

/**
 * A simple text area contained within a autoscrolling pane.
 * @author escapeNT
 */
public class ScrollableTextArea extends JTextArea {

    private JScrollPane scroller;

    public ScrollableTextArea() {
       scroller = new JScrollPane(this);
       scroller.setVerticalScrollBarPolicy(
               ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
       scroller.setHorizontalScrollBarPolicy(
               ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
       setLineWrap(true);
       setWrapStyleWord(true);
       setFont(new Font("SansSerif", Font.PLAIN, 12));
       setEditable(false);
    }

    /**
     * Returns the text area contained within its scroll pane.
     * @return the panel with scrollbar
     */
    public JScrollPane getScrollerPanel() {
       return scroller;
    }
}
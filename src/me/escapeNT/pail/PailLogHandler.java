
package me.escapeNT.pail;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import javax.swing.JScrollBar;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;

import me.escapeNT.pail.util.ScrollableTextArea;

/**
 * Log Handler to print the console output to the GUI
 * @author escapeNT
 */
public class PailLogHandler extends Handler {
    
    ScrollableTextArea output;
    boolean scroll = true;
    int scroller;

    /**
     * Constructs a new log handler using the specified text area for output.
     * @param output The JTextArea to write the log data to.
     */
    public PailLogHandler(ScrollableTextArea output) {
        this.output = output;
    }

    public synchronized void publish(LogRecord record) {
        
        final StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append(new SimpleDateFormat("HH:mm:ss").format(new Date(record.getMillis())));
        sb.append(" [");
        sb.append(record.getLevel().toString());
        sb.append("] ");
        sb.append(record.getMessage());

        final JScrollBar vert = output.getScrollerPanel().getVerticalScrollBar();
        if(vert.getValue() != vert.getMaximum()-vert.getSize().height) {
            scroll = false;
            try {
                scroller = output.getLineStartOffset((int) Math.round(output.getLineCount() * (vert.getValue() /
                        (new Integer(vert.getMaximum() - vert.getSize().height).doubleValue()))));
            } catch (BadLocationException ex) {
                scroll = true;
            }
        } else if(!scroll) {
            scroll = true;
        }     
        
        SwingUtilities.invokeLater(new Runnable() {
            public void run(){
                output.append(sb.toString().replace("[0m", ""));
                if(scroll && output.getCaretPosition() != output.getText().length()) {
                    output.setCaretPosition(output.getText().length());
                }
                else if (!scroll && output.getCaretPosition() == output.getText().length()) {
                    output.setCaretPosition(scroller);
                }
            }
        });
    }

    public void flush() {}

    public void close() throws SecurityException {}
}

package me.escapeNT.pail;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import javax.swing.JScrollBar;
import javax.swing.SwingUtilities;

import me.escapeNT.pail.util.ScrollableTextArea;

/**
 * Log Handler to print the console output to the GUI.
 * @author escapeNT
 */
public class PailLogHandler extends Handler {
    
    ScrollableTextArea output;

    /**
     * Constructs a new log handler using the specified text area for output.
     * @param output The JTextArea to write the log data to.
     */
    public PailLogHandler(ScrollableTextArea output) {
        this.output = output;
    }

    @Override
    public synchronized void publish(LogRecord record) {

        Date date = new Date(record.getMillis());
        SimpleDateFormat fmt = new SimpleDateFormat("HH:mm:ss");

        final StringBuilder sb = new StringBuilder();
        sb.append(fmt.format(date));
        sb.append(" [");
        sb.append(record.getLevel().toString());
        sb.append("] ");
        sb.append(record.getMessage());
        sb.append("\n");

        SwingUtilities.invokeLater(new Runnable() {
            public void run(){
                JScrollBar vert = output.getScrollerPanel().getVerticalScrollBar();
                boolean scroll = false;
                if(vert.getValue() == vert.getMaximum()) {
                    scroll = true;
                }
                output.append(sb.toString().replace("[0m", ""));
                if(scroll) {
                    vert.setValue(vert.getMaximum());
                }
            }
        });
    }

    @Override
    public void flush() {}

    @Override
    public void close() throws SecurityException {}
}
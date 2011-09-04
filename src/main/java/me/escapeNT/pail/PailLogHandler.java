
package me.escapeNT.pail;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import me.escapeNT.pail.Util.ScrollableTextArea;

/**
 * Log Handler to print the console output to the GUI
 * @author escapeNT
 */
public class PailLogHandler extends Handler {
    
    private ScrollableTextArea output;

    /**
     * Constructs a new log handler using the specified text area for output.
     * @param output The JTextArea to write the log data to.
     */
    public PailLogHandler(ScrollableTextArea output) {
        this.output = output;
    }

    public synchronized void publish(final LogRecord record) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                output.append(Color.GRAY, true, new SimpleDateFormat("hh:mm a").format(new Date(record.getMillis())));

                Color color = Color.BLACK;
                Level lv = record.getLevel();
                if(lv == Level.INFO) {
                    color = Color.BLUE;
                } else if(lv == Level.WARNING) {
                    color = Color.ORANGE;
                } else if(lv == Level.SEVERE) {
                    color = Color.RED;
                }

                output.append(color, " [" + record.getLevel().toString() + "] ");

                if(UIManager.getLookAndFeel().getName().equals("HiFi")) {
                    color = Color.WHITE;
                } else {
                    color = Color.BLACK;
                }
                for(String s : record.getMessage().split(" ")) {
                    if(s.startsWith("[") && s.contains("]")) {
                        output.append(color, true, s + " ");
                    } else {
                        output.append(color, s + " ");
                    }
                }
                output.append(color, "\n");
            }
        });
    }

    public void flush() {}

    public void close() throws SecurityException {}
}
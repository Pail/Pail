
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

                char[] c = record.getMessage().toCharArray();
                StringBuilder message = new StringBuilder(record.getMessage());
                int off = 0;
                try {
                    for(int i = 0; i < c.length; i++) {
                        if(c[i] == '[' && Character.isDigit(c[i+1])) {
                            if(Character.isDigit(c[i+2]) && c[i+3] == 'm') {
                                message.delete(i - (off + 1), i + 4 - off);
                                off += 5;
                            } else if(c[i+2] == 'm') {
                                message.delete(i - (off + 1), i + 3 - off);
                                off += 4;
                            }
                        }
                    }
                } catch(IndexOutOfBoundsException e) {}

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
                for(String s : message.toString().trim().split(" ")) {
                    output.append(color, (((s.startsWith("[") && s.contains("]"))
                        || (s.startsWith("<") && s.contains(">")))), s.trim() + " ");
                }
                output.append(color, "\n");
            }
        });
    }

    public void flush() {}

    public void close() throws SecurityException {}
}
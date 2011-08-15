
package me.escapeNT.pail.GUIComponents;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JTextField;

import me.escapeNT.pail.util.ScrollableTextArea;
import me.escapeNT.pail.util.Util;
import org.bukkit.Bukkit;

import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;

/**
 * Panel containing the server console and input.
 * @author escapeNT
 */
public class ServerConsolePanel extends JPanel {

    private ScrollableTextArea consoleOutput;
    private JTextField consoleInput;
    
    public ServerConsolePanel() {
        consoleOutput = new ScrollableTextArea();
        consoleOutput.setAutoscrolls(true);
        this.setLayout(new BorderLayout());
        this.add(BorderLayout.CENTER, consoleOutput.getScrollerPanel());

        consoleInput = new JTextField();
        consoleInput.addActionListener(new ConsoleCommandListener());
        this.add(BorderLayout.SOUTH, consoleInput);
    }

    /**
     * Returns the JTextArea responsible for displaying the console output.
     * @return the consoleOutput The console output area.
     */
    public ScrollableTextArea getConsoleOutput() {
        return consoleOutput;
    }

    /**
     * Listener class handling command input.
     */
    private class ConsoleCommandListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Server server = Bukkit.getServer();
            if(consoleInput.getText().equals("reload")) {
                Util.getPlugin().saveState();
            }
            server.dispatchCommand(new ConsoleCommandSender(server), consoleInput.getText());
            consoleInput.setText("");
        }
    }
}
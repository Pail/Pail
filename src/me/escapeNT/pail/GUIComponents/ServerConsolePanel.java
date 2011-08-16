package me.escapeNT.pail.GUIComponents;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
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
    private LinkedList<String> cmdHistory = new LinkedList<String>();
    private CommandRecallListener crl = new CommandRecallListener();
    
    public ServerConsolePanel() {
        consoleOutput = new ScrollableTextArea();
        consoleOutput.setAutoscrolls(true);
        this.setLayout(new BorderLayout());
        this.add(BorderLayout.CENTER, consoleOutput.getScrollerPanel());

        consoleInput = new JTextField();
        consoleInput.addActionListener(new ConsoleCommandListener());
        consoleInput.addKeyListener(crl);
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

            if(!consoleInput.getText().equals("")) {
                cmdHistory.addFirst(consoleInput.getText());
                if(cmdHistory.size() > 10) {
                    cmdHistory.removeLast();
                }
            }

            consoleInput.setText("");
            crl.setIndex(-1);
        }
    }

    /**
     * Listener for up and down arrow events to recall command history.
     */
    private class CommandRecallListener implements KeyListener {

        private int index = -1;

        public void keyTyped(KeyEvent e) {}
        public void keyReleased(KeyEvent e) {}

        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_DOWN && index == -1 
                    || e.getKeyCode() == KeyEvent.VK_UP && cmdHistory.isEmpty()) {
                return;
            }
            
            if(e.getKeyCode() == KeyEvent.VK_UP && index < cmdHistory.size()-1) {
                index++;
            }
            else if(e.getKeyCode() == KeyEvent.VK_DOWN && index > -1) {
                index--;
            }
            
            if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_UP) {
                if (index > -1) {
                    consoleInput.setText(cmdHistory.get(index));
                } else {
                    consoleInput.setText("");
                }
            }
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }
}
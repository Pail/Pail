
package me.escapeNT.pail;

import me.escapeNT.pail.Util.Util;

import org.bukkit.event.*;
import org.bukkit.event.server.ServerCommandEvent;

/**
 * Listener for reload commands in the terminal.
 * @author escapeNT
 */
public class PailServerListener implements Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    public void onServerCommand(ServerCommandEvent event) {
        if(event.getCommand().equalsIgnoreCase("reload")) {
            Util.getPlugin().saveState();
        }
    }
}
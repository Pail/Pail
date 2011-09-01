
package me.escapeNT.pail;

import me.escapeNT.pail.Util.Util;

import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.event.server.ServerListener;

/**
 * Listener for reload commands in the terminal.
 * @author escapeNT
 */
public class PailServerListener extends ServerListener {

    @Override
    public void onServerCommand(ServerCommandEvent event) {
        if(event.getCommand().equalsIgnoreCase("reload")) {
            Util.getPlugin().saveState();
        }
    }
}
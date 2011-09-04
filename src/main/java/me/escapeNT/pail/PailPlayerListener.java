
package me.escapeNT.pail;

import javax.swing.SwingUtilities;

import me.escapeNT.pail.Util.ServerReadyListener;
import me.escapeNT.pail.Util.Util;

import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Listener for player login and quit events to update the player list.
 * @author escapeNT
 */
public class PailPlayerListener extends PlayerListener {

    @Override
    public void onPlayerJoin(final PlayerJoinEvent event) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Util.getServerControls().getListModel().addElement(event.getPlayer().getName());
                Util.getServerControls().addPlayer(event.getPlayer().getName());
                if(ServerReadyListener.settings != null) {
                    ServerReadyListener.settings.getWaypointEditor().getPlayers().addItem(event.getPlayer().getName());
                }
            }
        });
    }

    @Override
    public void onPlayerKick(final PlayerKickEvent event) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Util.getServerControls().getListModel().removeElement(event.getPlayer().getName());
                if(ServerReadyListener.settings != null) {
                    ServerReadyListener.settings.getWaypointEditor().getPlayers().removeItem(event.getPlayer().getName());
                }
            }
        });
    }

    @Override
    public void onPlayerQuit(final PlayerQuitEvent event) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Util.getServerControls().getListModel().removeElement(event.getPlayer().getName());
                if(ServerReadyListener.settings != null) {
                    ServerReadyListener.settings.getWaypointEditor().getPlayers().removeItem(event.getPlayer().getName());
                }
            }
        });
    }
}
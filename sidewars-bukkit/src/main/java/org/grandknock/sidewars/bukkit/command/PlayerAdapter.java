package org.grandknock.sidewars.bukkit.command;

import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.grandknock.sidewars.core.entity.SWPlayer;

import java.util.UUID;

public class PlayerAdapter extends CommandSenderAdapter implements SWPlayer {

    private final Player player;

    public PlayerAdapter(Player player) {
        super(player);
        this.player = player;
    }

    @Override
    public String getWorldID() {
        return player.getWorld().getName();
    }

    @Override
    public UUID getPlayerID() {
        return player.getUniqueId();
    }

    @Override
    public void sendMessage(MessageType type, String message) {
        ChatColor color;
        switch (type) {
            case UNAUTHORIZED: color = ChatColor.DARK_RED; break;
            case ERROR: color = ChatColor.RED; message = "ERROR: " + message; break;
            case INFO: color = ChatColor.YELLOW; break;
            case SUCCESS: color = ChatColor.GREEN; break;
            default: throw new IllegalArgumentException("Unknown message type: " + type);
        }
        bukkitSender.sendMessage(color + message);
    }

    @Override
    public LocalSession getWorldEditSession() {
        return WorldEdit.getInstance().getSessionManager().get(BukkitAdapter.adapt(player));
    }
}

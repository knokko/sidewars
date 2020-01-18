package org.grandknock.sidewars.bukkit.command;

import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.BukkitPlayer;
import org.bukkit.entity.Player;
import org.grandknock.sidewars.core.command.WESessionOwner;

public class PlayerSessionOwner implements WESessionOwner {

    private final Player player;

    public PlayerSessionOwner(Player player) {
        this.player = player;
    }

    @Override
    public LocalSession getSession() {
        BukkitPlayer bukkitPlayer = BukkitAdapter.adapt(player);
        return WorldEdit.getInstance().getSessionManager().get(bukkitPlayer);
    }
}

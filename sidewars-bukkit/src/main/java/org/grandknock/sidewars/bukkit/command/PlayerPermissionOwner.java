package org.grandknock.sidewars.bukkit.command;

import org.bukkit.entity.Player;
import org.grandknock.sidewars.core.command.PermissionOwner;

public class PlayerPermissionOwner implements PermissionOwner {

    private final Player player;

    public PlayerPermissionOwner(Player player) {
        this.player = player;
    }

    @Override
    public boolean hasPermission(String permission) {
        return player.hasPermission(permission);
    }
}

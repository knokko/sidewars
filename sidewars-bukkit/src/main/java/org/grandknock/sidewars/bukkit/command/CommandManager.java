package org.grandknock.sidewars.bukkit.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.grandknock.sidewars.core.command.SWCommandSender;
import org.grandknock.sidewars.core.entity.SWPlayer;

public class CommandManager {

    public SWCommandSender toSWSender(CommandSender bukkitSender) {
        if (bukkitSender instanceof Player) {
            Player p = (Player) bukkitSender;
            return new SWPlayer(new PlayerPermissionOwner(p), new PlayerSessionOwner(p), p.getWorld().getName());
        } else {
            return new NonPlayerCommandSender(bukkitSender);
        }
    }
}

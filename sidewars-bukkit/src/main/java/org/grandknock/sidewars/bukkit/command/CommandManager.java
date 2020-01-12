package org.grandknock.sidewars.bukkit.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.grandknock.sidewars.core.command.SWCommandSender;

public class CommandManager {

    public SWCommandSender toSWSender(CommandSender bukkitSender) {
        if (bukkitSender instanceof Player) {
            // TODO Do your think Grantis
        } else {
            return new NonPlayerCommandSender(bukkitSender);
        }
    }
}

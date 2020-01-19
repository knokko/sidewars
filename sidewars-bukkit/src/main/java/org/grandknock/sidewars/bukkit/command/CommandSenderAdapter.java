package org.grandknock.sidewars.bukkit.command;

import org.bukkit.command.CommandSender;

public class CommandSenderAdapter {

    protected final CommandSender bukkitSender;

    public CommandSenderAdapter(CommandSender bukkitSender) {
        this.bukkitSender = bukkitSender;
    }

    public boolean hasPermission(String permission) {
        return bukkitSender.hasPermission(permission);
    }
}

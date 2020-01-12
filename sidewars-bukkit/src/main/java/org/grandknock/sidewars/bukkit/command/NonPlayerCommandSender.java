package org.grandknock.sidewars.bukkit.command;

import org.bukkit.command.CommandSender;
import org.grandknock.sidewars.core.command.SWCommandSender;

public class NonPlayerCommandSender implements SWCommandSender {

    private final CommandSender bukkitSender;

    public NonPlayerCommandSender(CommandSender bukkitSender) {
        this.bukkitSender = bukkitSender;
    }

    @Override
    public boolean hasPermission(String permission) {
        return bukkitSender.hasPermission(permission);
    }

    @Override
    public void sendMessage(MessageType type, String message) {
        // TODO Let the message type determine color
        bukkitSender.sendMessage(message);
    }
}

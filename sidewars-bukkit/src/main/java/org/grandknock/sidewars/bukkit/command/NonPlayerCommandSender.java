package org.grandknock.sidewars.bukkit.command;

import com.sk89q.worldedit.LocalSession;
import org.bukkit.command.CommandSender;
import org.grandknock.sidewars.core.command.SWCommandSender;

public class NonPlayerCommandSender extends CommandSenderAdapter implements SWCommandSender  {

    public NonPlayerCommandSender(CommandSender bukkitSender) {
        super(bukkitSender);
    }

    @Override
    public void sendMessage(MessageType type, String message) {
        if (type == MessageType.ERROR)
            message = "ERROR: " + message;
        bukkitSender.sendMessage(message);
    }

    @Override
    public LocalSession getWorldEditSession() {
        return null;
    }
}

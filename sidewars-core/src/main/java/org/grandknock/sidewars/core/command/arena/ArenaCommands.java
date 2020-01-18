package org.grandknock.sidewars.core.command.arena;

import org.grandknock.sidewars.core.command.SWCommandSender;
import org.grandknock.sidewars.core.command.management.SubCommand;
import org.grandknock.sidewars.core.entity.SWPlayer;

public class ArenaCommands {

    @SubCommand(name="create")
    public void handleCreation(String[] args, SWCommandSender sender) {
        SWPlayer player = (SWPlayer) sender;
        player.sendMessage(SWCommandSender.MessageType.SUCCESS, "Arena" + args[0] + " was successfully created!");
    }
}

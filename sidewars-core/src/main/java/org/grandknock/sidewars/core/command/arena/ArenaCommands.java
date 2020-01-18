package org.grandknock.sidewars.core.command.arena;

import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.session.SessionOwner;
import org.grandknock.sidewars.core.command.SWCommandSender;
import org.grandknock.sidewars.core.command.management.SubCommand;
import org.grandknock.sidewars.core.entity.SWPlayer;

public class ArenaCommands {

    @SubCommand(name="create")
    public void handleCreation(String[] args, SWCommandSender sender) {
        sender.sendMessage(SWCommandSender.MessageType.SUCCESS, "Arena " + args[0] + " was successfully created!");
        WorldEdit.getInstance().getSessionManager().findByName("test");
    }
}

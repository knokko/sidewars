package org.grandknock.sidewars.core.command;

import org.grandknock.sidewars.core.SideWars;
import org.grandknock.sidewars.core.command.arena.ArenaCommands;
import org.grandknock.sidewars.core.command.management.SubCommand;
import org.grandknock.sidewars.core.command.management.SubCommandManager;

public class SWCommands {

    private final SubCommandManager arenaManager;

    public SWCommands(SideWars instance) {
        arenaManager = new SubCommandManager(new ArenaCommands(instance));
    }

    @SubCommand(name="arena")
    public void processArena(String[] args, SWCommandSender sender) {
        if (!arenaManager.execute(args, sender)) {
            sender.sendMessage(SWCommandSender.MessageType.ERROR, "Use /sw arena/create");
        }
    }
}

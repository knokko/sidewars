package org.grandknock.sidewars.core;

import org.grandknock.sidewars.core.arena.ArenaPrototype;
import org.grandknock.sidewars.core.command.SWCommandSender;
import org.grandknock.sidewars.core.command.SWCommands;
import org.grandknock.sidewars.core.command.management.SubCommandManager;

import java.util.HashSet;
import java.util.Set;

public class SideWars {

    private final SubCommandManager commandManager = new SubCommandManager(new SWCommands());

    private final Set<ArenaPrototype> arenas;

    public SideWars() {
        arenas = new HashSet<>();
    }

    public void processCommand(String[] args, SWCommandSender sender) {
        if (!commandManager.execute(args, sender)) {
            sender.sendMessage(SWCommandSender.MessageType.ERROR, "Use /sw arena");
        }
    }

    public void addArenaPrototype(ArenaPrototype proto) {
        // TODO Validation checks
        arenas.add(proto);
    }

    public Iterable<ArenaPrototype> getArenaPrototypes() {
        return arenas;
    }

    // TODO Add getArenaPrototype method
}

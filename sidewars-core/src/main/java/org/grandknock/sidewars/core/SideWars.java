package org.grandknock.sidewars.core;

import org.grandknock.sidewars.core.arena.ArenaPrototype;
import org.grandknock.sidewars.core.command.SWCommandSender;
import org.grandknock.sidewars.core.command.SWCommands;
import org.grandknock.sidewars.core.command.management.SubCommandManager;
import org.grandknock.sidewars.core.files.FileDataManager;
import org.grandknock.sidewars.core.files.FileManager;

import java.util.HashSet;
import java.util.Set;

public class SideWars {

    private final SubCommandManager commandManager;
    private final FileDataManager fileManager;

    private final Set<ArenaPrototype> arenas;

    public SideWars(FileManager fileHandler) {
        commandManager = new SubCommandManager(new SWCommands(this));
        fileManager = new FileDataManager(fileHandler);

        arenas = new HashSet<>();
    }

    public void saveData() {
        // TODO Well... save all unsaved changes
    }

    public void loadData() {
        // TODO Wel... load the data
    }

    public void processCommand(String[] args, SWCommandSender sender) {
        if (!commandManager.execute(args, sender)) {
            sender.sendMessage(SWCommandSender.MessageType.ERROR, "Use /sw arena");
        }
    }

    /**
     * Adds an ArenaPrototype to this SideWars instance.
     *
     * If this SideWars instance already has an ArenaPrototype with the same name as {@code proto},
     * an IllegalArgumentException will be thrown instead.
     *
     * @param proto The ArenaPrototype to add
     * @throws IllegalArgumentException If there is already an ArenaPrototype with the same name
     */
    public void addArenaPrototype(ArenaPrototype proto) throws IllegalArgumentException{
        if (getArenaPrototype(proto.getName()) != null) {
            throw new IllegalArgumentException("There is already an arena with name " + proto.getName());
        }
        arenas.add(proto);
    }

    public Iterable<ArenaPrototype> getArenaPrototypes() {
        return arenas;
    }

    /**
     * Finds and returns the ArenaPrototype with {@code name} if such an arena prototype exists.
     * If no ArenaPrototype with the given name was found, this method will return null.
     * @param name The name of the arena prototype to get
     * @return The arena prototype with {@code name} or null if no ArenaPrototype has the given name
     */
    public ArenaPrototype getArenaPrototype(String name) {
        for (ArenaPrototype arena : arenas)
            if (arena.getName().equals(name))
                return arena;

        return null;
    }
}

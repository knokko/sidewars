package org.grandknock.sidewars.core;

import org.grandknock.sidewars.core.arena.ArenaPrototype;
import org.grandknock.sidewars.core.command.SWCommandSender;
import org.grandknock.sidewars.core.command.SWCommands;
import org.grandknock.sidewars.core.command.management.SubCommandManager;
import org.grandknock.sidewars.core.entity.SWPlayer;
import org.grandknock.sidewars.core.session.Session;
import org.grandknock.sidewars.core.storage.StorageHelper;
import org.grandknock.sidewars.core.storage.StorageManager;

import java.util.*;

public class SideWars {

    public static boolean isNameAllowed(String name) {
        if (name == null || name.isEmpty() || name.length() > 50)
            return false;
        for (int index = 0; index < name.length(); index++)
            if (!isCharAllowed(name.charAt(index)))
                return false;
        return true;
    }

    private static boolean isCharAllowed(char c) {
        return c == '_' || (c >= 'a' && c <= 'z') || (c >= '0' && c <= '9');
    }

    private final SubCommandManager commandManager;
    private final StorageHelper storageHelper;

    private final Set<ArenaPrototype> arenas;
    private final Map<UUID, Session> sessions;

    private int arenaPrepTime;

    public SideWars(StorageManager fileHandler) {
        commandManager = new SubCommandManager(new SWCommands(this));
        storageHelper = new StorageHelper(fileHandler);

        arenas = new HashSet<>();
        sessions = new HashMap<>();
    }

    public void saveData() {
        saveBinary();
        for (ArenaPrototype arena : arenas) {
            arena.saveData(storageHelper);
        }
    }

    private void saveBinary() {
        storageHelper.writeSWBinary(output -> {

            // TODO Prepare encoding for updates
            output.writeInt(arenas.size());
            for (ArenaPrototype arena : arenas) {
                output.writeUTF(arena.getName());
            }
        });
    }

    public void loadData() {
        storageHelper.readSWBinary(input -> {

            // TODO Prepare encoding for updates

            // Load arena prototypes
            int numArenas = input.readInt();
            Set<ArenaPrototype> loadedArenas = new HashSet<>(numArenas);

            for (int counter = 0; counter < numArenas; counter++) {
                loadedArenas.add(ArenaPrototype.loadExisting(storageHelper, input.readUTF()));
            }
            arenas.addAll(loadedArenas);

        }, () -> System.out.println("Couldn't find binary data file of SideWars, " +
                    "this is ok if you use this plug-in for the first time.")
        );

        storageHelper.getSWConfig(config -> {
            arenaPrepTime = (Integer) config.get("ArenaPreparationTime");
        }, config -> {
            config.put("ArenaPreparationTime", arenaPrepTime);
        });
    }

    public void processCommand(String[] args, SWCommandSender sender) {
        if (!commandManager.execute(args, sender)) {
            sender.sendMessage(SWCommandSender.MessageType.ERROR, "Use /sw arena");
        }
    }

    public Session getSession(SWPlayer player) {
        Session session = sessions.get(player.getPlayerID());
        if (session == null) {
            session = new Session();
            sessions.put(player.getPlayerID(), session);
        }
        return session;
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
        proto.updateConfig(storageHelper);
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

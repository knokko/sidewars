package org.grandknock.sidewars.core.arena;

import org.grandknock.sidewars.core.Region;
import org.grandknock.sidewars.core.storage.StorageHelper;

import java.util.Map;

public class ArenaPrototype {

    public static ArenaPrototype createNew(String name, Region region) {
        ArenaPrototype arena = new ArenaPrototype(name);
        arena.region = region;
        return arena;
    }

    public static ArenaPrototype loadExisting(StorageHelper storage, String name) {
        ArenaPrototype arena = new ArenaPrototype(name);
        storage.readArenaPrototypeBinary(input -> {
            arena.region = Region.load(input);
        }, name);
        arena.updateConfig(storage);
        return arena;
    }

    private String name;

    // Binary properties
    private Region region;

    // Config properties
    private int numTeams;
    private int playersPerTeam;

    private ArenaPrototype(String name) {
        this.name = name;
    }

    public void updateConfig(StorageHelper storage) {
        storage.getArenaPrototypeConfig(name, this::read, this::writeDefault);
    }

    private void read(Map<String,Object> config) {
        numTeams = (Integer) config.get("NumberOfTeams");
        playersPerTeam = (Integer) config.get("PlayersPerTeam");
    }

    private void writeDefault(Map<String,Object> config) {
        config.put("NumberOfTeams", 2);
        config.put("PlayersPerTeam", 4);
    }

    public void saveData(StorageHelper storage) {
        saveBinaryData(storage);
        // TODO Call saveData of all structures of this ArenaPrototype
    }

    private void saveBinaryData(StorageHelper storage) {
        storage.writeArenaPrototypeBinary(output -> {
            region.save(output);
        }, name);
    }

    public String getName() {
        return name;
    }

    public Region getRegion() {
        return region;
    }

    public int getNumberOfTeams() {
        return numTeams;
    }

    public int getPlayersPerTeam() {
        return playersPerTeam;
    }
}

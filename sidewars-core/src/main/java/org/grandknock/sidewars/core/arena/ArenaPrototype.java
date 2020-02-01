package org.grandknock.sidewars.core.arena;

import org.grandknock.sidewars.core.Region;
import org.grandknock.sidewars.core.SideWars;
import org.grandknock.sidewars.core.arena.team.TeamPrototype;
import org.grandknock.sidewars.core.storage.StorageHelper;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ArenaPrototype {

    public static ArenaPrototype createNew(String name, Region region) throws IllegalArgumentException {
        ArenaPrototype arena = new ArenaPrototype(name);
        arena.region = region;
        return arena;
    }

    public static ArenaPrototype loadExisting(StorageHelper storage, String name) {
        ArenaPrototype arena = new ArenaPrototype(name);
        storage.readArenaPrototypeBinary(input -> {
            arena.region = Region.load(input);

            int numTeams = input.readInt();
            Set<TeamPrototype> loadedTeams = new HashSet<>(numTeams);
            for (int counter = 0; counter < numTeams; counter++)
                loadedTeams.add(TeamPrototype.loadExisting(storage, name, input.readUTF(), numTeams));
            arena.teams.addAll(loadedTeams);
        }, name);
        arena.updateConfig(storage);
        return arena;
    }

    private String name;

    private final Set<TeamPrototype> teams;

    // Binary properties
    private Region region;

    // Config properties
    private int playersPerTeam;

    private ArenaPrototype(String name) {
        if (!SideWars.isNameAllowed(name))
            throw new Error("Invalid ArenaPrototype name: " + name);
        this.name = name;
        this.teams = new HashSet<>();
    }

    public void updateConfig(StorageHelper storage) {
        storage.getArenaPrototypeConfig(name, this::read, this::writeDefault);
        // TODO Update config of all structures
        for (TeamPrototype team : teams) {
            team.updateConfig(storage);
        }
    }

    private void read(Map<String,Object> config) {
        playersPerTeam = (Integer) config.get("PlayersPerTeam");
    }

    private void writeDefault(Map<String,Object> config) {
        config.put("PlayersPerTeam", 4);
    }

    public void saveData(StorageHelper storage) {
        saveBinaryData(storage);
        // TODO Call saveData of all structures of this ArenaPrototype
        for (TeamPrototype team : teams)
            team.saveData(storage, name);
    }

    private void saveBinaryData(StorageHelper storage) {
        storage.writeArenaPrototypeBinary(output -> {
            region.save(output);

            output.writeInt(teams.size());
            for (TeamPrototype team : teams)
                output.writeUTF(team.getName());
        }, name);
    }

    public String getName() {
        return name;
    }

    public Region getRegion() {
        return region;
    }

    public int getNumberOfTeams() {
        return teams.size();
    }

    public int getPlayersPerTeam() {
        return playersPerTeam;
    }
}

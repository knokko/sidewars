package org.grandknock.sidewars.core.arena.team;

import org.grandknock.sidewars.core.SideWars;
import org.grandknock.sidewars.core.storage.StorageHelper;

public class TeamPrototype {

    public static TeamPrototype createNew(String name, TeamRegion region, int playersPerTeam) {
        TeamPrototype team = new TeamPrototype(name);
        team.region = region;
        team.spawns = new TeamSpawn[playersPerTeam];
        return team;
    }

    public static TeamPrototype loadExisting(StorageHelper storage, String arenaName,
                                             String teamName, int playersPerTeam) {
        TeamPrototype team = new TeamPrototype(teamName);
        storage.readTeamPrototypeBinary(input -> {
            team.region = TeamRegion.load(input);

            team.spawns = new TeamSpawn[playersPerTeam];
            for (int index = 0; index < playersPerTeam; index++)
                if (input.readBoolean())
                    team.spawns[index] = TeamSpawn.load(input);
        }, arenaName, teamName);

        return team;
    }

    private String name;

    private TeamRegion region;
    /**
     * The spawn positions of the players of this team. The length must be equal to the number of
     * players per team. It can contain null, which means that the spawn point for the corresponding
     * player hasn't been set yet.
     */
    private TeamSpawn[] spawns;

    private TeamPrototype(String name) {
        if (!SideWars.isNameAllowed(name))
            throw new Error("Forbidden (team) name: " + name);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void saveData(StorageHelper storage, String arenaName) {
        storage.writeTeamPrototypeBinary(output -> {
            region.save(output);

            for (TeamSpawn spawn : spawns) {
                output.writeBoolean(spawn != null);
                if (spawn != null)
                    spawn.save(output);
            }
        }, arenaName, name);
    }

    public void updateConfig(StorageHelper storage) {
        // TODO Read config
    }
}

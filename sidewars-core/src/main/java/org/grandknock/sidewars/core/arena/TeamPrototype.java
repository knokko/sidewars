package org.grandknock.sidewars.core.arena;

import org.grandknock.sidewars.core.SideWars;
import org.grandknock.sidewars.core.storage.StorageHelper;

public class TeamPrototype {

    public static TeamPrototype createNew(String name) {
        return new TeamPrototype(name);
    }

    public static TeamPrototype loadExisting(StorageHelper storage, String name) {
        // TODO Load the data
        return new TeamPrototype(name);
    }

    private String name;

    private TeamPrototype(String name) {
        if (!SideWars.isNameAllowed(name))
            throw new Error("Forbidden (team) name: " + name);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void saveData(StorageHelper storage) {
        // TODO Save the data
    }

    public void updateConfig(StorageHelper storage) {
        // TODO Read config
    }
}

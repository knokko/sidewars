package org.grandknock.sidewars.core.arena;

import org.grandknock.sidewars.core.Region;

public class ArenaPrototype {

    private String name;

    private Region region;

    public ArenaPrototype(String name, Region region) {
        this.name = name;
        this.region = region;
    }

    public String getName() {
        return name;
    }

    public Region getRegion() {
        return region;
    }
}

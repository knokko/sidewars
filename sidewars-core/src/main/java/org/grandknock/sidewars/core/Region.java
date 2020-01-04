package org.grandknock.sidewars.core;

import java.util.UUID;

public class Region {

    public final int minX, minY, minZ, maxX, maxY, maxZ;
    public final UUID worldID;

    public Region(UUID worldID, int minX, int minY, int minZ, int maxX, int maxY, int maxZ){
        this.worldID = worldID;
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
    }
}

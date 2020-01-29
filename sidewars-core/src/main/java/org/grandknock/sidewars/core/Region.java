package org.grandknock.sidewars.core;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Region {

    public static Region load(DataInputStream input) throws IOException {
        return new Region(input.readUTF(), input.readInt(), input.readInt(), input.readInt(),
                input.readInt(), input.readInt(), input.readInt());
    }

    public final int minX, minY, minZ, maxX, maxY, maxZ;
    public final String worldID;

    public Region(String worldID, int minX, int minY, int minZ, int maxX, int maxY, int maxZ){
        this.worldID = worldID;
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
    }

    public void save(DataOutputStream output) throws IOException {
        output.writeUTF(worldID);
        output.writeInt(minX);
        output.writeInt(minY);
        output.writeInt(minZ);
        output.writeInt(maxX);
        output.writeInt(maxY);
        output.writeInt(maxZ);
    }
}

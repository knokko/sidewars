package org.grandknock.sidewars.core.arena.team;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class TeamRegion {

    public static TeamRegion load(DataInputStream input) throws IOException {
        return new TeamRegion(input.readInt(), input.readInt(), input.readInt(),
                input.readInt(), input.readInt(), input.readInt());
    }

    public final int minX, minY, minZ, maxX, maxY, maxZ;

    public TeamRegion(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
    }

    public void save(DataOutputStream output) throws IOException {
        output.writeInt(minX);
        output.writeInt(minY);
        output.writeInt(minZ);
        output.writeInt(maxX);
        output.writeInt(maxY);
        output.writeInt(maxZ);
    }
}

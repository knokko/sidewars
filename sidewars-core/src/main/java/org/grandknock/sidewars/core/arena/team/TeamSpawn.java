package org.grandknock.sidewars.core.arena.team;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Spawn position for a single player
 */
public class TeamSpawn {

    public static TeamSpawn load(DataInputStream input) throws IOException {
        return new TeamSpawn(input.readInt(), input.readInt(), input.readInt());
    }

    public final int x, y, z;

    public TeamSpawn(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void save(DataOutputStream output) throws IOException {
        output.writeInt(x);
        output.writeInt(y);
        output.writeInt(z);
    }
}

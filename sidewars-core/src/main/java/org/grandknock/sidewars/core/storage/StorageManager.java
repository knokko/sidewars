package org.grandknock.sidewars.core.storage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface StorageManager {

    InputStream readSWBinary() throws IOException;

    OutputStream writeSWBinary() throws IOException;

    boolean hasSWBinary();

    InputStream readSWConfig() throws IOException;

    OutputStream createSWConfig() throws IOException;

    boolean hasSWConfig();

    InputStream readArenaPrototypeConfig(String arenaPrototypeName) throws IOException;

    OutputStream createArenaPrototypeConfig(String arenaPrototypeName) throws IOException;

    boolean hasArenaPrototypeConfig(String arenaPrototypeName);

    InputStream readArenaPrototypeBinary(String arenaPrototypeName) throws IOException;

    OutputStream writeArenaPrototypeBinary(String arenaPrototypeName) throws IOException;

    boolean hasArenaPrototypeBinary(String arenaPrototypeName);

    InputStream readTeamPrototypeConfig(String arenaName, String teamName) throws IOException;

    OutputStream createTeamPrototypeConfig(String arenaName, String teamName) throws IOException;

    boolean hasTeamPrototypeConfig(String arenaName, String teamName);

    InputStream readTeamPrototypeBinary(String arenaName, String teamName) throws IOException;

    OutputStream writeTeamPrototypeBinary(String arenaName, String teamName) throws IOException;

    boolean hasTeamPrototypeBinary(String arenaName, String teamName);
}

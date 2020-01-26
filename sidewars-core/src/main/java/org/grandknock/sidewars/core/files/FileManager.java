package org.grandknock.sidewars.core.files;

import java.io.InputStream;
import java.io.OutputStream;

public interface FileManager {

    InputStream readSideWarsBinary();

    OutputStream writeSideWarsBinary();

    InputStream readSideWarsConfig();

    OutputStream createSideWarsConfig();

    InputStream readArenaPrototypeConfig(String arenaPrototypeName);

    OutputStream writeArenaPrototypeConfig(String arenaPrototypeName);

    InputStream readArenaPrototypeBinary(String arenaPrototypeName);

    OutputStream writeArenaPrototypeBinary(String arenaPrototypeName);
}

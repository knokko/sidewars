package org.grandknock.sidewars.core.files;

import java.io.InputStream;
import java.io.OutputStream;

public class FileDataManager {

    // TODO Add some yaml util to classpath

    private final FileManager files;

    public FileDataManager(FileManager files) {
        this.files = files;
    }

    public void writeSWConfig() {
        // TODO What would the SideWars config be?
    }

    public InputStream readSWBinary() {
        return files.readSWBinary();
    }

    public OutputStream writeSWBinary() {
        return files.writeSWBinary();
    }

    public void writeArenaPrototypeConfig(String arenaPrototypeName) {
        // TODO What would the default arena prototype config be like?
    }

    public InputStream readArenaPrototypeBinary(String arenaPrototypeName) {
        return files.readArenaPrototypeBinary(arenaPrototypeName);
    }

    public OutputStream writeArenaPrototypeBinary(String arenaPrototypeName) {
        return files.writeArenaPrototypeBinary(arenaPrototypeName);
    }
}

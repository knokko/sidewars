package org.grandknock.sidewars.core.storage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class VoidStorageManager implements StorageManager {

    @Override
    public InputStream readSWBinary() throws IOException {
        throw new IOException();
    }

    @Override
    public OutputStream writeSWBinary() throws IOException {
        return new VoidOutputStream();
    }

    @Override
    public boolean hasSWBinary() {
        return false;
    }

    @Override
    public InputStream readSWConfig() throws IOException {
        throw new IOException();
    }

    @Override
    public OutputStream createSWConfig() throws IOException {
        return new VoidOutputStream();
    }

    @Override
    public boolean hasSWConfig() {
        return false;
    }

    @Override
    public InputStream readArenaPrototypeConfig(String arenaPrototypeName) throws IOException {
        throw new IOException();
    }

    @Override
    public OutputStream createArenaPrototypeConfig(String arenaPrototypeName) throws IOException {
        return new VoidOutputStream();
    }

    @Override
    public boolean hasArenaPrototypeConfig(String arenaPrototypeName) {
        return false;
    }

    @Override
    public InputStream readArenaPrototypeBinary(String arenaPrototypeName) throws IOException {
        throw new IOException();
    }

    @Override
    public OutputStream writeArenaPrototypeBinary(String arenaPrototypeName) throws IOException {
        return new VoidOutputStream();
    }

    @Override
    public boolean hasArenaPrototypeBinary(String arenaPrototypeName) {
        return false;
    }

    class VoidOutputStream extends OutputStream {

        @Override
        public void write(int b) throws IOException {
            // The idea of void is that it forgets everything
            // So... just don't store it
        }
    }
}

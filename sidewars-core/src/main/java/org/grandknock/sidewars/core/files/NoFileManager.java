package org.grandknock.sidewars.core.files;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class NoFileManager implements FileManager {

    @Override
    public InputStream readSWBinary() throws IOException {
        throw new IOException();
    }

    @Override
    public OutputStream writeSWBinary() throws IOException {
        throw new IOException();
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
        throw new IOException();
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
        throw new IOException();
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
        throw new IOException();
    }

    @Override
    public boolean hasArenaPrototypeBinary(String arenaPrototypeName) {
        return false;
    }
}

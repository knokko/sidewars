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
    public OutputStream writeSWBinary() {
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
    public OutputStream createSWConfig() {
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
    public OutputStream createArenaPrototypeConfig(String arenaPrototypeName) {
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
    public OutputStream writeArenaPrototypeBinary(String arenaPrototypeName) {
        return new VoidOutputStream();
    }

    @Override
    public boolean hasArenaPrototypeBinary(String arenaPrototypeName) {
        return false;
    }

    @Override
    public InputStream readTeamPrototypeConfig(String arenaName, String teamName) throws IOException {
        throw new IOException();
    }

    @Override
    public OutputStream createTeamPrototypeConfig(String arenaName, String teamName) {
        return new VoidOutputStream();
    }

    @Override
    public boolean hasTeamPrototypeConfig(String arenaName, String teamName) {
        return false;
    }

    @Override
    public InputStream readTeamPrototypeBinary(String arenaName, String teamName) throws IOException {
        throw new IOException();
    }

    @Override
    public OutputStream writeTeamPrototypeBinary(String arenaName, String teamName) {
        return new VoidOutputStream();
    }

    @Override
    public boolean hasTeamPrototypeBinary(String arenaName, String teamName) {
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

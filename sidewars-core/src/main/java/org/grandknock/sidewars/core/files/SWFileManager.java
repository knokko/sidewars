package org.grandknock.sidewars.core.files;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public class SWFileManager implements FileManager{

    private File rootDir;

    public SWFileManager(File rootDir) {
        this.rootDir = rootDir;
    }

    @Override
    public InputStream readSWBinary() throws IOException {
        return null;
    }

    @Override
    public OutputStream writeSWBinary() throws IOException {
        return null;
    }

    @Override
    public boolean hasSWBinary() {
        return false;
    }

    @Override
    public InputStream readSWConfig() throws IOException {
        return null;
    }

    @Override
    public OutputStream createSWConfig() throws IOException {
        return null;
    }

    @Override
    public boolean hasSWConfig() {
        return false;
    }

    @Override
    public InputStream readArenaPrototypeConfig(String arenaPrototypeName) throws IOException {
        return null;
    }

    @Override
    public OutputStream createArenaPrototypeConfig(String arenaPrototypeName) throws IOException {

    }

    @Override
    public boolean hasArenaPrototypeConfig(String arenaPrototypeName) {
        return false;
    }

    @Override
    public InputStream readArenaPrototypeBinary(String arenaPrototypeName) throws IOException {
        return null;
    }

    @Override
    public OutputStream writeArenaPrototypeBinary(String arenaPrototypeName) throws IOException {
        return null;
    }

    @Override
    public boolean hasArenaPrototypeBinary(String arenaPrototypeName) {
        return false;
    }

    private File getSWFile() {
        return null;
    }

    private File getArenaPrototypeFile() {
        return null;
    }

}

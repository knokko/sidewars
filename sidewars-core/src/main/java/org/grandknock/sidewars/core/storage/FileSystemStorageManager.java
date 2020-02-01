package org.grandknock.sidewars.core.storage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

public class FileSystemStorageManager implements StorageManager {

    private File rootDir;

    public FileSystemStorageManager(File rootDir) {
        this.rootDir = rootDir;
    }

    private File getSWConfig() {
        return new File(rootDir + "/config.yml");
    }

    private File getSWBinary() {
        return new File(rootDir + "/data.bin");
    }

    private InputStream createInput(File file) throws IOException {
        return Files.newInputStream(file.toPath());
    }

    private OutputStream createOutput(File file) throws IOException {
        file.getParentFile().mkdirs();
        return Files.newOutputStream(file.toPath());
    }

    @Override
    public InputStream readSWBinary() throws IOException {
        return Files.newInputStream(getSWBinary().toPath());
    }

    @Override
    public OutputStream writeSWBinary() throws IOException {
        return createOutput(getSWBinary());
    }

    @Override
    public boolean hasSWBinary() {
        return getSWBinary().exists();
    }

    @Override
    public InputStream readSWConfig() throws IOException {
        return createInput(getSWConfig());
    }

    @Override
    public OutputStream createSWConfig() throws IOException {
        return createOutput(getSWConfig());
    }

    @Override
    public boolean hasSWConfig() {
        return getSWConfig().exists();
    }

    private File getArenaPrototypeFolder(String name) {
        return new File(rootDir + "/ArenaPrototype/" + name);
    }

    private File getArenaPrototypeConfig(String name) {
        return new File(getArenaPrototypeFolder(name) + "/config.yml");
    }

    private File getArenaPrototypeBinary(String name) {
        return new File(getArenaPrototypeFolder(name) + "/data.bin");
    }

    @Override
    public InputStream readArenaPrototypeConfig(String arenaPrototypeName) throws IOException {
        return createInput(getArenaPrototypeConfig(arenaPrototypeName));
    }

    @Override
    public OutputStream createArenaPrototypeConfig(String arenaPrototypeName) throws IOException {
        return createOutput(getArenaPrototypeConfig(arenaPrototypeName));
    }

    @Override
    public boolean hasArenaPrototypeConfig(String arenaPrototypeName) {
        return getArenaPrototypeConfig(arenaPrototypeName).exists();
    }

    @Override
    public InputStream readArenaPrototypeBinary(String arenaPrototypeName) throws IOException {
        return createInput(getArenaPrototypeBinary(arenaPrototypeName));
    }

    @Override
    public OutputStream writeArenaPrototypeBinary(String arenaPrototypeName) throws IOException {
        return createOutput(getArenaPrototypeBinary(arenaPrototypeName));
    }

    @Override
    public boolean hasArenaPrototypeBinary(String arenaPrototypeName) {
        return getArenaPrototypeBinary(arenaPrototypeName).exists();
    }
}

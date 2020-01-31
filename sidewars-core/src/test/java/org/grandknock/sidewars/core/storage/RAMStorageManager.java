package org.grandknock.sidewars.core.storage;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class RAMStorageManager implements StorageManager {

    private byte[] swBinary, swConfig;

    private final Map<String,byte[]> arenaBinaries = new HashMap<>();
    private final Map<String,byte[]> arenaConfigs = new HashMap<>();

    @Override
    public InputStream readSWBinary() throws IOException {
        if (!hasSWBinary())
            throw new IOException("SideWars binary doesn't exist");
        return new ByteArrayInputStream(swBinary);
    }

    @Override
    public OutputStream writeSWBinary() {
        return new RAMOutputStream(bytes -> swBinary = bytes);
    }

    @Override
    public boolean hasSWBinary() {
        return swBinary != null;
    }

    @Override
    public InputStream readSWConfig() throws IOException {
        if (!hasSWConfig())
            throw new IOException("SideWars config doesn't exist");
        return new ByteArrayInputStream(swConfig);
    }

    @Override
    public OutputStream createSWConfig() {
        return new RAMOutputStream(bytes -> swConfig = bytes);
    }

    @Override
    public boolean hasSWConfig() {
        return swConfig != null;
    }

    @Override
    public InputStream readArenaPrototypeConfig(String name) throws IOException {
        if (!hasArenaPrototypeConfig(name))
            throw new IOException("This arena prototype doesn't exist");
        return new ByteArrayInputStream(arenaConfigs.get(name));
    }

    @Override
    public OutputStream createArenaPrototypeConfig(String name) {
        return new RAMOutputStream(bytes -> arenaConfigs.put(name, bytes));
    }

    @Override
    public boolean hasArenaPrototypeConfig(String arenaPrototypeName) {
        return arenaConfigs.containsKey(arenaPrototypeName);
    }

    @Override
    public InputStream readArenaPrototypeBinary(String arenaPrototypeName) throws IOException {
        if (!hasArenaPrototypeBinary(arenaPrototypeName))
            throw new IOException("Doesn't have binary for this arena prototype");
        return new ByteArrayInputStream(arenaBinaries.get(arenaPrototypeName));
    }

    @Override
    public OutputStream writeArenaPrototypeBinary(String arenaPrototypeName) {
        return new RAMOutputStream(bytes -> arenaBinaries.put(arenaPrototypeName, bytes));
    }

    @Override
    public boolean hasArenaPrototypeBinary(String arenaPrototypeName) {
        return arenaBinaries.containsKey(arenaPrototypeName);
    }

    private static class RAMOutputStream extends ByteArrayOutputStream {

        private final Consumer<byte[]> onClose;

        private RAMOutputStream(Consumer<byte[]> onClose) {
            super();
            this.onClose = onClose;
        }

        @Override
        public void close() {
            onClose.accept(toByteArray());
        }
    }
}

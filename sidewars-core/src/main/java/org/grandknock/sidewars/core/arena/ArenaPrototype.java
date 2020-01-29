package org.grandknock.sidewars.core.arena;

import org.grandknock.sidewars.core.Region;
import org.grandknock.sidewars.core.storage.StorageHelper;

public class ArenaPrototype {

    private String name;

    private Region region;

    public ArenaPrototype(String name, Region region) {
        this.name = name;
        this.region = region;
    }

    public ArenaPrototype(String name, StorageHelper dataStorage) {
        this.name = name;
        dataStorage.readArenaPrototypeBinary(input -> {
            this.region = Region.load(input);
        }, name);
    }

    public void saveData(StorageHelper storage) {
        saveBinaryData(storage);
        // TODO Call saveData of all structures of this ArenaPrototype
    }

    private void saveBinaryData(StorageHelper storage) {
        storage.writeArenaPrototypeBinary(output -> {
            region.save(output);
        }, name);
    }

    public String getName() {
        return name;
    }

    public Region getRegion() {
        return region;
    }
}

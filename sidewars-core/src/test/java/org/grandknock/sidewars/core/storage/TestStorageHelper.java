package org.grandknock.sidewars.core.storage;

import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static org.junit.Assert.*;

public class TestStorageHelper {

    /*
     * This method currently uses getSWConfig() to test getConfig() because it simply is more
     * convenient for testing.
     */
    @Test
    public void testGetConfigBasic() {
        StorageHelper storage = new StorageHelper(new RAMStorageManager());
        boolean[] called = {false, false};

        // Initially, the config will not exist yet, so it will have to be created and then read
        storage.getSWConfig(config -> {
            assertTrue(called[1]);
            assertEquals(10, config.get("var1"));
            assertEquals("Hello", config.get("var2"));
            assertEquals(3.14, config.get("var3"));
            called[0] = true;
        }, config -> {
            assertFalse(called[0]);
            assertTrue(config.isEmpty());
            config.put("var1", 10);
            config.put("var2", "Hello");
            config.put("var3", 3.14);
            called[1] = true;
        });
        assertTrue(called[0]);
        assertTrue(called[1]);

        // Now, the config will exist already, so only the reading code should be executed
        called[0] = false;
        called[1] = false;
        storage.getSWConfig(config -> {
            assertEquals(10, config.get("var1"));
            assertEquals("Hello", config.get("var2"));
            assertEquals(3.14, config.get("var3"));
            called[0] = true;
        }, config -> fail());
        assertTrue(called[0]);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testGetConfigDeep() {
        StorageHelper storage = new StorageHelper(new VoidStorageManager());
        boolean[] called = {false};
        storage.getSWConfig(config -> {
            assertEquals("level0", config.get("var"));
            Map<String,Object> firstLevel = (Map<String, Object>) config.get("level");
            assertEquals("level1", firstLevel.get("var"));
            Map<String,Object> secondLevel = (Map<String, Object>) firstLevel.get("level");
            assertEquals("level2", secondLevel.get("var"));
            called[0] = true;
        }, config -> {
            Map<String,Object> level2 = new HashMap<>();
            level2.put("var", "level2");
            Map<String,Object> level1 = new HashMap<>();
            level1.put("level", level2);
            level1.put("var", "level1");
            config.put("level", level1);
            config.put("var", "level0");
        });

        assertTrue(called[0]);
    }

    @Test(expected=Error.class)
    public void testGetConfigWriteDetection1() {
        StorageHelper storage = new StorageHelper(new VoidStorageManager());
        storage.getSWConfig(config -> config.put("test", 123), config -> config.get("test"));
    }

    @Test(expected=Error.class)
    public void testGetConfigWriteDetection2() {
        StorageHelper storage = new StorageHelper(new RAMStorageManager());
        storage.getSWConfig(config -> config.get("test"), config -> config.put("test", 123));
        storage.getSWConfig(config -> config.put("test2", 1234), config -> config.get("test2"));
    }

    // ClassCastException's caused by the default writer should NOT be caught
    @Test(expected=ClassCastException.class)
    public void testGetConfigClassCast1() {
        StorageHelper storage = new StorageHelper(new VoidStorageManager());
        storage.getSWConfig(config -> assertEquals(10, (int) (Integer) config.get("test")),
                config -> config.put("test", "10"));
    }

    @Test
    public void testGetConfigClassCast2() throws IOException {
        RAMStorageManager ramStorage = new RAMStorageManager();
        StorageHelper storage = new StorageHelper(ramStorage);

        OutputStream configOutput = ramStorage.createSWConfig();
        Map<String,Object> badConfig = new HashMap<>();
        badConfig.put("test", "10");
        Yaml yaml = new Yaml();
        yaml.dump(badConfig, new OutputStreamWriter(configOutput));
        configOutput.flush();
        configOutput.close();

        int[] callCounter = {0, 0};

        // The ClassCastException thrown by the config reader should be caught
        storage.getSWConfig(config -> {
            callCounter[0]++;
            assertEquals(10, (int) (Integer) config.get("test"));
        }, config -> {
            config.put("test", 10);
            callCounter[1]++;
        });

        assertEquals(2, callCounter[0]);
        assertEquals(1, callCounter[1]);

        // Finally check that the old (bad) config was preserved
        storage.getSWConfig(config -> assertEquals("10", config.get("test")),
                config -> fail());
    }

    @Test
    public void testGetConfigEmpty() throws IOException {
        RAMStorageManager ramStorage = new RAMStorageManager();
        StorageHelper storage = new StorageHelper(ramStorage);

        // Create an empty config
        ramStorage.createSWConfig().close();

        // It must 'think' that there is a config
        assertTrue(ramStorage.hasSWConfig());

        // This must NOT throw an error, but instead load default values
        storage.getSWConfig(config -> assertEquals(3.14, config.get("test")),
                config -> config.put("test", 3.14));
    }

    @Test
    public void testGetConfigString() throws IOException {
        RAMStorageManager ramStorage = new RAMStorageManager();
        StorageHelper storage = new StorageHelper(ramStorage);

        // Create a string config
        Yaml yaml = new Yaml();
        OutputStream output = ramStorage.createSWConfig();
        yaml.dump("hello", new OutputStreamWriter(output));
        output.flush();
        output.close();

        // It must 'think' that there is a config
        assertTrue(ramStorage.hasSWConfig());

        // This must NOT throw an error, but instead load default values
        storage.getSWConfig(config -> assertEquals(3.14, config.get("test")),
                config -> config.put("test", 3.14));
    }

    @Test
    public void testGetConfigInvalid() throws IOException {
        RAMStorageManager ramStorage = new RAMStorageManager();
        StorageHelper storage = new StorageHelper(ramStorage);

        // Create an invalid config
        OutputStream output = ramStorage.createSWConfig();
        for (int i = 0; i < 200; i++) {
            output.write(i);
        }
        output.flush();
        output.close();

        // It must 'think' that there is a config
        assertTrue(ramStorage.hasSWConfig());

        // This must NOT throw an error, but instead load default values
        storage.getSWConfig(config -> assertEquals(3.14, config.get("test")),
                config -> config.put("test", 3.14));
    }

    private void dumpBinary(OutputStream output) throws IOException {
        for (int x = 10; x < 500; x++) {
            output.write(x);
        }
    }

    private void checkBinary(InputStream input) throws IOException {
        for (int x = 10; x < 500; x++) {
            assertEquals(x, input.read());
        }
    }
}

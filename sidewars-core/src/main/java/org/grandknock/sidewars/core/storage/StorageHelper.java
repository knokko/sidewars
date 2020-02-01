package org.grandknock.sidewars.core.storage;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class StorageHelper {

    private final StorageManager files;
    private final Yaml yaml;

    public StorageHelper(StorageManager files) {
        this.files = files;
        this.yaml = new Yaml();
    }

    @SuppressWarnings("unchecked")
    private Map<String,Object> parseYaml(InputStream input) throws YAMLException, IOException {
        Object config = yaml.load(input);
        input.close();
        if (config instanceof Map) {

            // I can't use instanceof with generics, so this will have to do...
            return (Map<String, Object>) config;
        } else if (config == null) {
            throw new YAMLException("Couldn't parse configuration file");
        } else {
            throw new YAMLException("The config should be a map");
        }
    }

    private void readBinary(DataReader reader, InputSupplier is, String purpose) {
        try (InputStream input = is.createInput()) {
            reader.read(new DataInputStream(input));
        } catch (IOException io) {
            System.err.println("Failed to load binary data for " + purpose);
            io.printStackTrace();
        }
    }

    private void writeBinary(DataWriter writer, OutputSupplier os, String purpose){
        try(OutputStream output = os.createOutput()) {
            DataOutputStream dataOutput = new DataOutputStream(output);
            writer.write(dataOutput);
            dataOutput.flush();
        } catch (IOException io) {
            System.err.println("Failed to save binary data for " + purpose);
            io.printStackTrace();
        }
    }

    private void writeYaml(OutputStream output, Map<String,Object> data) throws IOException {
        yaml.dump(data, new OutputStreamWriter(output));
        output.flush();
        output.close();
    }

    private void getConfig(ConfigReader reader, ConfigWriter defaultWriter, boolean exists,
                           InputSupplier configSource, OutputSupplier configDest, String purpose) {
        if (exists) {

            // Try to read from the existing config
            Map<String,Object> config = null;
            try {
                config = parseYaml(configSource.createInput());
            } catch (IOException io) {
                System.err.println("Failed to load config for " + purpose);
                io.printStackTrace();
            } catch (YAMLException yaml) {
                System.err.println("The config for " + purpose + " seems to be invalid yaml: " + yaml.getMessage());
            }

            boolean readSuccess = false;

            if (config != null) {

                // Actually process the config data
                int oldSize = config.size();
                try {
                    reader.read(config);
                    readSuccess = true;
                } catch (ClassCastException ex) {

                    /*
                     * Here, we catch ClassCastException because it can be caused by bad config files
                     * that have for instance a String value when it should have been an integer.
                     */
                    System.err.println("The config for " + purpose + " has an incorrect type for a key");
                    ex.printStackTrace();
                }

                // Check that the reading code didn't write anything
                // This is a check to detect programming errors where the reading code is swapped with the writing code
                int newSize = config.size();
                if (oldSize != newSize) {
                    throw new Error("The config reading code for " + purpose + " shouldn't write to the config");
                }
            }

            // If the reading of the config failed, we will use the default config
            if (!readSuccess) {
                Map<String,Object> defaultConfig = new HashMap<>();
                defaultWriter.writeDefault(defaultConfig);
                reader.read(defaultConfig);
            }
        } else {

            // Create and write to the config in memory
            Map<String,Object> config = new HashMap<>();
            defaultWriter.writeDefault(config);

            int oldSize = config.size();

            // Let the reader read the values from the default config
            // We don't catch ClassCastException because that would indicate a programming error
            reader.read(config);

            // Check that the reader didn't write anything
            int newSize = config.size();
            if (oldSize != newSize) {
                throw new Error("Reader for config " + purpose + " shouldn't write to it");
            }

            try {
                writeYaml(configDest.createOutput(), config);
            } catch (IOException io) {
                System.err.println("Failed to create default config for " + purpose);
                io.printStackTrace();
            }
        }
    }

    public void getSWConfig(ConfigReader reader, ConfigWriter writer) {
        getConfig(reader, writer, files.hasSWConfig(), files::readSWConfig, files::createSWConfig,
                "SideWars");
    }

    public void readSWBinary(DataReader reader, Runnable notFoundAction) {
        if (files.hasSWBinary()) {
            readBinary(reader, files::readSWBinary, "SideWars binary");
        } else {
            notFoundAction.run();
        }
    }

    public void writeSWBinary(DataWriter writer) {
        writeBinary(writer, files::writeSWBinary, "SideWars binary");
    }

    public void getArenaPrototypeConfig(String name, ConfigReader reader, ConfigWriter writer) {
        getConfig(reader, writer, files.hasArenaPrototypeConfig(name),
                () -> files.readArenaPrototypeConfig(name), () -> files.createArenaPrototypeConfig(name),
                "Arena prototype " + name);
    }

    public void readArenaPrototypeBinary(DataReader reader, String name) {
        readBinary(reader, () -> files.readArenaPrototypeBinary(name), "Arena prototype " + name);
    }

    public void writeArenaPrototypeBinary(DataWriter writer, String name) {
        writeBinary(writer, () -> files.writeArenaPrototypeBinary(name), "Arena prototype " + name);
    }

    public void getTeamPrototypeConfig(String arenaName, String teamName, ConfigReader reader,
                                       ConfigWriter writer) {
        getConfig(reader, writer, files.hasTeamPrototypeConfig(arenaName, teamName),
                () -> files.readTeamPrototypeConfig(arenaName, teamName),
                () -> files.createTeamPrototypeConfig(arenaName, teamName),
                "TeamPrototype " + teamName + " for ArenaPrototype " + arenaName);
    }

    public void readTeamPrototypeBinary(DataReader reader, String arenaName, String teamName) {
        readBinary(reader, () -> files.readTeamPrototypeBinary(arenaName, teamName),
                "TeamPrototype " + teamName + " for ArenaPrototype " + arenaName);
    }

    public void writeTeamPrototypeBinary(DataWriter writer, String arenaName, String teamName) {
        writeBinary(writer, () -> files.writeTeamPrototypeBinary(arenaName, teamName),
                "TeamPrototype " + teamName + " for ArenaPrototype " + arenaName);
    }

    @FunctionalInterface
    public interface DataWriter {

        void write(DataOutputStream output) throws IOException;
    }

    @FunctionalInterface
    private interface OutputSupplier {

        OutputStream createOutput() throws IOException;
    }

    @FunctionalInterface
    public interface DataReader {

        void read(DataInputStream input) throws IOException;
    }

    @FunctionalInterface
    private interface InputSupplier {

        InputStream createInput() throws IOException;
    }

    @FunctionalInterface
    public interface ConfigWriter {

        void writeDefault(Map<String,Object> config);
    }

    @FunctionalInterface
    public interface ConfigReader {

        void read(Map<String,Object> config) throws ClassCastException;
    }
}

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
        } else {
            throw new YAMLException("Couldn't parse configuration file");
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

    public Map<String,Object> readSWConfig() throws IOException, YAMLException {
        return parseYaml(files.readSWConfig());
    }

    public void writeSWConfig() throws IOException {
        // TODO Add stuff to the default config
        writeYaml(files.createSWConfig(), new HashMap<>());
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

    public void createArenaPrototypeConfig(String arenaPrototypeName) throws IOException {
        // TODO What would the default arena prototype config be like?
        writeYaml(files.createArenaPrototypeConfig(arenaPrototypeName), new HashMap<>());
    }

    public void readArenaPrototypeBinary(DataReader reader, String name) {
        readBinary(reader, () -> files.readArenaPrototypeBinary(name), "Arena prototype " + name);
    }

    public void writeArenaPrototypeBinary(DataWriter writer, String name) {
        writeBinary(writer, () -> files.writeArenaPrototypeBinary(name), "Arena prototype " + name);
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
}

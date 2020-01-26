package org.grandknock.sidewars.core.files;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FileDataManager {

    private final FileManager files;
    private final Yaml yaml;

    public FileDataManager(FileManager files) {
        this.files = files;
        this.yaml = new Yaml();
    }

    private Map<String,Object> parseYaml(InputStream input) throws YAMLException {
        Object config = yaml.load(input);
        if (config instanceof Map) {

            // I can't use instanceof with generics, so this will have to do...
            return (Map<String, Object>) config;
        } else {
            throw new YAMLException("Couldn't parse configuration file");
        }
    }

    private void writeYaml(OutputStream output, Map<String,Object> data) throws IOException {
        yaml.dump(data, new OutputStreamWriter(output));
    }

    public Map<String,Object> readSWConfig() throws IOException, YAMLException {
        return parseYaml(files.readSWConfig());
    }

    public void writeSWConfig() throws IOException {
        // TODO Add stuff to the default config
        writeYaml(files.createSWConfig(), new HashMap<>());
    }

    public InputStream readSWBinary() throws IOException {
        return files.readSWBinary();
    }

    public OutputStream writeSWBinary() throws IOException {
        return files.writeSWBinary();
    }

    public void writeArenaPrototypeConfig(String arenaPrototypeName) throws IOException {
        // TODO What would the default arena prototype config be like?
        writeYaml(files.createArenaPrototypeConfig(arenaPrototypeName), new HashMap<>());
    }

    public InputStream readArenaPrototypeBinary(String arenaPrototypeName) throws IOException {
        return files.readArenaPrototypeBinary(arenaPrototypeName);
    }

    public OutputStream writeArenaPrototypeBinary(String arenaPrototypeName) throws IOException {
        return files.writeArenaPrototypeBinary(arenaPrototypeName);
    }
}

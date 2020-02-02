package org.grandknock.sidewars.core;

import org.junit.Test;

import java.io.*;

import static org.junit.Assert.assertEquals;

public class TestRegion {

    @Test
    public void testConstructor() {
        Region region = new Region("test", 1, 2, 3, 4 ,5, 6);
        assertEquals("test", region.worldID);
        assertEquals(1, region.minX);
        assertEquals(2, region.minY);
        assertEquals(3, region.minZ);
        assertEquals(4, region.maxX);
        assertEquals(5, region.maxY);
        assertEquals(6, region.maxZ);
    }

    @Test
    public void testSaveLoad() throws IOException {
        Region initial = new Region("test", 1, 2, 3, 4 ,5, 6);
        ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
        initial.save(new DataOutputStream(byteOutput));
        byteOutput.write(100);
        ByteArrayInputStream byteInput = new ByteArrayInputStream(byteOutput.toByteArray());
        Region loaded = Region.load(new DataInputStream(byteInput));
        assertEquals("test", loaded.worldID);
        assertEquals(1, loaded.minX);
        assertEquals(2, loaded.minY);
        assertEquals(3, loaded.minZ);
        assertEquals(4, loaded.maxX);
        assertEquals(5, loaded.maxY);
        assertEquals(6, loaded.maxZ);
        assertEquals(100, byteInput.read());
    }
}

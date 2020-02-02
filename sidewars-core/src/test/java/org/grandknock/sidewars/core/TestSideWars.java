package org.grandknock.sidewars.core;

import com.sk89q.worldedit.LocalSession;
import org.grandknock.sidewars.core.arena.ArenaPrototype;
import org.grandknock.sidewars.core.entity.SWPlayer;
import org.grandknock.sidewars.core.storage.RAMStorageManager;
import org.grandknock.sidewars.core.storage.VoidStorageManager;
import org.junit.Test;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.grandknock.sidewars.core.SideWars.isNameAllowed;
import static org.junit.Assert.*;

public class TestSideWars {

    @Test
    public void testIsNameAllowed() {

        assertTrue(isNameAllowed("hello"));
        assertTrue(isNameAllowed("hello_world"));
        assertTrue(isNameAllowed("test1234"));
        assertTrue(isNameAllowed("0"));

        assertFalse(isNameAllowed("Hello"));
        assertFalse(isNameAllowed(("hello?")));
        assertFalse(isNameAllowed(("test-1234")));
        assertFalse(isNameAllowed("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));

        assertFalse(isNameAllowed(null));
        assertFalse(isNameAllowed(""));
    }

    @Test
    public void testGetSession() {
        SideWars sideWars = new SideWars(new VoidStorageManager());
        SWPlayer player1 = new DummyPlayer(1);
        SWPlayer player2 = new DummyPlayer(2);
        Region region = new Region("world", 1, 2, 3, 4, 5, 6);
        ArenaPrototype arena1 = ArenaPrototype.createNew("a1", region);
        ArenaPrototype arena2 = ArenaPrototype.createNew("a2", region);

        assertFalse(sideWars.getSession(player1).isEditingArena());
        sideWars.getSession(player1).setArenaToEdit(arena1);
        assertEquals(arena1, sideWars.getSession(player1).getArenaToEdit());
        assertFalse(sideWars.getSession(player2).isEditingArena());
        sideWars.getSession(player2).setArenaToEdit(arena2);
        assertEquals(arena2, sideWars.getSession(player2).getArenaToEdit());
        assertEquals(arena1, sideWars.getSession(player1).getArenaToEdit());
    }

    @Test
    public void testArenaPrototypes() {
        LastArenaStorageManager storage = new LastArenaStorageManager();
        SideWars sideWars = new SideWars(storage);
        Region riftRegion = new Region("rift_world", 0, 0, 0, 100, 10, 100);
        Region swampRegion = new Region("swamp_world", -10, 20, -10, 30, 40, 50);
        ArenaPrototype rift1 = ArenaPrototype.createNew("rift", riftRegion);
        ArenaPrototype rift2 = ArenaPrototype.createNew("rift", riftRegion);
        ArenaPrototype swamp = ArenaPrototype.createNew("swamp", swampRegion);

        assertNull(storage.lastWrittenArenaConfig);
        sideWars.addArenaPrototype(swamp);
        assertEquals("swamp", storage.lastWrittenArenaConfig);
        sideWars.addArenaPrototype(rift1);
        assertEquals("rift", storage.lastWrittenArenaConfig);
        storage.lastWrittenArenaConfig = null;
        try {
            sideWars.addArenaPrototype(rift2);
            fail();
        } catch (IllegalArgumentException args) {
            // We are expecting an IllegalArgumentException
        }
        assertNull(storage.lastWrittenArenaConfig);

        assertSame(swamp, sideWars.getArenaPrototype("swamp"));
        assertSame(rift1, sideWars.getArenaPrototype("rift"));
        assertNull(sideWars.getArenaPrototype("nope"));

        List<ArenaPrototype> arenas = new ArrayList<>(2);
        for (ArenaPrototype arena : sideWars.getArenaPrototypes())
            arenas.add(arena);

        assertEquals(2, arenas.size());
        assertTrue(arenas.contains(swamp));
        assertTrue(arenas.contains(rift1));
    }

    @Test
    public void testSaveLoad() {
        SideWars sideWars = new SideWars(new RAMStorageManager());

    }

    private static class DummyPlayer implements SWPlayer {

        private final UUID id;

        private DummyPlayer(long number) {
            id = new UUID(number, ++number);
        }

        @Override
        public String getWorldID() {
            return "test";
        }

        @Override
        public UUID getPlayerID() {
            return id;
        }

        @Override
        public void sendMessage(MessageType type, String message) {
            throw new UnsupportedOperationException();
        }

        @Override
        public LocalSession getWorldEditSession() {
            return null;
        }

        @Override
        public boolean hasPermission(String permission) {
            return false;
        }
    }

    private static class LastArenaStorageManager extends VoidStorageManager {

        private String lastWrittenArenaConfig = null;

        @Override
        public OutputStream createArenaPrototypeConfig(String arenaName) {
            lastWrittenArenaConfig = arenaName;
            return super.createArenaPrototypeConfig(arenaName);
        }
    }
}

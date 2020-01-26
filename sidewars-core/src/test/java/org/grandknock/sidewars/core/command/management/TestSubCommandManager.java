package org.grandknock.sidewars.core.command.management;

import com.sk89q.worldedit.LocalSession;
import org.grandknock.sidewars.core.command.SWCommandSender;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestSubCommandManager {

    private static final String[] JUST_FIRST = { "first" };
    private static final String[] JUST_SECOND = { "second" };
    private static final String[] JUST_THIRD = { "third" };

    private static final SWCommandSender TEST_SENDER = new SWCommandSender() {

        @Override
        public boolean hasPermission(String permission) {
            return true;
        }

        @Override
        public void sendMessage(MessageType type, String message) {
            throw new UnsupportedOperationException("No messages in these test cases");
        }

        @Override
        public LocalSession getWorldEditSession() {
            throw new UnsupportedOperationException("No sessions in these test cases");
        }
    };

    @Test
    public void testExecutes() {
        DummyCommands dummy = new DummyCommands();
        SubCommandManager manager = new SubCommandManager(dummy);

        // Initial testValue is 0
        assertEquals(0, dummy.testValue);

        // Execute first should increment it by 1
        assertTrue(manager.execute(JUST_FIRST, TEST_SENDER));
        assertEquals(1, dummy.testValue);

        // Execute second should increase it from 1 to 6
        assertTrue(manager.execute(JUST_SECOND, TEST_SENDER));
        assertEquals(6, dummy.testValue);

        // There is no third subcommand
        assertFalse(manager.execute(JUST_THIRD, TEST_SENDER));
        assertEquals(6, dummy.testValue);
    }

    @Test
    public void testArgs() {

        String[] empty = { "remember" };
        String[] notEmpty = { "remember", "something", "also" };

        RememberArgsCommand remember = new RememberArgsCommand();
        SubCommandManager manager = new SubCommandManager(remember);

        // prevArgs should initially be null
        assertNull(remember.prevArgs);

        // Now prevArgs should become an empty string array
        manager.execute(empty, TEST_SENDER);
        assertArrayEquals(new String[0], remember.prevArgs);

        // Now prevArgs should become a non-empty string array
        manager.execute(notEmpty, TEST_SENDER);
        assertArrayEquals(new String[] { "something", "also" }, remember.prevArgs);
    }

    private static class DummyCommands {

        int testValue = 0;

        @SubCommand(name="first")
        public void executeFirst(String[] subArgs, SWCommandSender sender) {
            testValue++;
        }

        @SubCommand(name="second")
        public void executeSecond(String[] subArgs, SWCommandSender sender) {
            testValue += 5;
        }
    }

    private static class RememberArgsCommand {

        String[] prevArgs = null;

        @SubCommand(name="remember")
        public void remember(String[] subArgs, SWCommandSender sender) {
            prevArgs = subArgs;
        }
    }
}

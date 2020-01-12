package org.grandknock.sidewars.core.command.management;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class TestSubCommandManager {

    private static final String[] JUST_FIRST = { "first" };
    private static final String[] JUST_SECOND = { "second" };
    private static final String[] JUST_THIRD = { "third" };

    @Test
    public void testExecutes() {
        DummyCommands dummy = new DummyCommands();
        SubCommandManager manager = new SubCommandManager(dummy);

        // Initial testValue is 0
        assertEquals(0, dummy.testValue);

        // Execute first should increment it by 1
        assertTrue(manager.execute(JUST_FIRST));
        assertEquals(1, dummy.testValue);

        // Execute second should increase it from 1 to 6
        assertTrue(manager.execute(JUST_SECOND));
        assertEquals(6, dummy.testValue);

        // There is no third subcommand
        assertFalse(manager.execute(JUST_THIRD));
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
        manager.execute(empty);
        assertArrayEquals(new String[0], remember.prevArgs);

        // Now prevArgs should become a non-empty string array
        manager.execute(notEmpty);
        assertArrayEquals(new String[] { "something", "also" }, remember.prevArgs);
    }

    private static class DummyCommands {

        int testValue = 0;

        @SubCommand(name="first")
        public void executeFirst(String[] subArgs) {
            testValue++;
        }

        @SubCommand(name="second")
        public void executeSecond(String[] subArgs) {
            testValue += 5;
        }
    }

    private static class RememberArgsCommand {

        String[] prevArgs = null;

        @SubCommand(name="remember")
        public void remember(String[] subArgs) {
            prevArgs = subArgs;
        }
    }
}

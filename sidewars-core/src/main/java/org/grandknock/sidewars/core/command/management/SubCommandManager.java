package org.grandknock.sidewars.core.command.management;

import org.grandknock.sidewars.core.command.SWCommandSender;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class SubCommandManager {

    private final Collection<SubCommandEntry> subCommands;

    public SubCommandManager(Object...commandOwners) {
        subCommands = new ArrayList<>();

        for (Object owner : commandOwners) {

            // Examine all methods and check which have the right annotation
            Method[] methods = owner.getClass().getMethods();
            for (Method method : methods) {

                // We are only interested in the methods with the SubCommand annotation
                SubCommand maybeSub = method.getAnnotation(SubCommand.class);
                if (maybeSub != null) {
                    subCommands.add(new SubCommandEntry(method, owner, maybeSub));
                }
            }
        }
    }

    public boolean execute(String[] args, SWCommandSender sender) {

        // If there are no arguments, no sub command can be executed
        if (args.length == 0) {
            return false;
        }

        // Check if any sub command matches the first argument
        for (SubCommandEntry sub : subCommands) {
            if (sub.meta.name().equals(args[0])) {

                // If so, execute it and return true
                sub.execute(Arrays.copyOfRange(args, 1, args.length), sender);
                return true;
            }
        }

        // If no sub command matched the first argument, we return false
        return false;
    }

    private static class SubCommandEntry {

        final Method method;
        final Object methodOwner;

        final SubCommand meta;

        SubCommandEntry(Method method, Object methodOwner, SubCommand meta) {
            this.method = method;
            this.methodOwner = methodOwner;
            this.meta = meta;
        }

        void execute(String[] subArgs, SWCommandSender sender) {
            try {
                method.invoke(methodOwner, subArgs, sender);
            } catch (IllegalAccessException ex) {
                throw new Error(ex);
            } catch (InvocationTargetException ex) {
                throw new SubCommandException(meta.name(), ex);
            }
        }
    }
}

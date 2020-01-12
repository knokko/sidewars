package org.grandknock.sidewars.core.command.management;

import java.lang.reflect.InvocationTargetException;

public class SubCommandException extends RuntimeException {

    public SubCommandException(String name, InvocationTargetException cause){
        super(name, cause);
    }
}

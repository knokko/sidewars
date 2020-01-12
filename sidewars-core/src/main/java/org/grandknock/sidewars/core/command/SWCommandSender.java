package org.grandknock.sidewars.core.command;

public interface SWCommandSender {

    boolean hasPermission(String permission);

    void sendMessage(MessageType type, String message);

    enum MessageType {

        ERROR,
        SUCCESS,
        INFO
    }
}

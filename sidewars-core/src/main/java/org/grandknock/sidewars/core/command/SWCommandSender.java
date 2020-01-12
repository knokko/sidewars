package org.grandknock.sidewars.core.command;

public interface SWCommandSender extends PermissionOwner {

    void sendMessage(MessageType type, String message);

    enum MessageType {

        ERROR,
        SUCCESS,
        INFO
    }
}

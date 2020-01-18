package org.grandknock.sidewars.core.command;

import com.sk89q.worldedit.LocalSession;

public interface SWCommandSender extends PermissionOwner {

    void sendMessage(MessageType type, String message);

    /**
     * Gets the WorldEdit session of this command sender if it has one.
     * If this command sender doesn't have a WorldEdit session, this method returns null.
     */
    LocalSession getWorldEditSession();

    enum MessageType {

        ERROR,
        SUCCESS,
        INFO
    }
}

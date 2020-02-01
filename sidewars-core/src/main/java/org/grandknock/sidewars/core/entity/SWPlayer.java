package org.grandknock.sidewars.core.entity;

import org.grandknock.sidewars.core.command.SWCommandSender;

import java.util.UUID;

public interface SWPlayer extends SWCommandSender {

    String getWorldID();

    UUID getPlayerID();
}

package org.grandknock.sidewars.core.entity;

import org.grandknock.sidewars.core.command.PermissionOwner;
import org.grandknock.sidewars.core.command.SWCommandSender;

public class SWPlayer implements SWCommandSender {

    private final PermissionOwner permissions;

    public SWPlayer(PermissionOwner permissions) {
        this.permissions = permissions;
    }

    @Override
    public boolean hasPermission(String permission) {
        return permissions.hasPermission(permission);
    }

    @Override
    public void sendMessage(MessageType type, String message) {

    }
}

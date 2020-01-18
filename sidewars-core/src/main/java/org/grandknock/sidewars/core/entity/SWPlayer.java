package org.grandknock.sidewars.core.entity;

import com.sk89q.worldedit.LocalSession;
import org.grandknock.sidewars.core.command.PermissionOwner;
import org.grandknock.sidewars.core.command.SWCommandSender;
import org.grandknock.sidewars.core.command.WESessionOwner;

public class SWPlayer implements SWCommandSender {

    private final PermissionOwner permissions;
    private final WESessionOwner weSessionOwner;

    public SWPlayer(PermissionOwner permissions, WESessionOwner weSessionOwner) {
        this.permissions = permissions;
        this.weSessionOwner = weSessionOwner;
    }

    @Override
    public boolean hasPermission(String permission) {
        return permissions.hasPermission(permission);
    }

    @Override
    public void sendMessage(MessageType type, String message) {

    }

    @Override
    public LocalSession getWorldEditSession() {
        return weSessionOwner.getSession();
    }
}

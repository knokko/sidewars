package org.grandknock.sidewars.core.session;

import org.grandknock.sidewars.core.arena.ArenaPrototype;

public class Session {

    private ArenaPrototype arenaToEdit;

    public Session() {
        // arenaToEdit stays null
    }

    public boolean isEditingArena() {
        return arenaToEdit != null;
    }

    public ArenaPrototype getArenaToEdit() throws IllegalStateException {
        if (arenaToEdit == null)
            throw new IllegalStateException("This player is currently not editing an arena");
        return arenaToEdit;
    }

    public void setArenaToEdit(ArenaPrototype newArena) throws NullPointerException {
        if (newArena == null)
            throw new NullPointerException("newArena is null");
        arenaToEdit = newArena;
    }

    public void stopEditingArena() throws IllegalStateException {
        if (arenaToEdit == null)
            throw new IllegalStateException("This player is not editing an arena");
        arenaToEdit = null;
    }
}

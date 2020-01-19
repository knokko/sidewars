package org.grandknock.sidewars.core.command.arena;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.session.SessionOwner;
import org.grandknock.sidewars.core.SideWars;
import org.grandknock.sidewars.core.arena.ArenaPrototype;
import org.grandknock.sidewars.core.command.SWCommandSender;
import org.grandknock.sidewars.core.command.management.SubCommand;
import org.grandknock.sidewars.core.entity.SWPlayer;

public class ArenaCommands {

    private final SideWars sideWars;

    public ArenaCommands(SideWars sideWars) {
        this.sideWars = sideWars;
    }

    @SubCommand(name="create")
    public void handleCreation(String[] args, SWCommandSender sender) throws IncompleteRegionException {
        if(sender instanceof SWPlayer) {
            SWPlayer player = (SWPlayer) sender;
            Region selection = player.getWorldEditSession().getSelection(player.getWorldEditSession().getSelectionWorld());

            int minX = selection.getMinimumPoint().getX();
            int minY = selection.getMinimumPoint().getY();
            int minZ = selection.getMinimumPoint().getZ();

            int maxX = selection.getMaximumPoint().getX();
            int maxY = selection.getMaximumPoint().getY();
            int maxZ = selection.getMaximumPoint().getZ();
            org.grandknock.sidewars.core.Region region = new org.grandknock.sidewars.core.Region(
                    player.getWorldID(), minX, minY, minZ, maxX, maxY, maxZ
            );

            ArenaPrototype arenaPrototype = new ArenaPrototype(args[0], region);
            sideWars.addArenaPrototype(arenaPrototype);
        }
    }
}

package org.grandknock.sidewars.core.command.arena;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.world.World;
import org.grandknock.sidewars.core.SideWars;
import org.grandknock.sidewars.core.arena.ArenaPrototype;
import org.grandknock.sidewars.core.command.SWCommandSender;
import org.grandknock.sidewars.core.command.management.SubCommand;
import org.grandknock.sidewars.core.entity.SWPlayer;

import static org.grandknock.sidewars.core.command.SWCommandSender.MessageType;

public class ArenaCommands {

    private final SideWars sideWars;

    public ArenaCommands(SideWars sideWars) {
        this.sideWars = sideWars;
    }

    @SubCommand(name = "create", permission = "sidewars.arena.create")
    public void handleCreation(String[] args, SWCommandSender sender) {
        if (args.length < 1) {
            sender.sendMessage(MessageType.ERROR, "Use: /sw arena create <name>");
            return;
        }else if(args.length > 1) {
            sender.sendMessage(MessageType.ERROR, "ERROR: You can't use spacing for arena names!");
            return;
        }

        if(sender instanceof SWPlayer) {
            try {
                SWPlayer player = (SWPlayer) sender;
                LocalSession session = sender.getWorldEditSession();
                World world = session.getSelectionWorld();

                if (world == null)
                    throw new IncompleteRegionException();
                Region selection = session.getSelection(world);

                int minX = selection.getMinimumPoint().getX();
                int minY = selection.getMinimumPoint().getY();
                int minZ = selection.getMinimumPoint().getZ();

                int maxX = selection.getMaximumPoint().getX();
                int maxY = selection.getMaximumPoint().getY();
                int maxZ = selection.getMaximumPoint().getZ();
                org.grandknock.sidewars.core.Region region = new org.grandknock.sidewars.core.Region(
                        player.getWorldID(), minX, minY, minZ, maxX, maxY, maxZ
                );

                ArenaPrototype arenaPrototype = ArenaPrototype.createNew(args[0], region);
                sideWars.addArenaPrototype(arenaPrototype);
                sender.sendMessage(MessageType.SUCCESS, "Arena " + arenaPrototype.getName() +
                        " was created successfully");
            } catch (IncompleteRegionException incomplete) {
                sender.sendMessage(MessageType.ERROR,
                        "You need a valid WorldEdit region selection to use this command.");
            } catch (IllegalArgumentException illegal) {
                sender.sendMessage(MessageType.ERROR, illegal.getMessage());
            }
        } else {
            sender.sendMessage(MessageType.ERROR,
                    "Only players can use this command because it requires a WorldEdit selection");
        }
    }

    @SubCommand(name = "list", permission = "sidewars.arena.list")
    public void handleList(String[] args, SWCommandSender sender) {
        sender.sendMessage(MessageType.INFO, "The arena's are:");
        for (ArenaPrototype arena : sideWars.getArenaPrototypes())
            sender.sendMessage(MessageType.INFO, arena.getName());
    }

    @SubCommand(name="info", permission = "sidewars.arena.info")
    public void handleInfo(String[] args, SWCommandSender sender) {
        if (args.length < 1) {
            sender.sendMessage(MessageType.ERROR, "Use: /sw arena info <name>");
            return;
        }else if(args.length > 1) {
            sender.sendMessage(MessageType.ERROR, "ERROR: You can't use spacing for arena names!");
            return;
        }

        ArenaPrototype arena = sideWars.getArenaPrototype(args[0]);
        if (arena == null) {
            sender.sendMessage(MessageType.ERROR, "There is no arena with name " + args[0]);
            return;
        }

        sender.sendMessage(MessageType.INFO, "Info about arena " + arena.getName() + ":");

        org.grandknock.sidewars.core.Region region = arena.getRegion();
        sender.sendMessage(MessageType.INFO, "Located in world " + region.worldID +
                " between (" + region.minX + "," + region.minY + "," + region.minZ + ") and (" +
                region.maxX + "," + region.maxY + "," + region.maxZ + ")");
        sender.sendMessage(MessageType.INFO, arena.getNumberOfTeams() +
                " teams of " + arena.getPlayersPerTeam() + " players");
    }
}

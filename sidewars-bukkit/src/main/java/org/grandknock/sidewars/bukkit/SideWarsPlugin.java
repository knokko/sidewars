package org.grandknock.sidewars.bukkit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.grandknock.sidewars.bukkit.command.CommandManager;
import org.grandknock.sidewars.core.SideWars;

public class SideWarsPlugin extends JavaPlugin {

    private SideWars sideWarsInstance;

    private CommandManager commandManager;

    @Override
    public void onEnable(){
        sideWarsInstance = new SideWars();
        // TODO Load data

        commandManager = new CommandManager();
    }

    @Override
    public void onDisable() {
        commandManager = null;

        // TODO Save data
        sideWarsInstance = null;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sideWarsInstance.processCommand(args, commandManager.toSWSender(sender));
        return true;
    }
}

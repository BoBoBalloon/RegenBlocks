package me.boboballoon.regenblocks.command;

import com.google.common.collect.Lists;
import me.boboballoon.regenblocks.BlockManager;
import me.boboballoon.regenblocks.RegenBlocks;
import org.bukkit.ChatColor;
import org.bukkit.FluidCollisionMode;
import org.bukkit.block.Block;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

public class RegenBlockCommand implements CommandExecutor, TabExecutor {
    private final BlockManager blockManager;

    private static final List<String> MODES = Lists.newArrayList("set", "remove");

    public RegenBlockCommand(@NotNull RegenBlocks plugin) {
        this.blockManager = plugin.getBlockManager();
        PluginCommand command = plugin.getCommand("regenblock");

        if (command == null) {
            plugin.getLogger().log(Level.SEVERE, "There was an error registering the plugin command!");
            throw new RuntimeException("There was an error getting the command registration from the server!");
        }

        command.setExecutor(this);
        command.setTabCompleter(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        //not player
        if (!(sender instanceof Player)) {
            this.sendMessage(sender, "&r&cOnly players can execute this command!");
            return false;
        }

        Player player = (Player) sender;

        Block target = player.getTargetBlockExact(5, FluidCollisionMode.NEVER);

        //not valid block
        if (target == null) {
            this.sendMessage(player, "&r&cInvalid block!");
            return false;
        }

        //correct remove
        if (args.length == 1 && args[0].trim().equalsIgnoreCase("remove")) {
            //nested if statement, YUCK
            if (this.blockManager.removeBlock(target.getLocation())) {
                this.sendMessage(player, "&r&aSuccess!");
            } else {
                this.sendMessage(player, "&r&cThis is not a regen block!");
            }

            return true;
        }

        //not correct set
        if (args.length != 2 || !args[0].trim().equalsIgnoreCase("set")) {
            this.sendMessage(player, "&r&cInvalid arguments!");
            return false;
        }

        long delay;
        try {
            delay = Long.parseLong(args[1].trim());
        } catch (NumberFormatException e) {
            this.sendMessage(player, "&r&cInvalid delay! Must be an integer!");
            return false;
        }

        if (this.blockManager.addBlock(target.getLocation(), delay)) {
            this.sendMessage(player, "&r&aSuccess!");
        } else {
            this.sendMessage(player, "&r&cAnother regen block already exists here!");
        }

        return true;
    }

    @Override
    @Nullable
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            return Collections.emptyList();
        }

        if (args.length <= 1) {
            return RegenBlockCommand.MODES;
        }

        if (!args[0].equalsIgnoreCase("set")) {
            return Collections.emptyList();
        }

        List<String> ticks = new ArrayList<>();

        for (int i = 0; i <= 1000; i++) {
            if (i % 20 == 0) {
                ticks.add(Integer.toString(i));
            }
        }

        return ticks;
    }

    /**
     * Sends a message to the user
     *
     * @param user the user to send the message to
     * @param message the message to be sent
     */
    private void sendMessage(@NotNull CommandSender user, @NotNull String message) {
        user.sendMessage(ChatColor.translateAlternateColorCodes('&', "&r&a[&r&cRegenBlocks&r&a] > &r" + message));
    }
}

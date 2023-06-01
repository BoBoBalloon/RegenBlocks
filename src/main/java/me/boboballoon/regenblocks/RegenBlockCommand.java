package me.boboballoon.regenblocks;

import com.google.common.collect.Lists;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RegenBlockCommand implements CommandExecutor, TabExecutor {
    private final BlockManager blockManager;

    private static final List<String> MODES = Lists.newArrayList("set", "remove");

    public RegenBlockCommand(@NotNull RegenBlocks plugin) {
        this.blockManager = plugin.getBlockManager();
        plugin.getCommand("regenblock").setExecutor(this);
        plugin.getCommand("regenblock").setTabCompleter(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            this.sendMessage(sender, "&r&cOnly players can execute this command!");
            return false;
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
        user.sendMessage(ChatColor.translateAlternateColorCodes('&', "&r[RegenBlocks] > " + message));
    }
}

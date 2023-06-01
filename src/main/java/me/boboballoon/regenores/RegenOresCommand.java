package me.boboballoon.regenores;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RegenOresCommand implements CommandExecutor, TabExecutor {
    private final BlockManager blockManager;

    public RegenOresCommand(@NotNull RegenBlocks plugin) {
        this.blockManager = plugin.getBlockManager();
        plugin.getCommand("regenblock").setExecutor(this);
        plugin.getCommand("regenblock").setTabCompleter(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return false;
    }

    @Override
    @Nullable
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return null;
    }
}

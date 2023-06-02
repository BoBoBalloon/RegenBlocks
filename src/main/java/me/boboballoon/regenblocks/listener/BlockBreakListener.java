package me.boboballoon.regenblocks.listener;

import me.boboballoon.regenblocks.RegenBlock;
import me.boboballoon.regenblocks.RegenBlocks;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.jetbrains.annotations.NotNull;

/**
 * A class that listens for when blocks are broken
 */
public class BlockBreakListener implements Listener {
    private final RegenBlocks plugin;

    public BlockBreakListener(@NotNull RegenBlocks plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    private void onBlockBreak(BlockBreakEvent event) {
        RegenBlock regenBlock = this.plugin.getBlockManager().getBlock(event.getBlock().getLocation());

        if (regenBlock == null) {
            return;
        }

        Block block = event.getBlock();

        block.setType(Material.BEDROCK);
        Bukkit.getScheduler().runTaskLater(this.plugin, () -> block.setType(regenBlock.getType()), regenBlock.getDelay());
    }
}

package me.boboballoon.regenblocks;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class RegenBlocks extends JavaPlugin {
    private BlockManager blockManager;

    /*
    TODO:
    1. Finish writing the command
    2. Test the permissions on the command (can you use it if you are deop)
    3. Create block break listener
     */

    @Override
    public void onEnable() {
        this.blockManager = new BlockManager(this);

        new RegenBlockCommand(this);
    }

    @Override
    public void onDisable() {
        this.blockManager.getBlocks().forEach(block -> block.getLocation().getBlock().setType(block.getType()));
    }

    /**
     * A method that returns the block manager of this plugin
     *
     * @return the block manager of this plugin
     */
    @NotNull
    public BlockManager getBlockManager() {
        return this.blockManager;
    }
}

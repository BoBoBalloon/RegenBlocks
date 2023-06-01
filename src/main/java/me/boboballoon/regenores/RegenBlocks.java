package me.boboballoon.regenores;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class RegenBlocks extends JavaPlugin {
    private BlockManager blockManager;

    @Override
    public void onEnable() {
        this.blockManager = new BlockManager(this);

        new RegenOresCommand(this);
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

package me.boboballoon.regenblocks;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.boboballoon.regenblocks.adapter.LocationAdapter;
import me.boboballoon.regenblocks.adapter.RegenBlockAdapter;
import me.boboballoon.regenblocks.command.RegenBlockCommand;
import me.boboballoon.regenblocks.listener.BlockBreakListener;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class RegenBlocks extends JavaPlugin {
    private BlockManager blockManager;

    public static final Gson GSON = new GsonBuilder()
            .registerTypeHierarchyAdapter(Location.class, new LocationAdapter())
            .registerTypeHierarchyAdapter(RegenBlock.class, new RegenBlockAdapter())
            .create();

    /*
    TODO
     */

    @Override
    public void onEnable() {
        this.blockManager = new BlockManager(this);

        Bukkit.getPluginManager().registerEvents(new BlockBreakListener(this), this);
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

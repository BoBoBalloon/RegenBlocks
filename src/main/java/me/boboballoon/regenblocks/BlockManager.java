package me.boboballoon.regenblocks;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Level;

/**
 * A class that loads up locations from disk
 */
public class BlockManager {
    private final RegenBlocks plugin;
    private final ConcurrentMap<Location, RegenBlock> cache;

    private static final Gson GSON = new GsonBuilder().create();

    public BlockManager(@NotNull RegenBlocks plugin) {
        this.plugin = plugin;
        this.cache = new ConcurrentHashMap<>();
        this.read();
    }

    /**
     * A method used to set the cache equal to disk
     */
    private void read() {
        this.cache.clear();

        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            File home = this.plugin.getDataFolder();

            if (!home.exists()) {
                home.mkdir();
            }

            for (File file : home.listFiles(file -> file.getName().endsWith(".json"))) {
                RegenBlock block;

                try {
                    block = GSON.fromJson(new FileReader(file), RegenBlock.class);
                } catch (Exception e) {
                    this.plugin.getLogger().log(Level.SEVERE, "There was an error decoding the JSON!");
                    e.printStackTrace();
                    continue;
                }

                this.cache.put(block.getLocation(), block);
            }
        });
    }

    /**
     * A method used write a block to disk
     *
     * @param block the block to write to json
     */
    private void write(@NotNull RegenBlock block) {
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            File home = this.plugin.getDataFolder();

            if (!home.exists()) {
                home.mkdir();
            }

            File file = new File(home, block.getUUID().toString() + ".json");

            try {
                file.createNewFile();
                GSON.toJson(block, RegenBlock.class, new JsonWriter(new FileWriter(file)));
            } catch (IOException e) {
                this.plugin.getLogger().log(Level.SEVERE, "There was an error writing to disk!");
                e.printStackTrace();
                return;
            }

            this.cache.put(block.getLocation(), block);
        });
    }

    /**
     * A method used to add a block to the cache and write it to disk
     *
     * @param location the location of the block
     * @param delay the time it will take to regenerate in ticks
     */
    public void addBlock(@NotNull Location location, long delay) {
        this.write(new RegenBlock(location, location.getBlock().getType(), delay));
    }

    /**
     * A method used to remove a block from the cache and remove it from disk
     *
     * @param location the location of the block
     */
    public void removeBlock(@NotNull Location location) {
        RegenBlock block = this.cache.remove(location);

        if (block == null) {
            return;
        }

        File file = new File(this.plugin.getDataFolder(), block.getUUID().toString() + ".json");
        file.delete();
    }
}

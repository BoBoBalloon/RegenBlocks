package me.boboballoon.regenblocks;

import com.google.common.collect.ImmutableSet;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Level;

/**
 * A class that loads up locations from disk
 */
public class BlockManager {
    private final RegenBlocks plugin;
    private final ConcurrentMap<Location, RegenBlock> cache;

    public BlockManager(@NotNull RegenBlocks plugin) {
        this.plugin = plugin;
        this.cache = new ConcurrentHashMap<>();
        this.read();
    }

    /**
     * A method used to get a copy of all the blocks registered to the cache
     *
     * @return a copy of all the blocks registered to the cache
     */
    @NotNull
    public ImmutableSet<RegenBlock> getBlocks() {
        return ImmutableSet.copyOf(this.cache.values());
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
                    JsonReader reader = new JsonReader(new FileReader(file));
                    reader.setLenient(true);

                    block = RegenBlocks.GSON.fromJson(reader, RegenBlock.class);

                    reader.close();
                } catch (Exception e) {
                    this.plugin.getLogger().log(Level.SEVERE, "There was an error decoding the JSON!");
                    e.printStackTrace();
                    continue;
                }

                if (block == null) {
                    continue;
                }

                this.cache.put(block.getLocation(), block);
            }

            Bukkit.getScheduler().runTask(this.plugin, () -> this.cache.values().forEach(block -> block.getLocation().getBlock().setType(block.getType())));
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

                JsonWriter writer = new JsonWriter(new BufferedWriter(new FileWriter(file)));

                RegenBlocks.GSON.toJson(block, RegenBlock.class, writer);

                writer.close();
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
     * @return true if the operation was a success
     */
    public boolean addBlock(@NotNull Location location, long delay) {
        if (this.cache.containsKey(location)) {
            return false;
        }

        this.write(new RegenBlock(location, location.getBlock().getType(), delay));
        return true;
    }

    /**
     * A method used to remove a block from the cache and remove it from disk
     *
     * @param location the location of the block
     * @return true if the operation was a success
     */
    public boolean removeBlock(@NotNull Location location) {
        RegenBlock block = this.cache.remove(location);

        if (block == null) {
            return false;
        }

        File file = new File(this.plugin.getDataFolder(), block.getUUID().toString() + ".json");
        file.delete();
        return true;
    }

    /**
     * A method that requests the block from the cache
     *
     * @param location the location of the block
     * @return a regen block or null
     */
    @Nullable
    public RegenBlock getBlock(@NotNull Location location) {
        return this.cache.get(location);
    }
}

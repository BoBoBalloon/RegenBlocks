package me.boboballoon.regenblocks;

import org.bukkit.Location;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * A class that represents a block that will regenerate over time
 */
public class RegenBlock {
    private final UUID uuid;
    private final Location location;
    private final Material type;
    private final long delay;

    public RegenBlock(@NotNull UUID uuid, @NotNull Location location, @NotNull Material type, long delay) {
        this.uuid = uuid;
        this.location = location;
        this.type = type;
        this.delay = delay;
    }

    public RegenBlock(@NotNull Location location, @NotNull Material type, long delay) {
        this(UUID.randomUUID(), location, type, delay);
    }

    /**
     * Get the id associated with this block
     *
     * @return the id associated with this block
     */
    @NotNull
    public UUID getUUID() {
        return this.uuid;
    }

    /**
     * A method that returns the location of the block
     *
     * @return the location of the block
     */
    @NotNull
    public final Location getLocation() {
        return this.location;
    }

    /**
     * A method that returns the material of the block
     *
     * @return the material of the block
     */
    @NotNull
    public final Material getType() {
        return this.type;
    }

    /**
     * A method that returns the regeneration delay of the block
     *
     * @return the regeneration delay of the block
     */
    public final long getDelay() {
        return this.delay;
    }
}

package net.dimensionmc.regenores;

import org.bukkit.Location;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

/**
 * A class that represents a block that will regenerate over time
 */
public class RegenBlock {
    private final Location location;
    private final Material type;
    private final long delay;


    public RegenBlock(@NotNull Location location, @NotNull Material type, long delay) {
        this.location = location;
        this.type = type;
        this.delay = delay;
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

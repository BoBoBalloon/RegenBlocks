package net.dimensionmc.regenores;

import org.bukkit.plugin.java.JavaPlugin;

public final class RegenOres extends JavaPlugin {
    private static RegenOres instance;

    @Override
    public void onEnable() {
        RegenOres.instance = this;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    /**
     * A method that returns the instance of the main class
     *
     * @return the instance of the main class
     */
    public static RegenOres getInstance() {
        return RegenOres.instance;
    }
}

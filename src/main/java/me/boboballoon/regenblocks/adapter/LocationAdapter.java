package me.boboballoon.regenblocks.adapter;

import com.google.gson.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.lang.reflect.Type;
import java.util.UUID;

/**
 * A class used to serialize and deserialize a {@link Location}
 */
public class LocationAdapter implements JsonSerializer<Location>, JsonDeserializer<Location> {
    @Override
    public Location deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();

        UUID uuid = UUID.fromString(object.get("world").getAsString());

        if (Bukkit.getWorld(uuid) == null) {
            throw new JsonParseException("The world stored in the location was unloaded!");
        }

        double x = object.get("x").getAsDouble();
        double y = object.get("y").getAsDouble();
        double z = object.get("z").getAsDouble();
        float yaw = object.get("yaw").getAsFloat();
        float pitch = object.get("pitch").getAsFloat();

        return new Location(Bukkit.getWorld(uuid), x, y, z, yaw, pitch);
    }

    @Override
    public JsonElement serialize(Location src, Type typeOfSrc, JsonSerializationContext context) {
        if (src.getWorld() == null) {
            throw new IllegalStateException("A location was about to be serialized but the world was unloaded!");
        }

        JsonObject object = new JsonObject();

        object.addProperty("x", src.getX());
        object.addProperty("y", src.getY());
        object.addProperty("z", src.getZ());
        object.addProperty("yaw", src.getYaw());
        object.addProperty("pitch", src.getPitch());
        object.addProperty("world", src.getWorld().getUID().toString());

        return object;
    }
}

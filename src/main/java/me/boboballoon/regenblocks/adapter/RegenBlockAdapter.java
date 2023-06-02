package me.boboballoon.regenblocks.adapter;

import com.google.gson.*;
import me.boboballoon.regenblocks.RegenBlock;
import me.boboballoon.regenblocks.RegenBlocks;
import org.bukkit.Location;
import org.bukkit.Material;

import java.lang.reflect.Type;
import java.util.UUID;

/**
 * A class used to serialize and deserialize a {@link RegenBlock}
 */
public class RegenBlockAdapter implements JsonSerializer<RegenBlock>, JsonDeserializer<RegenBlock> {
    @Override
    public RegenBlock deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();

        UUID uuid = UUID.fromString(object.get("uuid").getAsString());
        Location location = RegenBlocks.GSON.fromJson(object.get("location").getAsString(), Location.class);
        Material type = Material.valueOf(object.get("type").getAsString());
        long delay = object.get("delay").getAsLong();

        return new RegenBlock(uuid, location, type, delay);
    }

    @Override
    public JsonObject serialize(RegenBlock src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();

        object.addProperty("uuid", src.getUUID().toString());
        object.addProperty("location", RegenBlocks.GSON.toJson(src.getLocation()));
        object.addProperty("type", src.getType().name());
        object.addProperty("delay", src.getDelay());

        return object;
    }
}

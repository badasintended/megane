package badasintended.megane.runtime.data.entity;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

import static badasintended.megane.runtime.util.Keys.O_HAS;
import static badasintended.megane.runtime.util.Keys.O_NAME;
import static badasintended.megane.util.MeganeUtils.config;

public class PetOwnerData extends EntityData {

    private static final Map<UUID, String> NAMES = new HashMap<>();

    private static final JsonParser PARSER = new JsonParser();

    public PetOwnerData() {
        super(() -> config().petOwner);
    }

    @Override
    void append(NbtCompound data, ServerPlayerEntity player, World world, Entity entity) {
        UUID ownerUuid = null;
        if (entity instanceof HorseBaseEntity) {
            ownerUuid = ((HorseBaseEntity) entity).getOwnerUuid();
        } else if (entity instanceof TameableEntity) {
            ownerUuid = ((TameableEntity) entity).getOwnerUuid();
        }
        if (ownerUuid == null)
            return;

        data.putBoolean(O_HAS, true);

        String ownerName = NAMES.getOrDefault(ownerUuid, null);

        if (ownerName == null) {
            ServerPlayerEntity owner = player.server.getPlayerManager().getPlayer(ownerUuid);
            if (owner != null) {
                ownerName = owner.getEntityName();
            } else if (config().petOwner.isOffline()) {
                // don't blame me, it just easier to do it this way
                try {
                    URL url = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + ownerUuid);
                    Scanner scanner = new Scanner(url.openStream(), StandardCharsets.UTF_8).useDelimiter("\\A");
                    String jsonStr = scanner.hasNext() ? scanner.next() : "{}";
                    JsonObject json = PARSER.parse(jsonStr).getAsJsonObject();
                    ownerName = json.has("name") ? json.getAsJsonPrimitive("name").getAsString() : null;
                    scanner.close();
                } catch (Exception e) {
                    // no-op
                }
            }

            if (ownerName == null) {
                ownerName = "???";
            } else {
                NAMES.put(ownerUuid, ownerName);
            }
        }

        data.putString(O_NAME, ownerName);
    }


}

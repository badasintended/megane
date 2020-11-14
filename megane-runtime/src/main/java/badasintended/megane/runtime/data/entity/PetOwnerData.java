package badasintended.megane.runtime.data.entity;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

import badasintended.megane.runtime.data.Appender;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

import static badasintended.megane.util.MeganeUtils.config;
import static badasintended.megane.util.MeganeUtils.key;

public class PetOwnerData extends EntityData {

    private static final Map<UUID, String> NAMES = new HashMap<>();

    private static final JsonParser PARSER = new JsonParser();

    public PetOwnerData() {
        super(() -> config().petOwner);
        appenders.add(new Child());
    }

    public static class Child implements Appender<LivingEntity> {

        @Override
        public boolean append(CompoundTag data, ServerPlayerEntity player, World world, LivingEntity livingEntity) {
            UUID ownerUuid = null;
            if (livingEntity instanceof HorseBaseEntity) {
                ownerUuid = ((HorseBaseEntity) livingEntity).getOwnerUuid();
            } else if (livingEntity instanceof TameableEntity) {
                ownerUuid = ((TameableEntity) livingEntity).getOwnerUuid();
            }
            if (ownerUuid == null) return false;

            data.putBoolean(key("hasOwner"), true);

            String ownerName = NAMES.getOrDefault(ownerUuid, null);

            if (ownerName == null) {
                ServerPlayerEntity owner = player.server.getPlayerManager().getPlayer(ownerUuid);
                if (owner != null) {
                    ownerName = owner.getEntityName();
                } else if (config().petOwner.isOffline()) {
                    // don't blame me, it just easier to do it this way
                    try {
                        URL url = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + ownerUuid.toString());
                        Scanner scanner = new Scanner(url.openStream(), "UTF-8").useDelimiter("\\A");
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

            data.putString(key("owner"), ownerName);
            return true;
        }

    }

}

package badasintended.megane.config;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;

import static badasintended.megane.util.MeganeUtils.id;

public class MeganeConfig {

    public int configVersion = 0;

    public final HashMap<String, HashMap<String, Boolean>> modules = new HashMap<>();

    public final BlockInventory inventory = new BlockInventory();
    public final Inventory entityInventory = new Inventory();
    public final Energy energy = new Energy();
    public final Fluid fluid = new Fluid();
    public final Progress progress = new Progress();
    public final PetOwner petOwner = new PetOwner();
    public final Effect effect = new Effect();

    private boolean spawnEgg = true;
    private boolean playerHead = true;

    public boolean getSpawnEgg() {
        return spawnEgg;
    }

    public boolean getPlayerHead() {
        return playerHead;
    }

    public void setSpawnEgg(boolean spawnEgg) {
        this.spawnEgg = spawnEgg;
    }

    public void setPlayerHead(boolean playerHead) {
        this.playerHead = playerHead;
    }

    public interface Base {

        boolean isEnabled();

        Set<Identifier> getBlacklist();

    }

    public interface Registry {

        boolean isForceRegistry();

        void setForceRegistry(boolean forceRegistry);

    }

    public static class BlockInventory extends Inventory implements Registry {

        private boolean forceRegistry = false;

        @Override
        public void setForceRegistry(boolean forceRegistry) {
            this.forceRegistry = forceRegistry;
        }

        @Override
        public boolean isForceRegistry() {
            return forceRegistry;
        }

    }

    public static class Inventory implements Base {

        private boolean enabled = true;
        private boolean itemCount = true;
        private boolean nbt = false;
        private int maxWidth = 9;
        private int maxHeight = 3;
        private final Set<Identifier> blacklist = new HashSet<>();

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public void setItemCount(boolean itemCount) {
            this.itemCount = itemCount;
        }

        public void setNbt(boolean nbt) {
            this.nbt = nbt;
        }

        public void setMaxWidth(int maxWidth) {
            this.maxWidth = maxWidth;
        }

        public void setMaxHeight(int maxHeight) {
            this.maxHeight = maxHeight;
        }

        @Override
        public boolean isEnabled() {
            return enabled;
        }

        public boolean isItemCount() {
            return itemCount;
        }

        public boolean isNbt() {
            return nbt;
        }

        public int getMaxWidth() {
            return maxWidth;
        }

        public int getMaxHeight() {
            return maxHeight;
        }

        @Override
        public Set<Identifier> getBlacklist() {
            return blacklist;
        }

    }

    public static class Energy implements Base, Registry {

        private boolean enabled = true;
        private boolean expandWhenSneak = false;
        private boolean forceRegistry = false;
        private final Map<String, Integer> colors = new HashMap<>();
        private final Map<String, String> units = new HashMap<>();
        private final Set<Identifier> blacklist = new HashSet<>();

        public Energy() {
            units.put("megane", "E");

            colors.put("megane", 0x710C00);

            blacklist.add(id("appliedenergistics2", "energy_acceptor"));
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public void setExpandWhenSneak(boolean expandWhenSneak) {
            this.expandWhenSneak = expandWhenSneak;
        }

        @Override
        public void setForceRegistry(boolean forceRegistry) {
            this.forceRegistry = forceRegistry;
        }

        @Override
        public boolean isEnabled() {
            return enabled;
        }

        public boolean isExpandWhenSneak() {
            return expandWhenSneak;
        }

        @Override
        public boolean isForceRegistry() {
            return forceRegistry;
        }

        public Map<String, String> getUnits() {
            return units;
        }

        public Map<String, Integer> getColors() {
            return colors;
        }

        @Override
        public Set<Identifier> getBlacklist() {
            return blacklist;
        }

    }

    public static class Fluid implements Base, Registry {

        private boolean enabled = true;
        private boolean expandWhenSneak = false;
        private boolean forceRegistry = false;
        private final Map<Identifier, Integer> colors = new HashMap<>();
        private final Set<Identifier> blacklist = new HashSet<>();

        public Fluid() {
            colors.put(id("default"), 0x0D0D59);
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public void setExpandWhenSneak(boolean expandWhenSneak) {
            this.expandWhenSneak = expandWhenSneak;
        }

        @Override
        public void setForceRegistry(boolean forceRegistry) {
            this.forceRegistry = forceRegistry;
        }

        @Override
        public boolean isEnabled() {
            return enabled;
        }

        public boolean isExpandWhenSneak() {
            return expandWhenSneak;
        }

        @Override
        public boolean isForceRegistry() {
            return forceRegistry;
        }

        public Map<Identifier, Integer> getColors() {
            return colors;
        }

        @Override
        public Set<Identifier> getBlacklist() {
            return blacklist;
        }

    }

    public static class Progress implements Base {

        private boolean enabled = true;
        private boolean showWhenZero = false;
        private final Set<Identifier> blacklist = new HashSet<>();

        public Progress() {
            blacklist.add(net.minecraft.util.registry.Registry.BLOCK.getId(Blocks.FURNACE));
            blacklist.add(net.minecraft.util.registry.Registry.BLOCK.getId(Blocks.BLAST_FURNACE));
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public void setShowWhenZero(boolean showWhenZero) {
            this.showWhenZero = showWhenZero;
        }

        @Override
        public boolean isEnabled() {
            return enabled;
        }

        public boolean isShowWhenZero() {
            return showWhenZero;
        }

        @Override
        public Set<Identifier> getBlacklist() {
            return blacklist;
        }

    }

    public static class PetOwner implements Base {

        private boolean enabled = true;
        private boolean offline = true;
        private final Set<Identifier> blacklist = new HashSet<>();

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public void setOffline(boolean offline) {
            this.offline = offline;
        }

        @Override
        public boolean isEnabled() {
            return enabled;
        }

        public boolean isOffline() {
            return offline;
        }

        @Override
        public Set<Identifier> getBlacklist() {
            return blacklist;
        }

    }

    public static class Effect implements Base {

        private boolean enabled = true;
        private boolean level = true;
        private boolean roman = false;
        private boolean hidden = false;
        private final Set<Identifier> blacklist = new HashSet<>();

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public void setLevel(boolean level) {
            this.level = level;
        }

        public void setRoman(boolean roman) {
            this.roman = roman;
        }

        public void setHidden(boolean hidden) {
            this.hidden = hidden;
        }

        @Override
        public boolean isEnabled() {
            return enabled;
        }

        public boolean getLevel() {
            return level;
        }

        public boolean isRoman() {
            return roman;
        }

        public boolean getHidden() {
            return hidden;
        }

        @Override
        public Set<Identifier> getBlacklist() {
            return blacklist;
        }

    }

}

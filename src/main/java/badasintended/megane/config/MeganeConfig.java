package badasintended.megane.config;

import java.util.HashMap;
import java.util.Map;

public class MeganeConfig {

    public final EffectiveTool effectiveTool = new EffectiveTool();
    public final Inventory inventory = new Inventory();
    public final Energy energy = new Energy();
    public final Fluid fluid = new Fluid();
    public final Progress progress = new Progress();
    public final EntityInfo entityInfo = new EntityInfo();

    public static class EffectiveTool {
        private boolean enabled = true;

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public boolean isEnabled() {
            return enabled;
        }
    }

    public static class Inventory {
        private boolean enabled = true;
        private int maxRow = 9;

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public void setMaxRow(int maxRow) {
            this.maxRow = maxRow;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public int getMaxRow() {
            return maxRow;
        }
    }

    public static class Energy {
        private boolean enabled = true;
        private Map<String, Integer> colors = new HashMap<>();
        private Map<String, String> units = new HashMap<>();

        public Energy() {
            units.put("astromine", "E");
            units.put("techreborn", "E");
            units.put("indrev", "LF");

            colors.put("astromine", -13275755);
            colors.put("techreborn", -8386560);
            colors.put("indrev", -12891426);
        }

        public void setColors(Map<String, Integer> colors) {
            this.colors = colors;
        }

        public void setUnits(Map<String, String> units) {
            this.units = units;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public Map<String, String> getUnits() {
            return units;
        }

        public Map<String, Integer> getColors() {
            return colors;
        }
    }

    public static class Fluid {
        private boolean enabled = true;

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public boolean isEnabled() {
            return enabled;
        }
    }

    public static class Progress {
        private boolean enabled = true;

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public boolean isEnabled() {
            return enabled;
        }
    }

    public static class EntityInfo {
        private boolean enabled = true;

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public boolean isEnabled() {
            return enabled;
        }
    }

}

package badasintended.megane.runtime.util;

/**
 * An attempt to minimize packet size
 * by using one character for data key
 *
 * don't laugh
 *
 * @formatter:off
 */
public class Keys {

    public static final String
        // Inventory
        I_HAS     = "\u3000",
        I_SIZE    = "\u3001",
        I_ID      = "\u3002",
        I_COUNT   = "\u3003",
        I_NBT     = "\u3004",
        I_SHOW    = "\u3005",
        I_MAX_W   = "\u3006",
        I_MAX_H   = "\u3007",

        // Energy
        E_HAS     = "\u3100",
        E_STORED  = "\u3101",
        E_MAX     = "\u3102",

        // Fluid
        F_HAS     = "\u3200",
        F_SIZE    = "\u3201",
        F_ID      = "\u3202",
        F_STORED  = "\u3203",
        F_MAX     = "\u3204",

        // Progress
        P_HAS     = "\u3300",
        P_I_SIZE  = "\u3301",
        P_I_ID    = "\u3302",
        P_I_COUNT = "\u3303",
        P_I_NBT   = "\u3304",
        P_O_SIZE  = "\u3305",
        P_O_ID    = "\u3306",
        P_O_COUNT = "\u3307",
        P_O_NBT   = "\u3308",
        P_PERCENT = "\u3309",

        // Status effect
        S_SIZE    = "\u3400",
        S_ID      = "\u3401",
        S_LV      = "\u3402",
        S_LV_STR  = "\u3403",

        // Pet Owner
        O_HAS     = "\u3500",
        O_NAME    = "\u3501",

        // Bar Rendering
        B_STORED  = "\u3600",
        B_MAX     = "\u3601",
        B_UNIT    = "\u3602",
        B_LONG    = "\u3603",
        B_PREFIX  = "\u3604",
        B_TL      = "\u3605",
        B_COLOR   = "\u3606";

}

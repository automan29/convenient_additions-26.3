package automandza.convenient_additions.config;

import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.Arrays;
import java.util.List;

public class ConAddCommonConfig {
    //public static final NeoForgeCommonConfig COMMON_CONFIG;
    public static final ModConfigSpec.Builder SPEC_BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec CONFIG_SPEC;

    public static final ModConfigSpec.ConfigValue<String> CUSTOM_ENHANCE_ENCH_LIST;

    static {
        SPEC_BUILDER.push("Convenient Additions Game Configs");

        // Enhancements
        SPEC_BUILDER.push("Enhancements");
        CUSTOM_ENHANCE_ENCH_LIST = SPEC_BUILDER.comment("\n List separated by | of \n - Enchantment names with mod ID before\n - Ingredients to be used to apply the chosen enchantment\n - Hints to give the player trying to figure out what item to use to get the enchantment\n - Max level of the enchantment\n\n e.g. convenient_extras:diplomatic,minecraft:emerald,Green thing favored by the locals,3|minecraft:sweeping_edge,minecraft:bone,Wolf's favourite,2\n\n THIS CONFIG MUST HAVE THE CONTENTS IN THE SAME ORDER AND FORMATTING AS THE EXAMPLE, NO SPACES!\n")
                .define("Enchantment List", "");

        SPEC_BUILDER.pop();
        CONFIG_SPEC = SPEC_BUILDER.build();
    }
}

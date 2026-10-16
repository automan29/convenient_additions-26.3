package automandza.convenientadditions.config;

import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.Arrays;
import java.util.List;

public class ConAddCommonConfig {
    //public static final NeoForgeCommonConfig COMMON_CONFIG;
    public static final ModConfigSpec.Builder SPEC_BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec CONFIG_SPEC;

    public static final ModConfigSpec.ConfigValue<List<? extends String>> CUSTOM_ENHANCE_ENCH_LIST;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> CUSTOM_ENHANCE_INGREDIENT_LIST;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> CUSTOM_ENHANCE_HINT_LIST;

    static {
        SPEC_BUILDER.push("Convenient Additions Game Configs");

        // Enhancements
        SPEC_BUILDER.push("==== Enhancements ====");
        CUSTOM_ENHANCE_ENCH_LIST = SPEC_BUILDER.comment("List of enchantment names with mod ID before, e.g. convenient_extras:diplomatic").defineList("Enchantment List", Arrays.asList("a","b"),entry -> true);
        CUSTOM_ENHANCE_INGREDIENT_LIST = SPEC_BUILDER.comment("List of ingredients to be used to apply the chosen enchantment, e.g. convenient_extras:cinder_pelt").defineList("Ingredient List", Arrays.asList("c","d"),entry -> true);
        CUSTOM_ENHANCE_HINT_LIST= SPEC_BUILDER.comment("List of hints to give the player trying to figure out what item to use to get the enchantment e.g. 'A form of leather, found in the heats through the purple portal'").defineList("Hint List", Arrays.asList("e","f"),entry -> true);
        SPEC_BUILDER.push("ALL CONFIGS FOR THIS SECTION MUST HAVE THE THING THEY ARE REFERING TO IN THE SAME ORDER AS EACH OTHER AND THE SAME AMOUNT!");

        SPEC_BUILDER.pop();
        CONFIG_SPEC = SPEC_BUILDER.build();
    }
}

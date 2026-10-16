package automandza.convenientadditions.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ConAddClientConfig {
    //public static final NeoForgeClientConfig CLIENT_CONFIG;
    public static final ModConfigSpec.Builder SPEC_BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec CONFIG_SPEC;

    static {
        SPEC_BUILDER.push("Convenient Additions Client Configs");

        // def configs

        SPEC_BUILDER.pop();
        CONFIG_SPEC = SPEC_BUILDER.build();
    }
}

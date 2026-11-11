package automandza.convenient_additions.armTrim;

import automandza.convenient_additions.ConvenientAdditionsMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Util;
import net.minecraft.world.inventory.SmithingMenu;
import net.minecraft.world.item.Item;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.trim.*;

import java.util.Map;

public class ConvAddArmourTrimMats {
    public static final ResourceKey<TrimMaterial> PEARL_TRIM = ResourceKey.create(Registries.TRIM_MATERIAL, Identifier.fromNamespaceAndPath(ConvenientAdditionsMod.MODID, "regular_pearl"));

    public static void bootstrap(BootstrapContext<TrimMaterial> context) {
        register(context, PEARL_TRIM, Style.EMPTY.withColor(12416357), Identifier.fromNamespaceAndPath(ConvenientAdditionsMod.MODID, "trim/regular_pearl"));
    }

    private static void register(BootstrapContext<TrimMaterial> context, ResourceKey<TrimMaterial> registryKey, Style hoverTextStyle, Identifier paletteId) {
        Component description = Component.translatable(Util.makeDescriptionId("trim_material", registryKey.identifier())).withStyle(hoverTextStyle);
        context.register(registryKey, new TrimMaterial(paletteId, description));
    }

    private static void register(BootstrapContext<TrimMaterial> context, ResourceKey<TrimMaterial> trimKey){
        TrimMaterial trimMat = new TrimMaterial(trimKey.identifier(), Component.literal("regular_pearl"));

        context.register(trimKey, trimMat);
    }
}

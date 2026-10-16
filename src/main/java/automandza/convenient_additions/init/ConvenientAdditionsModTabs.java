/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package automandza.convenientadditions.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.core.registries.Registries;

import automandza.convenientadditions.ConvenientAdditionsMod;

@EventBusSubscriber
public class ConvenientAdditionsModTabs {
	public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ConvenientAdditionsMod.MODID);

	@SubscribeEvent
	public static void buildTabContentsVanilla(BuildCreativeModeTabContentsEvent tabData) {
		if (tabData.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
			tabData.accept(ConvenientAdditionsModBlocks.LOCKED_DOOR.get().asItem());
			tabData.accept(ConvenientAdditionsModBlocks.THE_LOCK_BLOCK.get().asItem());
			tabData.accept(ConvenientAdditionsModBlocks.SAWMILL.get().asItem());
			tabData.accept(ConvenientAdditionsModBlocks.DECORATION_STATION.get().asItem());
			tabData.accept(ConvenientAdditionsModBlocks.KILN.get().asItem());
			tabData.accept(ConvenientAdditionsModBlocks.ENCH_SCIENCE_RESEARCH_TABLE.get().asItem());
			tabData.accept(ConvenientAdditionsModBlocks.MATERIALS_TO_ENCHANTMENT_MACHINE.get().asItem());
		} else if (tabData.getTabKey() == CreativeModeTabs.COMBAT) {
			tabData.accept(ConvenientAdditionsModItems.WODDEN_VEST_CHESTPLATE.get());
			tabData.accept(ConvenientAdditionsModItems.WODDEN_VEST_LEGGINGS.get());
			tabData.accept(ConvenientAdditionsModItems.FLAME_CHARGE.get());
		} else if (tabData.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
			tabData.accept(ConvenientAdditionsModBlocks.CRUDE_DOOR.get().asItem());
			tabData.accept(ConvenientAdditionsModBlocks.MARBLE_RUBBLE.get().asItem());
			tabData.accept(ConvenientAdditionsModBlocks.QUARTZ_PEARL_TOMBSTONE.get().asItem());
		} else if (tabData.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
			tabData.accept(ConvenientAdditionsModItems.SNOW_CREEPER_SPAWN_EGG.get());
		} else if (tabData.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
			tabData.accept(ConvenientAdditionsModItems.GOLDEN_KEY.get());
			tabData.accept(ConvenientAdditionsModItems.STONE_DRILL.get());
			tabData.accept(ConvenientAdditionsModItems.FIRE_EXTINGUISHER.get());
		} else if (tabData.getTabKey() == CreativeModeTabs.INGREDIENTS) {
			tabData.accept(ConvenientAdditionsModItems.OAK_BARK.get());
			tabData.accept(ConvenientAdditionsModItems.SPRUCE_BARK.get());
			tabData.accept(ConvenientAdditionsModItems.DARKOAK_BARK.get());
			tabData.accept(ConvenientAdditionsModItems.BIRCH_BARK.get());
			tabData.accept(ConvenientAdditionsModItems.ACACIA_BARK.get());
			tabData.accept(ConvenientAdditionsModItems.JUNGLE_BARK.get());
			tabData.accept(ConvenientAdditionsModItems.CHERRY_BARK.get());
			tabData.accept(ConvenientAdditionsModItems.PALE_BARK.get());
			tabData.accept(ConvenientAdditionsModItems.MANGROVE_BARK.get());
			tabData.accept(ConvenientAdditionsModItems.DRILL_HEAD.get());
			tabData.accept(ConvenientAdditionsModItems.REGULAR_PEARL.get());
		}
	}
}
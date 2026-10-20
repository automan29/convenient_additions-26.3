package automandza.convenient_additions.init;

//imports
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;


import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.core.registries.Registries;

import automandza.convenient_additions.ConvenientAdditionsMod;




public class ConvAddModCustomTabs {
	public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ConvenientAdditionsMod.MODID);

	public static void register(IEventBus eventBus){
		REGISTRY.register(eventBus);
	}

	public static void addCreativeTab(BuildCreativeModeTabContentsEvent tabData){
		if (tabData.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
			tabData.accept(ConvenientAdditionsModBlocks.CEILING_LAMP.get().asItem());
		}
	}
}
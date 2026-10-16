/*
*	MCreator note: This file will be REGENERATED on each build.
*/
package automandza.convenientadditions.init;

import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import net.neoforged.neoforge.common.BasicItemListing;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;

@EventBusSubscriber
public class ConvenientAdditionsModTrades {
	@SubscribeEvent
	public static void registerTrades(VillagerTradesEvent event) {
		if (event.getType() == ResourceKey.create(Registries.VILLAGER_PROFESSION, ResourceLocation.parse("convenient_additions:locksmith"))) {
			event.getTrades().get(1).add(new BasicItemListing(new ItemStack(ConvenientAdditionsModItems.GOLDEN_KEY.get()), new ItemStack(Items.EMERALD, 4), 8, 3, 0.05f));
			event.getTrades().get(1).add(new BasicItemListing(new ItemStack(Items.EMERALD), new ItemStack(Blocks.TRIPWIRE_HOOK, 2), 4, 5, 0.05f));
			event.getTrades().get(2).add(new BasicItemListing(new ItemStack(Blocks.IRON_DOOR), new ItemStack(Items.EMERALD, 2), 10, 5, 0.05f));
			event.getTrades().get(2).add(new BasicItemListing(new ItemStack(Items.EMERALD, 3), new ItemStack(ConvenientAdditionsModBlocks.LOCKED_DOOR.get()), 10, 10, 0.05f));
			event.getTrades().get(3).add(new BasicItemListing(new ItemStack(ConvenientAdditionsModBlocks.CRUDE_DOOR.get(), 20), new ItemStack(Items.EMERALD), 10, 5, 0.05f));
			event.getTrades().get(5).add(new BasicItemListing(new ItemStack(ConvenientAdditionsModItems.PENCIL.get(), 3), new ItemStack(Items.EMERALD, 2), 10, 5, 0.05f));
		}
	}
}
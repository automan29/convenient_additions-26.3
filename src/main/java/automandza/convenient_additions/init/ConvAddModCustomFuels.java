package automandza.convenientadditions.init;

//imports
import net.neoforged.neoforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.item.ItemStack;
import net.minecraft.tags.TagKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;



@EventBusSubscriber
public class ConvAddModCustomFuels {
	public static final TagKey<Item> BARK_TAG = TagKey.create(
		// The registry key. The type of the registry must match the generic type of the tag.
		Registries.ITEM,
		// The location of the tag. This will put our tag at data/convenient_additions/tags/blocks/material/bark.json.
		ResourceLocation.fromNamespaceAndPath("convenient_additions", "material/bark")
	);
	@SubscribeEvent
	public static void furnaceFuelBurnTimeEvent(FurnaceFuelBurnTimeEvent event) {
		ItemStack itemstack = event.getItemStack();
		if (itemstack.is(BARK_TAG)){
			event.setBurnTime(100);
		}
	}
}

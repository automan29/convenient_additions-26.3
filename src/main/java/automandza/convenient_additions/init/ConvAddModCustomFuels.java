package automandza.convenient_additions.init;

//imports
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
import net.minecraft.world.level.storage.loot.providers.number.floats.ContextFloatProviders;
import net.minecraft.world.item.component.CookingFuel;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.TagKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;



@EventBusSubscriber
public class ConvAddModCustomFuels {
	public static final TagKey<Item> BARK_TAG = TagKey.create(
		// The registry key. The type of the registry must match the generic type of the tag.
		Registries.ITEM,
		// The location of the tag. This will put our tag at data/convenient_additions/tags/blocks/material/bark.json.
		Identifier.fromNamespaceAndPath("convenient_additions", "material/bark")
	);

	@SubscribeEvent
	public static void furnaceFuelBurnTimeEvent(ModifyDefaultComponentsEvent event) {
		for (Item bark : new Item[] {ConvenientAdditionsModItems.OAK_BARK.get(), ConvenientAdditionsModItems.BIRCH_BARK.get(), ConvenientAdditionsModItems.JUNGLE_BARK.get(), ConvenientAdditionsModItems.CHERRY_BARK.get(), ConvenientAdditionsModItems.SPRUCE_BARK.get(),
				ConvenientAdditionsModItems.ACACIA_BARK.get(), ConvenientAdditionsModItems.DARKOAK_BARK.get(), ConvenientAdditionsModItems.PALE_BARK.get(), ConvenientAdditionsModItems.MANGROVE_BARK.get()}) {
			event.modify(bark, (components, context, item) -> components.set(DataComponents.COOKING_FUEL, new CookingFuel(ContextIntProviders.COOKING_TIME_WOOD_ITEMS_EXTRA_SMALL, ContextFloatProviders.COOKING_DEFAULT_SPEED_MULTIPLIER)));
		}
	}
}

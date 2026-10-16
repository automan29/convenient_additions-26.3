package automandza.convenient_additions.init;

//imports
import net.minecraft.util.datafix.fixes.BlockEntityFurnaceBurnTimeFix;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.tags.TagKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.*;
import net.minecraft.world.item.Item;



@EventBusSubscriber
public class ConvAddModCustomFuels {
	public static final TagKey<Item> BARK_TAG = TagKey.create(
		// The registry key. The type of the registry must match the generic type of the tag.
		Registries.ITEM,
		// The location of the tag. This will put our tag at data/convenient_additions/tags/blocks/material/bark.json.
		Identifier.fromNamespaceAndPath("convenient_additions", "material/bark")
	);
}

package automandza.convenient_additions.item;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;

import automandza.convenient_additions.ConvenientAdditionsMod;

public class DisctestItem extends Item {
	public DisctestItem(Item.Properties properties) {
		super(properties.rarity(Rarity.UNCOMMON).stacksTo(1).jukeboxPlayable(ResourceKey.create(Registries.JUKEBOX_SONG, Identifier.fromNamespaceAndPath(ConvenientAdditionsMod.MODID, "disctest"))));
	}
}
package automandza.convenientadditions.item;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;

import automandza.convenientadditions.ConvenientAdditionsMod;

public class DisctestItem extends Item {
	public DisctestItem(Item.Properties properties) {
		super(properties.rarity(Rarity.UNCOMMON).stacksTo(1).jukeboxPlayable(ResourceKey.create(Registries.JUKEBOX_SONG, ResourceLocation.fromNamespaceAndPath(ConvenientAdditionsMod.MODID, "disctest"))));
	}
}
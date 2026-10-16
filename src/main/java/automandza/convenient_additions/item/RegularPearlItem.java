package automandza.convenientadditions.item;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;

public class RegularPearlItem extends Item {
	public RegularPearlItem(Item.Properties properties) {
		super(properties.rarity(Rarity.RARE).stacksTo(16));
	}
}
package automandza.convenient_additions.item;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;

public class LegoGuyItem extends Item {
	public LegoGuyItem(Item.Properties properties) {
		super(properties.rarity(Rarity.UNCOMMON));
	}
}
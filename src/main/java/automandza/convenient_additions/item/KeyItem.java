package automandza.convenient_additions.item;

import net.minecraft.world.item.Item;

public class KeyItem extends Item {
	public KeyItem(Item.Properties properties) {
		super(properties.stacksTo(1));
	}
}
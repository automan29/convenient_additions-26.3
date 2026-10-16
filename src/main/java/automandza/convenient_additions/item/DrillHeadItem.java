package automandza.convenient_additions.item;

import net.minecraft.world.item.Item;

public class DrillHeadItem extends Item {
	public DrillHeadItem(Item.Properties properties) {
		super(properties.fireResistant());
	}
}
package automandza.convenientadditions.item;

import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.Item;
import net.minecraft.world.food.FoodProperties;

public class CookedArmItem extends Item {
	public CookedArmItem(Item.Properties properties) {
		super(properties.food((new FoodProperties.Builder()).nutrition(5).saturationModifier(8f).build(), Consumables.defaultFood().consumeSeconds(1.5F).build()));
	}
}
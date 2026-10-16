package automandza.convenientadditions.procedures;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import automandza.convenientadditions.init.ConvenientAdditionsModItems;

public class DrillShowFuelUIProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == ConvenientAdditionsModItems.STONE_DRILL.get()) {
			return true;
		}
		return false;
	}
}
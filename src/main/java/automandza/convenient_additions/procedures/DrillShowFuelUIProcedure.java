package automandza.convenient_additions.procedures;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import automandza.convenient_additions.init.ConvenientAdditionsModItems;

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
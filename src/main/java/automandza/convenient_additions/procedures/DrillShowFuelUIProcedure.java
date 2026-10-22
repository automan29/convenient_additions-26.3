package automandza.convenient_additions.procedures;

//imports
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import automandza.convenient_additions.init.ConvenientAdditionsModItems;



public class DrillShowFuelUIProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null) {
			return false;
		}
        return entity instanceof LivingEntity livEnt && livEnt.getMainHandItem().getItem() == ConvenientAdditionsModItems.STONE_DRILL.get();
    }
}
package automandza.convenient_additions.procedures;

import net.minecraft.world.Container;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.minecraft.world.entity.Entity;

public class PremiumSawmillCheckProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null) {
			return false;
		}

		if (entity.getCapability(Capabilities.Item.ENTITY, null) instanceof Container _modHandler0) {
			for (int i = 0; i < _modHandler0.getContainerSize(); i++) {
				if (_modHandler0.getItem(i).copy().getItem().toString().equals("convenient_extras:premium_ingot")){
					return true;
				}
			}
		}
		return false;
    }
}
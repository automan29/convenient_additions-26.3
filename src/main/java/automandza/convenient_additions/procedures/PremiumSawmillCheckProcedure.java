package automandza.convenientadditions.procedures;

import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.minecraft.world.entity.Entity;

public class PremiumSawmillCheckProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null) {
			return false;
		}
		if (entity.getCapability(Capabilities.ItemHandler.ENTITY, null) instanceof IItemHandlerModifiable _modHandler0) {
			for (int i = 0; i < _modHandler0.getSlots(); i++) {
				if (_modHandler0.getStackInSlot(i).copy().getItem().toString().equals("convenient_extras:premium_ingot")){
					return true;
				}
			}
		}
		return false;
    }
}
package automandza.convenientadditions.procedures;

import net.minecraft.world.entity.Entity;

public class ResetUnlockedEnchtsProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		entity.getPersistentData().putString("unlockedEnchantList", "hard luck");
	}
}
package automandza.convenientadditions.procedures;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;

import automandza.convenientadditions.init.ConvenientAdditionsModMenus;

public class SawmillGUIThisGUIIsClosedProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof Player _player && _player.containerMenu instanceof ConvenientAdditionsModMenus.MenuAccessor _menu) {
			_menu.getSlots().get(1).set(ItemStack.EMPTY);
			_player.containerMenu.broadcastChanges();
		}
	}
}
package automandza.convenientadditions.procedures;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.BlockPos;

import automandza.convenientadditions.init.ConvenientAdditionsModMenus;

public class EnchWMatsTakeOutputProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof Player _player && _player.containerMenu instanceof ConvenientAdditionsModMenus.MenuAccessor _menu) {
			if (_menu.getSlots().get(2).getItem() == ItemStack.EMPTY){
				_player.giveExperienceLevels(-5);
			}
			_menu.getSlots().get(0).set(ItemStack.EMPTY);
			_menu.getSlots().get(1).remove((int) getBlockNBTNumber(world, BlockPos.containing(x, y, z), "enchCost"));
			_menu.getSlots().get(2).remove((int) getBlockNBTNumber(world, BlockPos.containing(x, y, z), "enchCost"));
			_player.containerMenu.broadcastChanges();
		}
	}

	private static int getBlockNBTNumber(LevelAccessor world, BlockPos pos, String tag) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity != null)
			return blockEntity.getPersistentData().getIntOr(tag, 0);
		return -1;
	}
}
package automandza.convenientadditions.procedures;

//imports
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.ItemTags;
import net.minecraft.resources.ResourceLocation;

import automandza.convenientadditions.init.ConvenientAdditionsModMenus;




public class SawmillTakeOutputProcedure {
	public static void execute(LevelAccessor world, int x, int y, int z, Entity entity, double amount) {
		if (entity == null)	return;
		int stackCount = (int) amount;
		
		if (entity instanceof Player _player && _player.containerMenu instanceof ConvenientAdditionsModMenus.MenuAccessor _menu) {
			ItemStack sawmillInputItem = _menu.getSlots().get(0).getItem();
			ItemStack sawmillOutputItem = _menu.getSlots().get(1).getItem();
			
			if (getAmountInGUISlot(entity, 0) > 1 && stackCount > 0) {
				if (sawmillInputItem.is(ItemTags.create(ResourceLocation.fromNamespaceAndPath("convenient_additions", "material/bark")))){
					_menu.getSlots().get(0).remove(stackCount*2);
				} else if (sawmillOutputItem.is(ItemTags.create(ResourceLocation.parse("minecraft:planks")))){
					_menu.getSlots().get(0).remove(1);
				} else {
					_menu.getSlots().get(0).remove(stackCount);
				}
			} else {
				_menu.getSlots().get(0).set(ItemStack.EMPTY);
			}
			_player.containerMenu.broadcastChanges();
		}
	}

	private static int getAmountInGUISlot(Entity entity, int sltid) {
		if (entity instanceof Player player && player.containerMenu instanceof ConvenientAdditionsModMenus.MenuAccessor menuAccessor) {
			ItemStack stack = menuAccessor.getSlots().get(sltid).getItem();
			if (stack != null) return stack.getCount();
		}
		return 0;
	}

	private static String getBlockNBTString(LevelAccessor world, BlockPos pos, String tag) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity != null)
			return blockEntity.getPersistentData().getStringOr(tag, "");
		return "";
	}
}
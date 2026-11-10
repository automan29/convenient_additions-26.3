package automandza.convenient_additions.procedures;

//imports
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.minecraft.resources.Identifier;

import automandza.convenient_additions.init.ConvenientAdditionsModMenus;




public class SawmillShiftClickOutputProcedure {
	public static void execute(LevelAccessor world, int x, int y, int z, Entity entity, double ignoreAmount) {
		if (entity == null)	return;

        String currentSawmillRecipe = getBlockNBTString(world, BlockPos.containing(x, y, z));
		
		if (currentSawmillRecipe.length() < 2){
			currentSawmillRecipe = "default";
		}

		// makes sure that the game doesn't delete items by giving the player stacks of items directly
		if (entity instanceof Player _player && _player.containerMenu instanceof ConvenientAdditionsModMenus.MenuAccessor _menu) {
			ItemStack sawmillInputItem = _menu.getSlots().get(0).getItem();
			ItemStack sawmillOutputItem = _menu.getSlots().get(1).getItem();
			
			if (_player.getInventory().getFreeSlot() == -1){
				System.out.println("Warning! Player could not receive items as inventory full");
				return;
			}

			// gives player planks from logs in 1:5 ratio
			if (currentSawmillRecipe.equals("cut") && sawmillInputItem.is(ItemTags.create(Identifier.parse("minecraft:logs")))){
				ItemStack planksQuickStack = sawmillOutputItem.copy(); // does not clear as we may need for later
				int numPlanksToReceive = getAmountInGUISlot(entity, 0)*5;

				// iterates through the amount of items to receive and adds 60 each time until inventory full or no more stacks to add
				if (numPlanksToReceive > 64) {
					int receiveStackCount = (int) Math.floor(numPlanksToReceive / 60d);

					for (int i = 1; i <= receiveStackCount; i++) {
						planksQuickStack.setCount(60);
						// checks there is enough space otherwise stops, but it doesn't work for unknown reasons
						if (_player.getInventory().getSlotWithRemainingSpace(planksQuickStack) == -1 && _player.getInventory().getFreeSlot() == -1){
							System.out.println("Warning! Player could not receive items as inventory full");
							return;
						}
						_player.addItem(planksQuickStack);
						_menu.getSlots().get(0).remove(12);
						numPlanksToReceive -= 60;
						// hopefully one of these helps
						_player.containerMenu.broadcastChanges();
						_player.inventoryMenu.broadcastChanges();
						_player.getInventory().tick();
					}
				}
				// adds excess
				planksQuickStack.setCount(numPlanksToReceive);
				if (numPlanksToReceive > 0 && (_player.getInventory().getSlotWithRemainingSpace(planksQuickStack) != -1 || _player.getInventory().getFreeSlot() != -1)){
					_player.addItem(planksQuickStack);
					sawmillOutputItem.setCount(0);
					_menu.getSlots().get(0).set(ItemStack.EMPTY);
				}

			// basically same deal but for slabs
			} else if (currentSawmillRecipe.equals("split") && sawmillInputItem.is(ItemTags.create(Identifier.parse("minecraft:planks")))){
				ItemStack slabsQuickStack = sawmillOutputItem.copy(); // does not clear as we may need for later
				int numSlabsToReceive = getAmountInGUISlot(entity, 0)*2;

				// iterates through the amount of items to receive and adds 64 each time until inventory full or no more stacks to add
				if (numSlabsToReceive > 64){
					int receiveStackCount = (int) Math.floor(numSlabsToReceive / 64d);

					for (int i = 1; i <= receiveStackCount; i++) {
						// checks there is enough space otherwise stops, but it doesn't work for unknown reasons
						if (_player.getInventory().getFreeSlot() == -1){
							System.out.println("info: Player could not receive items as inventory full");
							return;
						}
						slabsQuickStack.setCount(64);
						_player.addItem(slabsQuickStack);
						_menu.getSlots().get(0).remove(32);
						numSlabsToReceive -= 64;
						// hopefully one of these helps
						_player.containerMenu.broadcastChanges();
						_player.inventoryMenu.broadcastChanges();
						_player.getInventory().tick();
					}
				}
				// adds excess
				slabsQuickStack.setCount(numSlabsToReceive);
				if (numSlabsToReceive > 0 && (_player.getInventory().getSlotWithRemainingSpace(slabsQuickStack) != -1 || _player.getInventory().getFreeSlot() != -1)) {
					_player.addItem(slabsQuickStack);
					sawmillOutputItem.setCount(0);
					_menu.getSlots().get(0).set(ItemStack.EMPTY);
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
	
	private static String getBlockNBTString(LevelAccessor world, BlockPos pos) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity != null) {
			return blockEntity.getPersistentData().getStringOr("currentSawRecipe", "");
		}
		return "";
	}

}
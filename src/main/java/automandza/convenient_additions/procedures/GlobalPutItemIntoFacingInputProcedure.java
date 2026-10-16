package automandza.convenientadditions.procedures;

import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.common.extensions.ILevelExtension;
import net.neoforged.neoforge.capabilities.Capabilities;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

public class GlobalPutItemIntoFacingInputProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Direction direction, ItemStack itemstack, int takeSlot) {
		if (itemstack == ItemStack.EMPTY){
			System.out.println("Warning! input ItemStack was empty");
			return;
		}
		if (direction == null){
			System.out.println("ERROR! invalid direction");
			return;
		}
		
		int currInvID = 0;
		BlockPos outputBlkPos = new BlockPos(((int) x)+direction.getStepX(), ((int) y)+direction.getStepY(), ((int) z)+direction.getStepZ());

		if (world instanceof ILevelExtension lvlExt){
			if (lvlExt.getCapability(Capabilities.ItemHandler.BLOCK, outputBlkPos, null) == null){
				//System.out.println("Warning! no storage container found at facing direction");
				return;
			}

			// search for available output slots
			while (currInvID < 99 && !canInsertInBlockInventory(lvlExt, outputBlkPos, currInvID, 1, itemstack.copy())) {
				currInvID++;
			}
			
			if (canInsertInBlockInventory(lvlExt, outputBlkPos, currInvID, 1, itemstack.copy())) {
				if (lvlExt.getCapability(Capabilities.ItemHandler.BLOCK, outputBlkPos, null) instanceof IItemHandlerModifiable _itemHandlerModifiable) {
					// adds item to storage
					ItemStack _setstack = itemstack.copy();
					int foundExistingStackCount = _itemHandlerModifiable.getStackInSlot(currInvID).getCount();
					_setstack.setCount(foundExistingStackCount+1);
					_itemHandlerModifiable.setStackInSlot(currInvID, _setstack);
				}
				if (lvlExt.getCapability(Capabilities.ItemHandler.BLOCK, BlockPos.containing(x, y, z), null) instanceof IItemHandlerModifiable _itemHandlerModifiable2) {
					// removes item from takeSlot of original block
					ItemStack _stk = _itemHandlerModifiable2.getStackInSlot(takeSlot).copy();
					_stk.shrink(1);
					_itemHandlerModifiable2.setStackInSlot(takeSlot, _stk);
				}
			}
			else {
				System.out.println("Warning! Could not find available output slot");
			}
		}
	}

	public static boolean canInsertInBlockInventory(ILevelExtension lvlExt, BlockPos pos, int slotId, int amount, ItemStack itemstack) {
		IItemHandler itemHandler = lvlExt.getCapability(Capabilities.ItemHandler.BLOCK, pos, null);
		if (itemHandler != null && slotId >= 0 && slotId < itemHandler.getSlots()) {
			ItemStack existingIStack = itemHandler.getStackInSlot(slotId);
			itemstack.setCount(amount);
			if(existingIStack.getItem() != itemstack.getItem() && existingIStack != ItemStack.EMPTY){
				return false;
			}
			if (existingIStack.getCount() >= 64 && existingIStack != ItemStack.EMPTY){
				return false;
			}
			return itemHandler.isItemValid(slotId, itemstack);
		}
		return false;
	}
}
package automandza.convenient_additions.procedures;

import net.neoforged.neoforge.transfer.transaction.Transaction;
import net.neoforged.neoforge.transfer.item.ItemUtil;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.ResourceHandler;
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
		if (direction == null){
			System.out.println("ERROR! invalid direction");
			return;
		}
		
		int currInvID = 0;
		BlockPos currBlkPos = BlockPos.containing(x,y,z);
		BlockPos outputBlkPos = new BlockPos(((int) x)+direction.getStepX(), ((int) y)+direction.getStepY(), ((int) z)+direction.getStepZ());

		if (world instanceof ILevelExtension lvlExt){
			if (lvlExt.getCapability(Capabilities.Item.BLOCK, outputBlkPos, null) == null){
				//System.out.println("Warning! no storage container found at facing direction");
				return;
			}

			// search for available output slots
			while (currInvID < 99 && !canInsertInBlockInventory(lvlExt, outputBlkPos, currInvID, 1, itemstack.copy())) {
				currInvID++;
			}
			
			if (canInsertInBlockInventory(lvlExt, outputBlkPos, currInvID, 1, itemstack.copy())) {
				// some crud that is required
				ResourceHandler<ItemResource> outputItmHandlr = lvlExt.getCapability(Capabilities.Item.BLOCK, outputBlkPos, null);
				ResourceHandler<ItemResource> inputItmHandlr = lvlExt.getCapability(Capabilities.Item.BLOCK, currBlkPos, null);
				ItemResource itemRes = ItemResource.of(itemstack);
				assert outputItmHandlr != null && inputItmHandlr != null;

				// adds item to storage
				try (Transaction _transaction = Transaction.openRoot()) {
					boolean insertWorked = outputItmHandlr.insert(currInvID, itemRes, 1, _transaction) == 1;
					boolean extractWorked; // for checking the insert/extract worked

					if (insertWorked) {
						// removes item from takeSlot of original block
						extractWorked = inputItmHandlr.extract(takeSlot, itemRes, 1, _transaction) == 1;
						if (extractWorked) {
							_transaction.commit();
						}
					}

				} catch (Exception e){
					System.out.println("ERROR occurred: "+e);
				}
			}
		}
	}

	public static boolean canInsertInBlockInventory(ILevelExtension lvlExt, BlockPos pos, int slotId, int amount, ItemStack itemstack) {
		ResourceHandler<ItemResource> itemHandler = lvlExt.getCapability(Capabilities.Item.BLOCK, pos, null);

		if (itemHandler != null && slotId >= 0 && slotId < itemHandler.size()) {
			ItemStack existingIStack = ItemUtil.getStack(itemHandler, slotId);
			itemstack.setCount(amount);

			if(existingIStack.getItem() != itemstack.getItem() && existingIStack != ItemStack.EMPTY){
				return false;
			}

			if (existingIStack.getCount() >= 64 && existingIStack != ItemStack.EMPTY){
				return false;
			}

			return itemHandler.isValid(slotId, ItemResource.of(itemstack));
		}

		return false;
	}
}
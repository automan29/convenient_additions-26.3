package automandza.convenientadditions.procedures;

//imports
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.common.extensions.ILevelExtension;
import net.neoforged.neoforge.capabilities.Capabilities;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import java.util.stream.Stream;
import net.minecraft.tags.BlockTags;
import net.minecraft.resources.ResourceLocation;

import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.EnumProperty;




public class KilnTickProcedure {

	public static void execute(LevelAccessor world, double x, double y, double z) {
		BlockPos KilnPos = BlockPos.containing(x, y, z);
		
		if (world instanceof ILevelExtension lvlExt){
			
			ItemStack inputItemStk = itemFromBlockInventory(lvlExt, KilnPos, 0).copy();
			ItemStack outputItemStk = itemFromBlockInventory(lvlExt, KilnPos, 1).copy();

			if (inputItemStk == ItemStack.EMPTY && outputItemStk == ItemStack.EMPTY){
				return;
			}

			double currTemperature = temperatureCalculationFromSurroundings(world, x, y, z);
			double netherMultiplier = 1d;
			
			// does temperature update
			if (!world.isClientSide()) {
				BlockEntity kilnEntity = world.getBlockEntity(KilnPos);
				if (kilnEntity != null) {
					BlockState KilnBlState = world.getBlockState(KilnPos);

					// move an item from kiln to block behind
					if (outputItemStk != ItemStack.EMPTY){
						GlobalPutItemIntoFacingInputProcedure.execute(world, x, y, z, getDirectionFromBlockState(KilnBlState).getOpposite(), outputItemStk, 1);
					}

					// update NBT value
					kilnEntity.getPersistentData().putDouble("temperature", currTemperature);

					// sends changes to kiln block
					if (world instanceof Level _level){
						_level.sendBlockUpdated(KilnPos, KilnBlState, KilnBlState, 3);
					}
				}
			}

			

			// smelting able check
			if (getBlockNBTNumber(world, KilnPos, "temperature") > 0 && canSmeltIStack(inputItemStk, outputItemStk)) {

				// nether multiplier
				if (world instanceof Level levl){
					if (levl.dimension() == Level.NETHER) netherMultiplier = 1.5d;
				} else if (world instanceof WorldGenLevel worGL){
					if (worGL.getLevel().dimension() == Level.NETHER) netherMultiplier = 1.5d;
				}
				
				double smeltPercentUpdate = getBlockNBTNumber(world, KilnPos, "percentageDone") + (getBlockNBTNumber(world, KilnPos, "temperature")*2d*netherMultiplier);

				// clay ball -> brick speed buff
				if (inputItemStk.getItem() == Items.CLAY_BALL){
					smeltPercentUpdate = getBlockNBTNumber(world, KilnPos, "percentageDone") + (getBlockNBTNumber(world, KilnPos, "temperature")*4d*netherMultiplier);
				}

				// updates furnace if smelt% is done
				if (lvlExt.getCapability(Capabilities.ItemHandler.BLOCK, KilnPos, null) instanceof IItemHandlerModifiable _itemHandlerModifiable) {
					if (smeltPercentUpdate >= 100d){
						determineKilnOutcome(inputItemStk, outputItemStk, _itemHandlerModifiable);
						smeltPercentUpdate = 0d;
					}
				}

				
				//checks
				if (!world.isClientSide()) {
					BlockEntity _blockEntity = world.getBlockEntity(KilnPos);
					if (_blockEntity != null) {
						BlockState _bs = world.getBlockState(KilnPos);

						// increases smelting %
						_blockEntity.getPersistentData().putDouble("percentageDone", smeltPercentUpdate);

						// sends changes to kiln block
						if (world instanceof Level _level)
{
							_level.sendBlockUpdated(KilnPos, _bs, _bs, 3);
						}
					}
				}
	
			}
			
		}
	}



	public static double temperatureCalculationFromSurroundings(LevelAccessor lvlAcc, double x, double y, double z){
		Block immediateBelowBlk = lvlAcc.getBlockState(BlockPos.containing(x, y-1, z)).getBlock();
		double lavaCount = 0;

		// lava check spam
		if (lvlAcc.getBlockState(BlockPos.containing(x+1, y, z)).getBlock() == Blocks.LAVA){
			lavaCount += 1d;
		}
		if (lvlAcc.getBlockState(BlockPos.containing(x-1, y, z)).getBlock() == Blocks.LAVA){
			lavaCount += 1d;
		}
		if (lvlAcc.getBlockState(BlockPos.containing(x, y, z+1)).getBlock() == Blocks.LAVA){
			lavaCount += 1d;
		}
		if (lvlAcc.getBlockState(BlockPos.containing(x, y, z-1)).getBlock() == Blocks.LAVA){
			lavaCount += 1d;
		}

		if (lavaCount > 0){
			// max 8
			return lavaCount*2d;
		}
		
		
		// checks what blocks are below for temperature calc
		if (immediateBelowBlk == Blocks.FIRE){
			if (lvlAcc.getBlockState(BlockPos.containing(x, y-2, z)).getBlock() == Blocks.COAL_BLOCK){
				return 14d;
			} else if(Stream.of(BlockTags.create(ResourceLocation.parse("minecraft:logs"))).anyMatch(lvlAcc.getBlockState(BlockPos.containing(x, y-2, z))::is)){
				// checks for logs tag
				return 7d;
			} else if(Stream.of(BlockTags.create(ResourceLocation.parse("minecraft:planks"))).anyMatch(lvlAcc.getBlockState(BlockPos.containing(x, y-2, z))::is)){
				// checks for planks tag
				return 3d;
			} else {
				return 1d;
			}
		} else if (immediateBelowBlk == Blocks.LAVA){
			return 2.5d;
		} else if (immediateBelowBlk == Blocks.MAGMA_BLOCK){
			return 1d;
		} else if (immediateBelowBlk == Blocks.CAMPFIRE){
			return 0.5d;
		} else if (immediateBelowBlk == Blocks.TORCH){
			return 0.02d;
		}

		return 0d;
	}
	

	public static void determineKilnOutcome(ItemStack inputIStk, ItemStack outputIStk, IItemHandlerModifiable itmHandler){
		ItemStack setStack = ItemStack.EMPTY;
		int stackCountIncrease = 1;

		// recipies
		if (inputIStk.getItem() == Blocks.COBBLESTONE.asItem()){
			setStack = new ItemStack(Blocks.STONE).copy();
		} else if (inputIStk.getItem() == Items.CLAY_BALL){
			setStack = new ItemStack(Items.BRICK).copy();
		} else if (inputIStk.getItem() == Blocks.COBBLED_DEEPSLATE.asItem()){
			setStack = new ItemStack(Blocks.DEEPSLATE).copy();
		} else if (inputIStk.getItem() == Blocks.STONE.asItem()){
			setStack = new ItemStack(Blocks.SMOOTH_STONE).copy();
		} else if (inputIStk.getItem() == Blocks.BASALT.asItem()){
			setStack = new ItemStack(Blocks.SMOOTH_BASALT).copy();
		} else if (inputIStk.getItem() == Blocks.CLAY.asItem()){
			setStack = new ItemStack(Blocks.TERRACOTTA).copy();
			stackCountIncrease = 2;
		} else if (inputIStk.getItem() == Blocks.PACKED_MUD.asItem()){
			setStack = new ItemStack(Blocks.MUD_BRICKS).copy();
			stackCountIncrease = 2;
		} else if (inputIStk.getItem() == Blocks.NETHERRACK.asItem()){
			setStack = new ItemStack(Items.NETHER_BRICK).copy();
			stackCountIncrease = 2;
		} else if (inputIStk.getItem() == Blocks.SAND.asItem() || inputIStk.getItem() == Items.RED_SAND){
			setStack = new ItemStack(Blocks.GLASS).copy();
			stackCountIncrease = 2;
		}

		// set output itemstack count
		if (outputIStk == ItemStack.EMPTY){
			setStack.setCount(stackCountIncrease);
		} else {
			setStack.setCount(outputIStk.getCount()+stackCountIncrease);
		}
		if (inputIStk.getCount() > 1){
			inputIStk.setCount(inputIStk.getCount()-1);
		} else {
			inputIStk = ItemStack.EMPTY;
		}

		// update output and input slot
		itmHandler.setStackInSlot(1, setStack);
		itmHandler.setStackInSlot(0, inputIStk);
	}


	public static boolean canSmeltIStack(ItemStack inputIStk, ItemStack outputIStk){
		if (inputIStk == ItemStack.EMPTY || outputIStk.getCount() >= 64){
			return false;
		}

		// recipies
		if (inputIStk.getItem() == Blocks.COBBLESTONE.asItem()){
            return outputIStk.getItem() == Blocks.STONE.asItem() || outputIStk == ItemStack.EMPTY;
		} else if (inputIStk.getItem() == Items.CLAY_BALL){
            return outputIStk.getItem() == Items.BRICK || outputIStk == ItemStack.EMPTY;
		} else if (inputIStk.getItem() == Blocks.NETHERRACK.asItem()){
            return outputIStk.getItem() == Items.NETHER_BRICK || outputIStk == ItemStack.EMPTY;
		} else if (inputIStk.getItem() == Blocks.SAND.asItem() || inputIStk.getItem() == Items.RED_SAND){
            return outputIStk.getItem() == Blocks.GLASS.asItem() || outputIStk == ItemStack.EMPTY;
		} else if(inputIStk.getItem() == Blocks.COBBLED_DEEPSLATE.asItem()){
            return outputIStk.getItem() == Blocks.DEEPSLATE.asItem() || outputIStk == ItemStack.EMPTY;
		} else if(inputIStk.getItem() == Blocks.STONE.asItem()){
            return outputIStk.getItem() == Blocks.SMOOTH_STONE.asItem() || outputIStk == ItemStack.EMPTY;
		} else if (inputIStk.getItem() == Blocks.CLAY.asItem()){
            return outputIStk.getItem() == Blocks.TERRACOTTA.asItem() || outputIStk == ItemStack.EMPTY;
		} else if (inputIStk.getItem() == Blocks.BASALT.asItem()){
            return outputIStk.getItem() == Blocks.SMOOTH_BASALT.asItem() || outputIStk == ItemStack.EMPTY;
		} else if (inputIStk.getItem() == Blocks.PACKED_MUD.asItem()){
            return outputIStk.getItem() == Blocks.MUD_BRICKS.asItem() || outputIStk == ItemStack.EMPTY;
		}
		
		return false;
	}

	

	

	// generated functions
	private static ItemStack itemFromBlockInventory(ILevelExtension ext, BlockPos pos, int slot) {
		IItemHandler itemHandler = ext.getCapability(Capabilities.ItemHandler.BLOCK, pos, null);
		if (itemHandler != null)
{
			return itemHandler.getStackInSlot(slot);
		}
		return ItemStack.EMPTY;
	}

	private static double getBlockNBTNumber(LevelAccessor world, BlockPos pos, String tag) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity != null)
{
			return blockEntity.getPersistentData().getDoubleOr(tag, 0);
		}
		return -1;
	}

	private static Direction getDirectionFromBlockState(BlockState blockState) {
		try {
			if (getPropertyByName(blockState, "facing") instanceof EnumProperty ep && ep.getValueClass() == Direction.class) {
				return (Direction) blockState.getValue(ep);
			}
			if (getPropertyByName(blockState, "axis") instanceof EnumProperty ep && ep.getValueClass() == Direction.Axis.class) {
				return Direction.fromAxisAndDirection((Direction.Axis) blockState.getValue(ep), Direction.AxisDirection.POSITIVE);
			}
		} catch (Exception e){
			System.out.println("ERROR! "+e);
		}
		return Direction.NORTH;
	}

	private static Property<?> getPropertyByName(BlockState state, String name) {
		for (Property<?> property : state.getProperties()) {
			if (property.getName().equals(name)) {
				return property;
			}
		}
		return null;
	}

}
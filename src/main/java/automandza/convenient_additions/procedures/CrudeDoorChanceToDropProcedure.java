package automandza.convenientadditions.procedures;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.core.BlockPos;

import automandza.convenientadditions.init.ConvenientAdditionsModBlocks;

public class CrudeDoorChanceToDropProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		if (Mth.nextInt(RandomSource.create(), 1, 5) == 5) {
			world.levelEvent(2001, BlockPos.containing(x, y, z), Block.getId(ConvenientAdditionsModBlocks.CRUDE_DOOR.get().defaultBlockState()));
			world.setBlock(BlockPos.containing(x, y, z), Blocks.AIR.defaultBlockState(), 3);
		}
	}
}
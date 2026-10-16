package automandza.convenientadditions.procedures;

//imports
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import automandza.convenientadditions.procedures.CeilingLampLuminanceProcedure;




public class CeilingLampRightclickedProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null) return;

		String currentLightBlockPositions;
		String decorationBlockPosString = entity.getPersistentData().getStringOr("decBlockPos", "NOT GOOD");
		if (decorationBlockPosString.equals("NOT GOOD")) {
			System.out.println("Warning: decoration block was not found");
			return;
		}

		double decBlockX = Double.parseDouble(decorationBlockPosString.substring(2, decorationBlockPosString.indexOf(",y=")));
		double decBlockY = Double.parseDouble(decorationBlockPosString.substring(decorationBlockPosString.indexOf(",y=")+3, decorationBlockPosString.indexOf(",z=")));
		double decBlockZ = Double.parseDouble(decorationBlockPosString.substring(decorationBlockPosString.indexOf(",z=")+3));
		BlockPos decBp = BlockPos.containing(decBlockX, decBlockY, decBlockZ);

		
		if (!world.isClientSide()) {
			BlockEntity _blockEntity = world.getBlockEntity(decBp);
			BlockState _bs = world.getBlockState(decBp);

			if (getBlockNBTString(world, decBp, "lightPositions").equals("")){
				currentLightBlockPositions = x + "," + y + "," + z;
			} else {
				currentLightBlockPositions = getBlockNBTString(world, decBp, "lightPositions") + "|" + x + "," + y + "," + z;
			}
			System.out.println("currentLightBlockPositions="+currentLightBlockPositions);
			
			if (_blockEntity != null) {
				_blockEntity.getPersistentData().putString("lightPositions", currentLightBlockPositions);
			}
			
			if (world instanceof Level _level){
				_level.sendBlockUpdated(decBp, _bs, _bs, 3);
				_level.setBlock(BlockPos.containing(x, y, z), _level.getBlockState(BlockPos.containing(x, y, z)).cycle(BlockStateProperties.LIT), 2);
			}
		}
	}


	private static String getBlockNBTString(LevelAccessor world, BlockPos pos, String tag) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		
		if (blockEntity != null){
			return blockEntity.getPersistentData().getStringOr(tag, "");
		}
		return "";
	}
}
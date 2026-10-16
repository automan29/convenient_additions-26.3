package automandza.convenientadditions.procedures;

//imports
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.Level;
import automandza.convenientadditions.procedures.CeilingLampLuminanceProcedure;



public class DecorationStationRedstoneOnProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		String lightPosList = getBlockNBTString(world, BlockPos.containing(x, y, z), "lightPositions");
		double tempX;
		double tempY;
		double tempZ;
		String templightPosList;
		
		if (world instanceof Level mcLevel){
			while (!lightPosList.isEmpty()){
				templightPosList = lightPosList;
				tempX = Double.parseDouble(templightPosList.substring(0, templightPosList.indexOf(",")));
				
				templightPosList = templightPosList.substring(templightPosList.indexOf(",")+1);
				tempY = Double.parseDouble(templightPosList.substring(0, templightPosList.indexOf(",")));
				
				templightPosList = templightPosList.substring(templightPosList.indexOf(",")+1);
				if (lightPosList.contains("|")){
					tempZ = Double.parseDouble(templightPosList.substring(0, templightPosList.indexOf("|")));
				} else {
					tempZ = Double.parseDouble(templightPosList);
				}
				
				BlockPos pos = BlockPos.containing(tempX, tempY, tempZ);
				//CeilingLampLuminanceProcedure.addCheckBlockPosList(tempX, tempY, tempZ, mcLevel);
				mcLevel.setBlock(pos, mcLevel.getBlockState(pos).setValue(BlockStateProperties.LIT, true), 2);
				
				if (lightPosList.contains("|")){
					lightPosList = lightPosList.substring(lightPosList.indexOf("|")+1);
				} else {
					lightPosList = "";
				}
			}
		}
	}



	private static String getBlockNBTString(LevelAccessor world, BlockPos pos, String tag) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity != null)
 {
			return blockEntity.getPersistentData().getStringOr(tag, "");
		}
		return "";
	}
}
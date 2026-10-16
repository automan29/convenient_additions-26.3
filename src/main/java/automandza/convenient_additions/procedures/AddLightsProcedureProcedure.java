package automandza.convenientadditions.procedures;

//imports
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.BlockPos;




public class AddLightsProcedureProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null) return;
		String targetDecorBlockPos = "x="+x+",y="+y+",z="+z;
		BlockPos decBp = BlockPos.containing(x, y, z);

		entity.getPersistentData().putString("decBlockPos", targetDecorBlockPos);


		if (!world.isClientSide()) {
			BlockEntity _blockEntity = world.getBlockEntity(decBp);
			BlockState _bs = world.getBlockState(decBp);
			
			if (_blockEntity != null) {
				_blockEntity.getPersistentData().putString("lightPositions", "");
			}
			
			if (world instanceof Level _level){
				_level.sendBlockUpdated(decBp, _bs, _bs, 3);
			}
		}
	}
}
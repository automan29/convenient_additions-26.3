package automandza.convenientadditions.procedures;

//imports
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;

import java.util.Comparator;
import net.minecraft.world.level.block.state.BlockState;
import java.util.List;





public class PutOutFiresProcedure {
	private static final int fireExtinguishArea = 3;
	
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
		if (entity == null){
			return;
		}
		if (world instanceof ServerLevel _level){
			// spawn particles
			BlockPos closeParticlePos = getBlockPosFromEntDir(entity, 1);
			BlockPos medParticlePos = getBlockPosFromEntDir(entity, 4);
			_level.sendParticles(ParticleTypes.CLOUD, closeParticlePos.getX(), closeParticlePos.getY(), closeParticlePos.getZ(), 2, 0.2, 0, 0.2, -0.2);
			_level.sendParticles(ParticleTypes.CLOUD, medParticlePos.getX(), medParticlePos.getY(), medParticlePos.getZ(), (int) (Math.round(Math.random()*3)+1), 1, 0.4, 1, -0.4);

			// put out mobs
			extinguishEntityInAreaWithClass(world, Entity.class, x, y, z, 7);

			// remove fire blocks
			BlockPos extinguishCenterPos = getBlockPosFromEntDir(entity, 5);
			for (int _ix = -fireExtinguishArea; _ix < fireExtinguishArea; _ix++){
				for (int _iy = -fireExtinguishArea; _iy < fireExtinguishArea; _iy++){
					for (int _iz = -fireExtinguishArea; _iz < fireExtinguishArea; _iz++){
						BlockPos blkPsi = new BlockPos(extinguishCenterPos.getX()+_ix, extinguishCenterPos.getY()+_iy, extinguishCenterPos.getZ()+_iz);
						if (world.getBlockState(blkPsi).getBlock() == Blocks.FIRE){
							world.setBlock(blkPsi, Blocks.AIR.defaultBlockState(), 3);
						}
					}
				}
			}

			if (!(entity instanceof Player plyr && plyr.isCreative())){
				itemstack.hurtAndBreak(1, _level, null, _stkprov -> {
				});
			}
		}
	}



	public static void extinguishEntityInAreaWithClass(LevelAccessor world, Class<? extends Entity> clazz, double x, double y, double z, double range) {
		for (Entity currEnt : world.getEntitiesOfClass(clazz, AABB.ofSize(new Vec3(x, y, z), range, range, range))){
			currEnt.clearFire();
		}
	}
	

	public static BlockPos getBlockPosFromEntDir(Entity entity, int scalerMult){
		ClipContext clpContxt = new ClipContext(
			entity.getEyePosition(1f),
			entity.getEyePosition(1f).add(entity.getViewVector(1f).scale(scalerMult)),
			ClipContext.Block.COLLIDER,
			ClipContext.Fluid.NONE,
			entity
		);
		return entity.level().clip(clpContxt).getBlockPos();
	}
}
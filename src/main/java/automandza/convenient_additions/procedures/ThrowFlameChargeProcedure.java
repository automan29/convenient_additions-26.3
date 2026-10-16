package automandza.convenientadditions.procedures;

//imports
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;



public class ThrowFlameChargeProcedure {
	public static void execute(Entity entity, ItemStack itemstack) {
		if (entity == null){
			return;
		}
		
		if (entity instanceof LivingEntity currLivEnt) {
			if (currLivEnt instanceof Player currPlayer){
				
				if (!currPlayer.isCreative()){
					itemstack.shrink(1);
				}

				Level projectileLevel = entity.level();
		
				if (!projectileLevel.isClientSide()) {
					Projectile _entityToSpawn = initProjectileProperties(new LargeFireball(EntityType.FIREBALL, projectileLevel), entity, new Vec3(entity.getLookAngle().x, entity.getLookAngle().y, entity.getLookAngle().z));
					_entityToSpawn.setPos(entity.getX(), entity.getEyeY() - 0.1, entity.getZ());
					_entityToSpawn.shoot(entity.getLookAngle().x, entity.getLookAngle().y, entity.getLookAngle().z, 1, 0);
					projectileLevel.addFreshEntity(_entityToSpawn);
				}
			}
		}
	}


	private static Projectile initProjectileProperties(Projectile entityToSpawn, Entity shooter, Vec3 acceleration) {
		entityToSpawn.setOwner(shooter);
		if (!Vec3.ZERO.equals(acceleration)) {
			entityToSpawn.setDeltaMovement(acceleration);
			entityToSpawn.hasImpulse = true;
		}
		return entityToSpawn;
	}
}
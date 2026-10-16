package automandza.convenientadditions.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.BlockPos;

import automandza.convenientadditions.init.ConvenientAdditionsModParticleTypes;
import automandza.convenientadditions.init.ConvenientAdditionsModItems;

public class GRS1600RightclickedProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("entity.warden.roar")), SoundSource.NEUTRAL, 1, -4);
			} else {
				_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("entity.warden.roar")), SoundSource.NEUTRAL, 1, -4, false);
			}
		}
		world.addParticle((SimpleParticleType) (ConvenientAdditionsModParticleTypes.PENGUIN_HORN_PARTICLE.get()), x, y, z, 0, 0.5, 0);
		if (entity instanceof Player _player)
			_player.getCooldowns().addCooldown(new ItemStack(ConvenientAdditionsModItems.GRS_1600.get()), 40);
	}
}
package automandza.convenientadditions.block;

import net.neoforged.neoforge.common.util.DeferredSoundType;

import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.util.ARGB;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;

import com.mojang.serialization.MapCodec;

public class ArmOreBlockBlock extends FallingBlock {
	public static final MapCodec<ArmOreBlockBlock> CODEC = simpleCodec(ArmOreBlockBlock::new);

	@Override
	public MapCodec<ArmOreBlockBlock> codec() {
		return CODEC;
	}

	@Override
	public int getDustColor(BlockState blockstate, BlockGetter world, BlockPos pos) {
		return blockstate.getMapColor(world, pos).col;
	}

	public ArmOreBlockBlock(BlockBehaviour.Properties properties) {
		super(properties
				.sound(new DeferredSoundType(1.0f, 1.0f, () -> BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("entity.parrot.imitate.zombie_villager")),
						() -> BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("block.stone.step")), () -> BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("convenient_additions:arm_ore_fall")),
						() -> BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("block.stone.hit")), () -> BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("convenient_additions:arm_ore_fall"))))
				.strength(3.5f, 10f).requiresCorrectToolForDrops().instrument(NoteBlockInstrument.SKELETON));
	}

	@Override
	public Integer getBeaconColorMultiplier(BlockState state, LevelReader world, BlockPos pos, BlockPos beaconPos) {
		return ARGB.opaque(-4026);
	}
}
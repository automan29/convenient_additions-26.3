package automandza.convenient_additions.block;

import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.BlockPos;

import automandza.convenient_additions.procedures.CrudeDoorChanceToDropProcedure;
import org.jspecify.annotations.NonNull;

public class CrudeDoorBlock extends DoorBlock {
	public CrudeDoorBlock(BlockBehaviour.Properties properties) {
		super(BlockSetType.OAK,
				properties.sound(SoundType.GRAVEL).strength(1f, 10f)
				.noOcclusion().pushReaction(PushReaction.POPPED)
				.isRedstoneConductor((_, _, _) -> false)
		);
	}

	@Override
	public @NonNull InteractionResult useWithoutItem(@NonNull BlockState blockstate, @NonNull Level world, @NonNull BlockPos pos, @NonNull Player entity, @NonNull BlockHitResult hit) {
		super.useWithoutItem(blockstate, world, pos, entity, hit);
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		CrudeDoorChanceToDropProcedure.execute(world, x, y, z);
		return InteractionResult.SUCCESS;
	}
}
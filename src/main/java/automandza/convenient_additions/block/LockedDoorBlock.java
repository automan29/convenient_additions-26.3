package automandza.convenient_additions.block;

import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.BlockPos;

import javax.annotation.Nullable;

import automandza.convenient_additions.procedures.LockedDoorOnBlockRightclickedProcedure;
import automandza.convenient_additions.procedures.LockedDoorIgnoreRedstoneProcedure;
import automandza.convenient_additions.block.entity.LockedDoorBlockEntity;
import org.jspecify.annotations.NonNull;

public class LockedDoorBlock extends DoorBlock implements EntityBlock {
	public LockedDoorBlock(BlockBehaviour.Properties properties) {
		super(BlockSetType.IRON, properties.mapColor(MapColor.NONE).sound(SoundType.IRON).strength(-1f, 40f).pushReaction(PushReaction.IMMOVEABLE).instrument(NoteBlockInstrument.GUITAR));
	}

	@Override
	public void neighborChanged(@NonNull BlockState blockstate, @NonNull Level world, @NonNull BlockPos pos, @NonNull Block neighborBlock, @Nullable Orientation orientation, boolean moving) {
		super.neighborChanged(blockstate, world, pos, neighborBlock, orientation, moving);
		if (world.getBestNeighborSignal(pos) > 0) {
			LockedDoorIgnoreRedstoneProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ());
		}
	}

	@Override
	public @NonNull InteractionResult useWithoutItem(@NonNull BlockState blockstate, @NonNull Level world, @NonNull BlockPos pos, @NonNull Player entity, @NonNull BlockHitResult hit) {
		super.useWithoutItem(blockstate, world, pos, entity, hit);
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		LockedDoorOnBlockRightclickedProcedure.execute(world, x, y, z, blockstate, entity);
		return InteractionResult.SUCCESS;
	}

	@Override
	public MenuProvider getMenuProvider(@NonNull BlockState state, Level worldIn, @NonNull BlockPos pos) {
		BlockEntity tileEntity = worldIn.getBlockEntity(pos);
		return tileEntity instanceof MenuProvider menuProvider ? menuProvider : null;
	}

	@Override
	public BlockEntity newBlockEntity(@NonNull BlockPos pos, @NonNull BlockState state) {
		return new LockedDoorBlockEntity(pos, state);
	}

	@Override
	public boolean triggerEvent(@NonNull BlockState state, @NonNull Level world, @NonNull BlockPos pos, int eventID, int eventParam) {
		super.triggerEvent(state, world, pos, eventID, eventParam);
		BlockEntity blockEntity = world.getBlockEntity(pos);
		return blockEntity != null && blockEntity.triggerEvent(eventID, eventParam);
	}
}
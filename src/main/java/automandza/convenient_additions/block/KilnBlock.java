package automandza.convenient_additions.block;

import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.Containers;
import net.minecraft.util.RandomSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import io.netty.buffer.Unpooled;

import automandza.convenient_additions.world.inventory.KilnGUIMenu;
import automandza.convenient_additions.procedures.KilnTickProcedure;
import automandza.convenient_additions.block.entity.KilnBlockEntity;
import org.jspecify.annotations.NonNull;

import java.util.Objects;

public class KilnBlock extends Block implements EntityBlock {
	public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;

	public KilnBlock(BlockBehaviour.Properties properties) {
		super(properties.strength(2f, 10f).instrument(NoteBlockInstrument.PLING));
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.@NonNull Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FACING);
	}

	@Override
	public BlockState getStateForPlacement(@NonNull BlockPlaceContext context) {
		return Objects.requireNonNull(super.getStateForPlacement(context)).setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

	public @NonNull BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
	}

	public @NonNull BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
	}

	@Override
	public void onPlace(@NonNull BlockState blockstate, @NonNull Level world, @NonNull BlockPos pos, @NonNull BlockState oldState, boolean moving) {
		super.onPlace(blockstate, world, pos, oldState, moving);
		world.scheduleTick(pos, this, 2);
	}

	@Override
	public void tick(@NonNull BlockState blockstate, @NonNull ServerLevel world, @NonNull BlockPos pos, @NonNull RandomSource random) {
		super.tick(blockstate, world, pos, random);
		KilnTickProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ());
		world.scheduleTick(pos, this, 2);
	}

	@Override
	public @NonNull InteractionResult useWithoutItem(@NonNull BlockState blockstate, @NonNull Level world, @NonNull BlockPos pos, @NonNull Player entity, @NonNull BlockHitResult hit) {
		super.useWithoutItem(blockstate, world, pos, entity, hit);
		if (entity instanceof ServerPlayer player) {
			player.openMenu(new MenuProvider() {
				@Override
				public @NonNull Component getDisplayName() {
					return Component.literal("Auto Kiln");
				}

				@Override
				public AbstractContainerMenu createMenu(int id, @NonNull Inventory inventory, @NonNull Player player) {
					return new KilnGUIMenu(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(pos));
				}
			}, pos);
		}
		return InteractionResult.SUCCESS;
	}

	@Override
	public MenuProvider getMenuProvider(@NonNull BlockState state, Level worldIn, @NonNull BlockPos pos) {
		BlockEntity tileEntity = worldIn.getBlockEntity(pos);
		return tileEntity instanceof MenuProvider menuProvider ? menuProvider : null;
	}

	@Override
	public BlockEntity newBlockEntity(@NonNull BlockPos pos, @NonNull BlockState state) {
		return new KilnBlockEntity(pos, state);
	}

	@Override
	public boolean triggerEvent(@NonNull BlockState state, @NonNull Level world, @NonNull BlockPos pos, int eventID, int eventParam) {
		super.triggerEvent(state, world, pos, eventID, eventParam);
		BlockEntity blockEntity = world.getBlockEntity(pos);
		return blockEntity != null && blockEntity.triggerEvent(eventID, eventParam);
	}

	@Override
	protected void affectNeighborsAfterRemoval(@NonNull BlockState blockstate, @NonNull ServerLevel world, @NonNull BlockPos blockpos, boolean flag) {
		Containers.updateNeighboursAfterDestroy(blockstate, world, blockpos);
	}

	@Override
	public boolean hasAnalogOutputSignal(@NonNull BlockState state) {
		return true;
	}

	@Override
	public int getAnalogOutputSignal(BlockState ignoredBlockState, Level level, BlockPos pos, Direction ignoredDir) {
		BlockEntity tileEnt = level.getBlockEntity(pos);
		if (tileEnt instanceof KilnBlockEntity be) {
			return AbstractContainerMenu.getRedstoneSignalFromContainer(be);
		}
		return 0;
	}
}
package automandza.convenient_additions.block;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import org.jspecify.annotations.NonNull;

import java.util.Objects;
import java.util.function.Function;

public class MarbleRubbleBlock extends Block {
	public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
	private final Function<BlockState, VoxelShape> shapes = this.makeShapes();

	public MarbleRubbleBlock(BlockBehaviour.Properties properties) {
		super(properties.strength(1.5f, 15f).requiresCorrectToolForDrops().noOcclusion().isRedstoneConductor((bs, br, bp) -> false).instrument(NoteBlockInstrument.SKELETON));
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
	}

	private Function<BlockState, VoxelShape> makeShapes() {
		return this.getShapeForEachState(state -> switch (state.getValue(FACING)) {
            case NORTH -> box(1, 0, 1, 15, 7, 15);
            case EAST -> box(1, 0, 1, 15, 7, 15);
            case WEST -> box(1, 0, 1, 15, 7, 15);
			default -> box(1, 0, 1, 15, 7, 15);
		});
	}

	@Override
	public @NonNull VoxelShape getShape(@NonNull BlockState state, @NonNull BlockGetter world, @NonNull BlockPos pos, @NonNull CollisionContext context) {
		return shapes.apply(state);
	}

	@Override
	public boolean propagatesSkylightDown(@NonNull BlockState state) {
		return true;
	}


	@Override
	public @NonNull VoxelShape getVisualShape(@NonNull BlockState state, @NonNull BlockGetter world, @NonNull BlockPos pos, @NonNull CollisionContext context) {
		return Shapes.empty();
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
}
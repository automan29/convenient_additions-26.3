package automandza.convenientadditions.procedures;


//imports
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResult;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.component.DataComponents;

import javax.annotation.Nullable;

import automandza.convenientadditions.init.ConvenientAdditionsModItems;

import java.util.Objects;



@EventBusSubscriber(modid = "convenient_additions")
public class GiveOakBarkProcedure {
	@SubscribeEvent
	public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
		if (event.getHand() != InteractionHand.MAIN_HAND){
			return;
		}
		execute(event, event.getLevel(), event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), event.getLevel().getBlockState(event.getPos()), event.getEntity(), event.getHitVec());
	}
	
	public static boolean execute(LevelAccessor world, double x, double y, double z, BlockState blockstate, Entity entity) {
		return execute(null, world, x, y, z, blockstate, entity, null);
	}

	private static boolean execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, BlockState blockstate, Entity entity, @Nullable BlockHitResult blockHitRes) {
		if (entity == null){
			return false;
		}
		double randomPosOffset;
		double randomPosOffsetZ;
		ItemStack current_bark = ItemStack.EMPTY.copy();

		ItemStack currentMainHandItem = (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY);
		ItemStack currentOffhandItem = (entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY);

		if (entity instanceof Player plyr) {
			if (currentOffhandItem.getItem() instanceof AxeItem _offhandAxe) {
				if (
						!blockstate.is(BlockTags.create(ResourceLocation.parse("minecraft:logs")))
						| _offhandAxe.useOn(new UseOnContext(plyr, InteractionHand.OFF_HAND, Objects.requireNonNull(blockHitRes))) != InteractionResult.SUCCESS
				) {
					return false;
				}
			}


			if ((
					currentMainHandItem.is(ItemTags.create(ResourceLocation.parse("minecraft:axes")))
					|| currentOffhandItem.is(ItemTags.create(ResourceLocation.parse("minecraft:axes")))
			) && !(
					currentOffhandItem.has(DataComponents.BLOCKS_ATTACKS) && !(plyr.isSecondaryUseActive())
			)) {
				if (blockstate.getBlock() == Blocks.OAK_LOG || blockstate.getBlock() == Blocks.OAK_WOOD) {
					current_bark = new ItemStack(ConvenientAdditionsModItems.OAK_BARK.get()).copy();
				}
				if (blockstate.getBlock() == Blocks.SPRUCE_LOG || blockstate.getBlock() == Blocks.SPRUCE_WOOD) {
					current_bark = new ItemStack(ConvenientAdditionsModItems.SPRUCE_BARK.get()).copy();
				}
				if (blockstate.getBlock() == Blocks.DARK_OAK_LOG || blockstate.getBlock() == Blocks.DARK_OAK_WOOD) {
					current_bark = new ItemStack(ConvenientAdditionsModItems.DARKOAK_BARK.get()).copy();
				}
				if (blockstate.getBlock() == Blocks.BIRCH_LOG || blockstate.getBlock() == Blocks.BIRCH_WOOD) {
					current_bark = new ItemStack(ConvenientAdditionsModItems.BIRCH_BARK.get()).copy();
				}
				if (blockstate.getBlock() == Blocks.ACACIA_LOG || blockstate.getBlock() == Blocks.ACACIA_WOOD) {
					current_bark = new ItemStack(ConvenientAdditionsModItems.ACACIA_BARK.get()).copy();
				}
				if (blockstate.getBlock() == Blocks.JUNGLE_LOG || blockstate.getBlock() == Blocks.JUNGLE_WOOD) {
					current_bark = new ItemStack(ConvenientAdditionsModItems.JUNGLE_BARK.get()).copy();
				}
				if (blockstate.getBlock() == Blocks.CHERRY_LOG || blockstate.getBlock() == Blocks.CHERRY_WOOD) {
					current_bark = new ItemStack(ConvenientAdditionsModItems.CHERRY_BARK.get()).copy();
				}
				if (blockstate.getBlock() == Blocks.MANGROVE_LOG || blockstate.getBlock() == Blocks.MANGROVE_WOOD) {
					current_bark = new ItemStack(ConvenientAdditionsModItems.MANGROVE_BARK.get()).copy();
				}
				if (blockstate.getBlock() == Blocks.PALE_OAK_LOG || blockstate.getBlock() == Blocks.PALE_OAK_WOOD) {
					current_bark = new ItemStack(ConvenientAdditionsModItems.PALE_BARK.get()).copy();
				}

				if (!(current_bark.getItem() == ItemStack.EMPTY.getItem())) {
					for (int index0 = 0; index0 < (int) (3 + Math.round(Math.random())); index0++) {
						randomPosOffset = (Math.random() - 0.5) * 2;
						randomPosOffsetZ = (Math.random() - 0.5) * 2;
						if (world instanceof ServerLevel _level) {
							ItemEntity entityToSpawn = new ItemEntity(_level, (x + randomPosOffset), y, (z + randomPosOffsetZ), current_bark);
							entityToSpawn.setPickUpDelay(10);
							_level.addFreshEntity(entityToSpawn);
						}
					}
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}

}
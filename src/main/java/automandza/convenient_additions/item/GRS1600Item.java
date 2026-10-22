package automandza.convenient_additions.item;

//imports
import net.minecraft.world.level.Level;
import net.minecraft.world.item.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;

import automandza.convenient_additions.procedures.GRS1600RightclickedProcedure;
import org.jspecify.annotations.NonNull;


public class GRS1600Item extends Item {
	public GRS1600Item(Item.Properties properties) {
		super(properties.stacksTo(1));
	}

	@Override
	public boolean isPiglinCurrency(ItemStack stack) {
		return true;
	}

	@Override
	public @NonNull ItemUseAnimation getUseAnimation(@NonNull ItemStack itemstack) {
		return ItemUseAnimation.BLOCK;
	}

	@Override
	public ItemStackTemplate getCraftingRemainder(@NonNull ItemInstance ignoreItemInst) {
		return new ItemStackTemplate(this);
	}

	@Override
	public @NonNull InteractionResult use(@NonNull Level world, @NonNull Player entity, @NonNull InteractionHand hand) {
		InteractionResult ar = super.use(world, entity, hand);
		GRS1600RightclickedProcedure.execute(world, entity.getX(), entity.getY(), entity.getZ(), entity);
		return ar;
	}
}
package automandza.convenientadditions.item;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;

import automandza.convenientadditions.procedures.ThrowFlameChargeProcedure;

public class FlameChargeItem extends Item {
	public FlameChargeItem(Item.Properties properties) {
		super(properties.rarity(Rarity.UNCOMMON).fireResistant());
	}

	@Override
	public InteractionResult use(Level world, Player entity, InteractionHand hand) {
		InteractionResult ar = super.use(world, entity, hand);
		ThrowFlameChargeProcedure.execute(entity, entity.getItemInHand(hand));
		return ar;
	}
}
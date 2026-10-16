package automandza.convenientadditions.entity;

import net.minecraft.world.entity.monster.Creeper;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.server.level.ServerLevel;
import org.jetbrains.annotations.NotNull;

public class SnowCreeperEntity extends Creeper {
	public final double LOOTING_BONUS_MULTIPLIER = 0.75d;

	public SnowCreeperEntity(EntityType<SnowCreeperEntity> type, Level world) {
		super(type, world);
		xpReward = 6;
		setNoAi(false);
	}

	protected void dropCustomDeathLoot(@NotNull ServerLevel serverLevel, @NotNull DamageSource source, boolean recentlyHitIn) {
		super.dropCustomDeathLoot(serverLevel, source, recentlyHitIn);
		ItemStack dropLoot = new ItemStack(Items.GUNPOWDER);
		double lootingMult = 1d;
		if (source.getWeaponItem() != null && source.getWeaponItem().getTagEnchantments().toString().contains("minecraft:looting")) {
			String tempCompString = source.getWeaponItem().getTagEnchantments().toString().substring(source.getWeaponItem().getTagEnchantments().toString().indexOf("minecraft:enchantment /"));
			String enchantsString = tempCompString.substring(24,tempCompString.indexOf("}}"));

			String[] itemEnchList = enchantsString.split(",");

			// for each enchantment on inserted item
			for (String currItemEnchFull : itemEnchList) {
				// get ench name
				String foundEnchName = currItemEnchFull.substring(currItemEnchFull.indexOf("Enchantment "), currItemEnchFull.length() - 4).toLowerCase();
				// get ench lvl
				int foundEnchLvl = Integer.parseInt(currItemEnchFull.substring(currItemEnchFull.length() - 1));

				if (foundEnchName.equals("looting")){
					lootingMult = 1d+(foundEnchLvl*LOOTING_BONUS_MULTIPLIER);
				}
			}
		}
		dropLoot.setCount((int) Math.round((Math.random() * 3d * lootingMult)+lootingMult));
		this.spawnAtLocation(serverLevel, dropLoot);
	}

	public static void init() {
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.35);
		builder = builder.add(Attributes.MAX_HEALTH, 9);
		builder = builder.add(Attributes.ARMOR, 0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 0);
		builder = builder.add(Attributes.FOLLOW_RANGE, 20);
		builder = builder.add(Attributes.STEP_HEIGHT, 0.8);
		return builder;
	}
}
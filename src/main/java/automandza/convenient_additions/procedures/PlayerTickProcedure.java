package automandza.convenient_additions.procedures;

//imports
import automandza.convenient_additions.enchantments.EnchantmentStringConverter;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;

import javax.annotation.Nullable;


@EventBusSubscriber
public class PlayerTickProcedure {
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent.Post event) {
		execute(event, event.getEntity());
	}

	public static final double CAPPY_ENCH_FORCE = 0.05d;

	public static void execute(Entity entity) {
		execute(null, entity);
	}

	private static void execute(@Nullable Event event, Entity entity) {
		if (entity == null){
			return;
		}
		if (entity instanceof LivingEntity livinEnt){
			if (entity.getPersistentData().getBooleanOr("poison_immune", false)) {
			 	if (livinEnt.hasEffect(MobEffects.POISON)) {
					livinEnt.removeEffect(MobEffects.POISON);
				}
			}
			if (livinEnt.getSlot(102) != null){
				if (livinEnt.isFallFlying() && livinEnt.getSlot(102).get().getTagEnchantments().toString().contains("Enchantment Cappy")){
					EnchantmentStringConverter enchStringConv = new EnchantmentStringConverter();
					int cappyLvl = enchStringConv.getEnchLevelFromItem(livinEnt.getSlot(102).get().getTagEnchantments().toString(),"convenient_additions:cappy");

					switch (cappyLvl){
						case 1:
							livinEnt.addDeltaMovement(new Vec3(-livinEnt.getLookAngle().x * (CAPPY_ENCH_FORCE * 0.05d), Math.max(0, livinEnt.getLookAngle().y * CAPPY_ENCH_FORCE), -livinEnt.getLookAngle().z * (CAPPY_ENCH_FORCE * 0.05d)));
							break;
						case 2:
							livinEnt.addDeltaMovement(new Vec3(livinEnt.getLookAngle().x * (CAPPY_ENCH_FORCE * 0.1d), Math.max(0, livinEnt.getLookAngle().y * (0.01d+CAPPY_ENCH_FORCE)), livinEnt.getLookAngle().z * (CAPPY_ENCH_FORCE * 0.1d)));
							break;
                        default:
							System.out.println("ERROR! could not find cappy enchantment level cappyLvl="+cappyLvl);
							break;
					}
				}
			} else {
				System.out.println("WARNING! Slot 102 is null");
			}

		}
	}
}
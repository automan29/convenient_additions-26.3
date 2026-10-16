package automandza.convenientadditions.procedures;

//imports
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

	public static void execute(Entity entity) {
		execute(null, entity);
	}

	private static void execute(@Nullable Event event, Entity entity) {
		if (entity == null){
			return;
		}
		if (entity.getPersistentData().getBooleanOr("poison_immune", false)) {
			if (entity instanceof LivingEntity _livEnt1 && _livEnt1.hasEffect(MobEffects.POISON)) {
				if (entity instanceof LivingEntity _entity)
					_entity.removeEffect(MobEffects.POISON);
			}
		}
	}
}
package automandza.convenientadditions.procedures;

//imports
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;

import automandza.convenientadditions.init.ConvenientAdditionsModItems;





public class TombstoneRightClickProcedure {

	public static void execute(Entity entity) {
		if (entity == null) {
			return;
		}
		if (entity instanceof LivingEntity livinEnt){
			if (livinEnt.getMainHandItem().getItem() == ConvenientAdditionsModItems.REGULAR_PEARL.asItem() || livinEnt.getOffhandItem().getItem() == ConvenientAdditionsModItems.REGULAR_PEARL.asItem()){
				try {
					if (entity instanceof Player _player && !_player.level().isClientSide()) {
						_player.displayClientMessage(Component.literal("In loving memory of ..."), false);
						_player.displayClientMessage(Component.literal("A merchant who sought many beautiful pearls."), false);
						_player.displayClientMessage(Component.literal("He had found one pearl of great price, went and sold all that he had, and bought it."), false);
						entity.getPersistentData().putBoolean("poison_immune", true);
					}
				} catch (Exception e){
					System.out.println("ERROR! "+e);
				}
			}
		}
		
	}
	
}
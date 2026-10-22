package automandza.convenient_additions.procedures;

//imports
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;



public class ToggleDrillSFXLogicProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity.getPersistentData().getBooleanOr("canHearDrill", true)) {
			entity.getPersistentData().putBoolean("canHearDrill", false);
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.sendSystemMessage(Component.literal("Drill sounds are now off"));
		} else {
			entity.getPersistentData().putBoolean("canHearDrill", true);
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.sendSystemMessage(Component.literal("Drill sounds are now on"));
		}
	}
}
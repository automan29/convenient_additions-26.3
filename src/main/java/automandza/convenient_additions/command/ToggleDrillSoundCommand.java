package automandza.convenient_additions.command;

import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.common.util.FakePlayerFactory;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.Direction;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.Commands;

import automandza.convenient_additions.procedures.ToggleDrillSFXLogicProcedure;

@EventBusSubscriber
public class ToggleDrillSoundCommand {
	@SubscribeEvent
	public static void registerCommand(RegisterCommandsEvent event) {
		event.getDispatcher().register(Commands.literal("toggledrillsound")
				.then(Commands.argument("name", EntityArgument.player()).executes(arguments -> {
					Level world = arguments.getSource().getUnsidedLevel();
					Entity entity = arguments.getSource().getEntity();
					if (entity == null && world instanceof ServerLevel _servLevel) {
						entity = FakePlayerFactory.getMinecraft(_servLevel);
					}

					ToggleDrillSFXLogicProcedure.execute(entity);
					return 0;
				})));
	}

}
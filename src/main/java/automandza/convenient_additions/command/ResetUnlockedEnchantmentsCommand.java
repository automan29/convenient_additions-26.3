package automandza.convenientadditions.command;

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

import automandza.convenientadditions.procedures.ResetUnlockedEnchtsProcedure;

@EventBusSubscriber
public class ResetUnlockedEnchantmentsCommand {
	@SubscribeEvent
	public static void registerCommand(RegisterCommandsEvent event) {
		event.getDispatcher().register(Commands.literal("resetunlockedenchantments").requires(s -> s.hasPermission(4)).then(Commands.argument("name", EntityArgument.player()).executes(arguments -> {
			Level world = arguments.getSource().getUnsidedLevel();
			Entity entity = arguments.getSource().getEntity();
			if (entity == null && world instanceof ServerLevel _servLevel) {
				entity = FakePlayerFactory.getMinecraft(_servLevel);
			}

			ResetUnlockedEnchtsProcedure.execute(entity);
			return 0;
		})));
	}

}
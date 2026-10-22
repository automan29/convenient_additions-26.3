package automandza.convenient_additions.procedures;

//imports
import net.minecraft.server.permissions.LevelBasedPermissionSet;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.CommandSource;




// at least this one still works first time. Wait... no it doesn't
public class AwManProcedureProcedure {
	public static void execute(LevelAccessor world) {
		if (world instanceof ServerLevel _level)
			_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(
						CommandSource.NULL,
						new Vec3(0, 0, 0),
						Vec2.ZERO, _level,
						LevelBasedPermissionSet.NO_PERMISSIONS,
						Component.literal(""),
						_level.getServer()
					).withSuppressedOutput(),
					"say @e[type=creeper]");
	}
}
package automandza.convenient_additions.procedures;

//imports
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;


public class AdvancementChecker {
    public static boolean playerHasAdvancementFromMCServer(ServerPlayer svrPlr, @NotNull MinecraftServer mcServer, String advancementID){
        PlayerAdvancements playerAchievements = mcServer.getPlayerList().getPlayerAdvancements(svrPlr);
        AdvancementHolder foundAdvancement = mcServer.getAdvancements().get(Identifier.parse(advancementID));

        if (foundAdvancement != null) {
            return playerAchievements.getOrStartProgress(foundAdvancement).isDone();
        } else {
            System.out.println("Warning! Advancement not found, advancementID="+advancementID);
        }

        return false;
    }
}

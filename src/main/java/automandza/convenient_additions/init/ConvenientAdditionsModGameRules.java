package automandza.convenient_additions.init;

//imports
import net.neoforged.neoforge.registries.RegisterEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.gamerules.*;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.resources.Identifier;
import net.minecraft.core.registries.Registries;
import com.mojang.serialization.Codec;
import com.mojang.brigadier.arguments.IntegerArgumentType;



@EventBusSubscriber
public class ConvenientAdditionsModGameRules {
	public static GameRule<Integer> ENCHANTING_SCIENCE_RESEARCH_DIFFICULTY;

	@SubscribeEvent
	public static void registerGameRules(RegisterEvent event) {
		event.register(Registries.GAME_RULE, helper -> {
			ENCHANTING_SCIENCE_RESEARCH_DIFFICULTY = new GameRule<>(GameRuleCategory.MISC, GameRuleType.INT, IntegerArgumentType.integer(), GameRuleTypeVisitor::visitInteger, Codec.INT, i -> i, 2, FeatureFlagSet.of());
			helper.register(Identifier.fromNamespaceAndPath("convenient_additions", "enchantingScienceResearchDifficulty"), ENCHANTING_SCIENCE_RESEARCH_DIFFICULTY);
		});
	}
}
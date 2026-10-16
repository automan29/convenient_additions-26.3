/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package automandza.convenientadditions.init;

import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.GameRules;

@EventBusSubscriber
public class ConvenientAdditionsModGameRules {
	public static GameRules.Key<GameRules.IntegerValue> ENCHANTING_SCIENCE_RESEARCH_DIFFICULTY;

	@SubscribeEvent
	public static void registerGameRules(FMLCommonSetupEvent event) {
		ENCHANTING_SCIENCE_RESEARCH_DIFFICULTY = GameRules.register("enchantingScienceResearchDifficulty", GameRules.Category.MISC, GameRules.IntegerValue.create(2));
	}
}
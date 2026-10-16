/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package automandza.convenientadditions.init;

import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import automandza.convenientadditions.client.gui.*;

@EventBusSubscriber(Dist.CLIENT)
public class ConvenientAdditionsModScreens {
	@SubscribeEvent
	public static void clientLoad(RegisterMenuScreensEvent event) {
		event.register(ConvenientAdditionsModMenus.SAWMILL_GUI.get(), SawmillGUIScreen::new);
		event.register(ConvenientAdditionsModMenus.DECORATION_STATION_GUI.get(), DecorationStationGUIScreen::new);
		event.register(ConvenientAdditionsModMenus.KILN_GUI.get(), KilnGUIScreen::new);
		event.register(ConvenientAdditionsModMenus.ENCH_RESEARCH_GUI.get(), EnchResearchGUIScreen::new);
		event.register(ConvenientAdditionsModMenus.ENCH_W_MATS_GUI.get(), EnchWMatsGUIScreen::new);
		event.register(ConvenientAdditionsModMenus.UNLOCKED_ENCH_TIPS_GUI.get(), UnlockedEnchTipsGUIScreen::new);
	}

	public interface ScreenAccessor {
		void updateMenuState(int elementType, String name, Object elementState);
	}
}
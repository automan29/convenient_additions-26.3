/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package automandza.convenientadditions.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.core.registries.Registries;
import net.minecraft.client.Minecraft;

import java.util.Map;

import automandza.convenientadditions.world.inventory.*;
import automandza.convenientadditions.network.MenuStateUpdateMessage;
import automandza.convenientadditions.ConvenientAdditionsMod;

public class ConvenientAdditionsModMenus {
	public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(Registries.MENU, ConvenientAdditionsMod.MODID);
	public static final DeferredHolder<MenuType<?>, MenuType<SawmillGUIMenu>> SAWMILL_GUI = REGISTRY.register("sawmill_gui", () -> IMenuTypeExtension.create(SawmillGUIMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<DecorationStationGUIMenu>> DECORATION_STATION_GUI = REGISTRY.register("decoration_station_gui", () -> IMenuTypeExtension.create(DecorationStationGUIMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<KilnGUIMenu>> KILN_GUI = REGISTRY.register("kiln_gui", () -> IMenuTypeExtension.create(KilnGUIMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<EnchResearchGUIMenu>> ENCH_RESEARCH_GUI = REGISTRY.register("ench_research_gui", () -> IMenuTypeExtension.create(EnchResearchGUIMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<EnchWMatsGUIMenu>> ENCH_W_MATS_GUI = REGISTRY.register("ench_w_mats_gui", () -> IMenuTypeExtension.create(EnchWMatsGUIMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<UnlockedEnchTipsGUIMenu>> UNLOCKED_ENCH_TIPS_GUI = REGISTRY.register("unlocked_ench_tips_gui", () -> IMenuTypeExtension.create(UnlockedEnchTipsGUIMenu::new));

	public interface MenuAccessor {
		Map<String, Object> getMenuState();

		Map<Integer, Slot> getSlots();

		default void sendMenuStateUpdate(Player player, int elementType, String name, Object elementState, boolean needClientUpdate) {
			getMenuState().put(elementType + ":" + name, elementState);
			if (player instanceof ServerPlayer serverPlayer) {
				PacketDistributor.sendToPlayer(serverPlayer, new MenuStateUpdateMessage(elementType, name, elementState));
			} else if (player.level().isClientSide) {
				if (Minecraft.getInstance().screen instanceof ConvenientAdditionsModScreens.ScreenAccessor accessor && needClientUpdate)
					accessor.updateMenuState(elementType, name, elementState);
				ClientPacketDistributor.sendToServer(new MenuStateUpdateMessage(elementType, name, elementState));
			}
		}

		default <T> T getMenuState(int elementType, String name, T defaultValue) {
			try {
				return (T) getMenuState().getOrDefault(elementType + ":" + name, defaultValue);
			} catch (ClassCastException e) {
				return defaultValue;
			}
		}
	}
}
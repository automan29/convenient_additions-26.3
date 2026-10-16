package automandza.convenientadditions.network;

import automandza.convenientadditions.world.inventory.EnchResearchGUIMenu;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.chat.Component;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.core.SectionPos;

import automandza.convenientadditions.procedures.EnchResearchTickProcedure;
import automandza.convenientadditions.ConvenientAdditionsMod;
import automandza.convenientadditions.world.inventory.UnlockedEnchTipsGUIMenu;
import net.minecraft.core.BlockPos;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.MenuProvider;
import net.minecraft.server.level.ServerPlayer;

@EventBusSubscriber
public record EnchResearchGUIButtonMessage(int buttonID, int x, int y, int z) implements CustomPacketPayload {
	public static final Type<EnchResearchGUIButtonMessage> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(ConvenientAdditionsMod.MODID, "ench_research_gui_buttons"));
	public static final StreamCodec<RegistryFriendlyByteBuf, EnchResearchGUIButtonMessage> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, EnchResearchGUIButtonMessage message) -> {
		buffer.writeInt(message.buttonID);
		buffer.writeInt(message.x);
		buffer.writeInt(message.y);
		buffer.writeInt(message.z);
	}, (RegistryFriendlyByteBuf buffer) -> new EnchResearchGUIButtonMessage(buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt()));

	@Override
	public Type<EnchResearchGUIButtonMessage> type() {
		return TYPE;
	}

	public static void handleData(final EnchResearchGUIButtonMessage message, final IPayloadContext context) {
		if (context.flow() == PacketFlow.SERVERBOUND) {
			context.enqueueWork(() -> handleButtonAction(context.player(), message.buttonID, message.x, message.y, message.z)).exceptionally(e -> {
				context.connection().disconnect(Component.literal(e.getMessage()));
				return null;
			});
		}
	}

	public static void handleButtonAction(Player entity, int buttonID, int x, int y, int z) {
		Level world = entity.level();
		// security measure to prevent arbitrary chunk generation
		if (!world.getChunkSource().hasChunk(SectionPos.blockToSectionCoord(x), SectionPos.blockToSectionCoord(z)))
	return;
		if (buttonID == 0) {
			EnchResearchTickProcedure.pressTestButton(world, x, y, z, entity);
		}
		
		// menu within a menu (scary)
		if (buttonID == 1){
			BlockPos pos = BlockPos.containing(x, y, z);
			if (entity instanceof ServerPlayer srvPlayer) {
				MenuProvider menuProvider = new MenuProvider() {					
					@Override
					public Component getDisplayName() {
						return Component.literal("Scientific Research Table");
					}

					@Override
					public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
						UnlockedEnchTipsGUIMenu menu = new UnlockedEnchTipsGUIMenu(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(pos));
						menu.listOfUnlockedEnc = entity.getPersistentData().getStringOr("unlockedEnchantList", "hard luck"); // sets the static var within the new menu
						return menu;
					}
				};
				srvPlayer.openMenu(menuProvider, pos);
			}
		}

		// go back to this menu (more scary)
		if (buttonID == 2){
			BlockPos pos = BlockPos.containing(x, y, z);
			if (entity instanceof ServerPlayer srvPlayer) {
				MenuProvider menuProvider = new MenuProvider() {
					@Override
					public Component getDisplayName() {
						return Component.literal("Scientific Research Table");
					}

					@Override
					public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
						EnchResearchGUIMenu menu = new EnchResearchGUIMenu(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(pos));
						return menu;
					}
				};
				srvPlayer.openMenu(menuProvider, pos);
			}
		}
		
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		ConvenientAdditionsMod.addNetworkMessage(EnchResearchGUIButtonMessage.TYPE, EnchResearchGUIButtonMessage.STREAM_CODEC, EnchResearchGUIButtonMessage::handleData);
	}
}
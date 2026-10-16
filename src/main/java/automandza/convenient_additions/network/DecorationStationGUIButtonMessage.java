package automandza.convenientadditions.network;

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

import automandza.convenientadditions.procedures.DoneLightsProcedureProcedure;
import automandza.convenientadditions.procedures.AddLightsProcedureProcedure;
import automandza.convenientadditions.ConvenientAdditionsMod;

@EventBusSubscriber
public record DecorationStationGUIButtonMessage(int buttonID, int x, int y, int z) implements CustomPacketPayload {
	public static final Type<DecorationStationGUIButtonMessage> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(ConvenientAdditionsMod.MODID, "decoration_station_gui_buttons"));
	public static final StreamCodec<RegistryFriendlyByteBuf, DecorationStationGUIButtonMessage> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, DecorationStationGUIButtonMessage message) -> {
		buffer.writeInt(message.buttonID);
		buffer.writeInt(message.x);
		buffer.writeInt(message.y);
		buffer.writeInt(message.z);
	}, (RegistryFriendlyByteBuf buffer) -> new DecorationStationGUIButtonMessage(buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt()));

	@Override
	public Type<DecorationStationGUIButtonMessage> type() {
		return TYPE;
	}

	public static void handleData(final DecorationStationGUIButtonMessage message, final IPayloadContext context) {
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

			AddLightsProcedureProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 1) {

			DoneLightsProcedureProcedure.execute(entity);
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		ConvenientAdditionsMod.addNetworkMessage(DecorationStationGUIButtonMessage.TYPE, DecorationStationGUIButtonMessage.STREAM_CODEC, DecorationStationGUIButtonMessage::handleData);
	}
}
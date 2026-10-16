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

import automandza.convenientadditions.procedures.SawmillTakeOutputProcedure;
import automandza.convenientadditions.procedures.SawmillShiftClickOutputProcedure;
import automandza.convenientadditions.ConvenientAdditionsMod;

@EventBusSubscriber
public record SawmillGUISlotMessage(int slotID, int x, int y, int z, int changeType, int meta) implements CustomPacketPayload {
	public static final Type<SawmillGUISlotMessage> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(ConvenientAdditionsMod.MODID, "sawmill_gui_slots"));
	public static final StreamCodec<RegistryFriendlyByteBuf, SawmillGUISlotMessage> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, SawmillGUISlotMessage message) -> {
		buffer.writeInt(message.slotID);
		buffer.writeInt(message.x);
		buffer.writeInt(message.y);
		buffer.writeInt(message.z);
		buffer.writeInt(message.changeType);
		buffer.writeInt(message.meta);
	}, (RegistryFriendlyByteBuf buffer) -> new SawmillGUISlotMessage(buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt()));

	@Override
	public Type<SawmillGUISlotMessage> type() {
		return TYPE;
	}

	public static void handleData(final SawmillGUISlotMessage message, final IPayloadContext context) {
		if (context.flow() == PacketFlow.SERVERBOUND) {
			context.enqueueWork(() -> handleSlotAction(context.player(), message.slotID, message.changeType, message.meta, message.x, message.y, message.z)).exceptionally(e -> {
				context.connection().disconnect(Component.literal(e.getMessage()));
				return null;
			});
		}
	}

	public static void handleSlotAction(Player entity, int slot, int changeType, int meta, int x, int y, int z) {
		Level world = entity.level();
		// security measure to prevent arbitrary chunk generation
		if (!world.getChunkSource().hasChunk(SectionPos.blockToSectionCoord(x), SectionPos.blockToSectionCoord(z)))
			return;
		if (slot == 1 && changeType == 1) {
			int amount = meta;

			SawmillTakeOutputProcedure.execute(world, x, y, z, entity, amount);
		}
		if (slot == 1 && changeType == 2) {
			int amount = meta;

			SawmillShiftClickOutputProcedure.execute(world, x, y, z, entity, amount);
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		ConvenientAdditionsMod.addNetworkMessage(SawmillGUISlotMessage.TYPE, SawmillGUISlotMessage.STREAM_CODEC, SawmillGUISlotMessage::handleData);
	}
}
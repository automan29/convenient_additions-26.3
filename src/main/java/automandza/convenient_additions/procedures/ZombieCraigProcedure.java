package automandza.convenientadditions.procedures;

//imports
import automandza.convenientadditions.init.ConvenientAdditionsModEntities;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.entity.Entity;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;

import javax.annotation.Nullable;




@EventBusSubscriber
public class ZombieCraigProcedure {
	@SubscribeEvent
	public static void onEntitySpawned(EntityJoinLevelEvent event) {
		execute(event, event.getEntity(), event.getLevel());
	}

	public static void execute(Entity entity) {
		execute(null, entity, null);
	}

	private static void execute(@Nullable Event event, Entity entity, @Nullable Level world) {
		if (entity == null) {
			return;
		}
		if (entity instanceof LivingEntity livinEnt) {
			if (Math.random() * 100d > 99.5d && entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:zombies")))) {
				ItemStack acaciaStack = new ItemStack(Items.ACACIA_SAPLING).copy();
				acaciaStack.setCount(2);
				entity.setSprinting(true);
				entity.setCustomName(Component.literal("Craig"));
				livinEnt.setItemInHand(InteractionHand.MAIN_HAND, acaciaStack);
			}
			assert world != null;
			if (entity.getType().toString().equals("entity.minecraft.creeper") && SnowCreeperBiomeCheck(world.getBiome(livinEnt.blockPosition()))){
				if (world instanceof ServerLevel svrLevel) {
					Entity entityToSpawn = ConvenientAdditionsModEntities.SNOW_CREEPER.get().spawn(svrLevel, livinEnt.blockPosition(), EntitySpawnReason.NATURAL);
					if (entityToSpawn != null && !world.isClientSide()){
						// works better than any other method for despawning lol
						entity.setPos(0,-999,0);
					}
				}
			}
		}
	}


	private static boolean SnowCreeperBiomeCheck(Holder<Biome> biome){
		if (biome == null){
			return false;
		}
		return (
				biome.is(ResourceLocation.parse("snowy_plains")) ||
				biome.is(ResourceLocation.parse("frozen_ocean")) ||
				biome.is(ResourceLocation.parse("ice_spikes")) ||
				biome.is(ResourceLocation.parse("snowy_taiga")) ||
				biome.is(ResourceLocation.parse("snowy_slopes")) ||
				biome.is(ResourceLocation.parse("snowy_beach")) ||
				biome.is(ResourceLocation.parse("grove")) ||
				biome.is(ResourceLocation.parse("jagged_peaks")) ||
				biome.is(ResourceLocation.parse("frozen_peaks")) ||
				biome.is(ResourceLocation.parse("frozen_river"))
		);
	}
}
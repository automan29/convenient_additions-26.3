/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package automandza.convenient_additions.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.resources.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;

import automandza.convenient_additions.entity.SnowCreeperEntity;
import automandza.convenient_additions.ConvenientAdditionsMod;

import static net.minecraft.world.entity.EntityType.*;

@EventBusSubscriber
public class ConvenientAdditionsModEntities {
	public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(Registries.ENTITY_TYPE, ConvenientAdditionsMod.MODID);
	public static final DeferredHolder<EntityType<?>, EntityType<SnowCreeperEntity>> SNOW_CREEPER = register(
			Builder.of(SnowCreeperEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
			.setTrackingRange(128).setUpdateInterval(3).sized(0.6f, 1.7f));

	private static <T extends Entity> DeferredHolder<EntityType<?>, EntityType<T>> register(EntityType.Builder<T> entityTypeBuilder) {
		return REGISTRY.register("snow_creeper", () -> entityTypeBuilder.build(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(ConvenientAdditionsMod.MODID, "snow_creeper"))));
	}

	@SubscribeEvent
	public static void init(RegisterSpawnPlacementsEvent ignoredA) {
		SnowCreeperEntity.init();
	}

	@SubscribeEvent
	public static void registerAttributes(EntityAttributeCreationEvent event) {
		event.put(SNOW_CREEPER.get(), SnowCreeperEntity.createAttributes().build());
	}
}
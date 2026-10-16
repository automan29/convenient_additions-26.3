/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package automandza.convenientadditions.init;

import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import automandza.convenientadditions.client.particle.PenguinHornParticleParticle;

@EventBusSubscriber(Dist.CLIENT)
public class ConvenientAdditionsModParticles {
	@SubscribeEvent
	public static void registerParticles(RegisterParticleProvidersEvent event) {
		event.registerSpriteSet(ConvenientAdditionsModParticleTypes.PENGUIN_HORN_PARTICLE.get(), PenguinHornParticleParticle::provider);
	}
}
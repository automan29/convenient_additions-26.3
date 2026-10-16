/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package automandza.convenientadditions.init;

import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import automandza.convenientadditions.client.renderer.SnowCreeperRenderer;

@EventBusSubscriber(Dist.CLIENT)
public class ConvenientAdditionsModEntityRenderers {
	@SubscribeEvent
	public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(ConvenientAdditionsModEntities.SNOW_CREEPER.get(), SnowCreeperRenderer::new);
	}
}
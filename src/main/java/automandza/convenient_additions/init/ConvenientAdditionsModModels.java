/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package automandza.convenient_additions.init;

import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import automandza.convenient_additions.client.model.ModelitemEnhancementMachine;

@EventBusSubscriber(Dist.CLIENT)
public class ConvenientAdditionsModModels {
	@SubscribeEvent
	public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(ModelitemEnhancementMachine.LAYER_LOCATION, ModelitemEnhancementMachine::createBodyLayer);
	}
}
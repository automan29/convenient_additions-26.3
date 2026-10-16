package automandza.convenientadditions.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.state.CreeperRenderState;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.CreeperModel;

import com.mojang.blaze3d.vertex.PoseStack;

import automandza.convenientadditions.entity.SnowCreeperEntity;

public class SnowCreeperRenderer extends MobRenderer<SnowCreeperEntity, CreeperRenderState, CreeperModel> {
	private SnowCreeperEntity entity = null;
	private final ResourceLocation entityTexture = ResourceLocation.parse("convenient_additions:textures/entities/snowcreeper.png");

	public SnowCreeperRenderer(EntityRendererProvider.Context context) {
		super(context, new CreeperModel(context.bakeLayer(ModelLayers.CREEPER)), 0.5f);
	}

	@Override
	public CreeperRenderState createRenderState() {
		return new CreeperRenderState();
	}

	@Override
	public void extractRenderState(SnowCreeperEntity entity, CreeperRenderState state, float partialTicks) {
		super.extractRenderState(entity, state, partialTicks);
		this.entity = entity;
	}

	@Override
	public ResourceLocation getTextureLocation(CreeperRenderState state) {
		return entityTexture;
	}

	@Override
	protected void scale(CreeperRenderState state, PoseStack poseStack) {
		poseStack.scale(0.9f, 0.9f, 0.9f);
	}
}
package automandza.convenient_additions.client.renderer;

import net.minecraft.resources.Identifier;
import net.minecraft.client.renderer.entity.state.CreeperRenderState;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.monster.creeper.CreeperModel;

import com.mojang.blaze3d.vertex.PoseStack;

import automandza.convenient_additions.entity.SnowCreeperEntity;
import net.minecraft.util.Mth;

public class SnowCreeperRenderer extends MobRenderer<SnowCreeperEntity, CreeperRenderState, CreeperModel> {
	private SnowCreeperEntity entity = null;
	private final Identifier entityTexture = Identifier.parse("convenient_additions:textures/entities/snowcreeper.png");

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
		state.swelling = entity.getSwelling(partialTicks);
		this.entity = entity;
	}

	@Override
	public Identifier getTextureLocation(CreeperRenderState state) {
		return entityTexture;
	}

	@Override
	public float getWhiteOverlayProgress(CreeperRenderState state) {
		float step = state.swelling;
		return (int)(step * 10.0F) % 2 == 0 ? 0.0F : Mth.clamp(step, 0.5F, 1.0F);
	}

	@Override
	public void scale(CreeperRenderState state, PoseStack poseStack) {
		float swellVal = state.swelling;
		float wobble = 1.0f + Mth.sin(swellVal * 100.0f) * swellVal * 0.01f;
		swellVal = Mth.clamp(swellVal, 0.0f, 1.0f);
		swellVal *= swellVal;
		swellVal *= swellVal;
		float horizScale = (0.9f + swellVal * 0.5f) * wobble;
		float vertScale = (0.9f + swellVal * 0.2f) / wobble;
		poseStack.scale(horizScale, vertScale, horizScale);
	}
}
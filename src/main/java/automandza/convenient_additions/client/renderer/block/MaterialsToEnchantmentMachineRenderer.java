package automandza.convenient_additions.client.renderer.block;

//imports
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.resources.Identifier;
import net.minecraft.core.Direction;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;

import java.util.Map;

import com.mojang.math.Axis;
import com.mojang.blaze3d.vertex.PoseStack;

import automandza.convenient_additions.init.ConvenientAdditionsModBlockEntities;
import automandza.convenient_additions.client.model.animations.itemEnhancementMachineAnimation;
import automandza.convenient_additions.client.model.ModelitemEnhancementMachine;
import automandza.convenient_additions.block.entity.MaterialsToEnchantmentMachineBlockEntity;
import automandza.convenient_additions.block.MaterialsToEnchantmentMachineBlock;





@EventBusSubscriber(Dist.CLIENT)
public class MaterialsToEnchantmentMachineRenderer implements BlockEntityRenderer<MaterialsToEnchantmentMachineBlockEntity, MaterialsToEnchantmentMachineRenderer.MachineRenderState> {
	private final CustomHierarchicalModel model;
	private final Identifier texture;

    MaterialsToEnchantmentMachineRenderer(BlockEntityRendererProvider.Context context) {
		this.model = new CustomHierarchicalModel(context.bakeLayer(ModelitemEnhancementMachine.LAYER_LOCATION));
		this.texture = Identifier.parse("convenient_additions:textures/block/enhancementmachnineblockimg.png");
	}

	@Override
	public MachineRenderState createRenderState() {
		return new MachineRenderState();
	}

	private void updateRenderState(MaterialsToEnchantmentMachineBlockEntity blockEntity, MachineRenderState renderState, float partialTick) {
		int tickCount = (int) blockEntity.getLevel().getGameTime();
		renderState.modelState.ageInTicks = tickCount + partialTick;
		blockEntity.animationState0.animateWhen(true, tickCount);
	}

	@Override
	public void extractRenderState(MaterialsToEnchantmentMachineBlockEntity blockEntity, MachineRenderState renderState, float partialTick, Vec3 cameraPos, ModelFeatureRenderer.CrumblingOverlay breakProgress) {
		BlockEntityRenderer.super.extractRenderState(blockEntity, renderState, partialTick, cameraPos, breakProgress);
		updateRenderState(blockEntity, renderState, partialTick);
		BlockState state = blockEntity.getBlockState();
		renderState.facing = state.getValue(MaterialsToEnchantmentMachineBlock.FACING);
	}

	@Override
	public void submit(MachineRenderState renderState, PoseStack poseStack, SubmitNodeCollector submitNodeColl, CameraRenderState camera) {
		poseStack.pushPose();
		poseStack.scale(-1, -1, 1);
		poseStack.translate(-0.5, -0.5, 0.5);
		Direction facing = renderState.facing;
		switch (facing) {
			case NORTH -> {	}
			case EAST -> poseStack.rotate(Axis.YP.rotationDegrees(90));
			case WEST -> poseStack.rotate(Axis.YP.rotationDegrees(-90));
			case SOUTH -> poseStack.rotate(Axis.YP.rotationDegrees(180));
		}
		poseStack.translate(0, -1, 0);
		submitNodeColl.submitModel(model, renderState.modelState, poseStack, RenderTypes.entityCutout(texture), renderState.lightCoords, OverlayTexture.NO_OVERLAY, 0);
		if (renderState.breakProgress != null) {
			submitNodeColl.order(1).submitCrumblingOverlay(model, renderState.modelState, poseStack, RenderTypes.entityCutout(texture), renderState.lightCoords, OverlayTexture.NO_OVERLAY, -1, renderState.breakProgress);
		}
		poseStack.popPose();
	}

	@SubscribeEvent
	public static void registerBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerBlockEntityRenderer(ConvenientAdditionsModBlockEntities.MATERIALS_TO_ENCHANTMENT_MACHINE.get(), MaterialsToEnchantmentMachineRenderer::new);
	}

	private static final class CustomHierarchicalModel extends ModelitemEnhancementMachine {
		private final KeyframeAnimation keyframeAnimation0;

		public CustomHierarchicalModel(ModelPart root) {
			super(root);
			this.keyframeAnimation0 = safeBake(itemEnhancementMachineAnimation.working);
		}

		private KeyframeAnimation safeBake(AnimationDefinition source) {
			try {
				return source.bake(root);
			} catch (IllegalArgumentException e) {
				return new AnimationDefinition(0, false, Map.of()).bake(root);
			}
		}

		@Override
		public void setupAnim(LivingEntityRenderState state) {
			this.root().getAllParts().forEach(ModelPart::resetPose);
			if (state instanceof MachineModelState machineState) {
				this.keyframeAnimation0.apply(machineState.animationState0, state.ageInTicks, 0.4f);
			}
			super.setupAnim(state);
		}
	}

	public static final class MachineRenderState extends BlockEntityRenderState {
		Direction facing = Direction.NORTH;
		final MachineModelState modelState = new MachineModelState();
	}

	private static final class MachineModelState extends LivingEntityRenderState {
		final AnimationState animationState0 = new AnimationState();
	}
}
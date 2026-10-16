package automandza.convenientadditions.client.renderer.block;

import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.animation.AnimationDefinition;

import java.util.Map;

import com.mojang.math.Axis;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;

import automandza.convenientadditions.init.ConvenientAdditionsModBlockEntities;
import automandza.convenientadditions.client.model.animations.itemEnhancementMachineAnimation;
import automandza.convenientadditions.client.model.ModelitemEnhancementMachine;
import automandza.convenientadditions.block.entity.MaterialsToEnchantmentMachineBlockEntity;
import automandza.convenientadditions.block.MaterialsToEnchantmentMachineBlock;

@EventBusSubscriber(Dist.CLIENT)
public class MaterialsToEnchantmentMachineRenderer implements BlockEntityRenderer<MaterialsToEnchantmentMachineBlockEntity> {
	private final CustomHierarchicalModel model;
	private final ResourceLocation texture;
	private final LivingEntityRenderState renderState;

	MaterialsToEnchantmentMachineRenderer(BlockEntityRendererProvider.Context context) {
		this.model = new CustomHierarchicalModel(context.bakeLayer(ModelitemEnhancementMachine.LAYER_LOCATION));
		this.texture = ResourceLocation.parse("convenient_additions:textures/block/enhancementmachnineblockimg.png");
		this.renderState = new LivingEntityRenderState();
	}

	private void updateRenderState(MaterialsToEnchantmentMachineBlockEntity blockEntity, float partialTick) {
		int tickCount = (int) blockEntity.getLevel().getGameTime();
		renderState.ageInTicks = tickCount + partialTick;
		blockEntity.animationState0.animateWhen(true, tickCount);
	}

	@Override
	public void render(MaterialsToEnchantmentMachineBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource renderer, int light, int overlayLight, Vec3 cameraPos) {
		updateRenderState(blockEntity, partialTick);
		poseStack.pushPose();
		poseStack.scale(-1, -1, 1);
		poseStack.translate(-0.5, -0.5, 0.5);
		BlockState state = blockEntity.getBlockState();
		Direction facing = state.getValue(MaterialsToEnchantmentMachineBlock.FACING);
		switch (facing) {
			case NORTH -> {
			}
			case EAST -> poseStack.mulPose(Axis.YP.rotationDegrees(90));
			case WEST -> poseStack.mulPose(Axis.YP.rotationDegrees(-90));
			case SOUTH -> poseStack.mulPose(Axis.YP.rotationDegrees(180));
		}
		poseStack.translate(0, -1, 0);
		VertexConsumer builder = renderer.getBuffer(RenderType.entityCutout(texture));
		model.setupBlockEntityAnim(blockEntity, renderState);
		model.renderToBuffer(poseStack, builder, light, overlayLight);
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

		public void setupBlockEntityAnim(MaterialsToEnchantmentMachineBlockEntity blockEntity, LivingEntityRenderState state) {
			this.root().getAllParts().forEach(ModelPart::resetPose);
			this.keyframeAnimation0.apply(blockEntity.animationState0, state.ageInTicks, 0.4f);
			super.setupAnim(state);
		}
	}
}
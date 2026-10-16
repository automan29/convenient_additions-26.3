package automandza.convenientadditions.client.model;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.EntityModel;

// Made with Blockbench 5.1.6
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports
public class ModelitemEnhancementMachine extends EntityModel<LivingEntityRenderState> {
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("convenient_additions", "modelitem_enhancement_machine"), "main");
	public final ModelPart sideFrame;
	public final ModelPart base;
	public final ModelPart stand;
	public final ModelPart arm1;
	public final ModelPart pouringthing;
	public final ModelPart arm2;
	public final ModelPart arm3;

	public ModelitemEnhancementMachine(ModelPart root) {
		super(root);
		this.sideFrame = root.getChild("side frame");
		this.base = root.getChild("base");
		this.stand = root.getChild("stand");
		this.arm1 = root.getChild("arm1");
		this.pouringthing = root.getChild("pouringthing");
		this.arm2 = root.getChild("arm2");
		this.arm3 = root.getChild("arm3");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition sideFrame = partdefinition
				.addOrReplaceChild(
						"side frame", CubeListBuilder.create().texOffs(16, 59).addBox(-7.0F, -1.0F, -4.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(28, 58).addBox(-7.0F, 2.0F, -3.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
								.texOffs(0, 18).addBox(-10.0F, -1.0F, -4.0F, 3.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)).texOffs(38, 35).addBox(-7.0F, -1.0F, -1.0F, 8.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)),
						PartPose.offset(2.0F, 9.0F, -4.0F));
		PartDefinition base = partdefinition.addOrReplaceChild("base",
				CubeListBuilder.create().texOffs(0, 0).addBox(-12.0F, -2.0F, -1.0F, 13.0F, 2.0F, 16.0F, new CubeDeformation(0.0F)).texOffs(38, 18).addBox(-12.0F, -7.0F, 1.0F, 5.0F, 5.0F, 12.0F, new CubeDeformation(0.0F)).texOffs(58, 46)
						.addBox(-8.0F, -8.0F, 3.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)).texOffs(58, 64).addBox(-8.0F, -9.0F, 5.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offset(7.0F, 24.0F, -7.0F));
		PartDefinition stand = partdefinition.addOrReplaceChild("stand",
				CubeListBuilder.create().texOffs(0, 50).addBox(1.0F, -5.0F, -4.0F, 6.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(58, 55).addBox(1.0F, -8.0F, -4.0F, 1.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(38, 46)
						.addBox(5.0F, -16.0F, -4.0F, 2.0F, 11.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(0, 59).addBox(1.0F, -4.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(58, 0)
						.addBox(0.0F, -13.0F, -6.0F, 5.0F, 11.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 24.0F, 0.0F));
		PartDefinition arm1 = partdefinition.addOrReplaceChild("arm1", CubeListBuilder.create(), PartPose.offset(-5.0F, 14.0F, 1.0F));
		PartDefinition cube_r1 = arm1.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(28, 56).addBox(-3.0F, -1.0F, -1.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(2.0F, -1.0F, 1.0F, 0.0F, 0.0F, -0.3491F));
		PartDefinition pouringthing = partdefinition.addOrReplaceChild("pouringthing",
				CubeListBuilder.create().texOffs(28, 50).addBox(-2.0F, -1.0F, -2.0F, 1.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(28, 61).addBox(-1.0F, -1.0F, -2.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(8, 65)
						.addBox(-1.0F, -1.0F, 1.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 65).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(28, 64)
						.addBox(-3.0F, -2.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offset(-2.0F, 16.0F, -3.0F));
		PartDefinition arm2 = partdefinition.addOrReplaceChild("arm2", CubeListBuilder.create(), PartPose.offset(-2.0F, 14.0F, 1.0F));
		PartDefinition cube_r2 = arm2.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(58, 13).addBox(-4.0F, -1.0F, -1.01F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.3491F));
		PartDefinition arm3 = partdefinition.addOrReplaceChild("arm3", CubeListBuilder.create(), PartPose.offset(3.0F, 14.0F, 1.0F));
		PartDefinition cube_r3 = arm3.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(58, 15).addBox(-1.5F, -0.5F, -1.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.8727F));
		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	public void setupAnim(LivingEntityRenderState state) {
		float limbSwing = state.walkAnimationPos;
		float limbSwingAmount = state.walkAnimationSpeed;
		float ageInTicks = state.ageInTicks;
		float netHeadYaw = state.yRot;
		float headPitch = state.xRot;

	}

}
package automandza.convenientadditions.client.model.animations;

import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.AnimationChannel;

// Save this class in your mod and generate all required imports
/**
 * Made with Blockbench 5.1.6 Exported for Minecraft version 1.19 or later with
 * Mojang mappings
 * 
 * @author Author
 */
public class itemEnhancementMachineAnimation {
	public static final AnimationDefinition working = AnimationDefinition.Builder.withLength(2.4167F).looping()
			.addAnimation("arm1",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 5.0F), AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.1667F, KeyframeAnimations.degreeVec(0.0F, 2.5F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.5417F, KeyframeAnimations.degreeVec(0.0F, 2.5F, 0.0F), AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.625F, KeyframeAnimations.degreeVec(0.0F, 2.0F, -1.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.7083F, KeyframeAnimations.degreeVec(0.0F, 2.0F, -2.0F), AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.7917F, KeyframeAnimations.degreeVec(0.0F, 1.0F, -4.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.875F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -6.0F), AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.125F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 5.0F), AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("arm2", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0833F, KeyframeAnimations.degreeVec(0.0F, 5.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(0.0F, 7.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, 7.0F, -7.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(0.0F, 14.0F, -7.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.4167F, KeyframeAnimations.degreeVec(0.0F, 17.0F, -7.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 17.0F, -3.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.5833F, KeyframeAnimations.degreeVec(0.0F, 17.0F, -1.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 17.0F, -1.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.0833F, KeyframeAnimations.degreeVec(0.0F, 17.0F, -5.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1667F, KeyframeAnimations.degreeVec(0.0F, 15.0F, -5.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.25F, KeyframeAnimations.degreeVec(0.0F, 15.0F, -1.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.5417F, KeyframeAnimations.degreeVec(0.0F, 15.0F, -1.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.625F, KeyframeAnimations.degreeVec(0.0F, 13.0F, -3.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.7083F, KeyframeAnimations.degreeVec(0.0F, 10.0F, -5.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.7917F, KeyframeAnimations.degreeVec(0.0F, 7.0F, -7.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.875F, KeyframeAnimations.degreeVec(0.0F, 4.0F, -4.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.125F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("arm2",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0.0F, KeyframeAnimations.posVec(0.2F, -0.2F, 0.0F), AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.1667F, KeyframeAnimations.posVec(0.0F, 0.1F, -0.1F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.5417F, KeyframeAnimations.posVec(0.0F, 0.1F, -0.1F), AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.625F, KeyframeAnimations.posVec(0.0F, 0.05F, -0.2F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.7083F, KeyframeAnimations.posVec(0.05F, 0.1F, -0.1F), AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.7917F, KeyframeAnimations.posVec(0.0F, 0.2F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.875F, KeyframeAnimations.posVec(0.0F, 0.3F, 0.0F), AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.125F, KeyframeAnimations.posVec(0.2F, -0.2F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("arm3", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 5.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.0833F, KeyframeAnimations.degreeVec(-0.6574F, 7.4713F, -0.0429F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(0.0F, 8.0F, -3.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.3333F, KeyframeAnimations.degreeVec(0.0F, 12.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4167F, KeyframeAnimations.degreeVec(0.0F, 16.0F, 3.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 18.0F, 6.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5833F, KeyframeAnimations.degreeVec(0.0F, 20.0F, 10.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 20.0F, 10.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.0833F, KeyframeAnimations.degreeVec(0.0F, 20.0F, 3.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.25F, KeyframeAnimations.degreeVec(0.0F, 20.0F, 8.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.5417F, KeyframeAnimations.degreeVec(0.0F, 20.0F, 8.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.625F, KeyframeAnimations.degreeVec(0.0F, 16.0F, 10.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.7083F, KeyframeAnimations.degreeVec(0.0F, 13.0F, 10.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.7917F, KeyframeAnimations.degreeVec(0.0F, 8.0F, 10.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.875F, KeyframeAnimations.degreeVec(0.0F, 4.0F, 15.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.125F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 5.0F), AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("arm3",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.1F, 0.0F), AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.0833F, KeyframeAnimations.posVec(0.0F, 0.0F, -0.4F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.1667F, KeyframeAnimations.posVec(-0.2F, 0.4F, -0.8F), AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.25F, KeyframeAnimations.posVec(-0.2F, 0.8F, -0.8F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.3333F, KeyframeAnimations.posVec(-0.4F, 0.8F, -1.3F), AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.4167F, KeyframeAnimations.posVec(-0.4F, 0.8F, -1.5F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.5F, KeyframeAnimations.posVec(-0.4F, 0.4F, -1.6F), AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5833F, KeyframeAnimations.posVec(-0.4F, 0.2F, -1.6F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.0F, KeyframeAnimations.posVec(-0.4F, 0.2F, -1.6F), AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.0833F, KeyframeAnimations.posVec(-0.5F, 0.5F, -1.6F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.1667F, KeyframeAnimations.posVec(-0.4F, 0.6F, -1.4F), AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.25F, KeyframeAnimations.posVec(-0.4F, 0.2F, -1.4F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.5417F, KeyframeAnimations.posVec(-0.4F, 0.2F, -1.4F), AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.625F, KeyframeAnimations.posVec(-0.4F, 0.4F, -1.3F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.7083F, KeyframeAnimations.posVec(-0.3F, 0.5F, -1.0F), AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.7917F, KeyframeAnimations.posVec(-0.4F, 0.7F, -0.6F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.875F, KeyframeAnimations.posVec(-0.3F, 0.6F, -0.4F), AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.125F, KeyframeAnimations.posVec(0.0F, -0.1F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
			.build();
}
package automandza.convenientadditions.client.screens;

//imports
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.Minecraft;

import automandza.convenientadditions.procedures.DrillShowFuelUIProcedure;
import net.minecraft.world.item.ItemStack;
import automandza.convenientadditions.item.StoneDrillItem;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.core.component.DataComponents;




@EventBusSubscriber(Dist.CLIENT)
public class DrillOverlayOverlay {
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public static void eventHandler(RenderGuiEvent.Pre event) {
		int w = event.getGuiGraphics().guiWidth();
		int h = event.getGuiGraphics().guiHeight();
		Player entity = Minecraft.getInstance().player;

		int fuelAmountVisualScale;


        if (DrillShowFuelUIProcedure.execute(entity)) {
            ItemStack currDrillIStack = entity.getMainHandItem();
			if (currDrillIStack.getItem() instanceof StoneDrillItem drillItemYe){

				fuelAmountVisualScale = (int)(75f * drillItemYe.getDrillFuelPercentage(currDrillIStack));

				int currDrillFuelVal = (int)currDrillIStack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("currDrillFuelFloat", 0);
				int maxDrillFuelVal = (int)currDrillIStack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("maxDrillFuelFloat", 75d);

				event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("convenient_additions:textures/screens/drillfuelbarbgimg.png"), w - 50, h / 2 + -98, 0, 0, 16, 75, 16, 75);

				event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("convenient_additions:textures/screens/drillfuelbarimg.png"), w - 50, h / 2 + -98, 0, 0, 16, fuelAmountVisualScale, 16, 75);

				event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("convenient_additions:textures/screens/drillfuelbarforegroundimg.png"), w - 50, h / 2 + -98, 0, 0, 16, 75, 16, 75);

				event.getGuiGraphics().drawString(Minecraft.getInstance().font, currDrillFuelVal+"/"+maxDrillFuelVal, w - 90, h / 2 + -96, -2658268, false);

			} else {
				System.out.println("ERROR! selected item is not a drill!!");
			}
		}
	}
}


/* NOTES
 *  
 *  event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, |SPRITE|, Xpos, Ypos, always 0, always 0, scale from left, scale from top, scale from right, scale from bottom);
 *  
 *  Ypos is negative
 *  
 *  all values at the end are ints
 *  
 *	
 *	event.getGuiGraphics().drawString(font, string, Xpos, Ypos, negative color in number format, has shadow bool);
 *	
 *	-14147294 is a netherite colour
 *	-2658268 is an orange colour
 */
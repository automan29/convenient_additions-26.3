package automandza.convenientadditions.client.gui;

//imports
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.GuiGraphics;

import automandza.convenientadditions.world.inventory.KilnGUIMenu;
import automandza.convenientadditions.init.ConvenientAdditionsModScreens;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.LevelAccessor;




public class KilnGUIScreen extends AbstractContainerScreen<KilnGUIMenu> implements ConvenientAdditionsModScreens.ScreenAccessor {
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	private boolean menuStateUpdateActive = false;
	private static final ResourceLocation BACKGROUND = ResourceLocation.parse("convenient_additions:textures/screens/kiln_gui.png");
	private static final ResourceLocation TermCold = ResourceLocation.parse("convenient_additions:textures/screens/kiln_thermometer_cold.png");
	private static final ResourceLocation ThermTorch = ResourceLocation.parse("convenient_additions:textures/screens/kiln_thermometer_torch.png");
	private static final ResourceLocation Therm_1 = ResourceLocation.parse("convenient_additions:textures/screens/kiln_thermometer_1.png");
	private static final ResourceLocation Therm_2 = ResourceLocation.parse("convenient_additions:textures/screens/kiln_thermometer_2.png");
	private static final ResourceLocation Therm_3 = ResourceLocation.parse("convenient_additions:textures/screens/kiln_thermometer_3.png");
	private static final ResourceLocation Therm_4 = ResourceLocation.parse("convenient_additions:textures/screens/kiln_thermometer_4.png");
	private static final ResourceLocation Therm_5 = ResourceLocation.parse("convenient_additions:textures/screens/kiln_thermometer_5.png");
	private static final ResourceLocation Therm_6 = ResourceLocation.parse("convenient_additions:textures/screens/kiln_thermometer_6.png");
	private static final ResourceLocation Prog_Bar_BG = ResourceLocation.parse("convenient_additions:textures/screens/generic_bar_bg.png");
	private static final ResourceLocation Prog_Bar_Fill = ResourceLocation.parse("convenient_additions:textures/screens/generic_bar_fill.png");

	public KilnGUIScreen(KilnGUIMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 176;
		this.imageHeight = 166;
	}

	@Override
	public void updateMenuState(int elementType, String name, Object elementState) {
		menuStateUpdateActive = true;
		menuStateUpdateActive = false;
	}

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		boolean customTooltipShown = false;
		if (mouseX > leftPos + 11 && mouseX < leftPos + 35 && mouseY > topPos + 29 && mouseY < topPos + 53) {
			guiGraphics.setTooltipForNextFrame(font, Component.translatable("gui.convenient_additions.kiln_gui.tooltip_place_over_heat_source"), mouseX, mouseY);
			customTooltipShown = true;
		}
		if (!customTooltipShown)
			this.renderTooltip(guiGraphics, mouseX, mouseY);
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
		int kilnProgressPercentage = 0;
		double currThermometerTemp = 0d;
		
		if (world instanceof LevelAccessor lvlAcc){
			BlockEntity blockEntity = lvlAcc.getBlockEntity(BlockPos.containing(x, y, z));
			if (blockEntity != null){
				kilnProgressPercentage = (int)(blockEntity.getPersistentData().getDoubleOr("percentageDone", 0)*0.75d);
				currThermometerTemp = blockEntity.getPersistentData().getDoubleOr("temperature", 0);
			}
		}
		
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, BACKGROUND, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
		if (currThermometerTemp <= 0){
			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, TermCold, this.leftPos + 19, this.topPos + 29, 0, 0, 8, 24, 8, 24);
		} else if (currThermometerTemp > 0 && currThermometerTemp < 0.1d){
			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ThermTorch, this.leftPos + 19, this.topPos + 29, 0, 0, 8, 24, 8, 24);
		} else if (currThermometerTemp < 0.9d){
			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, Therm_1, this.leftPos + 19, this.topPos + 29, 0, 0, 8, 24, 8, 24);
		} else if (currThermometerTemp < 1.9d){
			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, Therm_2, this.leftPos + 19, this.topPos + 29, 0, 0, 8, 24, 8, 24);
		} else if (currThermometerTemp < 4d){
			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, Therm_3, this.leftPos + 19, this.topPos + 29, 0, 0, 8, 24, 8, 24);
		} else if (currThermometerTemp < 6d){
			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, Therm_4, this.leftPos + 19, this.topPos + 29, 0, 0, 8, 24, 8, 24);
		} else if (currThermometerTemp < 10d){
			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, Therm_5, this.leftPos + 19, this.topPos + 29, 0, 0, 8, 24, 8, 24);
		} else {
			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, Therm_6, this.leftPos + 19, this.topPos + 29, 0, 0, 8, 24, 8, 24);
		}
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, Prog_Bar_BG, this.leftPos + 61, this.topPos + 35, 0, 0, 75, 6, 75, 6);
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, Prog_Bar_Fill, this.leftPos + 61, this.topPos + 35, 0, 0, kilnProgressPercentage, 6, 75, 6);
	}

	@Override
	public boolean keyPressed(int key, int b, int c) {
		if (key == 256) {
			this.minecraft.player.closeContainer();
			return true;
		}
		return super.keyPressed(key, b, c);
	}

	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		guiGraphics.drawString(this.font, Component.translatable("gui.convenient_additions.kiln_gui.label_inventory"), 7, 72, -12829636, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.convenient_additions.kiln_gui.label_kiln"), 6, 5, -12829636, false);
	}

	@Override
	public void init() {
		super.init();
	}
}
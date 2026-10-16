package automandza.convenientadditions.client.gui;

import net.neoforged.neoforge.client.network.ClientPacketDistributor;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.PlainTextButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.GuiGraphics;

import automandza.convenientadditions.world.inventory.SawmillGUIMenu;
import automandza.convenientadditions.procedures.PremiumSawmillCheckProcedure;
import automandza.convenientadditions.network.SawmillGUIButtonMessage;
import automandza.convenientadditions.init.ConvenientAdditionsModScreens;

public class SawmillGUIScreen extends AbstractContainerScreen<SawmillGUIMenu> implements ConvenientAdditionsModScreens.ScreenAccessor {
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	private boolean menuStateUpdateActive = false;
	private Button button_default;
	private Button button_cut;
	private Button button_split;
	private Button button_do;
	private static final ResourceLocation BACKGROUND = ResourceLocation.parse("convenient_additions:textures/screens/sawmill_gui.png");
	private static final ResourceLocation IMAGE_0 = ResourceLocation.parse("convenient_additions:textures/screens/sawmill_arrow.png");

	public SawmillGUIScreen(SawmillGUIMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 174;
		this.imageHeight = 176;
	}

	@Override
	public void updateMenuState(int elementType, String name, Object elementState) {
		menuStateUpdateActive = true;
		menuStateUpdateActive = false;
	}

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		this.renderTooltip(guiGraphics, mouseX, mouseY);
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, BACKGROUND, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, IMAGE_0, this.leftPos + 125, this.topPos + 42, 0, 0, 20, 16, 20, 16);
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
		guiGraphics.drawString(this.font, Component.translatable("gui.convenient_additions.sawmill_gui.label_inventory"), 6, 82, -12829636, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.convenient_additions.sawmill_gui.label_sawmill"), 6, 5, -12829636, false);
	}

	@Override
	public void init() {
		super.init();
		button_default = Button.builder(Component.translatable("gui.convenient_additions.sawmill_gui.button_default"), e -> {
			int x = SawmillGUIScreen.this.x;
			int y = SawmillGUIScreen.this.y;
			if (true) {
				ClientPacketDistributor.sendToServer(new SawmillGUIButtonMessage(0, x, y, z));
				SawmillGUIButtonMessage.handleButtonAction(entity, 0, x, y, z);
			}
		}).bounds(this.leftPos + 6, this.topPos + 15, 61, 20).build();
		this.addRenderableWidget(button_default);
		button_cut = Button.builder(Component.translatable("gui.convenient_additions.sawmill_gui.button_cut"), e -> {
			int x = SawmillGUIScreen.this.x;
			int y = SawmillGUIScreen.this.y;
			if (true) {
				ClientPacketDistributor.sendToServer(new SawmillGUIButtonMessage(1, x, y, z));
				SawmillGUIButtonMessage.handleButtonAction(entity, 1, x, y, z);
			}
		}).bounds(this.leftPos + 6, this.topPos + 36, 40, 20).build();
		this.addRenderableWidget(button_cut);
		button_split = Button.builder(Component.translatable("gui.convenient_additions.sawmill_gui.button_split"), e -> {
			int x = SawmillGUIScreen.this.x;
			int y = SawmillGUIScreen.this.y;
			if (true) {
				ClientPacketDistributor.sendToServer(new SawmillGUIButtonMessage(2, x, y, z));
				SawmillGUIButtonMessage.handleButtonAction(entity, 2, x, y, z);
			}
		}).bounds(this.leftPos + 6, this.topPos + 57, 51, 20).build();
		this.addRenderableWidget(button_split);
		button_do = new PlainTextButton(this.leftPos + -124, this.topPos + 159, 35, 20, Component.translatable("gui.convenient_additions.sawmill_gui.button_do"), e -> {
			int x = SawmillGUIScreen.this.x;
			int y = SawmillGUIScreen.this.y;
			if (PremiumSawmillCheckProcedure.execute(entity)) {
				ClientPacketDistributor.sendToServer(new SawmillGUIButtonMessage(3, x, y, z));
				SawmillGUIButtonMessage.handleButtonAction(entity, 3, x, y, z);
			}
		}, this.font);
		this.addRenderableWidget(button_do);
	}

	@Override
	protected void containerTick() {
		super.containerTick();
		this.button_do.visible = PremiumSawmillCheckProcedure.execute(entity);
	}
}
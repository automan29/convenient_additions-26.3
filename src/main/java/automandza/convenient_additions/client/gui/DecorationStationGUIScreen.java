package automandza.convenient_additions.client.gui;

import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.input.KeyEvent;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.Identifier;
import net.minecraft.network.chat.Component;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.GuiGraphicsExtractor;

import automandza.convenient_additions.world.inventory.DecorationStationGUIMenu;
import automandza.convenient_additions.network.DecorationStationGUIButtonMessage;
import automandza.convenient_additions.init.ConvenientAdditionsModScreens;

public class DecorationStationGUIScreen extends AbstractContainerScreen<DecorationStationGUIMenu> implements ConvenientAdditionsModScreens.ScreenAccessor {
	private final Level world;
	private final int x, y, z, imageWidth, imageHeight;
	private final Player entity;
	private boolean menuStateUpdateActive = false;
	private Button button_add_lights;
	private Button button_done;
	private static final Identifier BACKGROUND = Identifier.parse("convenient_additions:textures/screens/decoration_station_gui.png");

	public DecorationStationGUIScreen(DecorationStationGUIMenu container, Inventory inventory, Component text) {
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
	public void extractRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
		super.extractRenderState(guiGraphics, mouseX, mouseY, partialTicks);
		this.extractTooltip(guiGraphics, mouseX, mouseY);
	}

	@Override
	public void extractBackground(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, BACKGROUND, leftPos, topPos, 0, 0, imageWidth, imageHeight, imageWidth, imageHeight);
	}

	@Override
	public boolean keyPressed(KeyEvent event) {
		int key = event.key();
		if (key == 256) {
            assert this.minecraft.player != null;
            this.minecraft.player.closeContainer();
			return true;
		}
		return super.keyPressed(event);
	}

	@Override
	protected void extractLabels(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY) {
	}

	@Override
	public void init() {
		super.init();
		button_add_lights = Button.builder(Component.translatable("gui.convenient_additions.decoration_station_gui.button_add_lights"), e -> {
			int x = DecorationStationGUIScreen.this.x;
			int y = DecorationStationGUIScreen.this.y;
			if (true) {
				ClientPacketDistributor.sendToServer(new DecorationStationGUIButtonMessage(0, x, y, z));
				DecorationStationGUIButtonMessage.handleButtonAction(entity, 0, x, y, z);
			}
		}).bounds(this.leftPos + 10, this.topPos + 28, 75, 20).build();
		this.addRenderableWidget(button_add_lights);
		button_done = Button.builder(Component.translatable("gui.convenient_additions.decoration_station_gui.button_done"), e -> {
			int x = DecorationStationGUIScreen.this.x;
			int y = DecorationStationGUIScreen.this.y;
			if (true) {
				ClientPacketDistributor.sendToServer(new DecorationStationGUIButtonMessage(1, x, y, z));
				DecorationStationGUIButtonMessage.handleButtonAction(entity, 1, x, y, z);
			}
		}).bounds(this.leftPos + 122, this.topPos + 28, 45, 20).build();
		this.addRenderableWidget(button_done);
	}
}
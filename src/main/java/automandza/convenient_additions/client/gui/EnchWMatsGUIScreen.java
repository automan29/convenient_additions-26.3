package automandza.convenient_additions.client.gui;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.Identifier;
import net.minecraft.network.chat.Component;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.render.GuiRenderer;

import automandza.convenient_additions.world.inventory.EnchWMatsGUIMenu;
import automandza.convenient_additions.init.ConvenientAdditionsModScreens;

public class EnchWMatsGUIScreen extends AbstractContainerScreen<EnchWMatsGUIMenu> implements ConvenientAdditionsModScreens.ScreenAccessor {
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	private boolean menuStateUpdateActive = false;
	private static final Identifier IMAGE_0 = Identifier.parse("convenient_additions:textures/screens/ench_mats_machine_bg.png");
	private static final Identifier IMAGE_1 = Identifier.parse("convenient_additions:textures/screens/amethest_box_overlay.png");
	private static final Identifier IMAGE_2 = Identifier.parse("convenient_additions:textures/screens/lapis_box_overlay.png");

	public EnchWMatsGUIScreen(EnchWMatsGUIMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 194;
		this.imageHeight = 180;
	}

	@Override
	public void updateMenuState(int elementType, String name, Object elementState) {
		menuStateUpdateActive = true;
		menuStateUpdateActive = false;
	}

	@Override
	public void render(GuiRenderer guiGraphics, int mouseX, int mouseY, float partialTicks) {
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		this.renderTooltip(guiGraphics, mouseX, mouseY);
	}

	@Override
	protected void renderBg(GuiRenderer guiGraphics, float partialTicks, int mouseX, int mouseY) {
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, IMAGE_0, this.leftPos + 0, this.topPos + 0, 0, 0, 194, 180, 194, 180);
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, IMAGE_1, this.leftPos + 24, this.topPos + 55, 0, 0, 16, 16, 16, 16);
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, IMAGE_2, this.leftPos + 24, this.topPos + 55, 0, 0, 16, 16, 16, 16);
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
	protected void renderLabels(GuiRenderer guiGraphics, int mouseX, int mouseY) {
		guiGraphics.drawString(this.font, Component.translatable("gui.convenient_additions.ench_w_mats_gui.label_inventory"), 49, 75, -6843986, true);
	}

	@Override
	public void init() {
		super.init();
	}
}
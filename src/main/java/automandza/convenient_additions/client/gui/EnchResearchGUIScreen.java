package automandza.convenientadditions.client.gui;

import net.neoforged.neoforge.client.network.ClientPacketDistributor;

//imports
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.GuiGraphics;

import automandza.convenientadditions.world.inventory.EnchResearchGUIMenu;
import automandza.convenientadditions.network.EnchResearchGUIButtonMessage;
import automandza.convenientadditions.init.ConvenientAdditionsModScreens;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;






public class EnchResearchGUIScreen extends AbstractContainerScreen<EnchResearchGUIMenu> implements ConvenientAdditionsModScreens.ScreenAccessor {
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	private boolean menuStateUpdateActive = false;
	private ImageButton imagebutton_ench_res_test_select;
	private ImageButton viewUnlockedEnchButton;
	private static final ResourceLocation BACKGROUND_IMG = ResourceLocation.parse("convenient_additions:textures/screens/enchant_research_bg.png");


	public EnchResearchGUIScreen(EnchResearchGUIMenu container, Inventory inventory, Component text) {
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
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		this.renderTooltip(guiGraphics, mouseX, mouseY);
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, BACKGROUND_IMG, this.leftPos, this.topPos, 0, 0, 194, 180, 194, 180); // background image
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
		BlockPos resTablePos = BlockPos.containing(x, y, z);
		BlockEntity resTableEntity = world.getBlockEntity(resTablePos);
		int expCost = Math.max(0, (int) resTableEntity.getPersistentData().getDoubleOr("xpCost", 0));
		
		guiGraphics.drawString(this.font, Component.translatable("gui.convenient_additions.ench_research_gui.label_inventory"), 21, 74, -6843986, true);
		guiGraphics.drawString(this.font, Component.literal("XP Cost: "+expCost), 68, 54, -8323296, true);
	}

	@Override
	public void init() {
		super.init();

		// button to unlock new enchants
		imagebutton_ench_res_test_select = new ImageButton(this.leftPos + 59, this.topPos + 28, 41, 18,
			new WidgetSprites(ResourceLocation.parse("convenient_additions:textures/screens/ench_res_test_button.png"), ResourceLocation.parse("convenient_additions:textures/screens/ench_res_test_select.png")), 
			e -> {
				int x = EnchResearchGUIScreen.this.x;
				int y = EnchResearchGUIScreen.this.y;
				int id = 0;
				ClientPacketDistributor.sendToServer(new EnchResearchGUIButtonMessage(id, x, y, z));
				EnchResearchGUIButtonMessage.handleButtonAction(entity, id, x, y, z);
			
}
		) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_ench_res_test_select);

		// button to open unlocked GUI
		viewUnlockedEnchButton = new ImageButton(this.leftPos + 130, this.topPos + 26, 22, 22,
			new WidgetSprites(ResourceLocation.parse("convenient_additions:textures/screens/view_unlocked_ench_button.png"), ResourceLocation.parse("convenient_additions:textures/screens/view_unlocked_ench_button_select.png")), 
			e -> {
				int x = EnchResearchGUIScreen.this.x;
				int y = EnchResearchGUIScreen.this.y;
				int id = 1;
				ClientPacketDistributor.sendToServer(new EnchResearchGUIButtonMessage(id, x, y, z));
				EnchResearchGUIButtonMessage.handleButtonAction(entity, id, x, y, z);
			}
		) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(viewUnlockedEnchButton);

	}
}
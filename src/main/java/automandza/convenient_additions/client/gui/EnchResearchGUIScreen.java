package automandza.convenient_additions.client.gui;

import net.minecraft.client.input.KeyEvent;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

//imports
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.Identifier;
import net.minecraft.network.chat.Component;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.GuiGraphicsExtractor;

import automandza.convenient_additions.world.inventory.EnchResearchGUIMenu;
import automandza.convenient_additions.network.EnchResearchGUIButtonMessage;
import automandza.convenient_additions.init.ConvenientAdditionsModScreens;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;
import org.jspecify.annotations.NonNull;


public class EnchResearchGUIScreen extends AbstractContainerScreen<EnchResearchGUIMenu> implements ConvenientAdditionsModScreens.ScreenAccessor {
	private final Level world;
	private final int x, y, z, imageWidth, imageHeight;
	private final Player entity;
    private static final Identifier BACKGROUND_IMG = Identifier.parse("convenient_additions:textures/screens/enchant_research_bg.png");


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
	}

	@Override
	public void extractRenderState(@NonNull GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
		super.extractRenderState(guiGraphics, mouseX, mouseY, partialTicks);
		this.extractTooltip(guiGraphics, mouseX, mouseY);
	}

	@Override
	public void extractBackground(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, BACKGROUND_IMG, this.leftPos, this.topPos, 0, 0, 194, 180, 194, 180); // background image
	}

	@Override
	public boolean keyPressed(KeyEvent event) {
		if (event.key() == 256) {
            assert this.minecraft.player != null;
            this.minecraft.player.closeContainer();
			return true;
		}
		return super.keyPressed(event);
	}

	@Override
	protected void extractLabels(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY) {
		BlockPos resTablePos = BlockPos.containing(x, y, z);
		BlockEntity resTableEntity = world.getBlockEntity(resTablePos);
        assert resTableEntity != null;
        int expCost = Math.max(0, (int) resTableEntity.getPersistentData().getDoubleOr("xpCost", 0));
		
		guiGraphics.text(this.font, Component.translatable("gui.convenient_additions.ench_research_gui.label_inventory"), 21, 74, -6843986, true);
		guiGraphics.text(this.font, Component.literal("XP Cost: "+expCost), 68, 54, -8323296, true);
	}

	@Override
	public void init() {
		super.init();

		// button to unlock new enchants
        ImageButton imagebutton_ench_res_test_select = new ImageButton(this.leftPos + 59, this.topPos + 28, 41, 18,
                new WidgetSprites(Identifier.parse("convenient_additions:textures/screens/ench_res_test_button.png"), Identifier.parse("convenient_additions:textures/screens/ench_res_test_select.png")),
                _ -> {
                    int x = EnchResearchGUIScreen.this.x;
                    int y = EnchResearchGUIScreen.this.y;
                    int id = 0;
                    ClientPacketDistributor.sendToServer(new EnchResearchGUIButtonMessage(id, x, y, z));
                    EnchResearchGUIButtonMessage.handleButtonAction(entity, id, x, y, z);
                }
        ) {
            @Override
            public void extractContents(@NonNull GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
                guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
            }
        };
		this.addRenderableWidget(imagebutton_ench_res_test_select);

		// button to open unlocked GUI
        ImageButton viewUnlockedEnchButton = new ImageButton(this.leftPos + 130, this.topPos + 26, 22, 22,
                new WidgetSprites(Identifier.parse("convenient_additions:textures/screens/view_unlocked_ench_button.png"), Identifier.parse("convenient_additions:textures/screens/view_unlocked_ench_button_select.png")),
                _ -> {
                    int x = EnchResearchGUIScreen.this.x;
                    int y = EnchResearchGUIScreen.this.y;
                    int id = 1;
                    ClientPacketDistributor.sendToServer(new EnchResearchGUIButtonMessage(id, x, y, z));
                    EnchResearchGUIButtonMessage.handleButtonAction(entity, id, x, y, z);
                }
        ) {
            @Override
            public void extractContents(@NonNull GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
                guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
            }
        };
		this.addRenderableWidget(viewUnlockedEnchButton);

	}
}
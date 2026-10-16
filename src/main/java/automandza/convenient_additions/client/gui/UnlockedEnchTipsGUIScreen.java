package automandza.convenientadditions.client.gui;

//imports
import automandza.convenientadditions.network.EnchResearchGUIButtonMessage;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.GuiGraphics;

import automandza.convenientadditions.world.inventory.UnlockedEnchTipsGUIMenu;
import automandza.convenientadditions.init.ConvenientAdditionsModScreens;
import automandza.convenientadditions.init.ConvAddModCustomResourceLoc;
import automandza.convenientadditions.procedures.EnchResearchTickProcedure;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import org.jetbrains.annotations.NotNull;


public class UnlockedEnchTipsGUIScreen extends AbstractContainerScreen<UnlockedEnchTipsGUIMenu> implements ConvenientAdditionsModScreens.ScreenAccessor {

	// initial vars and finals
	private final Level world;
	private final int x, y, z;
	private final Player plr;
	private boolean menuStateUpdateActive = false;
	private String listOfUnlockedEnc;
	private final int lineSeparation = 21;

	private	ImageButton goBackButton;
	private ImageButton scrollBarButton;
	private int scrollPosition, maxScrollPosition;
	private final int scrollSpeed = 5;
	public final int[] scrollBoxDims = {25, 174, 164- lineSeparation, 2};

	// images
	private static final ResourceLocation BACKGROUND = ResourceLocation.parse("convenient_additions:textures/screens/unlocked_ench_tips_gui.png");
	private static final ResourceLocation FOREGROUND = ResourceLocation.parse("convenient_additions:textures/screens/unlocked_ench_tips_foreground.png");
	private static final ResourceLocation EMPTY_XP_BAR = ResourceLocation.parse("convenient_additions:textures/screens/xp_bar_10_empty.png");
	private static final ResourceLocation XP_BAR_EDGE = ResourceLocation.parse("convenient_additions:textures/screens/xp_bar_single_edge_full.png");
	private static final ResourceLocation XP_BAR_SEGMENT_FULL = ResourceLocation.parse("convenient_additions:textures/screens/xp_bar_single_full.png");
	private static final ResourceLocation FULL_XP_BAR = ResourceLocation.parse("xp_metre_full_10.png");
	private static final ResourceLocation BACK_BUTTON = ResourceLocation.parse("convenient_additions:textures/screens/tips_back_button.png");

	

	public UnlockedEnchTipsGUIScreen(UnlockedEnchTipsGUIMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.listOfUnlockedEnc = container.listOfUnlockedEnc;
		this.plr = inventory.player;
		this.imageWidth = 176;
		this.imageHeight = 166;
		this.scrollPosition = 0;

		this.maxScrollPosition = scrollBoxDims[2]-scrollBoxDims[0];
		String[] storedUnlockedEnchants = listOfUnlockedEnc.split(",");
		for (int i = 0; i < storedUnlockedEnchants.length; i++){
			if (i*lineSeparation - lineSeparation*5 > maxScrollPosition){
				maxScrollPosition = i*lineSeparation - lineSeparation*5;
			}
		}
	}

	@Override
	public void updateMenuState(int elementType, String name, Object elementState) {

	}

	@Override
	public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		boolean customTooltipShown = false;

		if (!(listOfUnlockedEnc == null || listOfUnlockedEnc.equals("hard luck") || listOfUnlockedEnc.isEmpty())){
			String[] storedUnlockedEnchants = listOfUnlockedEnc.split(",");
			int i = 0;

			if (scrollPosition == 0) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ConvAddModCustomResourceLoc.WHITE_PIXEL, leftPos+scrollBoxDims[3], topPos+scrollBoxDims[0], 0, 0, scrollBoxDims[1]-scrollBoxDims[3], 1, scrollBoxDims[1]-scrollBoxDims[3], 1);
			}

			for (String currUnlkdData : storedUnlockedEnchants) {
				double currUnlkdEnchLvl = Double.parseDouble(currUnlkdData.substring(currUnlkdData.indexOf(":")+1));
				double unlockedLevelPercentage = (currUnlkdEnchLvl-Math.floor(currUnlkdEnchLvl))*10;
				String currUnlkdEnchName = currUnlkdData.substring(12, currUnlkdData.indexOf(":")).toLowerCase();
				int currYpos = scrollPosition+(i* lineSeparation);

				if (currYpos > scrollBoxDims[2]-scrollBoxDims[0]){
					break;
				}

				if (currYpos > -lineSeparation) {
					// seperator line
					guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ConvAddModCustomResourceLoc.WHITE_PIXEL, leftPos+scrollBoxDims[3], topPos+scrollBoxDims[0] + scrollPosition + ((i + 1) * lineSeparation), 0, 0, scrollBoxDims[1]-scrollBoxDims[3], 1, scrollBoxDims[1]-scrollBoxDims[3], 1);

					// Unlocked Percentage Bar
					if (EnchResearchTickProcedure.convertEnchantmentStrToMaxLvl(currUnlkdEnchName) > currUnlkdEnchLvl) {
						guiGraphics.blit(RenderPipelines.GUI_TEXTURED, EMPTY_XP_BAR, leftPos + scrollBoxDims[3] + 12, topPos + scrollBoxDims[0] + currYpos + 13, 0, 0, 111, 5, 111, 5);
						if (unlockedLevelPercentage > 0.1d) {
							guiGraphics.blit(RenderPipelines.GUI_TEXTURED, XP_BAR_EDGE, leftPos + scrollBoxDims[3] + 12, topPos + scrollBoxDims[0] + currYpos + 13, 0, 0, 11, 5, 11, 5);
						}
						if (unlockedLevelPercentage > 1d && unlockedLevelPercentage <= 9.45d) {
							for (int j = 0; j < unlockedLevelPercentage; j++) {
								guiGraphics.blit(RenderPipelines.GUI_TEXTURED, XP_BAR_SEGMENT_FULL, leftPos + scrollBoxDims[3] + 13 + (10 * (j + 1)), topPos + scrollBoxDims[0] + currYpos + 13, 0, 0, 10, 5, 10, 5);
							}
						}
						if (unlockedLevelPercentage > 9.45d) {
							guiGraphics.blit(RenderPipelines.GUI_TEXTURED, FULL_XP_BAR, leftPos + scrollBoxDims[3] + 12, topPos + scrollBoxDims[0] + currYpos + 13, 0, 0, 111, 5, 111, 5);
						}
					}

					// info icon
					guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ConvAddModCustomResourceLoc.INFO_ICON, leftPos+scrollBoxDims[1] - 40, topPos+scrollBoxDims[0] + 3 + currYpos, 0, 0, 12, 12, 12, 12);
					if (mouseX > leftPos+scrollBoxDims[1] - 41 && mouseX < leftPos+scrollBoxDims[1] - 28 && mouseY > topPos+scrollBoxDims[0] + 2 + currYpos && mouseY < topPos+scrollBoxDims[0] + 16 + currYpos) {
						guiGraphics.setTooltipForNextFrame(font, Component.literal(hint1Tooltip(currUnlkdEnchName)), mouseX, mouseY);
						customTooltipShown = true;
					}
				}

				i++;
			}
		}

		// scroll bar
		updateScrollPosFromBar(mouseY);
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ConvAddModCustomResourceLoc.GENERIC_BAR_BACKGROUND, leftPos+scrollBoxDims[1]-6, topPos+scrollBoxDims[0], 0, 0, 6, scrollBarButton.getHeight(), 6, scrollBarButton.getHeight());
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ConvAddModCustomResourceLoc.WHITE_PIXEL, leftPos+scrollBoxDims[1]-6, topPos+scrollBoxDims[0]-(int)Math.min(((double)scrollPosition / (double)maxScrollPosition) * (double)(scrollBoxDims[2] - scrollBoxDims[0]), scrollBoxDims[2] - scrollBoxDims[0]), 0, 0, 6, 8, 6, 8);

		// foreground
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, FOREGROUND, leftPos, topPos, 0, 0, imageWidth, imageHeight, imageWidth, imageHeight); // foreground image


		// back button graphics
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, BACK_BUTTON, goBackButton.getX(), goBackButton.getY(), goBackButton.isHoveredOrFocused()?20:0, 0, 20, goBackButton.getHeight(), 40, goBackButton.getHeight());


        if (!customTooltipShown) this.renderTooltip(guiGraphics, mouseX, mouseY);
	}

	@Override
	protected void renderLabels(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY) {

		// if unlocked string is empty or null gives a warning and stops
		if (listOfUnlockedEnc == null || listOfUnlockedEnc.equals("hard luck") || listOfUnlockedEnc.isEmpty()){
			System.out.println("Warning! Unlocked Enhancements string is empty");
			return;
		}

		String[] storedUnlockedEnchants = listOfUnlockedEnc.split(",");
		int i = 0;

		for (String currUnlkdData : storedUnlockedEnchants) {
			String currUnlkdEnchName = currUnlkdData.substring(12, currUnlkdData.indexOf(":"));
			double currUnlkdEnchLvl = Double.parseDouble(currUnlkdData.substring(currUnlkdData.indexOf(":")+1));
			int currYpos = scrollPosition+(i* lineSeparation);

			if (currYpos > scrollBoxDims[2]-scrollBoxDims[0]){
				break;
			}
			if (currYpos > -lineSeparation) {
				guiGraphics.drawString(this.font, Component.literal(currUnlkdEnchName), scrollBoxDims[3]+4, scrollBoxDims[0]+3 + currYpos, -1, false);
				guiGraphics.drawString(this.font, Component.literal("" + (int) Math.floor(currUnlkdEnchLvl)), scrollBoxDims[3]+4, scrollBoxDims[0]+12 + currYpos, -1, false);
			}
			i++;
		}
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, BACKGROUND, leftPos, topPos, 0, 0, imageWidth, imageHeight, imageWidth, imageHeight); // Background image
	}

	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double whatDisDo, double scrollDir) {
		if (scrollDir < 0){
			if(scrollPosition > -maxScrollPosition){
				if (scrollPosition - scrollSpeed > -maxScrollPosition){
					scrollPosition -= scrollSpeed;
				} else {
					scrollPosition = -maxScrollPosition;
				}
				return true;
			}
		} else if (scrollDir > 0) {
			if (scrollPosition < 0) {
				if (scrollPosition + scrollSpeed < 0){
					scrollPosition += scrollSpeed;
				} else {
					scrollPosition = 0;
				}
				return true;
			}
		}
		return false;
	}


	private void updateScrollPosFromBar(int mouseY){
		double mouseYPos = (mouseY - (topPos+scrollBoxDims[0]));
		if(menuStateUpdateActive){
			double yPercent = Math.clamp((mouseYPos / (scrollBoxDims[2] - scrollBoxDims[0])) * maxScrollPosition, 0, maxScrollPosition);
			scrollPosition = (int) -(yPercent);
		}
	}

	@Override
	public boolean mouseReleased(double probMouseX, double probMouseY, int g) {
		menuStateUpdateActive = false;
		if (g == 0 && this.isDragging()) {
			this.setDragging(false);
			if (this.getFocused() != null) {
				return this.getFocused().mouseReleased(probMouseX, probMouseY, g);
			}
		}

		return false;
	}

	@Override
	public boolean keyPressed(int key, int b, int c) {
		if (key == 256) {
			if (this.minecraft != null && this.minecraft.player != null) {
				this.minecraft.player.closeContainer();
			} else {
				System.out.println("ERROR! Minecraft doesn't exist! D:");
			}
			return true;
		}
		//System.out.println("key="+key);
		return super.keyPressed(key, b, c);
	}

	@Override
	public void init() {
		super.init();

		// button to go back
		goBackButton = new ImageButton(leftPos+2, topPos+2, 20, 20,
				new WidgetSprites(BACK_BUTTON, BACK_BUTTON),
				e -> {
					int id = 2;
					ClientPacketDistributor.sendToServer(new EnchResearchGUIButtonMessage(id, x, y, z));
					EnchResearchGUIButtonMessage.handleButtonAction(plr, id, x, y, z);
				}
		);
		this.addRenderableWidget(goBackButton);


		//scroll bar disguised as a button *indeed face*
		scrollBarButton = new ImageButton(leftPos+scrollBoxDims[1]-6, topPos+scrollBoxDims[0], 6, scrollBoxDims[2]-(scrollBoxDims[0]/2),
				new WidgetSprites(BACK_BUTTON, BACK_BUTTON),
				e -> {
					menuStateUpdateActive = true;
				}
		);
		this.addRenderableWidget(scrollBarButton);
	}


	// hints
	private String hint1Tooltip(String enhInput){
		String outputStr = "hey guys it doseph, something broke :(";
		try {
            outputStr = switch (enhInput) {
                case "fire aspect" -> "Gathered from a flaming entity";
                case "feather falling" -> "What could it be?";
                case "thorns" -> "The 'Netherite Killer'";
                case "aqua affinity" -> "Precious guarded material";
                case "bane of arthropods" -> "A rock made with two items from the nether and two from the overworld";
                case "breach" -> "The most common item";
                case "unbreaking" -> "If only we could make things out of this to begin with";
                case "blast protection" -> "Reduces the impact";
                case "channeling" -> "dat ain't how you make the golem";
                case "depth strider" -> "Streamlines swimming";
                case "flame" -> "Something to light all the arrows with";
                case "impaling" -> "Known for being found underwater and being sharp";
                case "infinity" -> "Dropped from a monster that's easiest to fight with a bow";
                case "knockback" -> "Improvised method to push mobs away";
                case "luck of the sea" -> "A strong magnet to collect more treasure!";
                case "lure" -> "It's disgusting but the fish love it";
                case "multishot" -> "Maybe you can fit one more arrow on? You only need another slot for it";
                case "piercing" -> "Using a different bowstring to get extra draw";
                case "projectile protection" -> "Why not attach this? It works on its own already";
                case "punch" -> "If this was Terraria it would be flame instead";
                case "quick charge" -> "Redstone logic";
                case "respiration" -> "Just turn it upside-down for air instead";
                case "silk touch" -> "Silk touch? That reminds me of the land flowing with milk and ....";
                case "smite" -> "Fish!";
                case "soul speed" -> "Its suppose to slow you down even more, we found it can do the opposite";
                case "sweeping edge" -> "If only the blade could go through them";
                case "swift sneak" -> "Never skip leg day";
                case "wind burst" -> "Why are those trial things so big?";
                case "fortune" -> "This would sell for a fortune";
                case "looting" -> "It works similarly to a specific monster in a thunderstorm";
                case "riptide" -> "Once you've made the armour it'll make sense";
                case "frost walker" -> "Ignore how this fits, where the electricity supply comes from, or how it is powerful enough";
                case "density" -> "It is said to cure mortality, but Friedrich Nietzsche never interacted with its brilliance.";
                case "fire protection" -> "I may be biased but it is a very effective tool indeed";
                case "sharpness" -> "Iron sharpens iron";
                case "protection" -> "Hard to come by, partly refined";
                case "power" -> "Normally this would cost a micro-transaction but it'll do";
                case "efficiency" -> "Usually used in contraptions but should speed stuff up";
                case "loyalty" -> "No LOYALTY";
                case "dwarvern instincts" -> "For rock and stone! but seriously this item is neither of them";
                case "infusion" -> "Where did his arms and legsgo?";
                case "curse of binding", "curse of vanishing" -> "This can't be made. You just wasted your XP...";
                default -> "Use your experience";
            };
		} catch (Exception e){
			System.out.println("ERROR! could not be switched, trace="+e);
		}
		return outputStr;
	}
}
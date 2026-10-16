package automandza.convenientadditions.procedures;

//imports
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.tags.ItemTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.AdvancementHolder;


import automandza.convenientadditions.init.ConvenientAdditionsModMenus;




public class SawmillCraftingProcedure {
	final static double inputID = 0;
	final static double outputID = 1;

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null) return;

		// warning is wrong it would make it way less readable
		ItemStack inputItemstackFound = entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof ConvenientAdditionsModMenus.MenuAccessor _menu0 ? _menu0.getSlots().get((int) inputID).getItem() : ItemStack.EMPTY;

		String currentSawmillRecipe = getBlockNBTString(world, BlockPos.containing(x, y, z), "currentSawRecipe");
		
		if (currentSawmillRecipe.length() < 2){
			currentSawmillRecipe = "default";
		}
		
		// checks player and the menu exists
		if (entity instanceof Player _player && _player.containerMenu instanceof ConvenientAdditionsModMenus.MenuAccessor _menu) {

			// paper recipe from bark
			if (inputItemstackFound.is(ItemTags.create(ResourceLocation.fromNamespaceAndPath("convenient_additions", "material/bark")))
			&& inputItemstackFound.getCount() % 2 == 0
			&& currentSawmillRecipe.equals("default")) {
			
				ItemStack _setstack2 = new ItemStack(Items.PAPER).copy();
				_setstack2.setCount(inputItemstackFound.getCount()/2);
				_menu.getSlots().get((int) outputID).set(_setstack2);

			// checks if inserted item has planks tag
			} else if (inputItemstackFound.is(ItemTags.create(ResourceLocation.parse("minecraft:planks")))){
				//calls SawmillWoodFindProcedure planks to get output as an ItemStack
				ItemStack _setstack2 = SawmillWoodFindProcedure.PlanksToSaw(inputItemstackFound, currentSawmillRecipe);
				_setstack2.setCount(inputItemstackFound.getCount());
				_menu.getSlots().get((int) outputID).set(_setstack2);

			// checks if inserted item has logs tag
			} else if (inputItemstackFound.is(ItemTags.create(ResourceLocation.parse("minecraft:logs")))){
				// stripped wood log recipe from any log
				if(currentSawmillRecipe.equals("default")){
					ItemStack _setstack2 = SawmillWoodFindProcedure.LogsToWood(inputItemstackFound.getItem().toString());
					_setstack2.setCount(inputItemstackFound.getCount());
					_menu.getSlots().get((int) outputID).set(_setstack2);

				// converts logs to planks at a 1 to 5 ratio
				} else if (currentSawmillRecipe.equals("cut")){
					ItemStack _setstack2 = SawmillWoodFindProcedure.LogsToPlanks(inputItemstackFound.getItem().toString());
					_setstack2.setCount(5);
					_menu.getSlots().get((int) outputID).set(_setstack2);
					
				// error happens
				} else {
					_menu.getSlots().get((int) outputID).set(ItemStack.EMPTY);
				}

			} else if (currentSawmillRecipe.equals("do")){
				// doer
				if (entity instanceof ServerPlayer srvrPlr && srvrPlr.level() instanceof ServerLevel svrLevel) {
					AdvancementHolder _adv = svrLevel.getServer().getAdvancements().get(ResourceLocation.parse("convenient_additions:doer_2"));
					if (_adv != null) {
						AdvancementProgress _ap = srvrPlr.getAdvancements().getOrStartProgress(_adv);
						if (!_ap.isDone()) {
							for (String criteria : _ap.getRemainingCriteria())
								srvrPlr.getAdvancements().award(_adv, criteria);
						}
					}
				}
				if (inputItemstackFound.getItem() == Items.DIRT){
					ItemStack outStack = new ItemStack(Items.PODZOL).copy();
					outStack.setCount(inputItemstackFound.getCount());
					_menu.getSlots().get((int) outputID).set(outStack);
				} else {
					_menu.getSlots().get((int) outputID).set(ItemStack.EMPTY);
				}

			} else {
				_menu.getSlots().get((int) outputID).set(ItemStack.EMPTY);
			
			}
			// update visible stuff
			_player.containerMenu.broadcastChanges();
		}
	}

	
	private static String getBlockNBTString(LevelAccessor world, BlockPos pos, String tag) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity != null)
			return blockEntity.getPersistentData().getStringOr(tag, "");
		return "";
	}
}
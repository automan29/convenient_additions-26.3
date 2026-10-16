package automandza.convenientadditions.procedures;

//imports
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;

import automandza.convenientadditions.init.ConvenientAdditionsModMenus;
import automandza.convenientadditions.init.ConvenientAdditionsModItems;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.Holder;
import java.util.Set;
import java.util.Iterator;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;




public class EnchWMatsOpenTickProcedure {
	
	private static String prevGUISlotState = "";
	
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null){
			return;
		}
		String fullEnchantUnlocksStr = entity.getPersistentData().getStringOr("unlockedEnchantList", "hard luck");
		// check that the player's unlocked enchantments are not empty
		if (fullEnchantUnlocksStr.equals("hard luck") || fullEnchantUnlocksStr.equals("")){
			return;
		}
		String[] enchantUnlocksArray = fullEnchantUnlocksStr.split(",");
		
		// get player menu and check lapis slot has lapis
		if (entity instanceof Player plyr && plyr.containerMenu instanceof ConvenientAdditionsModMenus.MenuAccessor currUImenu) {
			ItemStack toolToEnch = currUImenu.getSlots().get(0).getItem();
			ItemStack enchMaterial = currUImenu.getSlots().get(2).getItem();
			int lapisCount = currUImenu.getSlots().get(1).getItem().getCount();

			if (toolToEnch == ItemStack.EMPTY || currUImenu.getSlots().get(1).getItem().getItem() != Items.LAPIS_LAZULI){
				currUImenu.getSlots().get(3).set(ItemStack.EMPTY);
				prevGUISlotState = enchMaterial+"|"+toolToEnch.toString()+" "+toolToEnch.getTagEnchantments().toString()+"|"+currUImenu.getSlots().get(1).getItem();
				plyr.containerMenu.broadcastChanges();
				return;
			}
			if (prevGUISlotState.equals(enchMaterial+"|"+toolToEnch.toString()+" "+toolToEnch.getTagEnchantments().toString()+"|"+currUImenu.getSlots().get(1).getItem())){
				return;
			}
			
			ResourceKey outputEnchantment;
			if (enchMaterial == ItemStack.EMPTY){
				if (plyr.experienceLevel >= 5){
					outputEnchantment = Enchantments.MENDING;
				} else {
					outputEnchantment = null;
				}
			} else {
				/*if (plyr instanceof ServerPlayer srvrPlr){ // testing
					srvrPlr.sendSystemMessage(Component.literal("item name="+enchMaterial.getItem().toString()));
				}*/
				outputEnchantment = getEnchantStringFromMat(enchMaterial);
			}

			boolean enchMatch = false;
			
			if (outputEnchantment != null){
				String outputEnchantmentStr = outputEnchantment.toString().substring(outputEnchantment.toString().indexOf("enchantment / "),outputEnchantment.toString().indexOf("]"));
				outputEnchantmentStr = outputEnchantmentStr.substring(outputEnchantmentStr.indexOf(":")+1);
				BlockPos machinePos = BlockPos.containing(x, y, z);
				BlockEntity machineEntity = world.getBlockEntity(machinePos);

				if (machineEntity == null){
					System.out.println("ERROR! machine entity not found at x="+x+"y="+y+"z="+z);
					return;
				}
			
				for (String currUnlockedEnchData : enchantUnlocksArray){
					// converting the data into a string of the enchantment name and int of the level
					String unlkdEnchName = currUnlockedEnchData.substring(0, currUnlockedEnchData.indexOf(":")).substring(12).replace(" ","_").toLowerCase();
					int unlkdEnchLvl = (int) Math.floor(Double.parseDouble(currUnlockedEnchData.substring(currUnlockedEnchData.indexOf(":")+1)));

					// check that the ench to use is in the unlocked player list and that there are enough materials to do the process
					if (unlkdEnchName.equals(outputEnchantmentStr) && unlkdEnchLvl > 0){
						// creates a new output item stack and converts the enchantment to apply into a Holder type
						ItemStack outputStack = toolToEnch.copy();
						Holder<Enchantment> outEnchHolder;
						try {
							outEnchHolder = world.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(outputEnchantment);
						} catch (Exception e){
							System.out.println("Provided enchantment was not found outEnchHolder="+world.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(outputEnchantment));
							System.out.println("ERROR! Exception="+e);
							return;
						}
						
						// hehe
						String adarMind = outputStack.getDisplayName().getString().toLowerCase();
						adarMind = adarMind.replaceAll("'| ", "");

						String existingEnchString = outputStack.getTagEnchantments().toString();
						int existingEnchLvl = 0;
						if (existingEnchString.contains(outputEnchantmentStr)){
							String tempTestStr = currUnlockedEnchData.substring(0, currUnlockedEnchData.indexOf(":"));
							try {
								existingEnchLvl = Integer.parseInt(existingEnchString.substring(existingEnchString.indexOf(tempTestStr)+tempTestStr.length()+3, existingEnchString.indexOf(tempTestStr)+tempTestStr.length()+4));
							} catch (Exception e){
								System.out.println("ERROR! invalid Enchantment level int for string: "+existingEnchString.substring(existingEnchString.indexOf(tempTestStr)+tempTestStr.length()+3, existingEnchString.indexOf(tempTestStr)+tempTestStr.length()+4));
								return;
							}
						}
						// does tons of checks that the item can be enchanted and that it has compatability
						// or funnis
						if ( 
							(outputStack.getItem() == Items.PUFFERFISH && adarMind.equals("[adamsbrain]"))
							|| (
								outputStack.has(DataComponents.ENCHANTABLE)
								&& (
									   outputStack.getTagEnchantments().isEmpty() 
									|| enchCompatibleFromSet(outputStack.getTagEnchantments().keySet(), outEnchHolder)
									|| outputStack.getTagEnchantments().toString().contains(outputEnchantmentStr)
								)
							) ){
							// after that debarkle
							// determins the output enchantment level
							int outputEnchLvl = 1;
							if (enchMaterial != ItemStack.EMPTY){
								outputEnchLvl = (int) Math.min(unlkdEnchLvl, Math.min(existingEnchLvl+enchMaterial.getCount(), existingEnchLvl+lapisCount));
							}
							
							// actually does the thing and outputs it
							outputStack.enchant(outEnchHolder, outputEnchLvl);
							currUImenu.getSlots().get(3).set(outputStack);

							// sends data to block and confirms it worked
							machineEntity.getPersistentData().putInt("enchCost", outputEnchLvl);
							enchMatch = true;
						}
					}
				}
			} else {
				System.out.println("Warning! invalid enchantment material: "+enchMaterial);
			}

			// no match found so output nothing
			if (!enchMatch){
				currUImenu.getSlots().get(3).set(ItemStack.EMPTY);
			}

			prevGUISlotState = enchMaterial+"|"+toolToEnch.toString()+" "+toolToEnch.getTagEnchantments().toString()+"|"+currUImenu.getSlots().get(1).getItem();
			
			plyr.containerMenu.broadcastChanges();
		}
	}

	// spoilers
	public static ResourceKey getEnchantStringFromMat(ItemStack currMaterial){
		Item matItem = currMaterial.getItem();
		
		if (matItem == Items.BLAZE_POWDER){
			return Enchantments.FIRE_ASPECT;
		}
		if (matItem == Items.FEATHER){
			return Enchantments.FEATHER_FALLING;
		}
		if (matItem == Items.CACTUS){
			return Enchantments.THORNS;
		}
		if (matItem == Items.PRISMARINE_CRYSTALS){
			return Enchantments.AQUA_AFFINITY;
		}
		if (matItem == Items.DIORITE){
			return Enchantments.BANE_OF_ARTHROPODS;
		}
		if (matItem == Items.COBBLESTONE){
			return Enchantments.BREACH;
		}
		if (matItem == Items.OBSIDIAN){
			return Enchantments.UNBREAKING;
		}
		if (matItem == Items.WHITE_WOOL){
			return Enchantments.BLAST_PROTECTION;
		}
		if (matItem == Items.LIGHTNING_ROD){
			return Enchantments.CHANNELING;
		}
		if (matItem == Items.PRISMARINE_SHARD){
			return Enchantments.DEPTH_STRIDER;
		}
		if (matItem == Items.FLINT_AND_STEEL){
			return Enchantments.FLAME;
		}
		if (matItem == Items.PUFFERFISH){
			return Enchantments.IMPALING;
		}
		if (matItem == Items.GHAST_TEAR){
			return Enchantments.INFINITY;
		}
		if (matItem == Items.PISTON){
			return Enchantments.KNOCKBACK;
		}
		if (matItem == Items.LODESTONE){
			return Enchantments.LUCK_OF_THE_SEA;
		}
		if (matItem == Items.ROTTEN_FLESH){
			return Enchantments.LURE;
		}
		if (matItem == Items.STRING){
			return Enchantments.MULTISHOT;
		}
		if (matItem == Items.BAMBOO){
			return Enchantments.PIERCING;
		}
		if (matItem == Items.SHIELD){
			return Enchantments.PROJECTILE_PROTECTION;
		}
		if (matItem == Items.SLIME_BALL){
			return Enchantments.PUNCH;
		}
		if (matItem == Items.REPEATER){
			return Enchantments.QUICK_CHARGE;
		}
		if (matItem == Items.BUCKET){
			return Enchantments.RESPIRATION;
		}
		if (matItem == Items.HONEY_BLOCK){
			return Enchantments.SILK_TOUCH;
		}
		if (matItem == Items.COD_BUCKET || matItem == Items.SALMON_BUCKET){
			return Enchantments.SMITE;
		}
		if (matItem == Items.PACKED_ICE){
			return Enchantments.SOUL_SPEED;
		}
		if (matItem == Items.PHANTOM_MEMBRANE){
			return Enchantments.SWEEPING_EDGE;
		}
		if (matItem == Items.LEATHER_LEGGINGS){
			return Enchantments.SWIFT_SNEAK;
		}
		if (matItem == Items.DIAMOND_BLOCK){
			return Enchantments.FORTUNE;
		}
		if (matItem == Items.TOTEM_OF_UNDYING){
			return Enchantments.LOYALTY;
		}
		if (matItem == Items.WIND_CHARGE){
			return Enchantments.WIND_BURST;
		}
		if (matItem == Items.TNT){
			return Enchantments.LOOTING;
		}
		if (matItem == Items.IRON_BLOCK){
			return Enchantments.SHARPNESS;
		}
		if (matItem == Items.NETHERITE_SCRAP){
			return Enchantments.PROTECTION;
		}
		if (matItem == Items.REDSTONE_BLOCK){
			return Enchantments.EFFICIENCY;
		}
		if (matItem.toString().equals("convenient_extras:premium_ingot")){
			return Enchantments.POWER;
		}
		if (matItem.toString().equals("convenient_extras:hydropi_ingot")){
			return Enchantments.RIPTIDE;
		}
		if (matItem.toString().equals("convenient_extras:fridge")){
			return Enchantments.FROST_WALKER;
		}
		if (matItem.toString().equals("convenient_extras:tungsten_ingot")){
			return Enchantments.DENSITY;
		}
		if (matItem == ConvenientAdditionsModItems.FIRE_EXTINGUISHER.asItem()){
			return Enchantments.FIRE_PROTECTION;
		}
		if (matItem == Items.COAL){
			return ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.parse("convenient_additions:dwarvern_instincts"));
		}
		if (matItem == ConvenientAdditionsModItems.LEGO_GUY.asItem()){
			return ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.parse("convenient_additions:infusion"));
		}
		
		return null;
	}

	// runs the compatible check built in function on each enchantment in a set
	public static boolean enchCompatibleFromSet(Set<Holder<Enchantment>> enchantmentSet, Holder<Enchantment> currEnch){
		Iterator<Holder<Enchantment>> existingItemEnchsIterator = enchantmentSet.iterator();

		while (existingItemEnchsIterator.hasNext()) {
			if (!Enchantment.areCompatible(currEnch, existingItemEnchsIterator.next())){
				return false;
			}
		}
		
		return true;
	}
}
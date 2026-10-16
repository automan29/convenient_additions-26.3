package automandza.convenientadditions.procedures;

//imports
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.BlockPos;

import automandza.convenientadditions.init.ConvenientAdditionsModMenus;
import automandza.convenientadditions.init.ConvenientAdditionsModGameRules;

import java.util.Arrays;
import java.util.ArrayList;
import net.minecraft.server.level.ServerLevel;




public class EnchResearchTickProcedure{

	// Required for xp cost number display in the GUI when inserting an item
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		BlockPos resTablePos = BlockPos.containing(x, y, z);
		BlockEntity resTableEntity = world.getBlockEntity(resTablePos);
		BlockState resTableState = world.getBlockState(resTablePos);
		int expCostOutput = 0;
		
		// Checks that the block entity for the table is not null and a player is opening the menu and then gets the menu
		if (entity instanceof Player plyr && plyr.containerMenu instanceof ConvenientAdditionsModMenus.MenuAccessor menu0 && initTestBool(world, entity, resTableEntity)){
			// Gets the unlocked enchantment string list from the player
			String fullUnlockedEnchStr = entity.getPersistentData().getStringOr("unlockedEnchantList", "hard luck");
			// Gets the itemStack from the only item slot
			ItemStack inputIStack = menu0.getSlots().get(0).getItem();
			
			if (inputIStack != ItemStack.EMPTY){
				String fullComponentString = ""+inputIStack.getComponents();
				
				if (fullComponentString.contains("minecraft:enchantment /")) {
					// Converts the full component string of the item into just the enchantment section
					String tempCompString = fullComponentString.substring(fullComponentString.indexOf("minecraft:enchantment /"));
					String enchantsString = tempCompString.substring(24,tempCompString.indexOf("}}"));

					try {
						expCostOutput = getXpCostFromEnch(world, enchantsString, fullUnlockedEnchStr);
					} catch (Exception e){
						plyr.displayClientMessage(Component.literal("Oi! Stop trying to break the game! >:("), false);
						menu0.getSlots().get(0).set(ItemStack.EMPTY);
						return;
					}
				}
				
			}
            assert resTableEntity != null;
            resTableEntity.getPersistentData().putDouble("xpCost", expCostOutput);
			
			// Send updates to player and block
			plyr.containerMenu.broadcastChanges();
			if (world instanceof Level _level){
				_level.sendBlockUpdated(resTablePos, resTableState, resTableState, 3);
			}
		}
	}

	public static void pressTestButton(LevelAccessor world, double x, double y, double z, Entity entity){
		BlockPos resTablePos = BlockPos.containing(x, y, z);
		BlockEntity resTableEntity = world.getBlockEntity(resTablePos);
		BlockState resTableState = world.getBlockState(resTablePos);
		
		if (entity instanceof Player plyr && plyr.containerMenu instanceof ConvenientAdditionsModMenus.MenuAccessor menu1 && initTestBool(world, entity, resTableEntity)){
			// Get existing ench list from player and item input
			String[] foundUnlockedEnchList = entity.getPersistentData().getStringOr("unlockedEnchantList", "hard luck").split(",");
			String enchUnlocksOutputStr;
			ArrayList<String> unlocksOutputList = new ArrayList<>(Arrays.asList(foundUnlockedEnchList));
			ItemStack inputIStack = menu1.getSlots().get(0).getItem();

			if (inputIStack != ItemStack.EMPTY){
				String fullComponentString = ""+inputIStack.getComponents();
				if (fullComponentString.contains("minecraft:enchantment /")) {
					// Check for cheaters
					if (inputIStack.getDisplayName().toString().contains("minecraft:enchantment /")){
						plyr.displayClientMessage(Component.literal("You filthy rotten cheater! No cheating in free stuff!"), false);
						return;
					}
					// Extract enchantment details from long string
					String tempCompString = fullComponentString.substring(fullComponentString.indexOf("minecraft:enchantment /"));
					String enchantsString = tempCompString.substring(24,tempCompString.indexOf("}}"));

					String[] itemEnchList = enchantsString.split(",");

					// for each enchantment on inserted item
                    StringBuilder enchUnlocksOutputStrBuilder = new StringBuilder(entity.getPersistentData().getStringOr("unlockedEnchantList", "hard luck"));
                    for (String currItemEnchFull : itemEnchList){
						// get ench name
						String foundEnchName = currItemEnchFull.substring(currItemEnchFull.indexOf("Enchantment "), currItemEnchFull.length()-4);
						// get ench lvl
						int foundEnchLvl = Integer.parseInt(currItemEnchFull.substring(currItemEnchFull.length()-1));

						// checks if player has unlocks
						if (!foundUnlockedEnchList[0].equals("hard luck")){
							boolean currEnchUnlocked = false;
							int i = 0;
							for (String currUlkdEnch : foundUnlockedEnchList){
								String foundUnlkdEnName = currUlkdEnch.substring(0, currUlkdEnch.indexOf(":"));
								double foundUnlkdEnVal = Double.parseDouble(currUlkdEnch.substring(currUlkdEnch.indexOf(":")+1));

								if (foundEnchName.equals(foundUnlkdEnName)){
									// upgrade player unlocked enchantment
									// each entry structure - <name>:<level>,
									unlocksOutputList.set(i, foundUnlkdEnName+":"+Math.min(getUnlockedValueOfEnchLvl(world, foundUnlkdEnVal, foundEnchLvl), convertEnchantmentStrToMaxLvl(foundUnlkdEnName)));
									currEnchUnlocked = true;
								}

								i++;
							}
							if (!currEnchUnlocked){
								// player unlocked list doesn't have current enchantment
								unlocksOutputList.add(foundEnchName+":"+getUnlockedValueOfEnchLvl(world, 0d, foundEnchLvl));
							}
						} else {
							// player unlocked list is empty
							// skip lists and go straight for the end string
							if (enchUnlocksOutputStrBuilder.toString().equals("hard luck")){
								// end string is empty
								enchUnlocksOutputStrBuilder = new StringBuilder(foundEnchName + ":" + getUnlockedValueOfEnchLvl(world, 0d, foundEnchLvl));
							} else {
								// end string is not empty
								enchUnlocksOutputStrBuilder.append(",").append(foundEnchName).append(":").append(getUnlockedValueOfEnchLvl(world, 0d, foundEnchLvl));
							}
						}
					}
                    enchUnlocksOutputStr = enchUnlocksOutputStrBuilder.toString();

                    // Converts unlocksOutputList to string
					if (!foundUnlockedEnchList[0].equals("hard luck")){
						StringBuilder str = new StringBuilder();
						// Traversing the ArrayList
						for (String eachstring : unlocksOutputList) {
							// Each element in ArrayList is appended
							// followed by comma
							str.append(eachstring).append(",");
						}
						// StringBuffer to String conversion
						String commaseparatedlist = str.toString();
						// Condition check to remove the last comma
						if (!commaseparatedlist.isEmpty()){
							enchUnlocksOutputStr = commaseparatedlist.substring(0, commaseparatedlist.length() - 1);
						}
					}

					
					// output string not empty
					if (!enchUnlocksOutputStr.equals("hard luck")){
						entity.getPersistentData().putString("unlockedEnchantList", enchUnlocksOutputStr);
						if ((int) getBlockNBTNumber(world, resTablePos) > -1) {
							plyr.giveExperienceLevels((int) -getBlockNBTNumber(world, resTablePos));
							menu1.getSlots().get(0).set(ItemStack.EMPTY);
						}
					} else {
						System.out.println("AH HA! There is no such thing as 'Enchantments'!");
					}

					// Send updates to player and block
					plyr.containerMenu.broadcastChanges();
					if (world instanceof Level _level){
						_level.sendBlockUpdated(resTablePos, resTableState, resTableState, 3);
					}
				}
			}
		}
	}


	// takes both full item enchantments and unlocked enchantments lists as strings
	// returns the XP amount required to unlock
	public static int getXpCostFromEnch(LevelAccessor world, String currEnchStr, String plrUnlockedList){
		String[] itemJumbledList = currEnchStr.split(",");
		String[] unlockedEnchList = plrUnlockedList.split(",");
		int outputExpVal = 0;
		double expMult = 2d;

		if (world instanceof ServerLevel srvLvl){
			expMult = srvLvl.getGameRules().getInt(ConvenientAdditionsModGameRules.ENCHANTING_SCIENCE_RESEARCH_DIFFICULTY);
		}
		
		for (String itemJumbledData : itemJumbledList){
			// for item input
			String itemEnchantName = itemJumbledData.substring(itemJumbledData.indexOf("Enchantment "), itemJumbledData.length()-4);
			int itemEnchantLvl = Integer.parseInt(itemJumbledData.substring(itemJumbledData.length()-1));

			if (plrUnlockedList.equals("hard luck")){
				// unlocked is empty
				if (getUnlockedValueOfEnchLvl(world, 0d, itemEnchantLvl) > 1){
					outputExpVal += (int) Math.ceil(getUnlockedValueOfEnchLvl(world, 0d, itemEnchantLvl)*expMult);
				}
			} else {
				// unlocked not empty
				boolean itemEnchIsUnlocked = false;
				for (String unlockedEnchData : unlockedEnchList){
					String unlkdEnchName = unlockedEnchData.substring(0, unlockedEnchData.indexOf(":"));
					double unlkdEnchVal = Double.parseDouble(unlockedEnchData.substring(unlockedEnchData.indexOf(":")+1));

					if (unlkdEnchName.equals(itemEnchantName)){
						// discount XP cost if already at maximum level or disallow
						if (convertEnchantmentStrToMaxLvl(unlkdEnchName) <= unlkdEnchVal){
							outputExpVal--;
						} else if (getUnlockedValueOfEnchLvl(world, unlkdEnchVal, itemEnchantLvl) > 1){
							outputExpVal += (int) Math.ceil((getUnlockedValueOfEnchLvl(world, unlkdEnchVal, itemEnchantLvl)-unlkdEnchVal)*expMult);
						}
						itemEnchIsUnlocked = true;
					}
				}
				if (!itemEnchIsUnlocked && getUnlockedValueOfEnchLvl(world, 0d, itemEnchantLvl) > 1){
					outputExpVal += (int) Math.ceil(getUnlockedValueOfEnchLvl(world, 0d, itemEnchantLvl)*expMult);
				}
			}
		}

		return outputExpVal;
	}

	// for converting the level stated on an enchantment to its research value
	public static double getUnlockedValueOfEnchLvl(LevelAccessor world, double existingVal, int enchLvlInt){
        double costMult = 2d;

		if (world instanceof ServerLevel srvLvl){
			costMult = srvLvl.getGameRules().getInt(ConvenientAdditionsModGameRules.ENCHANTING_SCIENCE_RESEARCH_DIFFICULTY);
		}
		
		if (existingVal == 0d){
			if (enchLvlInt <= costMult){
				return (double) enchLvlInt /costMult;
			}
			if (enchLvlInt <= costMult+(costMult*2d)){
				return 1+(((double) enchLvlInt -costMult)/(costMult*2d));
			}
			return 2;
		}

		double goldenRatioBankBill = (double) enchLvlInt /(Math.ceil(existingVal)*costMult);
		
		if (existingVal + goldenRatioBankBill > Math.floor(existingVal) + 1d){
			// Works out remaining value of inputted enchantment level ... IDK tbh
			double remainingLvlVal = (goldenRatioBankBill-(1-(existingVal - Math.floor(existingVal))))*((Math.floor(existingVal)+1d)*costMult);
			double ratio = remainingLvlVal/((Math.floor(existingVal)+2d)*costMult);
			double L = Math.floor(existingVal) + 1d;
			// true ending
			return L+ratio;
		}

		if (existingVal + goldenRatioBankBill >= Math.floor(existingVal) + 0.998d){
			return Math.floor(existingVal) + 1d;
		}
		
		return existingVal + goldenRatioBankBill;
	}

	// returns the max level of the enchantment string input
	public static int convertEnchantmentStrToMaxLvl(String inputEnchant){
		if(inputEnchant.contains(" ")){
			inputEnchant = inputEnchant.replace(" ","_").replaceFirst(".*:","").replace("Enchantment_","").toLowerCase();
		}
        return switch (inputEnchant) {
            case "aqua_affinity", "channeling", "flame", "infinity", "mending", "multishot", "silk_touch" -> 1;
            case "fire_aspect", "frost_walker", "knockback", "punch" -> 2;
            case "depth_strider", "fortune", "looting", "loyalty", "luck_of_the_sea", "lunge", "lure", "quick_charge", "respiration", "riptide", "soul_speed", "sweeping_edge", "swift_sneak", "thorns", "unbreaking", "wind_burst", "dwarvern_instincts" -> 3;
            case "blast_protection", "fire_protection", "breach", "feather_falling", "piercing", "projectile_protection" -> 4;
            case "bane_of_arthropods", "density", "efficiency", "impaling", "power", "protection", "sharpness", "smite", "infusion" -> 5;
            default -> 225;
        };
	}

	// called on starting any void function in this class
	private static boolean initTestBool(LevelAccessor lvlAcc, Entity ent, BlockEntity tblEnt){
		return ent != null && !lvlAcc.isClientSide() && tblEnt != null;
	}


	// mcreator generated functions
	private static double getBlockNBTNumber(LevelAccessor world, BlockPos pos) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity != null)
			return blockEntity.getPersistentData().getDoubleOr("xpCost", 0);
		return -1;
	}
}
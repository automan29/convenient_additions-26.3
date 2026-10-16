package automandza.convenientadditions.item;

//imports
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.tags.BlockTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

import java.util.stream.Stream;
import java.util.List;
import java.io.Console;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.server.level.ServerLevel;
import javax.annotation.Nullable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.extensions.IItemExtension;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.core.Holder;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundSource;
import automandza.convenientadditions.init.ConvenientAdditionsModBlocks;
import net.minecraft.world.InteractionHand;
import java.util.Random;


public class StoneDrillItem extends Item {
	public boolean drillHasCoal;
	public float drillFuelCount;
	public float maxDrillFuelCount = 75;
	public int blocksBroken;
	public int dwarvernFuryValue = 0;
	public int drillSoundEffectTimer;
	public String currentDrillSoundPlaying = "drill_idle";
	private boolean drillSoundsActive = true;
	
	public StoneDrillItem(Item.Properties properties) {
		// freaking stupid properties
		super(properties.fireResistant().attributes(ItemAttributeModifiers.builder().add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, 3, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
				.add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, -3, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).build()).stacksTo(1).enchantable(12));
	}

	
	@Override
	public float getDestroySpeed(ItemStack itemstack, BlockState blockstate) {
		// sets how fast drill can break specific blocks
		if (Stream.of(BlockTags.create(ResourceLocation.parse("minecraft:planks"))).anyMatch(blockstate::is)) return 1f;

		// disables drill
		if (itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("currDrillFuelFloat", 0) <= 0) return 0f;
		if (!customExcludesBlockstate(blockstate)) return 0f;

		// default
		return 11f;
	}
	

	@Override
	public boolean mineBlock(ItemStack itemstack, Level world, BlockState blockstate, BlockPos pos, LivingEntity entity) {
		// sets up the NBT stuff
		if (itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("maxDrillFuelFloat", 65d) < maxDrillFuelCount){
			CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putDouble("maxDrillFuelFloat", maxDrillFuelCount));
		}
		drillFuelCount = (float)itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("currDrillFuelFloat", 0);

		blocksBroken++;

		// checks current enchants
		ItemEnchantments currEnchants = itemstack.getTagEnchantments();
		String currEnchStr = currEnchants.toString();

		// do dwarvern enchant things
		if (List.of(Blocks.COAL_ORE, Blocks.DEEPSLATE_COAL_ORE).contains(blockstate.getBlock()) && currEnchStr.contains("Enchantment Dwarvern Instincts")){
			dwarvernFuryValue++;
			
			if (!world.isClientSide()) {
				world.playSound(null, BlockPos.containing(pos.getX(), pos.getY(), pos.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("block.note_block.iron_xylophone")), SoundSource.BLOCKS, 0.8f, (dwarvernFuryValue+1)/5f);
				if (dwarvernFuryValue >= 5f) world.playSound(null, BlockPos.containing(pos.getX(), pos.getY(), pos.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("convenient_additions:dwarf_activate")), SoundSource.BLOCKS, 2f, 1f);
			} else {
				world.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("block.note_block.iron_xylophone")), SoundSource.BLOCKS, 0.8f, (dwarvernFuryValue+1)/5f, false);
			}
			dwarvernInstEffects(entity, Integer.parseInt(currEnchStr.substring(currEnchStr.indexOf("Enchantment Dwarvern Instincts")+33, currEnchStr.indexOf("Enchantment Dwarvern Instincts")+34)));
		}

		// do infusion enchant things
		boolean numbyGoUp = false;
		if (currEnchStr.contains("Enchantment Infusion")){
			int enchLvl = Integer.parseInt(currEnchStr.substring(currEnchStr.indexOf("Enchantment Infusion")+23, currEnchStr.indexOf("Enchantment Infusion")+24));
			System.out.println("enchLvl="+enchLvl);
			
			switch (enchLvl) {
				case 1:
					numbyGoUp = blocksBroken >= 500;
					break;
				case 2:
					numbyGoUp = blocksBroken >= 400;
					break;
				case 3:
					numbyGoUp = blocksBroken >= 300;
					break;
				case 4:
					numbyGoUp = blocksBroken >= 200;
					break;
				case 5:
					numbyGoUp = blocksBroken >= 100;
					break;
				default:
					System.out.println("ERROR! enchant value out of bounds");
			}
			// numby go up
			if (numbyGoUp){
				CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putDouble("maxDrillFuelFloat", itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("maxDrillFuelFloat", 75d)+1));
				blocksBroken = 0;
			}
		}

		// adds fuel cost for specific blocks (do this last)
		if (Stream.of(BlockTags.create(ResourceLocation.parse("c:flowers"))).anyMatch(blockstate::is)
			|| Stream.of(BlockTags.create(ResourceLocation.parse("minecraft:leaves"))).anyMatch(blockstate::is)
			|| Stream.of(BlockTags.create(ResourceLocation.parse("minecraft:snow"))).anyMatch(blockstate::is)) {
			drillFuelCount -= 0.25f;
		} else if (Stream.of(BlockTags.create(ResourceLocation.parse("c:gravels"))).anyMatch(blockstate::is)
			|| Stream.of(BlockTags.create(ResourceLocation.parse("c:sands"))).anyMatch(blockstate::is)
			|| Stream.of(BlockTags.create(ResourceLocation.parse("minecraft:dirt"))).anyMatch(blockstate::is)
			|| Stream.of(BlockTags.create(ResourceLocation.parse("minecraft:ice"))).anyMatch(blockstate::is)){
			drillFuelCount -= 0.5f;
		} else if (Stream.of(BlockTags.create(ResourceLocation.parse("c:netherracks"))).anyMatch(blockstate::is)){
			drillFuelCount -= 0.75f;
		} else {
			drillFuelCount -= 1f;
		}

		CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putDouble("currDrillFuelFloat", drillFuelCount));
		return drillFuelCount > -1;
	}
	
	

	// sets usable enchantments
	@Override
	public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
		String enchString = enchantment.toString();
		if (enchString.contains("Enchantment Efficiency") || enchString.contains("Enchantment Infusion") || enchString.contains("Enchantment Dwarvern Instincts")){
			System.out.println("enchant string= "+enchantment.toString());
			return true;
		}
		
        return false;
    }

	// does Dwarvern Instincts enchantment effects
	public void dwarvernInstEffects(LivingEntity lvEntity, int enchantLvl){
		if (dwarvernFuryValue >= 5){
			lvEntity.addEffect(new MobEffectInstance(MobEffects.HASTE, 200+(enchantLvl*200), enchantLvl));
			dwarvernFuryValue = 0;
		}
	}

	

	
	// called every tick
	@Override
	public void inventoryTick(ItemStack iStack, ServerLevel svrLvl, Entity enti, @Nullable EquipmentSlot equipSlot) {
		// check if drill is empty
		if (iStack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("currDrillFuelFloat", 0) < 1){
			int coalSlot = -99;
			coalSlot = getCoalSlotInInv(enti);
			drillHasCoal = coalSlot != -99;
		
			if (drillHasCoal) takeDrillCoal(coalSlot, enti, iStack);
		}
		
		// play drill idle sound
		if (enti instanceof LivingEntity lvingEnt){
			drillSoundsActive = lvingEnt.getPersistentData().getBooleanOr("canHearDrill", true);
			if (drillSoundsActive){
				if (lvingEnt.getMainHandItem() == iStack && iStack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("currDrillFuelFloat", 0) > 0 && drillSoundEffectTimer < lvingEnt.tickCount){
					Level world = lvingEnt.level();
					if (!world.isClientSide()) {
						if (drillSoundEffectTimer+40 < lvingEnt.tickCount) {
							world.playSound(null, BlockPos.containing(lvingEnt.getX(), lvingEnt.getY(), lvingEnt.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("convenient_additions:drill_startup")), SoundSource.PLAYERS, 1f, 1f);
							currentDrillSoundPlaying = "drill_startup";
							drillSoundEffectTimer = lvingEnt.tickCount+35;
						} else {
							world.playSound(null, BlockPos.containing(lvingEnt.getX(), lvingEnt.getY(), lvingEnt.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("convenient_additions:drill_idle")), SoundSource.PLAYERS, 0.6f, 1f);
							currentDrillSoundPlaying = "drill_idle";
							drillSoundEffectTimer = lvingEnt.tickCount+5;
						}
					} else {
						if (drillSoundEffectTimer+45 < lvingEnt.tickCount) {
							world.playLocalSound(lvingEnt.getX(), lvingEnt.getY(), lvingEnt.getZ(), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("convenient_additions:drill_startup")), SoundSource.PLAYERS, 1f, 1f, false);
							currentDrillSoundPlaying = "drill_startup";
							drillSoundEffectTimer = lvingEnt.tickCount+35;
						} else {
							world.playLocalSound(lvingEnt.getX(), lvingEnt.getY(), lvingEnt.getZ(), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("convenient_additions:drill_idle")), SoundSource.PLAYERS, 0.6f, 1f, false);
							currentDrillSoundPlaying = "drill_idle";
							drillSoundEffectTimer = lvingEnt.tickCount+5;
						}
					}
				}
			}
		}
    }

    

	// returns the first slot where charcoal is found, if not then where coal is found
	public int getCoalSlotInInv(Entity enti){
		if(enti instanceof Player playr){
			Inventory plrInv = playr.getInventory();
			if(plrInv.contains(new ItemStack(Items.CHARCOAL))){
				return plrInv.findSlotMatchingItem(new ItemStack(Items.CHARCOAL));
			} else if (plrInv.contains(new ItemStack(Items.COAL))){
				return plrInv.findSlotMatchingItem(new ItemStack(Items.COAL));
			}
		}
		
		return -99;
	}

	// refuels drill and removes 1 piece of charcoal or coal
	public void takeDrillCoal(int coalSlot, Entity enti, ItemStack itemstack){
		if(enti instanceof Player playr){
			Inventory plrInv = playr.getInventory();
			ItemStack coalStack = plrInv.getItem(coalSlot);
			double drillFuelNBT = itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("maxDrillFuelFloat", 75d);

			if (drillFuelNBT > 70){
				drillFuelCount = (float)drillFuelNBT;
			} else {
				drillFuelCount = maxDrillFuelCount;
			}
			
			CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putDouble("currDrillFuelFloat", drillFuelCount));
			coalStack.shrink(1);
			
		} else {
			System.out.println("ERROR no fuel found");
		}
	}


	// get function for external code
	public float getDrillFuelPercentage(ItemStack itemstack){
		return ((float)itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("currDrillFuelFloat", 0)) / ((float)itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("maxDrillFuelFloat", 75d));
	}




	// this section determins what blocks the drill can break
	@Override
	public boolean isCorrectToolForDrops(ItemStack itemstack, BlockState blockstate) {
		return customExcludesBlockstate(blockstate);
	}

	
	public boolean customExcludesBlockstate(BlockState blockstate){
		//System.out.println("block="+blockstate.getBlock());
		
		return !(List.of(Blocks.SPAWNER, Blocks.AMETHYST_BLOCK, Blocks.TRIAL_SPAWNER, Blocks.SCULK_CATALYST, Blocks.SCULK_SENSOR, Blocks.SCULK_SHRIEKER, Blocks.BUDDING_AMETHYST, ConvenientAdditionsModBlocks.ARM_ORE_BLOCK.get()).contains(blockstate.getBlock())
				|| Stream.of(BlockTags.create(ResourceLocation.parse("c:obsidians"))).anyMatch(blockstate::is)
				|| Stream.of(BlockTags.create(ResourceLocation.parse("c:storage_blocks"))).anyMatch(blockstate::is)
				|| Stream.of(BlockTags.create(ResourceLocation.parse("minecraft:logs"))).anyMatch(blockstate::is)
				|| Stream.of(BlockTags.create(ResourceLocation.parse("c:chests"))).anyMatch(blockstate::is)
				|| (Stream.of(BlockTags.create(ResourceLocation.parse("c:ores"))).anyMatch(blockstate::is) && !Stream.of(BlockTags.create(ResourceLocation.parse("c:ores/coal"))).anyMatch(blockstate::is)));
	}



	// sounds
	@Override
	public boolean onEntitySwing(ItemStack stack, LivingEntity entity, InteractionHand hand){
		if (drillSoundsActive){
			Level world = entity.level();
			
			if (drillSoundEffectTimer < entity.tickCount || currentDrillSoundPlaying.equals("drill_idle")){
				if (!world.isClientSide()) {
					world.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("convenient_additions:drill_use")), SoundSource.PLAYERS, 0.8f, 1f);
				} else {
					world.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("convenient_additions:drill_use")), SoundSource.PLAYERS, 0.8f, 1f, false);
				}
				currentDrillSoundPlaying = "drill_use";
				drillSoundEffectTimer = entity.tickCount+10;
			}
			
		}
		return false;
	}
	
}
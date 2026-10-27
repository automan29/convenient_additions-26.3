package automandza.convenient_additions.item;

//imports
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
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
import net.minecraft.resources.Identifier;
import net.minecraft.core.BlockPos;

import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Stream;
import java.util.List;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.server.level.ServerLevel;
import javax.annotation.Nullable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.core.Holder;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundSource;
import automandza.convenient_additions.init.ConvenientAdditionsModBlocks;
import net.minecraft.world.InteractionHand;
import org.jspecify.annotations.NonNull;


public class StoneDrillItem extends Item {
	public StoneDrillItem(Item.Properties properties) {
		// freaking stupid properties
		super(properties.fireResistant().attributes(ItemAttributeModifiers.builder().add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, 3, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
				.add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, -3, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).build()).stacksTo(1).enchantable(12));
	}

	
	@Override
	public float getDestroySpeed(@NonNull ItemStack itemstack, BlockState blockstate) {
		// sets how fast drill can break specific blocks
		if (Stream.of(BlockTags.create(Identifier.parse("minecraft:planks"))).anyMatch(blockstate::is)) return 1f;

		// disables drill
		if (itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("currDrillFuelFloat", 0) <= 0) return 0f;
		if (!customExcludesBlockstate(blockstate)) return 0f;

		// default
		return 11f;
	}
	

	@Override
	public boolean mineBlock(ItemStack itemstack, @NonNull Level world, @NonNull BlockState blockstate, @NonNull BlockPos pos, @NonNull LivingEntity entity) {
		if (itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY) == CustomData.EMPTY){
			System.out.println("ERROR! cannot find custom data on itemstack="+itemstack);
			return false;
		}

		// sets up the NBT stuff
		assert itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY) != CustomData.EMPTY; // good one IntelliJ
		int dwarvernFuryValue = itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getIntOr("dwarfFuryVal", 0);
		float drillFuelCount = (float)itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("currDrillFuelFloat", 0);
		int blocksBroken = 1 + itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getIntOr("blocksBroken", 0);

		// checks current enchants
		ItemEnchantments currEnchants = itemstack.getTagEnchantments();
		String currEnchStr = currEnchants.toString();

		// do dwarvern enchant things
		if (List.of(Blocks.COAL_ORE, Blocks.DEEPSLATE_COAL_ORE).contains(blockstate.getBlock()) && currEnchStr.contains("Enchantment Dwarvern Instincts")){
			dwarvernFuryValue++;
			
			if (!world.isClientSide()) {
				world.playSound(null, BlockPos.containing(pos.getX(), pos.getY(), pos.getZ()), Objects.requireNonNull(BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.note_block.iron_xylophone"))), SoundSource.BLOCKS, 0.8f, (dwarvernFuryValue+1)/5f);
				if (dwarvernFuryValue >= 5f) world.playSound(null, BlockPos.containing(pos.getX(), pos.getY(), pos.getZ()), Objects.requireNonNull(BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("convenient_additions:dwarf_activate"))), SoundSource.BLOCKS, 2f, 1f);
			} else {
				world.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), Objects.requireNonNull(BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.note_block.iron_xylophone"))), SoundSource.BLOCKS, 0.8f, (dwarvernFuryValue+1)/5f, false);
			}
			dwarvernFuryValue = dwarvernInstEffects(entity, Integer.parseInt(currEnchStr.substring(currEnchStr.indexOf("Enchantment Dwarvern Instincts")+33, currEnchStr.indexOf("Enchantment Dwarvern Instincts")+34)), dwarvernFuryValue);
		}

		// do infusion enchant things
		boolean numbyGoUp = false;
		if (currEnchStr.contains("Enchantment Infusion")){
			int enchLvl = Integer.parseInt(currEnchStr.substring(currEnchStr.indexOf("Enchantment Infusion")+23, currEnchStr.indexOf("Enchantment Infusion")+24));
			
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
				double prevMaxFuel = Objects.requireNonNull(itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)).copyTag().getDoubleOr("maxDrillFuelFloat", 75d);
				final double outMaxFuel = prevMaxFuel+1;
				CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putDouble("maxDrillFuelFloat", outMaxFuel));
				blocksBroken = 0;
			}
		}

		// adds fuel cost for specific blocks (do this last)
		if (Stream.of(BlockTags.create(Identifier.parse("c:flowers"))).anyMatch(blockstate::is)
			|| Stream.of(BlockTags.create(Identifier.parse("minecraft:leaves"))).anyMatch(blockstate::is)
			|| Stream.of(BlockTags.create(Identifier.parse("minecraft:snow"))).anyMatch(blockstate::is)) {
			drillFuelCount -= 0.25f;
		} else if (Stream.of(BlockTags.create(Identifier.parse("c:gravels"))).anyMatch(blockstate::is)
			|| Stream.of(BlockTags.create(Identifier.parse("c:sands"))).anyMatch(blockstate::is)
			|| Stream.of(BlockTags.create(Identifier.parse("minecraft:dirt"))).anyMatch(blockstate::is)
			|| Stream.of(BlockTags.create(Identifier.parse("minecraft:ice"))).anyMatch(blockstate::is)){
			drillFuelCount -= 0.5f;
		} else if (Stream.of(BlockTags.create(Identifier.parse("c:netherracks"))).anyMatch(blockstate::is)){
			drillFuelCount -= 0.75f;
		} else {
			drillFuelCount -= 1f;
		}

		//outputs the NBT stuff
		final float drFulCOut = drillFuelCount;
		final int dwarfFury = dwarvernFuryValue;
		final int blBroken = blocksBroken;
		CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putFloat("currDrillFuelFloat", drFulCOut));
		CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putInt("dwarfFuryVal", dwarfFury));
		CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putInt("blocksBroken", blBroken));
		return drillFuelCount > -1;
	}
	
	

	// sets usable enchantments
	@Override
	public boolean supportsEnchantment(@NonNull ItemStack stack, Holder<Enchantment> enchantment) {
		String enchString = enchantment.toString();
		if (enchString.contains("Enchantment Efficiency") || enchString.contains("Enchantment Infusion") || enchString.contains("Enchantment Dwarvern Instincts")){
			//System.out.println("enchant string= "+enchantment.toString());
			return true;
		}
		
        return false;
    }

	// does Dwarvern Instincts enchantment effects
	public int dwarvernInstEffects(LivingEntity lvEntity, int enchantLvl, int furyValue){
		if (furyValue >= 5){
			lvEntity.addEffect(new MobEffectInstance(MobEffects.HASTE, 200+(enchantLvl*200), enchantLvl));
			furyValue = 0;
		}
		return furyValue;
	}

	

	
	// called every tick
	@Override
	public void inventoryTick(ItemStack iStack, @NonNull ServerLevel svrLvl, @NonNull Entity enti, @Nullable EquipmentSlot equipSlot) {
		boolean drillHasCoal;

		// check if drill is empty
		if (iStack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("currDrillFuelFloat", 0) < 1){
			int coalSlot = -99;
			coalSlot = getCoalSlotInInv(enti);
			drillHasCoal = coalSlot != -99;
		
			if (drillHasCoal) takeDrillCoal(coalSlot, enti, iStack);
		}

		// smoke particle
		svrLvl.addParticle(new ParticleOptions() {
			@Override
			public ParticleType<?> getType() {
				return ParticleTypes.LARGE_SMOKE;
			}
		}, enti.getX()+(0.5f-Math.random()), enti.getY(),enti.getZ()+(0.5f-Math.random()), Direction.UP.getStepX(), Direction.UP.getStepY(), Direction.UP.getStepZ());
		
		// play drill idle sound
		if (enti instanceof LivingEntity lvingEnt){
			if (lvingEnt.getPersistentData().getBooleanOr("canHearDrill", false)){
				if (lvingEnt.getMainHandItem() == iStack && iStack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("currDrillFuelFloat", 0) > 0 && lvingEnt.getTicksUsingItem()+3 < lvingEnt.tickCount){
					Level world = lvingEnt.level();

					if (!world.isClientSide()) {
						if (lvingEnt.getTicksUsingItem()+45 < lvingEnt.tickCount) {
							world.playSound(null, BlockPos.containing(lvingEnt.getX(), lvingEnt.getY(), lvingEnt.getZ()), Objects.requireNonNull(BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("convenient_additions:drill_startup"))), SoundSource.PLAYERS, 1f, 1f);
						} else if (lvingEnt.getTicksUsingItem()+5 < lvingEnt.tickCount) {
							world.playSound(null, BlockPos.containing(lvingEnt.getX(), lvingEnt.getY(), lvingEnt.getZ()), Objects.requireNonNull(BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("convenient_additions:drill_idle"))), SoundSource.PLAYERS, 0.6f, 1f);
						}
					} else {
						if (lvingEnt.getTicksUsingItem()+45 < lvingEnt.tickCount) {
							world.playLocalSound(lvingEnt.getX(), lvingEnt.getY(), lvingEnt.getZ(), Objects.requireNonNull(BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("convenient_additions:drill_startup"))), SoundSource.PLAYERS, 1f, 1f, false);
						} else if (lvingEnt.getTicksUsingItem()+5 < lvingEnt.tickCount) {
							world.playLocalSound(lvingEnt.getX(), lvingEnt.getY(), lvingEnt.getZ(), Objects.requireNonNull(BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("convenient_additions:drill_idle"))), SoundSource.PLAYERS, 0.6f, 1f, false);
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
		float drillFuelCount;
		if(enti instanceof Player playr){
			Inventory plrInv = playr.getInventory();
			ItemStack coalStack = plrInv.getItem(coalSlot);
			double drillFuelNBT = itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("maxDrillFuelFloat", 75d);

			drillFuelCount = (float)drillFuelNBT;

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
	public boolean isCorrectToolForDrops(ItemStack itemstack, @NonNull BlockState blockstate) {
		return customExcludesBlockstate(blockstate);
	}

	
	public boolean customExcludesBlockstate(BlockState blockstate){
		// blocks that cannot be broken with the drill
		return !(List.of(Blocks.SPAWNER, Blocks.AMETHYST_BLOCK, Blocks.TRIAL_SPAWNER, Blocks.SCULK_CATALYST, Blocks.SCULK_SENSOR, Blocks.SCULK_SHRIEKER, Blocks.BUDDING_AMETHYST, ConvenientAdditionsModBlocks.ARM_ORE_BLOCK.get()).contains(blockstate.getBlock())
				|| Stream.of(BlockTags.create(Identifier.parse("c:obsidians"))).anyMatch(blockstate::is)
				|| Stream.of(BlockTags.create(Identifier.parse("c:storage_blocks"))).anyMatch(blockstate::is)
				|| Stream.of(BlockTags.create(Identifier.parse("minecraft:logs"))).anyMatch(blockstate::is)
				|| Stream.of(BlockTags.create(Identifier.parse("c:chests"))).anyMatch(blockstate::is)
				|| (Stream.of(BlockTags.create(Identifier.parse("c:ores"))).anyMatch(blockstate::is) && !Stream.of(BlockTags.create(Identifier.parse("c:ores/coal"))).anyMatch(blockstate::is)));
	}



	// sounds
	@Override
	public boolean onEntitySwing(@NonNull ItemStack stack, LivingEntity entity, @NonNull InteractionHand hand){
		if (entity.getPersistentData().getBooleanOr("canHearDrill", false)) {
            if (entity.getTicksUsingItem() + 5 < entity.tickCount) {
                Level world = entity.level();
                if (!world.isClientSide()) {
                    world.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), Objects.requireNonNull(BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("convenient_additions:drill_use"))), SoundSource.PLAYERS, 0.7f, 1f);
                } else {
                    world.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), Objects.requireNonNull(BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("convenient_additions:drill_use"))), SoundSource.PLAYERS, 0.7f, 1f, true);
                }
            }
        }
		return false;
	}
	
}
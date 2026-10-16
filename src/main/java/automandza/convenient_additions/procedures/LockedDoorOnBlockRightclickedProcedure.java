package automandza.convenientadditions.procedures;

//imports
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.BlockPos;

import automandza.convenientadditions.init.ConvenientAdditionsModItems;
import java.util.Objects;




public class LockedDoorOnBlockRightclickedProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate, Entity entity) {
		if (entity == null){
			return;
		}
		double randomIDNumb;

		//closes door if open
		if (blockstate.getBlock().getStateDefinition().getProperty("open") instanceof BooleanProperty _getbp1 && blockstate.getValue(_getbp1)) {
			BlockPos _pos = BlockPos.containing(x, y, z);
			BlockState _bs = world.getBlockState(_pos);
			if (_bs.getBlock().getStateDefinition().getProperty("open") instanceof BooleanProperty _booleanProp){
				world.setBlock(_pos, _bs.setValue(_booleanProp, false), 3);
			}
			
			//plays door close sound
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), Objects.requireNonNull(BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("block.iron_door.close"))), SoundSource.BLOCKS, (float) 0.8, -1);
				} else {
					_level.playLocalSound(x, y, z, Objects.requireNonNull(BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("block.iron_door.close"))), SoundSource.BLOCKS, (float) 0.8, -1, false);
				}
			}
		} else {
			//door closed
			if (entity instanceof LivingEntity _livEnt){
				ItemStack mainhandStackToTest = _livEnt.getMainHandItem();
				ItemStack offhandStackToTest = _livEnt.getOffhandItem();

				// finds Key ID
				double mainHandKeyID = mainhandStackToTest.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("KeyID", 0);
				double offhandKeyID = offhandStackToTest.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("KeyID", 0);
				
				//check if door is locked with ID
				if (getBlockNBTNumber(world, BlockPos.containing(x, y, z)) > 0) {

					//checks if offhand or mainhand item is key and that its keyID is the same as the door's
					if (offhandStackToTest.getItem() == ConvenientAdditionsModItems.GOLDEN_KEY.get() && offhandKeyID > 0 && offhandKeyID == getBlockNBTNumber(world, BlockPos.containing(x, y, z))
					|| mainhandStackToTest.getItem() == ConvenientAdditionsModItems.GOLDEN_KEY.get() && mainHandKeyID > 0 && mainHandKeyID == getBlockNBTNumber(world, BlockPos.containing(x, y, z))) {
						//opens the freaking door
						BlockPos _pos = BlockPos.containing(x, y, z);
						BlockState _bs = world.getBlockState(_pos);
						if (_bs.getBlock().getStateDefinition().getProperty("open") instanceof BooleanProperty _booleanProp){
							world.setBlock(_pos, _bs.setValue(_booleanProp, true), 3);
						}
						
						//play open sound
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), Objects.requireNonNull(BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("block.iron_door.open"))), SoundSource.BLOCKS, (float) 0.8, -1);
							} else {
								_level.playLocalSound(x, y, z, Objects.requireNonNull(BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("block.iron_door.open"))), SoundSource.BLOCKS, (float) 0.8, -1, false);
							}
						}
					}
				} else {
					//checks if main hand item is a blank key
					if (mainhandStackToTest.getItem() == ConvenientAdditionsModItems.GOLDEN_KEY.get() && mainHandKeyID <= 0) {
						//sets the keyID to be a random number between 1 and 1000 and changes item visual
						randomIDNumb = Mth.nextDouble(RandomSource.create(), 1, 1000);
						final String _tagName = "KeyID";
						final double _tagValue = randomIDNumb;
						CustomData.update(DataComponents.CUSTOM_DATA, mainhandStackToTest, tag -> tag.putDouble(_tagName, _tagValue));
						mainhandStackToTest.enchant(world.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.BINDING_CURSE), 1);

						//sets ID of door to match keyID
						if (!world.isClientSide()) {
							setDoorNbtID(world, x, y, z, randomIDNumb);
							setDoorNbtID(world, x, y+1, z, randomIDNumb);
							setDoorNbtID(world, x, y-1, z, randomIDNumb);
						}

					//checks if offhand item is a blank key
					} else if (offhandStackToTest.getItem() == ConvenientAdditionsModItems.GOLDEN_KEY.get() && offhandKeyID <= 0) {
						//sets the keyID to be a random number between 1 and 1000 and changes item visual
						randomIDNumb = Mth.nextDouble(RandomSource.create(), 1, 1000);
						final String _tagName = "KeyID";
						final double _tagValue = randomIDNumb;
						CustomData.update(DataComponents.CUSTOM_DATA, offhandStackToTest, tag -> tag.putDouble(_tagName, _tagValue));
						offhandStackToTest.enchant(world.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.BINDING_CURSE), 1);

						//sets ID of door to match keyID
						if (!world.isClientSide()) {
							setDoorNbtID(world, x, y, z, randomIDNumb);
							setDoorNbtID(world, x, y+1, z, randomIDNumb);
							setDoorNbtID(world, x, y-1, z, randomIDNumb);
						}
					}
				}
			}
		}
	}



	private static void setDoorNbtID(LevelAccessor world, double x, double y, double z, double doorID){
		BlockPos _bp = BlockPos.containing(x, y, z);
		BlockEntity _blockEntity = world.getBlockEntity(_bp);
		BlockState _bs = world.getBlockState(_bp);
		
		if (_blockEntity != null) {
			_blockEntity.getPersistentData().putDouble("LockedDoorID", doorID);
		}
		if (world instanceof Level _level){
			_level.sendBlockUpdated(_bp, _bs, _bs, 3);
		}
	}

	private static double getBlockNBTNumber(LevelAccessor world, BlockPos pos) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity != null)
			return blockEntity.getPersistentData().getDoubleOr("LockedDoorID", 0);
		return -1;
	}
}
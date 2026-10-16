/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package automandza.convenientadditions.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredBlock;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;

import java.util.function.Function;

import automandza.convenientadditions.block.*;
import automandza.convenientadditions.ConvenientAdditionsMod;

public class ConvenientAdditionsModBlocks {
	public static final DeferredRegister.Blocks REGISTRY = DeferredRegister.createBlocks(ConvenientAdditionsMod.MODID);
	public static final DeferredBlock<Block> LOCKED_DOOR;
	public static final DeferredBlock<Block> THE_LOCK_BLOCK;
	public static final DeferredBlock<Block> SAWMILL;
	public static final DeferredBlock<Block> ARM_ORE_BLOCK;
	public static final DeferredBlock<Block> CRUDE_DOOR;
	public static final DeferredBlock<Block> MARBLE_RUBBLE;
	public static final DeferredBlock<Block> CEILING_LAMP;
	public static final DeferredBlock<Block> DECORATION_STATION;
	public static final DeferredBlock<Block> QUARTZ_PEARL_TOMBSTONE;
	public static final DeferredBlock<Block> KILN;
	public static final DeferredBlock<Block> ENCH_SCIENCE_RESEARCH_TABLE;
	public static final DeferredBlock<Block> MATERIALS_TO_ENCHANTMENT_MACHINE;
	static {
		LOCKED_DOOR = register("locked_door", LockedDoorBlock::new);
		THE_LOCK_BLOCK = register("the_lock_block", TheLockBlockBlock::new);
		SAWMILL = register("sawmill", SawmillBlock::new);
		ARM_ORE_BLOCK = register("arm_ore_block", ArmOreBlockBlock::new);
		CRUDE_DOOR = register("crude_door", CrudeDoorBlock::new);
		MARBLE_RUBBLE = register("marble_rubble", MarbleRubbleBlock::new);
		CEILING_LAMP = register("ceiling_lamp", CeilingLampBlock::new);
		DECORATION_STATION = register("decoration_station", DecorationStationBlock::new);
		QUARTZ_PEARL_TOMBSTONE = register("quartz_pearl_tombstone", QuartzPearlTombstoneBlock::new);
		KILN = register("kiln", KilnBlock::new);
		ENCH_SCIENCE_RESEARCH_TABLE = register("ench_science_research_table", EnchScienceResearchTableBlock::new);
		MATERIALS_TO_ENCHANTMENT_MACHINE = register("materials_to_enchantment_machine", MaterialsToEnchantmentMachineBlock::new);
	}

	// Start of user code block custom blocks
	// End of user code block custom blocks
	private static <B extends Block> DeferredBlock<B> register(String name, Function<BlockBehaviour.Properties, ? extends B> supplier) {
		return REGISTRY.registerBlock(name, supplier);
	}
}
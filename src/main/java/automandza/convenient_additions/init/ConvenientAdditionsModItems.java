/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package automandza.convenientadditions.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.DoubleHighBlockItem;
import net.minecraft.world.item.BlockItem;

import java.util.function.Function;

import automandza.convenientadditions.item.*;
import automandza.convenientadditions.block.EnchScienceResearchTableBlock;
import automandza.convenientadditions.ConvenientAdditionsMod;

public class ConvenientAdditionsModItems {
	public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(ConvenientAdditionsMod.MODID);
	public static final DeferredItem<Item> GRS_1600;
	public static final DeferredItem<Item> GOLDEN_KEY;
	public static final DeferredItem<Item> LOCKED_DOOR;
	public static final DeferredItem<Item> THE_LOCK_BLOCK;
	public static final DeferredItem<Item> OAK_BARK;
	public static final DeferredItem<Item> SPRUCE_BARK;
	public static final DeferredItem<Item> BIRCH_BARK;
	public static final DeferredItem<Item> DARKOAK_BARK;
	public static final DeferredItem<Item> WODDEN_VEST_CHESTPLATE;
	public static final DeferredItem<Item> WODDEN_VEST_LEGGINGS;
	public static final DeferredItem<Item> ACACIA_BARK;
	public static final DeferredItem<Item> JUNGLE_BARK;
	public static final DeferredItem<Item> CHERRY_BARK;
	public static final DeferredItem<Item> PALE_BARK;
	public static final DeferredItem<Item> SAWMILL;
	public static final DeferredItem<Item> ARM_ORE_BLOCK;
	public static final DeferredItem<Item> ARM_ORE;
	public static final DeferredItem<Item> COOKED_ARM;
	public static final DeferredItem<Item> MANGROVE_BARK;
	public static final DeferredItem<Item> LEGO_GUY;
	public static final DeferredItem<Item> CRUDE_DOOR;
	public static final DeferredItem<Item> FLAME_CHARGE;
	public static final DeferredItem<Item> STONE_DRILL;
	public static final DeferredItem<Item> DRILL_HEAD;
	public static final DeferredItem<Item> PENCIL;
	public static final DeferredItem<Item> DISCTEST;
	public static final DeferredItem<Item> MARBLE_RUBBLE;
	public static final DeferredItem<Item> REGULAR_PEARL;
	public static final DeferredItem<Item> CEILING_LAMP;
	public static final DeferredItem<Item> DECORATION_STATION;
	public static final DeferredItem<Item> QUARTZ_PEARL_TOMBSTONE;
	public static final DeferredItem<Item> KILN;
	public static final DeferredItem<Item> ENCH_SCIENCE_RESEARCH_TABLE;
	public static final DeferredItem<Item> MATERIALS_TO_ENCHANTMENT_MACHINE;
	public static final DeferredItem<Item> FIRE_EXTINGUISHER;
	public static final DeferredItem<Item> SNOW_CREEPER_SPAWN_EGG;
	static {
		GRS_1600 = register("grs_1600", GRS1600Item::new);
		GOLDEN_KEY = register("golden_key", KeyItem::new);
		LOCKED_DOOR = doubleBlock(ConvenientAdditionsModBlocks.LOCKED_DOOR);
		THE_LOCK_BLOCK = block(ConvenientAdditionsModBlocks.THE_LOCK_BLOCK);
		OAK_BARK = register("oak_bark", OakBarkItem::new);
		SPRUCE_BARK = register("spruce_bark", SpruceBarkItem::new);
		BIRCH_BARK = register("birch_bark", BirchBarkItem::new);
		DARKOAK_BARK = register("darkoak_bark", DarkoakBarkItem::new);
		WODDEN_VEST_CHESTPLATE = register("wodden_vest_chestplate", WoddenVestItem.Chestplate::new);
		WODDEN_VEST_LEGGINGS = register("wodden_vest_leggings", WoddenVestItem.Leggings::new);
		ACACIA_BARK = register("acacia_bark", AcaciaBarkItem::new);
		JUNGLE_BARK = register("jungle_bark", JungleBarkItem::new);
		CHERRY_BARK = register("cherry_bark", CherryBarkItem::new);
		PALE_BARK = register("pale_bark", PaleBarkItem::new);
		SAWMILL = block(ConvenientAdditionsModBlocks.SAWMILL);
		ARM_ORE_BLOCK = block(ConvenientAdditionsModBlocks.ARM_ORE_BLOCK);
		ARM_ORE = register("arm_ore", ArmOreItem::new);
		COOKED_ARM = register("cooked_arm", CookedArmItem::new);
		MANGROVE_BARK = register("mangrove_bark", MangroveBarkItem::new);
		LEGO_GUY = register("lego_guy", LegoGuyItem::new);
		CRUDE_DOOR = doubleBlock(ConvenientAdditionsModBlocks.CRUDE_DOOR);
		FLAME_CHARGE = register("flame_charge", FlameChargeItem::new);
		STONE_DRILL = register("stone_drill", StoneDrillItem::new);
		DRILL_HEAD = register("drill_head", DrillHeadItem::new);
		PENCIL = register("pencil", PencilItem::new);
		DISCTEST = register("disctest", DisctestItem::new);
		MARBLE_RUBBLE = block(ConvenientAdditionsModBlocks.MARBLE_RUBBLE);
		REGULAR_PEARL = register("regular_pearl", RegularPearlItem::new);
		CEILING_LAMP = block(ConvenientAdditionsModBlocks.CEILING_LAMP);
		DECORATION_STATION = block(ConvenientAdditionsModBlocks.DECORATION_STATION);
		QUARTZ_PEARL_TOMBSTONE = block(ConvenientAdditionsModBlocks.QUARTZ_PEARL_TOMBSTONE);
		KILN = block(ConvenientAdditionsModBlocks.KILN);
		ENCH_SCIENCE_RESEARCH_TABLE = register("ench_science_research_table", EnchScienceResearchTableBlock.Item::new);
		MATERIALS_TO_ENCHANTMENT_MACHINE = block(ConvenientAdditionsModBlocks.MATERIALS_TO_ENCHANTMENT_MACHINE);
		FIRE_EXTINGUISHER = register("fire_extinguisher", FireExtinguisherItem::new);
		SNOW_CREEPER_SPAWN_EGG = register("snow_creeper_spawn_egg", properties -> new SpawnEggItem(ConvenientAdditionsModEntities.SNOW_CREEPER.get(), properties));
	}

	// Start of user code block custom items
	// End of user code block custom items
	private static <I extends Item> DeferredItem<I> register(String name, Function<Item.Properties, ? extends I> supplier) {
		return REGISTRY.registerItem(name, supplier, new Item.Properties());
	}

	private static DeferredItem<Item> block(DeferredHolder<Block, Block> block) {
		return block(block, new Item.Properties());
	}

	private static DeferredItem<Item> block(DeferredHolder<Block, Block> block, Item.Properties properties) {
		return REGISTRY.registerItem(block.getId().getPath(), prop -> new BlockItem(block.get(), prop), properties);
	}

	private static DeferredItem<Item> doubleBlock(DeferredHolder<Block, Block> block) {
		return doubleBlock(block, new Item.Properties());
	}

	private static DeferredItem<Item> doubleBlock(DeferredHolder<Block, Block> block, Item.Properties properties) {
		return REGISTRY.registerItem(block.getId().getPath(), prop -> new DoubleHighBlockItem(block.get(), prop), properties);
	}
}
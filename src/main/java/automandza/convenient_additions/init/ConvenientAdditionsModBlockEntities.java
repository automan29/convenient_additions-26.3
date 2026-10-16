/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package automandza.convenientadditions.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.items.wrapper.SidedInvWrapper;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.core.registries.BuiltInRegistries;

import automandza.convenientadditions.block.entity.*;
import automandza.convenientadditions.ConvenientAdditionsMod;

@EventBusSubscriber
public class ConvenientAdditionsModBlockEntities {
	public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, ConvenientAdditionsMod.MODID);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<LockedDoorBlockEntity>> LOCKED_DOOR = register("locked_door", ConvenientAdditionsModBlocks.LOCKED_DOOR, LockedDoorBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SawmillBlockEntity>> SAWMILL = register("sawmill", ConvenientAdditionsModBlocks.SAWMILL, SawmillBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<DecorationStationBlockEntity>> DECORATION_STATION = register("decoration_station", ConvenientAdditionsModBlocks.DECORATION_STATION, DecorationStationBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<KilnBlockEntity>> KILN = register("kiln", ConvenientAdditionsModBlocks.KILN, KilnBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<EnchScienceResearchTableBlockEntity>> ENCH_SCIENCE_RESEARCH_TABLE = register("ench_science_research_table", ConvenientAdditionsModBlocks.ENCH_SCIENCE_RESEARCH_TABLE,
			EnchScienceResearchTableBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MaterialsToEnchantmentMachineBlockEntity>> MATERIALS_TO_ENCHANTMENT_MACHINE = register("materials_to_enchantment_machine",
			ConvenientAdditionsModBlocks.MATERIALS_TO_ENCHANTMENT_MACHINE, MaterialsToEnchantmentMachineBlockEntity::new);

	// Start of user code block custom block entities
	// End of user code block custom block entities
	private static <T extends BlockEntity> DeferredHolder<BlockEntityType<?>, BlockEntityType<T>> register(String registryname, DeferredHolder<Block, Block> block, BlockEntityType.BlockEntitySupplier<T> supplier) {
		return REGISTRY.register(registryname, () -> new BlockEntityType(supplier, block.get()));
	}

	@SubscribeEvent
	public static void registerCapabilities(RegisterCapabilitiesEvent event) {
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, LOCKED_DOOR.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, SAWMILL.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, DECORATION_STATION.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, KILN.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ENCH_SCIENCE_RESEARCH_TABLE.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, MATERIALS_TO_ENCHANTMENT_MACHINE.get(), SidedInvWrapper::new);
	}
}
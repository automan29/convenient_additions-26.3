/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package automandza.convenient_additions.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.Identifier;
import net.minecraft.core.registries.Registries;

import automandza.convenient_additions.ConvenientAdditionsMod;

public class ConvenientAdditionsModSounds {
	public static final DeferredRegister<SoundEvent> REGISTRY = DeferredRegister.create(Registries.SOUND_EVENT, ConvenientAdditionsMod.MODID);
	public static final DeferredHolder<SoundEvent, SoundEvent> ARM_ORE_FALL = REGISTRY.register("arm_ore_fall", () -> SoundEvent.createVariableRangeEvent(Identifier.fromNamespaceAndPath("convenient_additions", "arm_ore_fall")));
	public static final DeferredHolder<SoundEvent, SoundEvent> DWARF_ACTIVATE = REGISTRY.register("dwarf_activate", () -> SoundEvent.createVariableRangeEvent(Identifier.fromNamespaceAndPath("convenient_additions", "dwarf_activate")));
	public static final DeferredHolder<SoundEvent, SoundEvent> DRILL_STARTUP = REGISTRY.register("drill_startup", () -> SoundEvent.createVariableRangeEvent(Identifier.fromNamespaceAndPath("convenient_additions", "drill_startup")));
	public static final DeferredHolder<SoundEvent, SoundEvent> DRILL_USE = REGISTRY.register("drill_use", () -> SoundEvent.createVariableRangeEvent(Identifier.fromNamespaceAndPath("convenient_additions", "drill_use")));
	public static final DeferredHolder<SoundEvent, SoundEvent> DRILL_IDLE = REGISTRY.register("drill_idle", () -> SoundEvent.createVariableRangeEvent(Identifier.fromNamespaceAndPath("convenient_additions", "drill_idle")));
	public static final DeferredHolder<SoundEvent, SoundEvent> LIL_TUNE_TINY_RUME = REGISTRY.register("lil_tune_tiny_rume",
			() -> SoundEvent.createVariableRangeEvent(Identifier.fromNamespaceAndPath("convenient_additions", "lil_tune_tiny_rume")));
}
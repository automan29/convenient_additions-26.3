/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package automandza.convenientadditions.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.core.registries.Registries;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.particles.ParticleType;

import automandza.convenientadditions.ConvenientAdditionsMod;

public class ConvenientAdditionsModParticleTypes {
	public static final DeferredRegister<ParticleType<?>> REGISTRY = DeferredRegister.create(Registries.PARTICLE_TYPE, ConvenientAdditionsMod.MODID);
	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> PENGUIN_HORN_PARTICLE = REGISTRY.register("penguin_horn_particle", () -> new SimpleParticleType(true));
}
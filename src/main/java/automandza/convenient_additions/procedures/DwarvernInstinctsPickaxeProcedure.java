package automandza.convenientadditions.procedures;

//imports
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;

import javax.annotation.Nullable;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Objects;
import java.util.stream.Stream;
import net.minecraft.tags.BlockTags;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import automandza.convenientadditions.init.ConvenientAdditionsModItems;





@EventBusSubscriber
public class DwarvernInstinctsPickaxeProcedure {
	@SubscribeEvent
	public static void onBlockBreak(BlockEvent.BreakEvent event) {
		if (checkBlockInOresList(event.getState())){
			execute(event, event.getLevel(), event.getPlayer(), event.getPos());
		}
	}

	public static void execute(LevelAccessor world, Entity entity) {
		execute(null, world, entity, null);
	}

	private static int dwarvernFuryValue = 0;
	private static int hiddenDwvTimer = 0;
	
	public static void execute(@Nullable Event event, LevelAccessor lvlAcc, Entity entity, @Nullable BlockPos pos) {
		if (entity == null){
			return;
		}
		
		if (entity instanceof LivingEntity livinEnt){
			Level world = livinEnt.level();
			int DwarvInstLvl = livinEnt.getMainHandItem().getEnchantmentLevel(lvlAcc.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.parse("convenient_additions:dwarvern_instincts"))));
			if (DwarvInstLvl > 0 && !world.isClientSide() && livinEnt.getMainHandItem().getItem() != ConvenientAdditionsModItems.STONE_DRILL.asItem()) {

				//System.out.println("hiddenDwvTimer="+hiddenDwvTimer);
				
				if (hiddenDwvTimer != 0 && hiddenDwvTimer < livinEnt.tickCount){
					dwarvernFuryValue = 0;
				}

				if (pos == null){
					pos = entity.getOnPos();
				}
				
				hiddenDwvTimer = livinEnt.tickCount + 600;
				dwarvernFuryValue++;
				world.playSound(null, pos, Objects.requireNonNull(BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("block.note_block.iron_xylophone"))), SoundSource.BLOCKS, 0.8f, (dwarvernFuryValue+1)/5f);

				if(dwarvernFuryValue >= 5){
					livinEnt.addEffect(new MobEffectInstance(MobEffects.HASTE, 100+(150*DwarvInstLvl), 0));
					world.playSound(null, pos, Objects.requireNonNull(BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("convenient_additions:dwarf_activate"))), SoundSource.BLOCKS, 2f, 1f);
					dwarvernFuryValue = 0;
				}

			}
		}
	}

	private static boolean checkBlockInOresList(BlockState state){
		return Stream.of(BlockTags.create(ResourceLocation.parse("c:ores"))).anyMatch(state::is);
	}
}

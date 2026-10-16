package automandza.convenientadditions.item;

import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.world.item.equipment.EquipmentAssets;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;

import java.util.Map;

public abstract class WoddenVestItem extends Item {
	public static ArmorMaterial ARMOR_MATERIAL = new ArmorMaterial(7, Map.of(ArmorType.BOOTS, 0, ArmorType.LEGGINGS, 2, ArmorType.CHESTPLATE, 3, ArmorType.HELMET, 0, ArmorType.BODY, 3), 14,
			DeferredHolder.create(Registries.SOUND_EVENT, ResourceLocation.parse("item.armor.equip_leather")), 0f, 0f, TagKey.create(Registries.ITEM, ResourceLocation.parse("convenient_additions:wodden_vest_repair_items")),
			ResourceKey.create(EquipmentAssets.ROOT_ID, ResourceLocation.parse("convenient_additions:wodden_vest")));

	private WoddenVestItem(Item.Properties properties) {
		super(properties);
	}

	public static class Chestplate extends WoddenVestItem {
		public Chestplate(Item.Properties properties) {
			super(properties.humanoidArmor(ARMOR_MATERIAL, ArmorType.CHESTPLATE));
		}
	}

	public static class Leggings extends WoddenVestItem {
		public Leggings(Item.Properties properties) {
			super(properties.humanoidArmor(ARMOR_MATERIAL, ArmorType.LEGGINGS));
		}
	}
}
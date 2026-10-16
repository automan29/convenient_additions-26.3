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

public class ArmOreItem extends Item {
	public static ArmorMaterial ARMOR_MATERIAL = new ArmorMaterial(16, Map.of(ArmorType.BOOTS, 4, ArmorType.LEGGINGS, 4, ArmorType.CHESTPLATE, 4, ArmorType.HELMET, 4, ArmorType.BODY, 2), 8,
			DeferredHolder.create(Registries.SOUND_EVENT, ResourceLocation.parse("item.armor.equip_iron")), 0f, 0f, TagKey.create(Registries.ITEM, ResourceLocation.parse("convenient_additions:arm_ore_repair_items")),
			ResourceKey.create(EquipmentAssets.ROOT_ID, ResourceLocation.parse("convenient_additions:arm_ore")));

	public ArmOreItem(Item.Properties properties) {
		super(properties.humanoidArmor(ARMOR_MATERIAL, ArmorType.BODY));
	}

}
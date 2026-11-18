package automandza.convenient_additions.item;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

import static automandza.convenient_additions.init.ConvenientAdditionsModItems.PREMIUM_INGOT;

public class ParsePremiumIngot extends Item {
    public ParsePremiumIngot(Item.Properties properties) {
        super(properties);
    }

    @Override
    public void inventoryTick(ItemStack itemStack, ServerLevel level, Entity owner, @Nullable EquipmentSlot slot) {
        itemStack.setCount(0);
        try {
            ItemStack premiumIStack = new ItemStack(PREMIUM_INGOT.get());
            premiumIStack.setCount(1);
            owner.spawnAtLocation(level, premiumIStack);
        } catch (Exception e){
            System.out.println("No loot because convex not installed");
        }
    }
}

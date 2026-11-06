package automandza.convenient_additions.enchantments;

public class EnchantmentStringConverter {
    public int getEnchLevelFromItem(String allEnchantsOnItem, String enchNameToFind){
        String tempCompString = allEnchantsOnItem.substring(allEnchantsOnItem.indexOf("minecraft:enchantment /"));
        String enchantsString = tempCompString.substring(24,tempCompString.indexOf("}}"));
        enchantsString = enchantsString.substring(0, enchantsString.indexOf("]="))+"="+enchantsString.substring(enchantsString.indexOf("=>")+2);

        String[] itemEnchList = enchantsString.split(",");

        // for each enchantment on inserted item
        for (String currItemEnchFull : itemEnchList) {
            // get ench name
            String foundEnchName = currItemEnchFull.substring(0, currItemEnchFull.indexOf("=")).toLowerCase();
            // get ench lvl
            int foundEnchLvl = Integer.parseInt(currItemEnchFull.substring(currItemEnchFull.indexOf("=")+1));

            //System.out.println("foundEnchName="+foundEnchName);
            //System.out.println("foundEnchLvl="+foundEnchLvl);

            if (foundEnchName.equals(enchNameToFind)){
                return foundEnchLvl;
            }
        }

        return -1;
    }
}

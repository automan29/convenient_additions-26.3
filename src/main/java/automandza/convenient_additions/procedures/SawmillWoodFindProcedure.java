package automandza.convenient_additions.procedures;

//imports
import automandza.convenient_additions.init.ConvenientAdditionsModItems;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;




public class SawmillWoodFindProcedure {
	public static ItemStack PlanksToSaw(ItemStack iStack, String passCurrRecipe) {
	    ItemStack outputIStack;
	    String plankTypeText = iStack.getItem().toString().substring(10,iStack.getItem().toString().indexOf("planks")-1);
		int plankCount = iStack.getCount();


	    SawmillWoodFindProcedure woodTypeDeterminator = new SawmillWoodFindProcedure();

        outputIStack = switch (plankTypeText) {
            case "oak" -> woodTypeDeterminator.oakDetare(passCurrRecipe, plankCount);
            case "spruce" -> woodTypeDeterminator.spruceDetare(passCurrRecipe, plankCount);
            case "dark_oak" -> woodTypeDeterminator.darkOakDetare(passCurrRecipe, plankCount);
            case "birch" -> woodTypeDeterminator.birchDetare(passCurrRecipe, plankCount);
            case "acacia" -> woodTypeDeterminator.acaciaDetare(passCurrRecipe, plankCount);
            case "jungle" -> woodTypeDeterminator.jungleDetare(passCurrRecipe, plankCount);
            case "cherry" -> woodTypeDeterminator.cherryDetare(passCurrRecipe, plankCount);
            case "pale_oak" -> woodTypeDeterminator.paleOakDetare(passCurrRecipe, plankCount);
            case "mangrove" -> woodTypeDeterminator.mangroveDetare(passCurrRecipe, plankCount);
            case "crimson" -> woodTypeDeterminator.crimsonDetare(passCurrRecipe, plankCount);
            case "warped" -> woodTypeDeterminator.warpedDetare(passCurrRecipe, plankCount);
			case "poplar" -> woodTypeDeterminator.poplarDetare(passCurrRecipe, plankCount);
			case "cloud" -> woodTypeDeterminator.cloudDetare(passCurrRecipe, plankCount);
            default -> new ItemStack(Items.AIR).copy();
        };

		return outputIStack;
	}
	
	public static ItemStack LogsToWood(String iStackString){
		ItemStack outputIStack;
		String logTypeText = iStackString.substring(10);
		SawmillWoodFindProcedure woodTypeDeterminator = new SawmillWoodFindProcedure();
		
		try{
			logTypeText = woodTypeDeterminator.getLogTypeFromString(logTypeText);
		} catch(Exception e) {
			System.out.println("ERROR invalid item stack string iStackString="+iStackString);
			System.out.println("exception="+e);
		}
		//System.out.println("logTypeText="+logTypeText);

        outputIStack = switch (logTypeText) {
            case "oak" -> new ItemStack(Items.STRIPPED_OAK_WOOD).copy();
            case "spruce" -> new ItemStack(Items.STRIPPED_SPRUCE_WOOD).copy();
            case "dark_oak" -> new ItemStack(Items.STRIPPED_DARK_OAK_WOOD).copy();
            case "birch" -> new ItemStack(Items.STRIPPED_BIRCH_WOOD).copy();
            case "acacia" -> new ItemStack(Items.STRIPPED_ACACIA_WOOD).copy();
            case "jungle" -> new ItemStack(Items.STRIPPED_JUNGLE_WOOD).copy();
            case "cherry" -> new ItemStack(Items.STRIPPED_CHERRY_WOOD).copy();
            case "pale_oak" -> new ItemStack(Items.STRIPPED_PALE_OAK_WOOD).copy();
            case "mangrove" -> new ItemStack(Items.STRIPPED_MANGROVE_WOOD).copy();
            case "crimson" -> new ItemStack(Items.STRIPPED_CRIMSON_HYPHAE).copy();
            case "warped" -> new ItemStack(Items.STRIPPED_WARPED_HYPHAE).copy();
            case "poplar" -> new ItemStack(Items.STRIPPED_POPLAR_WOOD).copy();
            default -> new ItemStack(Items.AIR).copy();
        };
		
		return outputIStack;
	}
	
	public static ItemStack LogsToPlanks(String iStackString){
		ItemStack outputIStack;
		String logTypeText = iStackString.substring(10);
		SawmillWoodFindProcedure woodTypeDeterminator = new SawmillWoodFindProcedure();
		
		try{
			logTypeText = woodTypeDeterminator.getLogTypeFromString(logTypeText);
		} catch(Exception e) {
			System.out.println("ERROR invalid item stack string iStackString="+iStackString);
			System.out.println("exception="+e);
		}
		//System.out.println("logTypeText="+logTypeText);

        outputIStack = switch (logTypeText) {
            case "oak" -> new ItemStack(Items.OAK_PLANKS).copy();
            case "spruce" -> new ItemStack(Items.SPRUCE_PLANKS).copy();
            case "dark_oak" -> new ItemStack(Items.DARK_OAK_PLANKS).copy();
            case "birch" -> new ItemStack(Items.BIRCH_PLANKS).copy();
            case "acacia" -> new ItemStack(Items.ACACIA_PLANKS).copy();
            case "jungle" -> new ItemStack(Items.JUNGLE_PLANKS).copy();
            case "cherry" -> new ItemStack(Items.CHERRY_PLANKS).copy();
            case "pale_oak" -> new ItemStack(Items.PALE_OAK_PLANKS).copy();
            case "mangrove" -> new ItemStack(Items.MANGROVE_PLANKS).copy();
            case "crimson" -> new ItemStack(Items.CRIMSON_PLANKS).copy();
            case "warped" -> new ItemStack(Items.WARPED_PLANKS).copy();
            case "poplar" -> new ItemStack(Items.POPLAR_PLANKS).copy();
            default -> new ItemStack(Items.AIR).copy();
        };
		
		return outputIStack;
	}
	
	String getLogTypeFromString(String woodLogStr){
		if (woodLogStr.contains("stripped")){
			if (woodLogStr.contains("wood")){
				return woodLogStr.substring(woodLogStr.indexOf("stripped")+9, woodLogStr.indexOf("wood")-1);
			}
			if (woodLogStr.contains("log")){
				return woodLogStr.substring(woodLogStr.indexOf("stripped")+9, woodLogStr.indexOf("log")-1);
			}
			if (woodLogStr.contains("hyphae")){
				return woodLogStr.substring(woodLogStr.indexOf("stripped")+9, woodLogStr.indexOf("hyphae")-1);
			}
			if (woodLogStr.contains("stem")){
				return woodLogStr.substring(woodLogStr.indexOf("stripped")+9, woodLogStr.indexOf("stem")-1);
			}
		}
		if (woodLogStr.contains("wood")){
			return woodLogStr.substring(0, woodLogStr.indexOf("wood")-1);
		}
		if (woodLogStr.contains("log")){
			return woodLogStr.substring(0, woodLogStr.indexOf("log")-1);
		}
		if (woodLogStr.contains("hyphae")){
			return woodLogStr.substring(0, woodLogStr.indexOf("hyphae")-1);
		}
		return woodLogStr.substring(0, woodLogStr.indexOf("stem")-1);
	}

	ItemStack oakDetare(String currRecipeType, int count){
		ItemStack woodROutput;
		switch(currRecipeType){
			case "split":
				woodROutput = new ItemStack(Items.OAK_SLAB).copy();
				woodROutput.setCount(count*2);
				break;
			case "cut":
				woodROutput = new ItemStack(Items.OAK_STAIRS).copy();
				break;
			default:
				woodROutput = new ItemStack(Items.OAK_FENCE).copy();
		}
		return woodROutput;
	}

	ItemStack spruceDetare(String currRecipeType, int count){
		ItemStack woodROutput;
        switch(currRecipeType){
			case "split":
				woodROutput = new ItemStack(Items.SPRUCE_SLAB).copy();
				woodROutput.setCount(count*2);
				break;
			case "cut":
				woodROutput = new ItemStack(Items.SPRUCE_STAIRS).copy();
				break;
			default:
				woodROutput = new ItemStack(Items.SPRUCE_FENCE).copy();
		}
		return woodROutput;
	}
	
	ItemStack darkOakDetare(String currRecipeType, int count){
        ItemStack woodROutput;
        switch(currRecipeType){
			case "split":
				woodROutput = new ItemStack(Items.DARK_OAK_SLAB).copy();
				woodROutput.setCount(count*2);
				break;
			case "cut":
				woodROutput = new ItemStack(Items.DARK_OAK_STAIRS).copy();
				break;
			default:
				woodROutput = new ItemStack(Items.DARK_OAK_FENCE).copy();
		}
		return woodROutput;
	}
	
	ItemStack birchDetare(String currRecipeType, int count){
		ItemStack woodROutput;
        switch(currRecipeType){
			case "split":
				woodROutput = new ItemStack(Items.BIRCH_SLAB).copy();
				woodROutput.setCount(count*2);
				break;
			case "cut":
				woodROutput = new ItemStack(Items.BIRCH_STAIRS).copy();
				break;
			default:
				woodROutput = new ItemStack(Items.BIRCH_FENCE).copy();
		}
		return woodROutput;
	}
	
	ItemStack acaciaDetare(String currRecipeType, int count){
		ItemStack woodROutput;
        switch(currRecipeType){
			case "split":
				woodROutput = new ItemStack(Items.ACACIA_SLAB).copy();
				woodROutput.setCount(count*2);
				break;
			case "cut":
				woodROutput = new ItemStack(Items.ACACIA_STAIRS).copy();
				break;
			default:
				woodROutput = new ItemStack(Items.ACACIA_FENCE).copy();
		}
		return woodROutput;
	}
	
	ItemStack jungleDetare(String currRecipeType, int count){
		ItemStack woodROutput;
        switch(currRecipeType){
			case "split":
				woodROutput = new ItemStack(Items.JUNGLE_SLAB).copy();
				woodROutput.setCount(count*2);
				break;
			case "cut":
				woodROutput = new ItemStack(Items.JUNGLE_STAIRS).copy();
				break;
			default:
				woodROutput = new ItemStack(Items.JUNGLE_FENCE).copy();
		}
		return woodROutput;
	}
	
	ItemStack cherryDetare(String currRecipeType, int count){
		ItemStack woodROutput;
        switch(currRecipeType){
			case "split":
				woodROutput = new ItemStack(Items.CHERRY_SLAB).copy();
				woodROutput.setCount(count*2);
				break;
			case "cut":
				woodROutput = new ItemStack(Items.CHERRY_STAIRS).copy();
				break;
			default:
				woodROutput = new ItemStack(Items.CHERRY_FENCE).copy();
		}
		return woodROutput;
	}
	
	ItemStack paleOakDetare(String currRecipeType, int count){
		ItemStack woodROutput;
        switch(currRecipeType){
			case "split":
				woodROutput = new ItemStack(Items.PALE_OAK_SLAB).copy();
				woodROutput.setCount(count*2);
				break;
			case "cut":
				woodROutput = new ItemStack(Items.PALE_OAK_STAIRS).copy();
				break;
			default:
				woodROutput = new ItemStack(Items.PALE_OAK_FENCE).copy();
		}
		return woodROutput;
	}
	
	ItemStack mangroveDetare(String currRecipeType, int count){
		ItemStack woodROutput;
        switch(currRecipeType){
			case "split":
				woodROutput = new ItemStack(Items.MANGROVE_SLAB).copy();
				woodROutput.setCount(count*2);
				break;
			case "cut":
				woodROutput = new ItemStack(Items.MANGROVE_STAIRS).copy();
				break;
			default:
				woodROutput = new ItemStack(Items.MANGROVE_FENCE).copy();
		}
		return woodROutput;
	}
	
	ItemStack crimsonDetare(String currRecipeType, int count){
		ItemStack woodROutput;
        switch(currRecipeType){
			case "split":
				woodROutput = new ItemStack(Items.CRIMSON_SLAB).copy();
				woodROutput.setCount(count*2);
				break;
			case "cut":
				woodROutput = new ItemStack(Items.CRIMSON_STAIRS).copy();
				break;
			default:
				woodROutput = new ItemStack(Items.CRIMSON_FENCE).copy();
		}
		return woodROutput;
	}
	
	ItemStack warpedDetare(String currRecipeType, int count){
		ItemStack woodROutput;
        switch(currRecipeType){
			case "split":
				woodROutput = new ItemStack(Items.WARPED_SLAB).copy();
				woodROutput.setCount(count*2);
				break;
			case "cut":
				woodROutput = new ItemStack(Items.WARPED_STAIRS).copy();
				break;
			default:
				woodROutput = new ItemStack(Items.WARPED_FENCE).copy();
		}
		return woodROutput;
	}

	ItemStack poplarDetare(String currRecipeType, int count){
		ItemStack woodROutput;
		switch(currRecipeType){
			case "split":
				woodROutput = new ItemStack(Items.POPLAR_SLAB).copy();
				woodROutput.setCount(count*2);
				break;
			case "cut":
				woodROutput = new ItemStack(Items.POPLAR_STAIRS).copy();
				break;
			default:
				woodROutput = new ItemStack(Items.POPLAR_FENCE).copy();
		}
		return woodROutput;
	}

	ItemStack cloudDetare(String currRecipeType, int count){
		ItemStack woodROutput;
		switch(currRecipeType){
			case "split":
				woodROutput = new ItemStack(Items.POPLAR_SLAB).copy();
				woodROutput.setCount(count*2);
				break;
			case "cut":
				woodROutput = new ItemStack(Items.POPLAR_STAIRS).copy();
				break;
			default:
				woodROutput = new ItemStack(Items.POPLAR_FENCE).copy();
		}
		return woodROutput;
	}
}
package automandza.convenientadditions.procedures;

//imports
import net.minecraft.world.level.block.state.BlockState;
import java.util.*;
import net.minecraft.world.level.Level;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;




public class CeilingLampLuminanceProcedure {
	public static ArrayList<ArrayList<ArrayList<Integer> > > global3DLightsArray = new ArrayList<ArrayList<ArrayList<Integer> > >();

	public static double execute(BlockState blockState) {
		return 15d;
	}

	public static void addCheckBlockPosList(double x, double y, double z, Level mcLvl){
		int i = 0;
		int j = 0;

		while (global3DLightsArray.get(i) != null){
			while (global3DLightsArray.get(i).get(j) != null){
				if (global3DLightsArray.get(i).get(j).get(0) == (int) x && 
					global3DLightsArray.get(i).get(j).get(1) == (int) y && 
					global3DLightsArray.get(i).get(j).get(2) == (int) z){
						toggleLightLit(i, j, mcLvl);
						return;
				}
				j++;
			}
			i++;
		}

		global3DLightsArray.add(new ArrayList<ArrayList<Integer> >());
		global3DLightsArray.get(i).add(new ArrayList<Integer>());
		global3DLightsArray.get(i).get(j).add(0, (int) x);
		global3DLightsArray.get(i).get(j).add(1, (int) y);
		global3DLightsArray.get(i).get(j).add(2, (int) z);

		toggleLightLit(i, j, mcLvl);
	}

	private static void toggleLightLit(int i, int j, Level mcLevel){
		BlockPos pos = BlockPos.containing(global3DLightsArray.get(i).get(j).get(0), global3DLightsArray.get(i).get(j).get(1), global3DLightsArray.get(i).get(j).get(2));

		mcLevel.setBlock(pos, mcLevel.getBlockState(pos).cycle(BlockStateProperties.LIT), 2);
	}
}
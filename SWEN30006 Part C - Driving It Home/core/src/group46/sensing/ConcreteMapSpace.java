package group46.sensing;

import java.awt.geom.Point2D.Double;
import java.util.ArrayList;

import com.unimelb.swen30006.partc.core.objects.WorldObject;

class ConcreteMapSpace extends MapGenerator implements IMapSpace {
	// Map
	private boolean[][] spaceMap;
	
	@Override
	public boolean[][] generateSpaceMap(Double refPos, int visibility, WorldObject[] objectArray) {
		this.spaceMap = new boolean[visibility][visibility];
		
		for(WorldObject object: objectArray){
			Double pos = object.getPosition();
			float height = object.getLength();
			float width = object.getWidth();
			ArrayList<Integer[]> blocks = getObjectBlocks(refPos, visibility, pos, width, height);
			processSpace(blocks);
			
		}
		
		return spaceMap;
	}
	
	private void processSpace(ArrayList<Integer[]> blocks){		
		for (Integer[] block: blocks){
			spaceMap[block[0]][block[1]] = true;			
		}
	}

}

package group46.sensing;

import java.awt.geom.Point2D.Double;
import java.util.ArrayList;

import com.unimelb.swen30006.partc.core.objects.WorldObject;
/**
 * Concrete Strategy of IMapVelocity
 * @author Group 46
 */
class ConcreteMapSpace extends MapGenerator implements IMapSpace {
	/** The space map that contains if an the specific space is occupied 
	 *  by an object */
	private boolean[][] spaceMap;
	
	@Override
	public boolean[][] generateSpaceMap(Double refPos, int visibility, WorldObject[] objectArray) {
		this.spaceMap = new boolean[visibility][visibility];
		
		/* Take each world object, collect its data and process the velocity of its blocks */
		for(WorldObject object: objectArray){
			Double pos = object.getPosition();
			float height = object.getLength();
			float width = object.getWidth();
			ArrayList<Integer[]> blocks = getObjectBlocks(refPos, visibility, pos, width, height);
			processSpace(blocks);
			
		}
		
		return spaceMap;
	}
	
	/**
	 * A method that takes the object blocks indexes and mark them with true, indicating that there
	 * is an object in that space.
	 * @param blocks The blocks to be processed
	 * */
	private void processSpace(ArrayList<Integer[]> blocks){		
		for (Integer[] block: blocks){
			spaceMap[block[0]][block[1]] = true;			
		}
	}

}

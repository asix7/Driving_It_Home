package group46.sensing;

import java.awt.geom.Point2D.Double;
import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.unimelb.swen30006.partc.core.objects.WorldObject;
import com.unimelb.swen30006.partc.roads.Road;

class ConcreteMapColour extends MapGenerator implements IMapColour  {

	private Color[][] colourMap;

	@Override
	public Color[][] generateColourMap(Double refPos, int visibility, WorldObject[] objectArray, Road[] roadsArray) {
		
		this.colourMap = new Color[visibility][visibility];
		
		for(Road road: roadsArray){
			Double startPos = road.getStartPos();
			Double endPos = road.getEndPos();
			float width = road.getWidth();
			// Assuming that we don't have a getter from road color, and the color is the same DARK_GRAY
			Color colour = Color.DARK_GRAY;
			ArrayList<Integer[]> blocks = getRoadBlocks(refPos, visibility, startPos, endPos, width);
			processColour(blocks, colour);			
		}
		
		for(WorldObject object: objectArray){
			Double pos = object.getPosition();
			float height = object.getLength();
			float width = object.getWidth();
			// Assuming that we don't have a getter from road color, and the color is the same DARK_GRAY
			Color colour = Color.DARK_GRAY;
			ArrayList<Integer[]> blocks = getObjectBlocks(refPos, visibility, pos, width, height);
			processColour(blocks, colour);			
		}
		
		return colourMap;
	}
	
	/**
	 * A method that takes the information of an road and gets the indexes of the map array 
	 * corresponding to it.
	 * @param refPos the reference position at the center of the map
	 * @param visibility the range of blocks from the reference position
	 * @param startPos the start position of the road to be mapped
	 * @param endPos the end position of the road to be mapped
	 * @param width the width of the object
	 * @return the indexes of an array of the blocks that the road belongs to
	 * */
	private ArrayList<Integer[]> getRoadBlocks(Double refPos, int visibility, Double startPos, Double endPos, float width){
		float height = (float) Double.distance(endPos.x, endPos.y, startPos.x, startPos.y);
		Double center = new Double(); 
		center.x = (startPos.x + endPos.x)/2.0;
		center.y = (startPos.y + endPos.y)/2.0;
		
		if(startPos.x - endPos.x != 0.0){			
			return getObjectBlocks(refPos, visibility, center, height, width);
		} else {
			return getObjectBlocks(refPos, visibility, center, width, height);
		}		
	}
	
	private void processColour(ArrayList<Integer[]> blocks, Color colour){
		for (Integer[] block: blocks){
			if(colourMap[block[0]][block[1]] == null){
				colourMap[block[0]][block[1]] = colour;
			} else {
				colourMap[block[0]][block[1]] = colourMap[block[0]][block[1]].add(colour);
			}
			  		
		}
	}
	

}

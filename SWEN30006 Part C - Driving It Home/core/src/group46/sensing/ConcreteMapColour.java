package group46.sensing;

import java.awt.geom.Point2D.Double;
import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.unimelb.swen30006.partc.core.objects.WorldObject;
import com.unimelb.swen30006.partc.roads.Road;
import com.unimelb.swen30006.partc.roads.RoadMarking;

class ConcreteMapColour extends MapGenerator implements IMapColour  {

	private Color[][] colourMap;
	private final float THRESHOLD = 0.9f;
	
	

	@Override
	public Color[][] generateColourMap(Double refPos, int visibility, WorldObject[] objectArray, Road[] roadsArray, Color environmentColour) {
		
		this.colourMap = new Color[visibility][visibility];
		
		// Inititalize velocity map
		for (int i = 0; i <= visibility - 1; i++)
			for (int j = 0; j <= visibility - 1; j++)
				colourMap[i][j] = environmentColour;
		
		for(Road road: roadsArray){
			Double startPos = road.getStartPos();
			Double endPos = road.getEndPos();
			float width = road.getWidth();
			// Assuming that we don't have a getter from road color, and the color is the same DARK_GRAY
			Color colour = Color.DARK_GRAY;
			ArrayList<Integer[]> blocks = getRoadBlocks(refPos, visibility, startPos, endPos, width);
			processColour(blocks, colour);		
			
			boolean horizontal = false;
			if(startPos.x - endPos.x != 0.0){
				horizontal = true;
			}
			
			// Get the road markers and process them
			RoadMarking[] markers = road.getMarkers();
			for(RoadMarking marker: markers){
				// No access to width of marking so we hard coded the value
				float markingWidth = 1.0f;
				Color markerColour = Color.LIGHT_GRAY;
				ArrayList<Integer[]> markerBlocks = getMarkerBlocks(refPos, visibility, marker.position,markingWidth, horizontal);
				processColour(markerBlocks, markerColour);	
			}
		}
		
		for(WorldObject object: objectArray){
			Double pos = object.getPosition();
			float height = object.getLength();
			float width = object.getWidth();
			// Assuming that we don't have a getter from road color, and the color is the same DARK_GRAY
			Color colour = object.getColour();
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
	
	private ArrayList<Integer[]> getMarkerBlocks(Double refPos, int visibility, Double pos, float width, boolean horizontal){
		// Hard coded width and height because we have no access to the size of marker
		float horizontalWidth = horizontal ? 2*width : width;
		float verticalWidth = horizontal ? width : 2*width;
		
		return getObjectBlocks(refPos, visibility, pos, verticalWidth, horizontalWidth);
	}
	
	private void processColour(ArrayList<Integer[]> blocks, Color colour){
		for (Integer[] block: blocks){
			float area = block[2]/100.0f;
			if(colourMap[block[0]][block[1]] == null){
				colourMap[block[0]][block[1]] = new Color(area* colour.r,area * colour.g, area * colour.b, 1.0f);
			} else {				

				if(area < THRESHOLD){
					colourMap[block[0]][block[1]] = new Color(colourMap[block[0]][block[1]].r + area * colour.r,
							  colourMap[block[0]][block[1]].g + area * colour.g, 
							  colourMap[block[0]][block[1]].b + area * colour.b, 1.0f);
				} else {
					colourMap[block[0]][block[1]] = new Color(colour.r, colour.g, colour.b, 1.0f);
					
				}

			}
			  		
		}
	}
	

}

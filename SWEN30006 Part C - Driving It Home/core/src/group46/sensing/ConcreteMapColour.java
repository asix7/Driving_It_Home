package group46.sensing;

import group46.sensing.exceptions.ZeroDimensionException;
import group46.sensing.exceptions.ZeroVisibilityException;

import java.awt.geom.Point2D.Double;
import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.unimelb.swen30006.partc.core.objects.WorldObject;
import com.unimelb.swen30006.partc.roads.Intersection;
import com.unimelb.swen30006.partc.roads.Road;
import com.unimelb.swen30006.partc.roads.RoadMarking;

/**
 * Concrete Strategy of the IMapColour
 * @author Group 46 
 */
class ConcreteMapColour extends MapGenerator implements IMapColour  {
	
	/** The colour map where the colours are going to be stored */
	private Color[][] colourMap;
	// New to design, averaging the colour of overlaping objects was oversight,
	// we based our colour assign in order priority so we don't average road with a world object
	// over it
	/** Area percentage to assign the full colour of the object to the block */
	private final float THRESHOLD = 0.9f; // Used to give priority to overlaping objects over this threshold

	@Override
	public Color[][] generateColourMap(Double refPos, int visibility, WorldObject[] objectArray, Road[] roadsArray, 
			Intersection[] intertersectionsArray, Color environmentColour) throws ZeroVisibilityException, ZeroDimensionException{
		
		if(visibility <= 0){
			throw new ZeroVisibilityException();
		}
		// Create and Initialize the velocity map with default colour
		this.colourMap = new Color[visibility][visibility];
		for (int i = 0; i <= visibility - 1; i++)
			for (int j = 0; j <= visibility - 1; j++)
				colourMap[i][j] = environmentColour;
		
		processRoads(refPos, visibility, roadsArray);		
		processIntersections(refPos,visibility,intertersectionsArray);
		processWorldObjects(refPos, visibility, objectArray);
		
		return colourMap;
	}	
	
	/**
	 * A method that takes the blocks indexes where some object its located, its colour, and 
	 * adds it to the colourMap
	 * @param blocks The blocks where an object is located
	 * @param colour The colour of the object
	 * */
	private void processColour(ArrayList<Integer[]> blocks, Color colour){
		for (Integer[] block: blocks){
			// Convert from percentage to float
			float area = block[2]/100.0f;
			
			if(colourMap[block[0]][block[1]] == null){
				colourMap[block[0]][block[1]] = new Color(area* colour.r,area * colour.g, area * colour.b, 1.0f);
			} else {
				// Add the percentage of the colour to the block, when it goes over the threshold 
				// it assigns the colour to the whole block
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

	/**
	 * A method that takes the information of a road and gets the indexes of the map array 
	 * corresponding to it.
	 * @param refPos the reference position at the center of the map
	 * @param visibility the range of blocks from the reference position
	 * @param startPos the start position of the road to be mapped
	 * @param endPos the end position of the road to be mapped
	 * @param width the width of the object
	 * @return the indexes of an array of the blocks that the road belongs to
	 * */
	private ArrayList<Integer[]> getRoadBlocks(Double refPos, int visibility, Double startPos, Double endPos, float width) throws ZeroDimensionException{
		// Get the center position of the road
		float height = (float) Double.distance(endPos.x, endPos.y, startPos.x, startPos.y);
		Double center = new Double(); 
		center.x = (startPos.x + endPos.x)/2.0;
		center.y = (startPos.y + endPos.y)/2.0;
		
		if(width <= 0 || height <= 0){
			throw new ZeroDimensionException();
		}
		
		// Change the direction of the road
		if(startPos.x - endPos.x != 0.0){			
			return getObjectBlocks(refPos, visibility, center, height, width);
		} else {
			return getObjectBlocks(refPos, visibility, center, width, height);
		}		
	}
	
	/*
	 * THIS NEW METHODS WHERE NOT STATED IN OUR ORIGINAL DESIGN
	 * 
	 * The reason is because at the time of submission May 15, We didn't have access to the additional accessors
	 * released on the 16 that open the possibility to design for Road Markers and Intersections. The process of the
	 * colors for each object was divided in  three methods
	 *  
	 */
	
	
	/**
	 * A method that takes the information of a marker and gets the indexes of the map array 
	 * corresponding to it.
	 * @param refPos the reference position at the center of the map
	 * @param visibility the range of blocks from the reference position
	 * @param pos the position of the marker to be mapped
	 * @param width the width of the object
	 * @param horizontal a boolean to determine the direction of the marker
	 * @return the indexes of an array of the blocks that the marker belongs to
	 * */
	private ArrayList<Integer[]> getMarkerBlocks(Double refPos, int visibility, Double pos, float width, boolean horizontal) throws ZeroDimensionException{
		// Coded the width and height because we have no access to this marker attributes or its direction
		float horizontalWidth = horizontal ? 2*width : width;
		float verticalWidth = horizontal ? width : 2*width;
		
		if(horizontalWidth <= 0 || verticalWidth <= 0){
			throw new ZeroDimensionException();
		}
		return getObjectBlocks(refPos, visibility, pos, verticalWidth, horizontalWidth);
	}
	
	/**
	 * Take each world object, collect its data and process the colour of its blocks
	 * @param refPos the position of the object that runs the sensing process
	 * @param visibility the maximum visibility within the world, at the current point in time
	 * @param objectArray World Objects in the visible range
	 */
	private void processWorldObjects(Double refPos, int visibility, WorldObject[] objectArray) throws ZeroDimensionException{
		
		for(WorldObject object: objectArray){
			Double pos = object.getPosition();
			float height = object.getLength();
			float width = object.getWidth();
			
			if(width <= 0 || height <= 0){
				throw new ZeroDimensionException();
			}
			// Assuming that we don't have a getter from road color, and the colour is the same DARK_GRAY
			Color colour = object.getColour();
			ArrayList<Integer[]> blocks = getObjectBlocks(refPos, visibility, pos, width, height);
			processColour(blocks, colour);			
		}
	}
	
	/**
	 * Take each road and markings, collect its data and process the colour of its blocks
	 * @param refPos the position of the object that runs the sensing process
	 * @param visibility the maximum visibility within the world, at the current point in time
	 * @param roadsArray Roads in the visible range
	 */
	private void processRoads(Double refPos, int visibility, Road[] roadsArray) 
			throws ZeroDimensionException{
		
		for(Road road: roadsArray){
			// Assuming that we don't have a getter from road color, and the color is the same DARK_GRAY
			Color colour = Color.DARK_GRAY;
			Double startPos = road.getStartPos();
			Double endPos = road.getEndPos();
			float width = road.getWidth();	
			if(width <= 0){
				throw new ZeroDimensionException();
			}
			
			ArrayList<Integer[]> blocks = getRoadBlocks(refPos, visibility, startPos, endPos, width);
			processColour(blocks, colour);		
			
			// Check the direction of the road
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
	}
	
	/**
	 * Take each world object, collect its data and process the colour of its blocks
	 * @param refPos the position of the object that runs the sensing process
	 * @param visibility the maximum visibility within the world, at the current point in time
	 * @param intersectionsArray Intersections in World
	 */
	private void processIntersections(Double refPos, int visibility,Intersection[] intertersectionsArray) throws ZeroDimensionException{
		
		for(Intersection intersection: intertersectionsArray){
			
			float height = intersection.length;
			float width = intersection.width;
			if(width <= 0 || height <= 0){
				throw new ZeroDimensionException();
			}
			Double pos = new Double(intersection.pos.x + width/2.0f ,intersection.pos.y + height/2.0f);
						
			// Assuming that we don't have a getter from intersection color, and the colour is the same DARK_GRAY
			Color colour = Color.DARK_GRAY;
			ArrayList<Integer[]> blocks = getObjectBlocks(refPos, visibility, pos, width, height);
			processColour(blocks, colour);
			
			// Assuming that we don't have a getter from intersection line color and width, and the colour is the same LIGHT_GRAY
			float line_width = 1.0f;
			colour = Color.LIGHT_GRAY;
			
			if(line_width <= 0){
				throw new ZeroDimensionException();
			}
			
			// Process the 4 intersections lines translating to center position of the line
			// Top Line
			Double line_pos = new Double(pos.x, pos.y - height/2.0f);			
			ArrayList<Integer[]> blocks_lines = getObjectBlocks(refPos, visibility, line_pos, width, line_width);
			// Bottom Line
			line_pos = new Double(pos.x, pos.y + height/2.0f);
			blocks_lines.addAll(getObjectBlocks(refPos, visibility, line_pos, width, line_width));
			// Left Line
			line_pos = new Double(pos.x - width/2.0f, pos.y);
			blocks_lines.addAll(getObjectBlocks(refPos, visibility, line_pos, line_width, height));
			// Right Line
			line_pos = new Double(pos.x + width/2.0f, pos.y);
			blocks_lines.addAll(getObjectBlocks(refPos, visibility, line_pos, line_width, height));
			
			processColour(blocks_lines, colour);
		}
	}
	

}

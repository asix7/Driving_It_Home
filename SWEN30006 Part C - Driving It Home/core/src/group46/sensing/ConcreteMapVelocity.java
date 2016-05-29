package group46.sensing;

import group46.sensing.exceptions.ZeroDeltaException;
import group46.sensing.exceptions.ZeroDimensionException;
import group46.sensing.exceptions.ZeroVisibilityException;

import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import com.badlogic.gdx.math.Vector2;
import com.unimelb.swen30006.partc.core.objects.WorldObject;
/**
 * Concrete Strategy of IMapVelocity
 * @author Group 46
 */
class ConcreteMapVelocity extends MapGenerator implements IMapVelocity {
	/** Stored Velocity Map */
	private Vector2[][] velocityMap;
	
	// Not stated on design 
	/** Max area stored of each block of the map at any point */
	private float[][] max_area; // Needed to assign the value of a block to the largest object velocity
	/** Reference position of the previous update */
	private Double previous_refPos; // We miss that the car was not part of the World Objects, need to store for next update
	
	/** Previous positions of the visible world objects */
	private HashMap<WorldObject, Double> previousPositions;
	
	public ConcreteMapVelocity()
	{
		previousPositions = new HashMap<WorldObject, Double>();
	}
	
	@Override
	public Vector2[][] generateVelocityMap(Double refPos, int visibility, float delta, WorldObject[] objectArray) 
		   throws ZeroDeltaException, ZeroVisibilityException, ZeroDimensionException {
		
		// Look for exceptions
		if(delta <= 0){
			throw new ZeroDeltaException();
		}
		
		if(visibility <= 0){
			throw new ZeroVisibilityException();
		}
		
		// Create and Initialize velocity map
		velocityMap = new Vector2[visibility][visibility];
		for (int i = 0; i <= visibility - 1; i++)
			for (int j = 0; j <= visibility - 1; j++)
				velocityMap[i][j] = new Vector2(0,0);
		max_area = new float[visibility][visibility];
		
		// Calculates the absolute velocity of the reference object 
		if(previous_refPos == null){
			previous_refPos = new Double(refPos.x, refPos.y);
		}
		Vector2 refVelocity = new Vector2((float)((refPos.x -previous_refPos.x)/delta), 
										 ((float)(refPos.y - previous_refPos.y)/delta));
		// Update the previous reference position 
		previous_refPos = new Double(refPos.x, refPos.y);
		
		// Transform the objects collections to better processing
		WorldObject[] hashMapObjects =  previousPositions.keySet().toArray(new WorldObject[0]);
		ArrayList<WorldObject> objectArrayList = new ArrayList<WorldObject>(Arrays.asList(objectArray));		
		
		// Remove the objects that are not visible anymore
		for(WorldObject object: hashMapObjects){
			if(!objectArrayList.contains(object)){
				previousPositions.remove(object);
			}
		}	
		
		// Take each world object, collect its data and process the relative velocity of its blocks
		for(WorldObject object: objectArray){
			Double pos = object.getPosition();
			float height = object.getLength();
			float width = object.getWidth();
			
			if(height <= 0 || width <= 0){
				throw new ZeroDimensionException();
			}
			Vector2 absVelocity = calculateAbsVelocity(delta, object, pos);
			ArrayList<Integer[]> blocks = getObjectBlocks(refPos, visibility, pos, width, height);
			processVelocity(blocks, refVelocity, absVelocity);			
		}	
		
		return velocityMap;
	}
	
	/**
	 * Calculates the absolute velocity of a WorldObject according to the information
	 * of its position in previous update and the time since it. 
	 * @param delta the times difference since previous update
	 * @param object the reference of WorldObject 
	 * @param pos the current position of the object
	 * @return the absolute velocity of the object, 
	 *  when the object appear for the first time velocity will be set to 0
	 */
	private Vector2 calculateAbsVelocity(float delta, WorldObject object,Double pos){
		// Default value for new  visible objects 
		Vector2 absVelocity = new Vector2(0,0);
		
		// Calculates the absolute velocity and add the position to the Hash Map
		if(previousPositions.containsKey(object)){
			absVelocity.x = (float) (pos.x - previousPositions.get(object).x)/delta; 
			absVelocity.y = (float) (pos.y - previousPositions.get(object).y)/delta; 
		} else {
			previousPositions.put(object, pos);
		}
		return absVelocity;		
	}
	
	/**
	 * A method that takes the blocks indexes where some object its located, the velocities, and 
	 * adds it to the velocityMap the velocity of the largest object
	 * @param blocks The blocks where an object is located
	 * @param refVelocity The absolute velocity of the reference position
	 * @param absVelocity The absolute velocity of the World Object
	 * */
	private void processVelocity(ArrayList<Integer[]> blocks, Vector2 refVelocity, Vector2 absVelocity){
		for (Integer[] block: blocks){
			// Convert from percentage to float
			float area = block[2]/100.0f;
			// Update the value if the Object is large than the one previously stored
			if(area > max_area[block[0]][block[1]]){
					velocityMap[block[0]][block[1]] =  new Vector2( absVelocity.x - refVelocity.x,
																	absVelocity.y - refVelocity.y);
					max_area[block[0]][block[1]] = area;			
			}			  		
		}
	}
}

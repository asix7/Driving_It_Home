package group46.sensing;

import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.unimelb.swen30006.partc.core.objects.Car;
import com.unimelb.swen30006.partc.core.objects.WorldObject;

class ConcreteMapVelocity extends MapGenerator implements IMapVelocity {
	// Map
	private Vector2[][] velocityMap;
	
	private float[][] max_area;
	
	private Double previous_refPos;
	
	// Previous positions of the visible world objects
	private HashMap<WorldObject, Double> previousPositions;
	
	public ConcreteMapVelocity()
	{
		previousPositions = new HashMap<WorldObject, Double>();
	}
	
	@Override
	public Vector2[][] generateVelocityMap(Double refPos, int visibility, float delta, WorldObject[] objectArray) {
		velocityMap = new Vector2[visibility][visibility];
		max_area = new float[visibility][visibility];
		
		if(previous_refPos == null){
			previous_refPos = new Double(refPos.x, refPos.y);
		}
		Vector2 refVelocity = new Vector2((float)((refPos.x -previous_refPos.x)/delta), 
										 ((float)(refPos.y - previous_refPos.y)/delta));
		
		previous_refPos = new Double(refPos.x, refPos.y);
		
		// Inititalize velocity map
		for (int i = 0; i <= visibility - 1; i++)
			for (int j = 0; j <= visibility - 1; j++)
				velocityMap[i][j] = new Vector2(0,0);
		
		
		WorldObject[] objects =  previousPositions.keySet().toArray(new WorldObject[0]);
		ArrayList<WorldObject> objectArrayList = new ArrayList<WorldObject>(Arrays.asList(objectArray));		
		
		for(WorldObject object: objects){
			if(!objectArrayList.contains(object)){
				previousPositions.remove(object);
			}
		}		

		for(WorldObject object: objectArray){
			Double pos = object.getPosition();
			float height = object.getLength();
			float width = object.getWidth();
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
		
		Vector2 absVelocity = new Vector2(0,0);
		
		if(previousPositions.containsKey(object)){
			absVelocity.x = (float) (pos.x - previousPositions.get(object).x)/delta; 
			absVelocity.y = (float) (pos.y - previousPositions.get(object).y)/delta; 
		} else {
			previousPositions.put(object, pos);
		}
		return absVelocity;
		
	}
	private void processVelocity(ArrayList<Integer[]> blocks, Vector2 refVelocity, Vector2 absVelocity){
		for (Integer[] block: blocks){
			float area = block[2]/100.0f;
			if(area > max_area[block[0]][block[1]]){
					velocityMap[block[0]][block[1]] =  new Vector2( absVelocity.x - refVelocity.x,
																	absVelocity.y - refVelocity.y);
					max_area[block[0]][block[1]] = area;			
			}			  		
		}
	}
}

package group46.sensing;

import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.math.Vector2;
import com.unimelb.swen30006.partc.core.objects.WorldObject;

class ConcreteMapVelocity extends MapGenerator implements IMapVelocity {
	// Map
	private Vector2[][] velocityMap;
	
	// Previous positions of the visible world objects
	private HashMap<WorldObject, Double> previousPositions;
	
	@Override
	public Vector2[][] generateVelocityMap(Double refPos, int visibility,WorldObject[] objectArray) {
		// TODO Auto-generated method stub
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
		return null;
		
	}
	private void processVelocity(ArrayList<Integer> blocks, Vector2 refVelocity, Vector2 velocity){
		
	}

}

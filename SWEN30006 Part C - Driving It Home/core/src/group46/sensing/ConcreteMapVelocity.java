package group46.sensing;

import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.math.Vector2;
import com.unimelb.swen30006.partc.core.objects.WorldObject;

class ConcreteMapVelocity extends MapGenerator implements IMapVelocity {
	
	private Vector2[][] velocityMap;
	
	// Previous positions of the visible world objects
	private HashMap<WorldObject, Double> previousPositions;
	
	@Override
	public Vector2[][] generateVelocityMap(Double refPos, int visibility,WorldObject[] objectArray) {
		// TODO Auto-generated method stub
		return velocityMap;
	}
	
	private Vector2 calculateAbsVelocity(float delta, WorldObject object,Double pos){
		return null;
		
	}
	private void processVelocity(ArrayList<Integer> blocks, Vector2 refVelocity, Vector2 velocity){
		
	}

}

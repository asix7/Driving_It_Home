package group46.sensing.wrappers;
import java.awt.geom.Point2D.Double;

import com.badlogic.gdx.graphics.Color;
import java.lang.reflect.Field;
import com.unimelb.swen30006.partc.roads.Road;
import com.unimelb.swen30006.partc.roads.RoadMarking;

/**
 * NEW TO DESIGN
 * Road wrapper class that allows access of object information 
 * Following Mat advice to improve the access to objects data
 * The Map generators will rely on this wrappers so if a change in the 
 * classes is made it is easy to change the way we access data in the future
 * @author Group 46
 **/
public class RoadWrapper {

	private final Road road;
	
	public RoadWrapper(Road road){
		this.road = road;
	}
	
	public Double getStartPos(){
		return road.getStartPos();
	}
	public Double getEndPos(){
		return road.getEndPos();
	}
	
	public float getWidth(){
		return road.getWidth();
	}
	
	public float getLength(){
		return road.getLength();
	}
	
	public RoadMarking[] getMarkings(){
		return road.getMarkers();
	}
	
	// WARNING this relies on a security break, We couldn't find another way to access this
	// data. If in the future we get the correct accessors we then can change this code to 
	// correctly obtain them.
	public Color getColour(){		
		Color colour;
		Field privateColour;
		// Change access to the field, get the value and change the access back to normal
		try {
			privateColour = road.getClass().getDeclaredField("ROAD_COLOUR");
			privateColour.setAccessible(true);
			colour = (Color)privateColour.get(road); 
			privateColour.setAccessible(false);
			return colour;
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}		
		return null;			
	}
}

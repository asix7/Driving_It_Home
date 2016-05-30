package group46.sensing.wrappers;

import java.awt.geom.Point2D;
import java.lang.reflect.Field;

import com.badlogic.gdx.graphics.Color;
import com.unimelb.swen30006.partc.roads.RoadMarking;

/**
 * NEW TO DESIGN
 * Road Marking wrapper class that allows access of object information 
 * Following Mat advice to improve the access to objects data
 * The Map generators will rely on this wrappers so if a change in the 
 * classes is made it is easy to change the way we access data in the future
 * @author Group 46
 *
 */
public class RoadMarkingWrapper {		

	public final RoadMarking roadMarking;
	
	public RoadMarkingWrapper(RoadMarking myRoadMarking){		
        this.roadMarking = myRoadMarking;
    }
	
	public Point2D.Double getPosition(){
		return this.roadMarking.position;
	}
	
	// WARNING this relies on a security break, We couldn't find another way to access this
	// data. If in the future we get the correct accessors we then can change this code to 
	// correctly obtain them.
	public Color getColour(){		
		Color colour;
		Field privateColour;
		// Change access to the field, get the value and change the access back to normal
		try {
			privateColour = roadMarking.getClass().getDeclaredField("MARKING_COLOUR");
			privateColour.setAccessible(true);
			colour = (Color)privateColour.get(roadMarking);
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
	
	// WARNING this relies on a security break, We couldn't find another way to access this
	// data. If in the future we get the correct accessors we then can change this code to 
	// correctly obtain them.
	public float getWidth(){
		Float width;
		Field privateWidth;
		// Change access to the field, get the value and change the access back to normal
		try {
			privateWidth = roadMarking.getClass().getDeclaredField("MARKING_WIDTH");
			privateWidth.setAccessible(true);
			width = (Float)privateWidth.get(roadMarking);
			privateWidth.setAccessible(false);
			return (float)width ;
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}		
		return 0f;	
	}
	
	// WARNING this relies on a security break, We couldn't find another way to access this
	// data. If in the future we get the correct accessors we then can change this code to 
	// correctly obtain them.
	public boolean getHorizontal(){
		Boolean horizontal;
		Field privateHorizontal;
		// Change access to the field, get the value and change the access back to normal
		try {
			privateHorizontal = roadMarking.getClass().getDeclaredField("horizontal");
			privateHorizontal.setAccessible(true);
			horizontal = (Boolean)privateHorizontal.get(roadMarking);
			privateHorizontal.setAccessible(false);
			return (boolean)horizontal ;
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}		
		return false;
	}	
}

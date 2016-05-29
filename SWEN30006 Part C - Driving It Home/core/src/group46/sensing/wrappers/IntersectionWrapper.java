package group46.sensing.wrappers;

import java.awt.geom.Point2D.Double;
import java.lang.reflect.Field;

import com.badlogic.gdx.graphics.Color;
import com.unimelb.swen30006.partc.roads.Intersection;

/**
 * NEW TO DESIGN
 * Intersection wrapper class that allows access of object information 
 * Following Mat advice to improve the access to objects data
 * The Map generators will rely on this wrappers so if a change in the 
 * classes is made it is easy to change the way we access data in the future
 * @author Group 46
 *
 */
public class IntersectionWrapper {
	
	private final Intersection intersection;
		
	public IntersectionWrapper(Intersection intersection){
		this.intersection = intersection;
	}
	
	public Double getPosition(){
		return intersection.pos;
	}
	
	public float getWidth(){
		return intersection.width;
	}
	
	public float getLenght(){
		return intersection.length;
	}
	
	// WARNING this relies on a security break, We couldn't find another way to access this
	// data. If in the future we get the correct accessors we then can change this code to 
	// correctly obtain them.
	public Color getColor(){
		Color colour;
		Field privateColour;
		// Change access to the field, get the value and change the access back to normal
		try {
			privateColour = intersection.getClass().getDeclaredField("INTERSECTION_COLOUR");
			privateColour.setAccessible(true);
			colour = (Color)privateColour.get(intersection); 
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
	public Color getLineColor(){
		Color colour;
		Field privateColour;
		// Change access to the field, get the value and change the access back to normal
		try {
			privateColour = intersection.getClass().getDeclaredField("INTERSECTION_LINE");
			privateColour.setAccessible(true);
			colour = (Color)privateColour.get(intersection); 
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
	public float getLineWidth(){
		Float linewidth;
		Field privateColour;
		// Change access to the field, get the value and change the access back to normal
		try {
			privateColour = intersection.getClass().getDeclaredField("INTERSECTION_LINE_WIDTH");
			privateColour.setAccessible(true);
			linewidth = (Float)privateColour.get(intersection); 
			privateColour.setAccessible(false);
			return (float)linewidth;
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}		
		return 0.0f;	
	}
}

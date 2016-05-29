package group46.sensing.wrappers;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.lang.reflect.Field;

import com.badlogic.gdx.graphics.Color;
import com.unimelb.swen30006.partc.core.objects.WorldObject;
import com.unimelb.swen30006.partc.render_engine.IRenderable;
import com.unimelb.swen30006.partc.roads.RoadMarking;

public class RoadMarkingWrapper {
	
	
//	// Private constants
//		private static final Color MARKING_COLOUR = Color.LIGHT_GRAY;
//		private static final float MARKING_WIDTH = 1f;
//	public final Point2D.Double position;
//	private final boolean horizontal;
//	
	
	public final RoadMarking roadMarking;
	
	public RoadMarkingWrapper(RoadMarking myRoadMarking){
		
        this.roadMarking = myRoadMarking;
    }
	
	
	public Color getColour(){		
		Color colour;
		Field privateColour;
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
	
	public float getWidth(){
		Float width;
		Field privateWidth;
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
	
	public Point2D.Double getPosition(){
		return this.roadMarking.position;
	}
	
	public boolean getHorizontal(){
		Boolean horizontal;
		Field privateHorizontal;
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

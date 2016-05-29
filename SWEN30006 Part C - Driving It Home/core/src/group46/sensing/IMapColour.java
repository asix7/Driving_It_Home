package group46.sensing;

import group46.sensing.exceptions.ZeroDimensionException;
import group46.sensing.exceptions.ZeroVisibilityException;

import java.awt.geom.Point2D.Double;

import com.badlogic.gdx.graphics.Color;
import com.unimelb.swen30006.partc.core.objects.WorldObject;
import com.unimelb.swen30006.partc.roads.Intersection;
import com.unimelb.swen30006.partc.roads.Road;
/**
 * Colour Map Generator Interface 
 * @author Group 46
 */
public interface IMapColour {
	/**
	 * Returns a two dimensional array of the additive colour of everything in a given position 
	 * around the current user. 
	 * @param refPos the position of the object that runs the sensing process
	 * @param visibility the maximum visibility within the world, at the current point in time
	 * @param objectArray World Objects in the visible range
	 * @param roadsArray Roads in the visible range
	 * New arguments different to design
	 * @param intersectionsArray Intersections in World, updated code on May 16 to access them
	 * @param environmentColour default colour of the environment, we dind't think about a value when a block was empty 
	 * @return the calculated colour map
	 */
	public Color[][] generateColourMap(Double refPos, int visibility, WorldObject[] objectArray, Road[] roadsArray, 
										Intersection[] intertersectionsArray, Color environmentColour) throws ZeroVisibilityException, ZeroDimensionException;
	
}

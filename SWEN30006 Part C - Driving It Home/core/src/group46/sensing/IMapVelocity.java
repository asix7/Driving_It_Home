package group46.sensing;

import group46.sensing.exceptions.ZeroDeltaException;
import group46.sensing.exceptions.ZeroDimensionException;
import group46.sensing.exceptions.ZeroVisibilityException;

import java.awt.geom.Point2D.Double;

import com.badlogic.gdx.math.Vector2;
import com.unimelb.swen30006.partc.core.objects.WorldObject;
/**
 * Velocity Map Generator Interface 
 * @author Group 46
 */
public interface IMapVelocity {
	
	/**
     * Returns a two dimensional array containing the current relative velocity of any objects
     * within the space. If there are no WorldObjects in the space, the velocity should be 0.
     * If there is a world object in the space, the velocity should be the relative velocity
     * of that object compared to the object we are tracking. As an example, an object that
     * has the same velocity and is moving along side a car would have a relative velocity of 0, 
     * and the velocity of an object moving toward us will have a relative velocity of our 
     * velocity plus their velocity towards us.
	 * @param refPos the position of the object that runs the sensing process
	 * @param visibility the maximum visibility within the world, at the current point in time
	 * @param objectArray World Objects in the visible range
	 * @return the calculated Velocity Map
	 */
	public Vector2[][] generateVelocityMap(Double refPos, int visibility, float delta, WorldObject [] objectArray) 
			throws ZeroDeltaException,ZeroVisibilityException,ZeroDimensionException ;

}

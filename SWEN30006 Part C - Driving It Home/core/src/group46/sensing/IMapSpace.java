package group46.sensing;

import group46.sensing.exceptions.ZeroVisibilityException;

import java.awt.geom.Point2D.Double;

import com.unimelb.swen30006.partc.core.objects.WorldObject;
/**
 * Space Map Generator Interface 
 * @author Group 46
 */
public interface IMapSpace {
	/**
	 * Returns a two dimensional array representing whether the space is
	 * or isn't occupied by another collidable World Object.
	 * @param refPos the position of the object that runs the sensing process
	 * @param visibility the maximum visibility within the world, at the current point in time
	 * @param objectArray World Objects in the visible range
	 * @return the calculated space map
	 */
	public boolean[][] generateSpaceMap(Double refPos, int visibility, WorldObject [] objectArray) throws ZeroVisibilityException;

}

package group46.sensing;

import java.awt.geom.Point2D.Double;

import com.unimelb.swen30006.partc.core.objects.WorldObject;

public interface IMapSpace {
	
	public boolean[][] generateSpaceMap(Double refPos, int visibility, WorldObject [] objectArray);

}

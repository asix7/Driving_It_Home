package group46.sensing;

import java.awt.geom.Point2D.Double;

import com.badlogic.gdx.graphics.Color;
import com.unimelb.swen30006.partc.core.objects.WorldObject;
import com.unimelb.swen30006.partc.roads.Road;

public interface IMapColour {
	
	public Color[][] generateColourMap(Double refPos, int visibility, WorldObject [ ] objectArray, Road[] roadsArray, Color environmentColour);
	
}

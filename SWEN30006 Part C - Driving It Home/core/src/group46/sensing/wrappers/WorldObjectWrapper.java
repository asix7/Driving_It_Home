package group46.sensing.wrappers;

import java.awt.geom.Point2D;
import com.badlogic.gdx.graphics.Color;
import com.unimelb.swen30006.partc.core.objects.WorldObject;

/**
 * NEW TO DESIGN
 * WorldObjects wrapper class that allows access of object data
 * Following Mat advice to improve the access to objects data
 * The Map generators will rely on this wrappers so if a change in the 
 * classes is made it is easy to change the way we access data in the future
 * @author Group 46
 */
public class WorldObjectWrapper {	
	
	public final WorldObject worldObject;
	
	public WorldObjectWrapper(WorldObject myWorldObject){
		
        this.worldObject = myWorldObject;
    }
	
	public WorldObject getWorldObject(){
		return this.worldObject;
	}
	
	public Point2D.Double getPosition(){
		return this.worldObject.getPosition();
	}
	
	public float getWidth(){
		return this.worldObject.getWidth() ;
	}
	
	public float getLength(){
		return this.worldObject.getLength();
	}
	
	public Color getColor(){
		return this.worldObject.getColour() ;
	}
	
}

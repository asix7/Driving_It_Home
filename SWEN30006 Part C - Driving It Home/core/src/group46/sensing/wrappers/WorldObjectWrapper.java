package group46.sensing.wrappers;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import com.badlogic.gdx.graphics.Color;
import com.unimelb.swen30006.partc.core.objects.WorldObject;
import com.unimelb.swen30006.partc.render_engine.IRenderable;

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

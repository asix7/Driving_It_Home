package group46.sensing;

import group46.sensing.exceptions.ZeroDeltaException;
import group46.sensing.exceptions.ZeroVisibilityException;

import java.awt.geom.Rectangle2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.unimelb.swen30006.partc.ai.interfaces.ISensing;
import com.unimelb.swen30006.partc.core.World;
import com.unimelb.swen30006.partc.core.objects.WorldObject;
import com.unimelb.swen30006.partc.roads.Intersection;
import com.unimelb.swen30006.partc.roads.Road;

/**
 *  The concrete implementation of ISensing Interface to generate Space, Colour and Velocity Maps 
 * @author Group 46
 */
public class ConcreteSensor implements ISensing {

	// Reference to the world object
	private World world;
	
	// Maps
	private Vector2[][] velocityMap;
	private boolean[][] spaceMap;
	private Color[][] colourMap;
	
	// Map generators strategies
	private IMapVelocity velocityStrategy;
	private IMapSpace spaceStrategy;
	private IMapColour colourStrategy;
	
	/**
	 * Default constructor for ConcreteSensor, assign the default strategies
	 * @param world a reference to the world
	 * @param refObject the WorldObject that it is controlled by AI 
	 * **/
	public ConcreteSensor(World world){
		
		this.world = world;
		velocityStrategy = new ConcreteMapVelocity();
		spaceStrategy = new ConcreteMapSpace();
		colourStrategy = new ConcreteMapColour();		
	}
	
	/**
	 * Alternative constructor for ConcreteSensor, let the developer select other strategies
	 * @param world a reference to the world
	 * @param velocityStrategy concrete strategy selected to generate the velocity maps
	 * @param spaceStrategy concrete strategy selected to generate the space maps
	 * @param colourStrategy concrete strategy selected to generate the colour maps
	 * **/
	public ConcreteSensor(World world, IMapVelocity velocityStrategy, IMapSpace spaceStrategy, IMapColour colourStrategy){		
		this.world = world;
		this.velocityStrategy = velocityStrategy;
		this.spaceStrategy = spaceStrategy;
		this.colourStrategy = colourStrategy;		
	}
	
	@Override
	public boolean update(Double pos, float delta, int visibility) {		
		try{
			// Gets the object from world
			WorldObject[] objectsArray = world.objectsAtPoint(pos);	
			Road[] roadsArray = world.roadsAroundPoint(pos);
			Color environmentColour = world.getEnvironmentColour();
			Intersection[] intertersectionsArray = world.getIntersections();
			// Updates the maps
			velocityMap = velocityStrategy.generateVelocityMap(pos, visibility, delta, objectsArray);
			spaceMap= spaceStrategy.generateSpaceMap(pos, visibility, objectsArray);
			colourMap = colourStrategy.generateColourMap(pos, visibility, objectsArray, roadsArray, intertersectionsArray,environmentColour);
		}
		catch(Exception e) {
			return false;			
		}
		return true;
	}

	@Override
	public Vector2[][] getVelocityMap() {
		return velocityMap;
	}

	@Override
	public boolean[][] getSpaceMap() {
		return spaceMap;
	}

	@Override
	public Color[][] getColourMap() {
		return colourMap;
	}

}

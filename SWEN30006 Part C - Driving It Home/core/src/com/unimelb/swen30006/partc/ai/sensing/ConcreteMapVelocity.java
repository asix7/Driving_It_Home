package com.unimelb.swen30006.partc.ai.sensing;

import java.awt.geom.Point2D.Double;

import com.badlogic.gdx.math.Vector2;
import com.unimelb.swen30006.partc.core.objects.Car;
import com.unimelb.swen30006.partc.core.objects.WorldObject;

public class ConcreteMapVelocity extends MapGenerator implements IMapVelocity {
	
	private Vector2 refVelocity;
	
	private Vector2[][] velocityMap;
	
	public ConcreteMapVelocity(WorldObject refObject){
		// Check if the object is a car and get its velocity
		if(refObject.getClass().equals(Car.class)){
			Car car = (Car)refObject;
			refVelocity = car.getVelocity();					
		}
		else {
			refVelocity = new Vector2(0,0);
		}
	}

	@Override
	public Vector2[][] generateVelocityMap(Double refPos, int visibility,WorldObject[] objectArray) {
		// TODO Auto-generated method stub
		return null;
	}

}

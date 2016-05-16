package group46.sensing;

import java.awt.geom.Point2D.Double;
import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.unimelb.swen30006.partc.core.objects.WorldObject;
import com.unimelb.swen30006.partc.roads.Road;

class ConcreteMapColour extends MapGenerator implements IMapColour  {

	private Color[][] colourMap;

	@Override
	public Color[][] generateColourMap(Double refPos, int visibility, WorldObject[] objectArray, Road[] roadsArray) {
		// TODO Auto-generated method stub
		return colourMap;
	}
	
	private ArrayList<Integer> getRoadBlocks(Double refPos, int visibility, Double startPos,Double endPos, float width){
		return null;
	}
	private void processColour(ArrayList<Integer> blocks, Color colour){
		
	}
	

}

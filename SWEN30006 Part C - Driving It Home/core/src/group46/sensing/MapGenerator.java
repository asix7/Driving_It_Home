package group46.sensing;

import java.awt.geom.Point2D.Double;
import java.util.ArrayList;

class MapGenerator {
	
	/**
	 * A method that takes the information of an object and gets the indexes of the map array 
	 * corresponding to it.
	 * @param refPos the reference position at the center of the map
	 * @param visibility the range of blocks from the reference position
	 * @param pos the center position of the object to be mapped
	 * @param width the width of the object
	 * @param height the height of the object
	 * @return the indexes of an array of the blocks that the object belongs to
	 * */
	protected ArrayList<Integer[]> getObjectBlocks(Double refPos, int visibility, Double pos, float width, float height){
		ArrayList<Integer[]> indexes = new ArrayList<Integer[]>();
		float abs_x = (float) (visibility/2.0 + (pos.x - refPos.x));
		float abs_y = (float) (visibility/2.0 + (pos.y - refPos.y));
		
		int top = Math.max(0, (int) Math.floor(abs_y - height/2.0));
		int bot = Math.min(visibility - 1, (int) Math.ceil(abs_y + height/2.0));
		int left = Math.max(0, (int) Math.floor(abs_x - width/2.0));
		int right = Math.min(visibility - 1, (int) Math.ceil(abs_x + width/2.0));
		
		// Assuming the object is shaped as a rectangle
		for(int i = top; i < bot; i++){
			for(int j = left; j < right; j++){
				//
				float block_top = (float) Math.max(i,abs_y - height/2.0);
				float block_bot = (float) Math.min(i + 1,abs_y + height/2.0);
				float block_left = (float) Math.max(j,abs_x - width/2.0);
				float block_right = (float) Math.min(j + 1,abs_x + width/2.0);
				// Calculate the percentage area of the object compare to the block
				int area = Math.round((block_bot - block_top)*(block_right - block_left)*100);
				Integer[] block = {i,j,area};
				indexes.add(block);
			}
		}
		
		return indexes;	 
	}
}

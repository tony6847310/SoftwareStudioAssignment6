package main.java;

import processing.core.PApplet;
import processing.data.JSONArray;
import processing.data.JSONObject;

/**
* This class is for sketching outcome using Processing
* You can do major UI control and some visualization in this class.  
*/
@SuppressWarnings("serial")
public class MainApplet extends PApplet{
	private String path = "main/resources/";
	private String file = "starwars-episode-1-interactions.json";
	
	private final static int width = 1200, height = 650;
	/* 
	 * Modified by Tony
	 */
	private JSONObject ep1;
	private JSONArray ep1_nodes;
	private JSONArray ep1_links;
	
	public void setup() {

		size(width, height);
		smooth();
		loadData();
		
	}

	public void draw() {
		
	}

	private void loadData(){
		ep1 = loadJSONObject(path + file);
		ep1_nodes = ep1.getJSONArray("nodes");
		ep1_links = ep1.getJSONArray("links");
	}

}

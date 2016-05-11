package main.java;

import processing.core.PApplet;
import processing.data.JSONArray;
import processing.data.JSONObject;
import java.util.*;

/**
* This class is for sketching outcome using Processing
* You can do major UI control and some visualization in this class.  
*/
@SuppressWarnings("serial")
public class MainApplet extends PApplet{
	private String path = "main/resources/";
	private String fileNameLeft = "starwars-episode-";
	private String fileNameRight = "-interactions.json";
	
	private final static int width = 1200, height = 650;
	/* 
	 * Modified by Tony
	 */
	private JSONObject[] content;
	private JSONArray[] nodes;
	private JSONArray[] links;
	private ArrayList<Character> characters;
	
	public void setup() {
		characters = new ArrayList<Character>();
		
		size(width, height);
		smooth();
		loadData();
	}

	public void draw() {
		background(255);
		
		noStroke();
		for(Character c: this.characters){
			c.display();
		}
	}

	private void loadData(){
		for(int i=0; i<7; i++){
			content[i] = loadJSONObject(path + fileNameLeft + i + fileNameRight);
			nodes[i] = content[i].getJSONArray("nodes");
			links[i] = content[i].getJSONArray("links");
		}
		for(int i=0 ; i<ep1_nodes.size(); i++){
			JSONObject node = ep1_nodes.getJSONObject(i);
			int color = unhex(node.getString("colour").substring(1, 8));
			//convert hex string to int
			characters.add(new Character(this, node.getString("name"), color, i%4*55 +50, i/4*55 + 50));
			//3 character each row
		}

		for(int i=0; i<ep1_links.size(); i++){
			JSONObject link = ep1_links.getJSONObject(i);
			characters.get(link.getInt("source")).addTarget(characters.get(link.getInt("target")));
			characters.get(link.getInt("source")).scenes.put(
					characters.get(link.getInt("target")).name, link.getInt("value"));
			//record scenes with each other
		}
	}

}

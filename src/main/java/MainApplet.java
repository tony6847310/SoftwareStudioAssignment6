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
	//private String file = "starwars-episode-1-interactions.json";
	
	private final static int width = 1200, height = 650;
	/* 
	 * Modified by Tony
	 */
	private JSONObject[] content = new JSONObject[8];
	private JSONArray[] nodes = new JSONArray[8];
	private JSONArray[] links = new JSONArray[8];
	private ArrayList<Character> characters;
	private int episode = 1;
	
	public void setup() {
		characters = new ArrayList<Character>();
		
		size(width, height);
		smooth();
		loadData();
		loadEpisode();
	}

	public void draw() {
		background(255);
		
		noStroke();
		for(Character c: this.characters){
			c.display();
		}
	}

	private void loadData(){
		//read all 7 episodes
		for(int i=1; i<=7; i++){
			content[i] = loadJSONObject(path + fileNameLeft + Integer.toString(i) + fileNameRight);
			nodes[i] = content[i].getJSONArray("nodes");
			links[i] = content[i].getJSONArray("links");
		}
	}
	
	private void loadEpisode(){
		//clear before reloading
		characters.clear();
		for(int i=0 ; i<nodes[episode].size() ; i++){
			JSONObject node = nodes[episode].getJSONObject(i);
			int color = unhex(node.getString("colour").replace("#", ""));
			//convert hex string to int
			characters.add(new Character(this, node.getString("name"), color, i%4*55 +50, i/4*55 + 50));
			//add 4 character each row
		}
		
		for(int i=0; i<links[episode].size(); i++){
			JSONObject link = links[episode].getJSONObject(i);
			characters.get(link.getInt("source")).addTarget(characters.get(link.getInt("target")), link.getInt("value"));
			//record scenes between each character
		}
	}

}

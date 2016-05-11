package main.java;

import processing.core.PApplet;
import processing.data.JSONArray;
import processing.data.JSONObject;
import controlP5.ControlP5;
import ddf.minim.Minim;
import de.looksgood.ani.Ani;
import java.awt.event.KeyEvent;

import java.util.*;

/**
* This class is for sketching outcome using Processing
* You can do major UI control and some visualization in this class.  
*/
@SuppressWarnings("serial")
public class MainApplet extends PApplet {
	private String path = "main/resources/";
	private String fileNameLeft = "starwars-episode-";
	private String fileNameRight = "-interactions.json";
	private final static int width = 1200, height = 650;
	/* 
	 * Modified by Tony
	 */
	private JSONObject[] content = new JSONObject[8];
	private JSONArray[] nodes = new JSONArray[8];
	private JSONArray[] links = new JSONArray[8];
	private ArrayList<Character> characters;
	private int episode = 1;
	private boolean pointingnode;
	private Character pointednode, pointingnodebutpressed;
	private Minim mn;
	private ControlP5 cp;
	private Ani ani;
	
	public void setup() {
		characters = new ArrayList<Character>();
		
		size(width, height);
		smooth();
		loadData();
		loadEpisode();
		cp = new ControlP5(this);
		cp.addButton("addAll").setPosition(900, 100).setSize(200, 75).setLabel("Add all");
		cp.addButton("clear").setPosition(900, 200).setSize(200, 75).setLabel("Clear");
		Ani.init(this);
	}

	public void draw() {
		background(255);
		//draw the circle
		fill(255);
		stroke(83, 198, 140);
		strokeWeight(5);
		ellipse(575, 340, 520, 520);
		//display text
		fill(100, 50, 25);
		textSize(30);
		text("Star Wars episode" + Integer.toString(episode), 440, 55);
		//draw character nodes
		for(Character c: this.characters){
			c.display();
			if(dist(c.cur_X, c.cur_Y, mouseX, mouseY) < c.radius && !mousePressed){
				pointingnodebutpressed = c;
			}
			else {
				ani = Ani.to(c, (float)0.5, "radius", c.radius);
			}
		}
		for(Character c: this.characters){
			if(dist(c.cur_X, c.cur_Y, mouseX, mouseY) < c.radius){
				pointingnode = true;
				break;
			}
			else {
				pointingnode = false;
			}
		}
		
		if(pointingnodebutpressed != null){
			if(dist(pointingnodebutpressed.cur_X, pointingnodebutpressed.cur_Y, mouseX, mouseY) < pointingnodebutpressed.radius){
				ani = Ani.to(pointingnodebutpressed, (float)0.5, "radius", 30);
				fill(0, 255, 187);
				rect(mouseX, mouseY-20, pointingnodebutpressed.getName().length()*20, 40, 20);
				fill(255, 213, 0);
				textSize(20);
				text(pointingnodebutpressed.getName(), mouseX+10, mouseY+5);
			}
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
			int color = unhex(node.getString("colour").substring(1));
			//convert hex string to int
			characters.add(new Character(this, node.getString("name"), color, i%4*60 +50, i/4*60 + 50));
			//add 4 character each row
		}
		
		for(int i=0; i<links[episode].size(); i++){
			JSONObject link = links[episode].getJSONObject(i);
			characters.get(link.getInt("source")).addTarget(characters.get(link.getInt("target")), link.getInt("value"));
			//record scenes between each character
		}
	}
	public void keyPressed(){
       if(keyCode == KeyEvent.VK_1)
           episode = 1;
       else if(keyCode == KeyEvent.VK_2)
           episode = 2;
       else if(keyCode == KeyEvent.VK_3)
           episode = 3;
       else if(keyCode == KeyEvent.VK_4)
           episode = 4;
       else if(keyCode == KeyEvent.VK_5)
           episode = 5;
       else if(keyCode == KeyEvent.VK_6)
           episode = 6;
       else if(keyCode == KeyEvent.VK_7)
           episode = 7;
      
       loadEpisode();
	}
 
	public void addAll() {
		for(Character c: characters) {
			c.activate(true);
		}
		
		rearrange();
	}
	
	public void clear() {
		for(Character c: characters) {
			c.activate(false);
			ani = Ani.to(c, (float)1, "cur_X", c.getOriPosX());
			ani = Ani.to(c, (float)1, "cur_Y", c.getOriPosY());
		}
		//rearrange();
	}
	
	public void mousePressed(){
		if(pointingnode)
			pointednode = pointingnodebutpressed;
	}
	
	public void mouseDragged(){
		if(pointednode != null){
			pointednode.cur_X = mouseX;
			pointednode.cur_Y = mouseY;
		}
	}
	
	public void mouseReleased(){
		if(pointednode != null){
			if(dist(pointednode.cur_X, pointednode.cur_Y, 575, 340) < 260){
				pointednode.activate(true);
			} else {
				pointednode.activate(false);
				ani = Ani.to(pointednode, (float)0.5, "cur_X", pointednode.getOriPosX());
				ani = Ani.to(pointednode, (float)0.5, "cur_Y", pointednode.getOriPosY());
			}
			rearrange();
		}
		pointednode = null;
	}
	
	private void rearrange(){
		int count = 0;
		float angle = 0;
		
		for(Character c : characters){
			if(c.checkActivated())
				count++;
			else
				c.setPos(c.getOriPosX(), c.getOriPosY());
		}
		
		for (Character c : characters) {
			float x,y;
			if (c.checkActivated()) {
				x = 575 + 260 * cos(angle);
				y = 340 - 260 * sin(angle);
				c.setPos(x, y);
				angle += (TWO_PI / (float) count);
			}
		}
	}
}

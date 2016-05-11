package main.java;

import java.util.ArrayList;

/**
* This class is used to store states of the characters in the program.
* You will need to declare other variables depending on your implementation.
*/
public class Character {
	
	private MainApplet parent;
	private static final float ellipse_X = 575, ellipse_Y = 340;

	private int color;
	private float ori_X,ori_Y;
	public float cur_X,cur_Y;
	public float radius = 25;
	private ArrayList<Character> targets;
	private ArrayList<Integer> scenes;
	private boolean activated;
	private String name;
	// initial character
	public Character(MainApplet parent, String name, int color, float x, float y){
		this.scenes = new ArrayList<Integer>();
		this.targets = new ArrayList<Character>();
		this.activated = false;
		this.parent = parent;
		this.name = name;
		this.color = color;
		this.ori_X = x;
		this.ori_Y = y;
		this.cur_X = x;
		this.cur_Y = y;
	}
	
	public void display(){
		this.parent.noStroke();
		this.parent.fill(color);
		this.parent.ellipse(cur_X, cur_Y, radius*2 , radius*2 );
		//If this node is activated , draw connect lines with other nodes
        if(activated){
           for(int i=0 ; i<targets.size(); i++){
               if(targets.get(i).checkActivated()){
                   float s1 = (ellipse_X + (cur_X + targets.get(i).cur_X) / 2) / 2;
                   float s2 = (ellipse_Y + (cur_Y + targets.get(i).cur_Y) / 2) / 2;
                   parent.noFill();
                   parent.stroke(51, 51, 51);
                   parent.strokeWeight(scenes.get(i) / 10 + 1);
                   parent.bezier(cur_X, cur_Y, s1, s2, s1, s2, 
                   targets.get(i).cur_X, targets.get(i).cur_Y);
               }
           }
       }
	}
	// set characters' targets and their relationship value
	public void addTarget(Character target,Integer value){
		this.targets.add(target);
		this.scenes.add(value);
	}
	// get characters' targets
	public ArrayList<Character> getTargets(){
		return this.targets;
	}
	// set character's position
	public void setPos(float x, float y){
		this.cur_X = x;
		this.cur_Y = y;
	}
	// set whether the node is put on the circle
	public void activate(boolean b){
		this.activated = b;
	}
	// get whether the node is put on the circle
	public boolean checkActivated(){
		return activated;
	}
	// get character's original X point
	public float getOriPosX(){
		return ori_X;
	}
	// get character's original Y point
	public float getOriPosY(){
		return ori_Y;
	}
	// get character's name
	public String getName(){
		return name;
	}
}

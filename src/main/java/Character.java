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
	private float cur_X,cur_Y;
	private float radius = 25;
	private ArrayList<Character> targets;
	private ArrayList<Integer> scenes;
	private boolean activated;
	private String name;
	
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
	
	public void addTarget(Character target,Integer value){
		this.targets.add(target);
		this.scenes.add(value);
	}
	
	public ArrayList<Character> getTargets(){
		return this.targets;
	}
	
	public void setPos(float x, float y){
		this.cur_X = x;
		this.cur_Y = y;
	}
	
	public void activate(boolean b){
		this.activated = b;
	}
	
	public boolean checkActivated(){
		return activated;
	}
	
	public float getOriPosX(){
		return ori_X;
	}
	
	public float getOriPosY(){
		return ori_Y;
	}
	
	public String getName(){
		return name;
	}
}

package main.java;

import java.util.ArrayList;

/**
* This class is used to store states of the characters in the program.
* You will need to declare other variables depending on your implementation.
*/
public class Character {
	
	private MainApplet parent;
	
	private int color;
	private float x,y;
	private float radius = 50;
	private ArrayList<Character> targets;
	public ArrayList<Integer> scenes;
	public String name;
	
	public Character(MainApplet parent, String name, int color, float x, float y){
		this.scenes = new ArrayList<Integer>();
		this.targets = new ArrayList<Character>();
		this.parent = parent;
		this.name = name;
		this.color = color;
		this.x = x;
		this.y = y;
	}

	public void display(){
		this.parent.fill(color);
		this.parent.ellipse(x, y, radius, radius);
	}
	
	public void addTarget(Character target,Integer value){
		this.targets.add(target);
		this.scenes.add(value);
	}
	
	public ArrayList<Character> getTargets(){
		return this.targets;
	}
}

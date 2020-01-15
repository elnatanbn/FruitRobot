package gameClient;

import Server.Game_Server;
import Server.fruits;
import Server.game_service;
import Server.robot;
import dataStructure.DGraph;
import oop_utils.OOP_Point3D;
import utils.Point3D;

public class Fruit {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double value;
	private OOP_Point3D p;
	private int type;

	public Fruit(String s) {
		String f = s.substring(s.indexOf("value")+7, s.indexOf(","));
		this.value = Double.parseDouble(f);
		f = s.substring(s.indexOf("type")+6, s.indexOf("pos")-2);
		this.type = Integer.parseInt(f);
		f = s.substring(s.indexOf("pos")+6, s.indexOf("}")-1);
		this.p = new OOP_Point3D(f);
	}

	public static void main(String[] args) {

		game_service game = Game_Server.getServer(2); // you have [0,23] games
		String g = game.getFruits().get(0);
		Fruit f = new Fruit(g);
	}


	public OOP_Point3D getLocation() {
		return this.p;
	}

	
	public int getType() {
		return this.type;
	}

	
	public double getValue() {
		return this.value;
	}

	
	public String toString() {
		return this.value+"";
	}
}

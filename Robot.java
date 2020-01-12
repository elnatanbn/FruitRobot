package gameClient;

import Server.Game_Server;
import Server.game_service;
import Server.robot;
import oop_utils.OOP_Point3D;

public class Robot implements robot{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double value;
	private double speed;
	private int id;
	private OOP_Point3D p;

	private int src;
	private int dest;

	public static void main(String[] args) {
		game_service game = Game_Server.getServer(4);
		game.addRobot(0);
		System.out.println(game.getRobots().get(0));

		Robot r = new Robot(game.getRobots().get(0));

	}

	public Robot(String s) {
		String f = s.substring(s.indexOf("id")+4, s.indexOf(","));
		this.id = Integer.parseInt(f);
		System.out.println(this.id); 

		f = s.substring(s.indexOf("value")+7, s.indexOf("src")-2);
		this.value = Double.parseDouble(f);
		System.out.println(this.value); 

		f = s.substring(s.indexOf("src")+5, s.indexOf("dest")-2);
		this.src = Integer.parseInt(f);
		System.out.println(this.src);

		f = s.substring(s.indexOf("dest")+6, s.indexOf("speed")-2);
		this.dest = Integer.parseInt(f);
		System.out.println(this.dest);

		f = s.substring(s.indexOf("speed")+7, s.indexOf("pos")-2);
		this.speed = Double.parseDouble(f);	
		System.out.println(this.speed);

		f = s.substring(s.indexOf("pos")+6, s.indexOf("}")-1);
		this.p = new OOP_Point3D(f);
		System.out.println(p);

	}

	@Override
	public void addMoney(double arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public double doubleSpeedWeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getBatLevel() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getID() {
		return this.id;
	}

	@Override
	public OOP_Point3D getLocation() {
		return this.p;
	}

	public void setLocation(OOP_Point3D p) {
		 this.p = p;
	}
	
	@Override
	public double getMoney() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNextNode() {
		return this.dest;
	}

	@Override
	public double getSpeed() {
		return this.speed;

	}

	@Override
	public int getSrcNode() {
		return this.src;
	}

	@Override
	public boolean isMoving() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean move() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void randomWalk() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setDoubleSpeedWeight(double arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean setNextNode(int arg0) {
		this.dest = arg0;
		return true;
	}

	@Override
	public void setSpeed(double arg0) {
		this.speed = arg0;		
	}

	@Override
	public void setTurboSpeedWeight(double arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public double turboSpeedWeight() {
		// TODO Auto-generated method stub
		return 0;
	}

}

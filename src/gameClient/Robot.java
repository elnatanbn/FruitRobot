package gameClient;

import dataStructure.graph;
import oop_utils.OOP_Point3D;

public class Robot {

	private double value;
	private double speed;
	private int way = -1;
	private int id;
	private OOP_Point3D p;
	private int  src;
	private  int dest;
	public static void main(String[] args) {


	}

	public Robot(String s , graph g) {
		String f = s.substring(s.indexOf("id")+4, s.indexOf(","));
		this.id = Integer.parseInt(f);

		f = s.substring(s.indexOf("value")+7, s.indexOf("src")-2);
		this.value = Double.parseDouble(f);

		f = s.substring(s.indexOf("src")+5, s.indexOf("dest")-2);
		this.src = Integer.parseInt(f);

		f = s.substring(s.indexOf("dest")+6, s.indexOf("speed")-2);
		this.dest = Integer.parseInt(f);

		f = s.substring(s.indexOf("speed")+7, s.indexOf("pos")-2);
		this.speed = Double.parseDouble(f);

		f = s.substring(s.indexOf("pos")+6, s.indexOf("}")-1);
		this.p = new OOP_Point3D(f);
	}

	public int getID() {
		return this.id;
	}

	public double getvalue() {
		return this.value;
	}

	public OOP_Point3D getLocation() {
		return this.p;
	}

	public int getNextNode() {
		return this.dest;
	}


	public int getSrcNode() {
		return this.src;
	}

	public void  setSrcNode(int src) {
		this.src = src;
	}

	public int getway() {
		return this.way;
	}

	public void setway(int way) {
		this.way = way;
	}

}

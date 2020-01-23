package gameClient;

import dataStructure.graph;
import oop_utils.OOP_Point3D;
/**
 * This class represents a Robot object for the game play 
 * @author OfirBador & ElnatanBerenson
 */
public class Robot {
	private double value;
	private int way = -1;
	private int id;
	private OOP_Point3D p;
	private int  src;
	private  int dest;
	/**
	 * Robot Constructor initialized info from String
	 */
	public Robot(String s , graph g) {
		String f = s.substring(s.indexOf("id")+4, s.indexOf(","));
		this.id = Integer.parseInt(f);

		f = s.substring(s.indexOf("value")+7, s.indexOf("src")-2);
		this.value = Double.parseDouble(f);

		f = s.substring(s.indexOf("src")+5, s.indexOf("dest")-2);
		this.src = Integer.parseInt(f);

		f = s.substring(s.indexOf("dest")+6, s.indexOf("speed")-2);
		this.dest = Integer.parseInt(f);

		f = s.substring(s.indexOf("pos")+6, s.indexOf("}")-1);
		this.p = new OOP_Point3D(f);
	}
	/**
	 * Each robot has is own id
	 * @return the Robot id
	 */
	public int getID() {
		return this.id;
	}
	/**
	 *@return the Robot value 
	 */
	public double getvalue() {
		return this.value;
	}
	/**
	 *@return the Robot location 3Dpoint(x,y,z) 
	 */
	public OOP_Point3D getLocation() {
		return this.p;
	}
	/**
	 *@return the Robot destination 
	 */
	public int getNextNode() {
		return this.dest;
	}
	/**
	 *@return the Robot start location 
	 */
	public int getSrcNode() {
		return this.src;
	}
	/**
	 *sets the Robot start location 
	 */
	public void  setSrcNode(int src) {
		this.src = src;
	}
	/**
	 *@return the Robot way to go to (only for info)
	 */
	public int getway() {
		return this.way;
	}
	/**
	 *sets the Robot Robot way to go to 
	 */
	public void setway(int way) {
		this.way = way;
	}
}

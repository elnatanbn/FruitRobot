package gameClient;



import dataStructure.Node;
import dataStructure.graph;
import dataStructure.node_data;
import oop_utils.OOP_Point3D;

public class Robot {
	
	private double value;
	private double speed;
	public int id;
	private OOP_Point3D p;
	private OOP_Point3D lastlocation;
	private int  src;
	private node_data  srcNode;
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
		this.srcNode = g.getNode(this.src);
		
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

	public  void setID(int k) {
		 this.id = k;
	}
	
	public OOP_Point3D getLocation() {
		return this.p;
	}
	
	public OOP_Point3D getlastLocation() {
		return this.lastlocation;
	}
	
	public void setlastLocation(OOP_Point3D p) {
		this.lastlocation = p;
	}
	
	public void setLocation(OOP_Point3D p) {
		 this.p = p;
	}
	
	public int getNextNode() {
		return this.dest;
	}

	public boolean setNextNode(int arg0) {
		this.dest = arg0;
		return true;
	}
	
	public int getSrcNode() {
		return this.src;
	}

	public int setSrcNode() {
		return this.src;
	}
}

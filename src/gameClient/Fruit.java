package gameClient;

import Server.game_service;
import dataStructure.DGraph;
import dataStructure.edge_data;
import dataStructure.graph;
import oop_utils.OOP_Point3D;
import utils.Point3D;
/**
 * This class represents a fruit object for the game play 
 * @author OfirBador & ElnatanBerenson
 */
public class Fruit {
	private double value;
	private OOP_Point3D p;
	private int type;
	private int dest;
	/**
	 * Fruit Constructor initialized info from String
	 */
	public Fruit(String s) {
		String f = s.substring(s.indexOf("value")+7, s.indexOf(","));
		this.value = Double.parseDouble(f);

		f = s.substring(s.indexOf("type")+6, s.indexOf("pos")-2);
		this.type = Integer.parseInt(f);

		f = s.substring(s.indexOf("pos")+6, s.indexOf("}")-1);
		this.p = new OOP_Point3D(f);
	}
	/**
	 *@return the fruit location
	 */
	public OOP_Point3D getLocation() {
		return this.p;
	}
	/**
	 * fruit type is set in the game by the following :
	 * type = -1 --> Src vertex > Dest vertex
	 * type = 1 --> Src vertex < Dest vertex
	 *@return the type of fruit
	 */
	public int getType() {
		return this.type;
	}
	/**
	 *@return the fruit value can be - 5.0,8.0,12.0,15.0
	 */
	public double getValue() {
		return this.value;
	}
	/**
	 *@return the fruit value String
	 */
	public String toString() {
		return this.value+"";
	}
	/**
	 *@return the fruit Destination for robot pick
	 */
	public int getdest() {
		return this.dest;
	}
	/**
	 *set the fruit Destination 
	 */
	public void setdest(int d) {
		this.dest = d;
	}
	/**
	 *This method located all the game fruit on the graph
	 *used for construct an random path for the game robots
	 *@return the destination of the fruit 
	 */
	public static int fruitlocation(game_service game , graph g ) {  
		Fruit[] fr = Gameplay.getfruits(game);
		DGraph gh = new DGraph(); 
		gh.init(game.getGraph());
		Fruit go = new Fruit(game.getFruits().get(0));
		for(Fruit f : fr) {
			Point3D P = new Point3D(f.getLocation().x(),f.getLocation().y(),0);
			for(edge_data e : gh.edge) {
				Point3D psrc = g.getNode(e.getSrc()).getLocation();
				Point3D pdest = g.getNode(e.getDest()).getLocation();
				double o =  Math.abs(P.distance2D(psrc) +P.distance2D(pdest));
				double h =  Math.abs(psrc.distance2D(pdest) + 0.00001);
				double h1 =  Math.abs(psrc.distance2D(pdest) - 0.00001);
				if( (o <= h && o >= h1  ) ) {
					if(e.getDest() > e.getSrc() && f.getType() != -1) go.setdest(e.getDest());
					else if(e.getDest() > e.getSrc() && f.getType() == -1)go.setdest(e.getSrc());
				}
			}
		}		
		return go.getdest();
	}
}
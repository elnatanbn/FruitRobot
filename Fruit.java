package gameClient;

import java.util.ArrayList;
import java.util.List;
import Server.Game_Server;
import Server.game_service;
import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
import oop_utils.OOP_Point3D;
import utils.Point3D;

public class Fruit {

	private double value;
	private OOP_Point3D p;
	private int type;
	private int dest;

	
	public Fruit(String s) {
		String f = s.substring(s.indexOf("value")+7, s.indexOf(","));
		this.value = Double.parseDouble(f);

		f = s.substring(s.indexOf("type")+6, s.indexOf("pos")-2);
		this.type = Integer.parseInt(f);

		f = s.substring(s.indexOf("pos")+6, s.indexOf("}")-1);
		this.p = new OOP_Point3D(f);
	}

	public static void main(String[] args) {
		game_service game = Game_Server.getServer(23); // you have [0,23] games
		DGraph h = new DGraph();
		h.init(game.getGraph());
				System.out.println(game.getFruits().get(1));
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

	public int getdest() {
		return this.dest;
	}

	public void setdest(int d) {
		this.dest = d;
	}

	public static List<Integer> pathtofruit(int src , int dest ,graph g) {
		Graph_Algo G = new Graph_Algo(g);
		List<node_data> nl = new ArrayList<node_data>();
		nl = G.shortestPath(src,dest);
		List<Integer> l = new ArrayList<Integer>(nl.size());
		for (int j = nl.size()-2; j >=0  ; j--) {
			l.add(nl.get(j).getKey());
		}
		l.add(nl.get(0).getKey());
		return l;
	}

	public static int  fruitlocation(game_service game , graph g ,Fruit[] ff) {  ///////////// current game fruit location

		DGraph gh = new DGraph(); 
		gh.init(game.getGraph());
		Fruit go = new Fruit(game.getFruits().get(0));
		for(Fruit f : ff) {
			Point3D P = new Point3D(f.getLocation().x(),f.getLocation().y(),0);
			for(edge_data e : gh.edge) {
				Point3D psrc = g.getNode(e.getSrc()).getLocation();
				Point3D pdest = g.getNode(e.getDest()).getLocation();
				double o =  Math.abs(P.distance2D(psrc) +P.distance2D(pdest));
				double h =  Math.abs(psrc.distance2D(pdest) + 0.00001);
				double h1 =  Math.abs(psrc.distance2D(pdest) - 0.00001);
				
				if( (o <= h && o >= h1  )) {
					if(e.getDest() > e.getSrc() )go.setdest(e.getSrc());
					else go.setdest(e.getDest());	
				}
			}
		}	
		return go.getdest();
	}

}
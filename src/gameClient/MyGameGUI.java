package gameClient;

import Server.Game_Server;
import Server.game_service;
import dataStructure.DGraph;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;

import utils.StdDraw;

public class MyGameGUI {
	public graph g;
	int i=0;
	public static void main(String[] args) {
		game_service game = Game_Server.getServer(0); // you have [0,23] games
		String g = game.getGraph();
		DGraph gg = new DGraph();
		gg.init(g);
		String info = game.toString();
		MyGameGUI f = new MyGameGUI(gg);
	}
	public  MyGameGUI(graph g){
		this.g = g;
		StdDraw.setXscale(35.1865,35.2135);	
		StdDraw.setYscale(32.098,32.112);
		double l=1;
		while(l<15) {
			StdDraw.setPenColor(StdDraw.GRAY);
			StdDraw.line(l,-15,l,15);	//+y grid	
			StdDraw.line(-l,-15,-l,15);	//-y grid
			StdDraw.line(-15,l,15,l);  //+x grid
			StdDraw.line(-15,-l,15,-l);	//-x grid
			l++;
		}

		if(this.g.nodeSize() == 1) {
			node_data n = this.g.getNode(0);
			double x =  n.getLocation().x();
			double y =  n.getLocation().y();
			StdDraw.setPenColor(StdDraw.BLUE);
			StdDraw.filledCircle(x, y, 0.4);
			StdDraw.text(x, y+1, n.getKey()+"");
		}

		else {
			for (node_data n : this.g.getV()) {

				double xn =  n.getLocation().x();
				double yn =  n.getLocation().y();
				StdDraw.setPenColor(StdDraw.BLUE);
				StdDraw.filledCircle(xn, yn, 0.0003);
				StdDraw.text(xn, yn+0.0005, n.getKey()+"");
				i++;
			}

			for (node_data n : this.g.getV()) {
				if(this.g.getE(n.getKey()) != null) {
					for (edge_data e : this.g.getE(n.getKey())) {
						StdDraw.setPenColor(StdDraw.RED);
						StdDraw.setPenRadius(0.004);
						double x =   this.g.getNode(e.getSrc()).getLocation().x();
						double y =   this.g.getNode(e.getSrc()).getLocation().y();
						double x1 =  this.g.getNode(e.getDest()).getLocation().x();
						double y1 =  this.g.getNode(e.getDest()).getLocation().y();
						StdDraw.line(x, y, x1, y1);
						double midy = (y+y1)/2;
						double midx = (x+x1)/2;
						//						StdDraw.setPenRadius(0.0004);
						//						StdDraw.text(midx, midy, e.getWeight()+"");	
						//						StdDraw.setPenRadius();
					}
				}
			}

			for (node_data n : this.g.getV()) {
				if(this.g.getE(n.getKey()) != null) {
					for (edge_data e : this.g.getE(n.getKey())) {
						StdDraw.setPenColor(StdDraw.YELLOW);
						double x =  this.g.getNode(e.getSrc()).getLocation().x();
						double y =  this.g.getNode(e.getSrc()).getLocation().y();
						double x1 =  this.g.getNode(e.getDest()).getLocation().x();
						double y1 = this.g.getNode(e.getDest()).getLocation().y();

						if(x==x1){
							if(y1>y)
							{StdDraw.filledCircle(x, y1-(y1-y)/9, 0.0002);}
							else
							{StdDraw.filledCircle(x, y1-(y1-y)/9, 0.0002);}}

						if(y==y1){
							if(x1>x)
							{StdDraw.filledCircle(x1+(x-x1)/9, y, 0.0002);}
							else
							{StdDraw.filledCircle(x1+(x-x1)/9, y, 0.0002);}
						}
						if(x1>x  && y1>y)
						{StdDraw.filledCircle(x1+(x-x1)/9, y1-(y1-y)/9, 0.0002);}
						if(x1>x  && y1<y)
						{StdDraw.filledCircle(x1+(x-x1)/9, y1-(y1-y)/9, 0.0002);}
						if(x1<x  && y1>y)
						{StdDraw.filledCircle(x1+(x-x1)/9, y1-(y1-y)/9, 0.0002);}
						if(x1<x  && y1<y)
						{StdDraw.filledCircle(x1+(x-x1)/9, y1-(y1-y)/9, 0.0002);}
					}
				}
			}
			i=0;
		}
		
		while(StdDraw.isKeyPressed(27) != true) {
			if(StdDraw.isMousePressed() == true) {
				while(StdDraw.isMousePressed() == true);
				System.out.println("bezim");
				System.out.println(StdDraw.mouseX());
				double xn = StdDraw.mouseX();
				double yn = StdDraw.mouseY();
				StdDraw.setPenColor(StdDraw.GREEN);
				StdDraw.filledCircle(xn, yn, 0.0003);
				
			}
		}
	}
}
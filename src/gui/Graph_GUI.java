package gui;

import utils.StdDraw;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
import gameClient.Fruit;
import gameClient.Robot;;

public class Graph_GUI {
	public graph g;
	public static final double eps = 0.00000001;

	public void drawGraph() {
		StdDraw.enableDoubleBuffering();		
		for (node_data n : this.g.getV()) {
			double xn =  n.getLocation().x();
			double yn =  n.getLocation().y();
			StdDraw.setPenColor(StdDraw.BLUE);
			StdDraw.filledCircle(xn, yn, 0.00015);
			StdDraw.text(xn, yn+0.0005, n.getKey()+"");

			if(this.g.getE(n.getKey()) != null) {
				for (edge_data e : this.g.getE(n.getKey())) {
					StdDraw.setPenColor(StdDraw.GRAY);
					StdDraw.setPenRadius(0.0008);
					double x =   this.g.getNode(e.getSrc()).getLocation().x();
					double y =   this.g.getNode(e.getSrc()).getLocation().y();
					double x1 =  this.g.getNode(e.getDest()).getLocation().x();
					double y1 =  this.g.getNode(e.getDest()).getLocation().y();
					StdDraw.line(x, y, x1, y1);

					StdDraw.setPenColor(StdDraw.YELLOW);
					if(x==x1){
						if(y1>y)
						{StdDraw.filledCircle(x, y1-(y1-y)/9, 0.00007);}
						else
						{StdDraw.filledCircle(x, y1-(y1-y)/9, 0.00007);}}

					if(y==y1){
						if(x1>x)
						{StdDraw.filledCircle(x1+(x-x1)/9, y, 0.00007);}
						else
						{StdDraw.filledCircle(x1+(x-x1)/9, y, 0.00007);}
					}
					if(x1>x  && y1>y)
					{StdDraw.filledCircle(x1+(x-x1)/9, y1-(y1-y)/9, 0.00007);}
					if(x1>x  && y1<y)
					{StdDraw.filledCircle(x1+(x-x1)/9, y1-(y1-y)/9, 0.00007);}
					if(x1<x  && y1>y)
					{StdDraw.filledCircle(x1+(x-x1)/9, y1-(y1-y)/9, 0.00007);}
					if(x1<x  && y1<y)
					{StdDraw.filledCircle(x1+(x-x1)/9, y1-(y1-y)/9, 0.00007);}
				}
			}
		}
	}

	public void drawRobots(Robot[] robots) { 
		StdDraw.enableDoubleBuffering();
		for(Robot r : robots) {
			StdDraw.picture(r.getLocation().x(), r.getLocation().y(), "oo.png");		
		}
	}

	public void drawedge(int src , int dest) {
		StdDraw.setPenColor(StdDraw.GRAY);
		StdDraw.setPenRadius(0.0008);
		edge_data e = this.g.getEdge(src, dest);
		double x =   this.g.getNode(e.getSrc()).getLocation().x();
		double y =   this.g.getNode(e.getSrc()).getLocation().y();
		double x1 =  this.g.getNode(e.getDest()).getLocation().x();
		double y1 =  this.g.getNode(e.getDest()).getLocation().y();
		StdDraw.line(x, y, x1, y1);
	}

	public void drawfruits(Fruit[] fruits) {
		StdDraw.setPenColor(StdDraw.RED);
		for(Fruit f : fruits) {
			StdDraw.filledCircle(f.getLocation().x(),f.getLocation().y(), 0.00011);
		}
	}


	public  Graph_GUI(graph g){
		this.g = g;
		StdDraw.setCanvasSize(1350,680);
		StdDraw.setXscale(35.1865,35.2135);	
		StdDraw.setYscale(32.098,32.112);
	}
}
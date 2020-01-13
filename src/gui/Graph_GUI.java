package gui;

import utils.Point3D;
import utils.StdDraw;
import dataStructure.DGraph;
import dataStructure.Edge;
import dataStructure.Node;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;;

public class Graph_GUI {
	public graph g;
	int i=0;

	public static void main(String[] argas) {
	
	}

	public  Graph_GUI(graph g){
		this.g = g;
		StdDraw.setCanvasSize(1350,680);
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
				StdDraw.filledCircle(xn, yn, 0.00015);
				StdDraw.text(xn, yn+0.0005, n.getKey()+"");
				i++;
			}

			for (node_data n : this.g.getV()) {
				if(this.g.getE(n.getKey()) != null) {
					for (edge_data e : this.g.getE(n.getKey())) {
						StdDraw.setPenColor(StdDraw.GRAY);
						StdDraw.setPenRadius(0.0008);
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
			i=0;
		}
		
//		while(StdDraw.isKeyPressed(27) != true) {
//			if(StdDraw.isMousePressed() == true) {
//				while(StdDraw.isMousePressed() == true);
//				System.out.println("bezim");
//				System.out.println(StdDraw.mouseX());
//				double xn = StdDraw.mouseX();
//				double yn = StdDraw.mouseY();
//				StdDraw.setPenColor(StdDraw.GREEN);
//				StdDraw.filledCircle(xn, yn, 0.0003);
//				
//			}
//		}
	}
}

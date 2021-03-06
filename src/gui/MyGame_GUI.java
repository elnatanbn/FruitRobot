package gui;

import utils.StdDraw;

import java.util.List;

import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
import gameClient.Fruit;
import gameClient.Gameplay;
import gameClient.Robot;;
/**
 * This class represents the print of the game 
 * @author OfirBador & ElnatanBerenson
 */
public class MyGame_GUI {
	public graph g;
	public static final double eps = 0.00000001;
	/**
	 * This method prints the graph of the game (can print any DGraph or graph) 
	 * @return true if draw success
	 */
	public  boolean  drawGraph() {
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
					if(x1>=x  && y1>=y)
					{StdDraw.filledCircle(x1+(x-x1)/9, y1-(y1-y)/9, 0.00007);}
					if(x1>=x  && y1<=y)
					{StdDraw.filledCircle(x1+(x-x1)/9, y1-(y1-y)/9, 0.00007);}
					if(x1<=x  && y1>=y)
					{StdDraw.filledCircle(x1+(x-x1)/9, y1-(y1-y)/9, 0.00007);}
					if(x1<=x  && y1<=y)
					{StdDraw.filledCircle(x1+(x-x1)/9, y1-(y1-y)/9, 0.00007);}
				}
			}
		}
	return true;
	}
	/**
	 * This method prints the game robots of the graph
	 * @return true if draw success
	 */
	public boolean drawRobots(Robot[] robots) { 
		StdDraw.enableDoubleBuffering();
		for(Robot r : robots) {	
			StdDraw.picture(r.getLocation().x(), r.getLocation().y(), "monkey.png");
		}
		return true;
	}
	/**
	 * This method prints the game robots info
	 * id number , value , starting place , location to move
	 */
	public void drawRobotsinfo(List<String> list) { 
		StdDraw.enableDoubleBuffering();
		StdDraw.setPenColor(StdDraw.BLACK);
		int i = 0;
		double k = 0.001;
		for(String s : list) {	
			Robot r = new Robot(s, Gameplay.mygraph);
			StdDraw.text(35.199,32.1106+k, "robot["+i+"]: "+r.getID()+" value: "+r.getvalue()+" from: "+r.getSrcNode()+" to : "+r.getNextNode() );
			k = k - 0.00033;
			i++;
		}
		i=0;

	}
	/**
	 * This method prints the game fruits on the graph
	 * @return true if draw success
	 */
	public  boolean drawfruits(Fruit[] fruits) {
		StdDraw.enableDoubleBuffering();
		for(Fruit f : fruits) {
			if(f.getType() == -1)StdDraw.picture(f.getLocation().x(), f.getLocation().y(), "banana.png");
			else StdDraw.picture(f.getLocation().x(), f.getLocation().y(), "strawberry.png");
		}
		return true;
	}
	/**
	 * This method prints the game time passed
	 * @return true if draw success
	 */
	public boolean printclk() {
		StdDraw.enableDoubleBuffering();
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.text(35.19, 32.1, "time passed: " + Gameplay.currentTime);
		return true;
	}
	/**
	 * This method sets an default canvas for the game
	 */
	public  MyGame_GUI(graph g){
		this.g = g;
		StdDraw.setCanvasSize(1350,680);
		StdDraw.setXscale(35.1865,35.2135);	
		StdDraw.setYscale(32.098,32.112);
	}
	/**
	 * This method print score value of the game 
	 * when game time is over
	 * @return true if draw success
	 */
	public boolean Gameover(Robot[] rob) {
		double totval = 0 ;
		int i = 0;
		for(Robot r : rob) {
			totval = totval +r.getvalue();
			i++;
		}
		StdDraw.disableDoubleBuffering();
		StdDraw.setCanvasSize(650,450);
		StdDraw.setXscale(35.1865,35.2135);	
		StdDraw.setYscale(32.098,32.112);
		StdDraw.clear();
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.text(35.2, 32.1105, "GAME - OVER");
		StdDraw.text(35.2, 32.1095, "Your score : " + totval );
		StdDraw.text(35.2, 32.1085, "Number of robot : " + i );
		StdDraw.text(35.2, 32.1075, "Game number : " + Gameplay.gamenum );
		StdDraw.text(35.2, 32.1065, "Hope you enjoy playing!" );
		return true;
	}
}
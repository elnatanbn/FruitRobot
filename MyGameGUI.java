package gameClient;


import Server.Game_Server;
import Server.game_service;
import dataStructure.DGraph;
import dataStructure.Node;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
import gui.Graph_GUI;
import utils.StdDraw;
import java.util.ArrayList;
import javax.swing.JOptionPane;


public class MyGameGUI {


	public static final double eps = 0.000000001;
	//	public ArrayList<Fruit> fruitlist = new ArrayList<Fruit>(4);
	int gamenum;
	private graph g;
	private Fruit[] fruits;
//	private Robot[] robots;
//	private edge_data[] edgesfruit;
	
	public static void main(String[] args) {
		MyGameGUI m = new MyGameGUI();

	}

	public MyGameGUI() {
		String gamenumber = JOptionPane.showInputDialog("please choose game number - [0,23]");
		this.gamenum = Integer.parseInt(gamenumber);
		game_service game = Game_Server.getServer(this.gamenum); // you have [0,23] games
		String g = game.getGraph();

		DGraph gg = new DGraph();
		gg.init(g);
		this.g = gg;
		this.fruits = new Fruit[game.getFruits().size()];

		int i = 0;
		for(String f : game.getFruits()) {
			Fruit d = new Fruit(f);
			this.fruits[i] = d;
			i++;
		}
		i = 0;

		Graph_GUI G = new Graph_GUI( gg);
		double dt = 0;
		double dfs = 0;
		double dfd = 0;
		
		for(node_data n : this.g.getV()) {

			for(edge_data e : this.g.getE(n.getKey())) {
				double xd = this.g.getNode(e.getDest()).getLocation().x();
				double yd = this.g.getNode(e.getDest()).getLocation().y();
				double xs = this.g.getNode(e.getSrc()).getLocation().x();
				double ys = this.g.getNode(e.getSrc()).getLocation().y();

				for(Fruit f : fruits) {
					double x = f.getLocation().x();
					double y = f.getLocation().y();
					double v = f.getValue();

					dt = Math.sqrt(Math.pow((xd - xs), 2) + Math.pow((yd - ys), 2));
					dfs = Math.sqrt(Math.pow((x - xs), 2) + Math.pow((y - ys), 2));
					dfd = Math.sqrt(Math.pow((x - xd), 2) + Math.pow((y - yd), 2));

					if( dt <= (dfs + dfd + eps) && dt >= (dfs + dfd - eps)) {
						if(e.getSrc() > e.getDest()) {
							StdDraw.setPenColor(StdDraw.DARK_GRAY);
							StdDraw.filledCircle(x, y, 0.00013);
							i++;
						}	
						else if(e.getSrc() < e.getDest()) {	
						StdDraw.setPenColor(StdDraw.RED);
						StdDraw.filledCircle(x, y, 0.00013);
						i++;
						}
					}		
				}
			}
		}
		i=0;
		 game.addRobot(0);
		 Robot r = new Robot(game.getRobots().get(0));
		 r.setNextNode(1);
		 double xr = r.getLocation().x();
		 double yr = r.getLocation().y();
		 StdDraw.setPenColor(StdDraw.PINK);
		 StdDraw.filledCircle(xr, yr, 0.00015);
		 node_data b = new Node();
	
			for(node_data n : this.g.getV()) {
				if(n.getLocation().x() == xr  && n.getLocation().y() == yr ) {
					b = n;
					System.out.println(n);
				}
			}
//			for (int j = 0; j < 600000; j++) {
//					System.out.println("j");
//				}
//			while(!StdDraw.isKeyPressed(27)) {
				StdDraw.setPenColor(StdDraw.BLUE);
				 StdDraw.filledCircle(xr, yr, 0.00015);
				
				 b = this.g.getNode(r.getNextNode());
				 System.out.println(b);
				 xr = b.getLocation().x();
				 yr = b.getLocation().y();
				 StdDraw.setPenColor(StdDraw.PINK);
				 StdDraw.filledCircle(xr, yr, 0.00015);
				 StdDraw.picture(xr, yr, "ball.gif");
				
//			}
	}


	public static void fruitlocation(ArrayList<Fruit> fruitlist) {

	}
}


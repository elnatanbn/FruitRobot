package gameClient;


import Server.Game_Server;
import Server.game_service;
import dataStructure.DGraph;
import dataStructure.Node;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
import gui.Graph_GUI;
import oop_utils.OOP_Point3D;
import utils.StdDraw;

import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JOptionPane;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.experimental.theories.FromDataPoints;

public class MyGameGUI {
	int second = 0;

	public Timer time = new Timer();

	TimerTask task = new TimerTask() {

		@Override
		public void run() {
			currentTime++;

		}
	};
	public static int currentTime = 0;
	public static final double eps = 0.00000001;
	public static final double radius =  0.00010;
	//	public ArrayList<Fruit> fruitlist = new ArrayList<Fruit>(4);
	int gamenum;
	private graph g;
	private Fruit[] fruits;
	private Robot[] gamerobot;
	private robotrun[] run;
	public static game_service Game;
	//	private Robot[] robots;
	//	private edge_data[] edgesfruit;

	public static void main(String[] args) {
		MyGameGUI m = new MyGameGUI();

	}

	public MyGameGUI() {
		String gamenumber = JOptionPane.showInputDialog("please choose game number - [0,23]");
		this.gamenum = Integer.parseInt(gamenumber);
		this.g = setgamegraph(this.gamenum);
		this.fruits = getfruits();
		Graph_GUI G = new Graph_GUI(this.g);///////////////print graph
		drawStartfruits();  /////////////////////////// print fruits
		this.gamerobot = getrobotsgame();

		run = new robotrun[gamerobot.length];
		for (int i = 0; i < gamerobot.length; i++) {
			String f = JOptionPane.showInputDialog("please choose robot("+i+") strat location: [0 - "+this.g.getV().size()+"]");	
			int robnum = Integer.parseInt(f);
			node_data nsrc = this.g.getNode(robnum);
//			f = JOptionPane.showInputDialog("please choose robot("+i+") strat move: [0 - "+this.g.getV().size()+"]");	
//			robnum = Integer.parseInt(f);
			OOP_Point3D p = new OOP_Point3D(nsrc.getLocation().toString());
			gamerobot[i].setLocation(p);
			node_data ndest = this.g.getNode(robnum);
			robotrun r0 = new robotrun(gamerobot[i] , nsrc , ndest , this.g);
			run[i] = r0;
		}

		this.startclk();

		int temp = 0;
		while(!StdDraw.isKeyPressed(27)) {
			if(StdDraw.isMousePressed()){
				while(StdDraw.isMousePressed());
				for (int i = 0; i < gamerobot.length; i++) {
					Robot r = gamerobot[i];
					double xm = StdDraw.mouseX();
					double ym = StdDraw.mouseY();
					double xr = r.getLocation().x();
					double yr = r.getLocation().y();
					
					if((xm >= xr - radius && xm <= xr + radius) && (ym >= yr - radius || ym <= yr + radius) ) {
						StdDraw.setPenColor(StdDraw.BLUE);
						StdDraw.filledCircle(r.getLocation().x(), r.getLocation().y(), 0.00015);
						String fg = JOptionPane.showInputDialog("enter your location");
						int robnum = Integer.parseInt(fg);
						node_data n = this.g.getNode(robnum);	
						r.setID(n.getKey());
						run[i].setsrc(n.getKey());
						OOP_Point3D p = new OOP_Point3D(n.getLocation().toString());
						gamerobot[i].setLocation(p);
					}	
				}
			}

			if(currentTime > temp) {
				for (int i = 0; i < run.length; i++) {
					run[i].interrupt();
				}	
				temp = currentTime; 
			}
		}
	}


	public  static Fruit[] getfruits() {
		Fruit[] fruits = new Fruit[Game.getFruits().size()];

		int i = 0;
		for(String f : Game.getFruits()) {
			Fruit d = new Fruit(f);
			fruits[i] = d;
			i++;
		}
		i = 0;
		return fruits;
	}

	public static graph setgamegraph(int num) {
		game_service game = Game_Server.getServer(num); // you have [0,23] games
		Game = game;
		String g = game.getGraph();
		DGraph gg = new DGraph();
		gg.init(g);
		return gg;
	}

	public void drawStartfruits(){
		double dt = 0;
		double dfs = 0;
		double dfd = 0;
		int i=0;
		for(node_data n : this.g.getV()) {

			for(edge_data e : this.g.getE(n.getKey())) {
				double xd = this.g.getNode(e.getDest()).getLocation().x();
				double yd = this.g.getNode(e.getDest()).getLocation().y();
				double xs = this.g.getNode(e.getSrc()).getLocation().x();
				double ys = this.g.getNode(e.getSrc()).getLocation().y();

				for(Fruit f : fruits) {
					double x = f.getLocation().x();
					double y = f.getLocation().y();

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
	}

	public void startclk() {
		time.schedule(task, 1000, 1000);

	}


	public  Robot[]  getrobotsgame() {
		JSONObject line;
		String info = Game.toString();
		try {
			line = new JSONObject(info);
			JSONObject ttt = line.getJSONObject("GameServer");
			int rs = ttt.getInt("robots");	
			int src_node = 0;  
			for(int a = 0;a<rs;a++) {
				Game.addRobot(src_node+a);
			}
		}
		catch (JSONException e) {e.printStackTrace();}
		gamerobot = new Robot[Game.getRobots().size()];
		int i=0;
		for(String s : Game.getRobots()) {
			Robot r = new Robot(s);
			this.gamerobot[i] = r;
			i++;
		}
		this.run = new robotrun[gamerobot.length];
		return gamerobot;
	}
}


package gameClient;

import Server.Game_Server;
import Server.game_service;
import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
import gui.MyGame_GUI;
import utils.StdDraw;
import java.util.List;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.json.JSONException;
import org.json.JSONObject;

public class Gameplay {
	
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
	public static  graph mygraph;
	private Fruit[] fruits;
	private static Fruit[] fruitspah;
	private Robot[] gamerobot;
	private robotrun[] run;
	public static game_service game;
	public static int gamenum;
	public static String gameset;

	public static void main(String[] args) {
		Gameplay m = new Gameplay();
	}

	public Gameplay() {
		String gamenumber = JOptionPane.showInputDialog("please choose game number - [0,23]");
		int num = Integer.parseInt(gamenumber);
		gamenum = num;
		game  = Game_Server.getServer(num); // you have [0,23] games
		String g = game.getGraph();

		mygraph = setgamegraph(g);

		MyGame_GUI G = new MyGame_GUI(mygraph);
		G.drawGraph();

		this.fruits = getfruits();
		G.drawfruits(this.fruits);
		this.gamerobot = getrobotsgame();
		G.drawRobots(this.gamerobot);

		StdDraw.show();

		gameset = JOptionPane.showInputDialog("m - MANUAL PLAY , a - AUTO PLAY ");

		if(gameset.equals("a")) {/////////////////////////////// auto play
			this.gamerobot = getrobotsgame();
			setrobotintterupt();
			G.drawRobots(this.gamerobot);
			game.startGame();
			startclk();
			while(game.isRunning()) {	
				game.move();

				StdDraw.clear();
				this.fruits = getfruits();
				G.drawfruits(this.fruits);
				this.gamerobot = getrobotsgame();
				G.drawRobots(this.gamerobot);
				G.drawGraph();
				G.printclk();
				G.drawRobotsinfo(game.getRobots());
				StdDraw.show();

				for(Robot r : gamerobot) {
					if(r.getNextNode() == -1 ) {
						run[r.getID()].interrupt();
					}
				}
			}
		}

		else if(gameset.equals("m")) {  //////////////////////// manual play
			this.gamerobot = getrobotsgame();
			userintpath(gamerobot);

			setrobotintterupt();
			G.drawRobots(this.gamerobot);

			game.startGame();
			startclk();
			while(game.isRunning()) {	
				game.move();

				StdDraw.clear();
				this.fruits = getfruits();
				G.drawfruits(this.fruits);
				this.gamerobot = getrobotsgame();
				G.drawRobots(this.gamerobot);
				G.drawGraph();
				G.printclk();
				G.drawRobotsinfo(game.getRobots());
				StdDraw.show();

				for(Robot r : gamerobot) {
					
					if(r.getNextNode() == -1 ) {
						run[r.getID()].interrupt();
					}
				}
			}
		}
	
		G.Gameover(gamerobot);
		
		KML_Logger h = new KML_Logger(run[0].ptokml , "game"+gamenum);
	}

	public static graph setgamegraph(String g) { //////////////////// set the game graph
		DGraph gg = new DGraph();
		gg.init(g);
		return gg;
	}	

	public  static Fruit[] getfruits() {        //////////////////// set the current fruits
		Fruit[] fruits = new Fruit[game.getFruits().size()];
		int i = 0;
		for(String f : game.getFruits()) {
			Fruit d = new Fruit(f);
			fruits[i] = d;
			i++;
		}
		i = 0;
		return fruits;
	}

	public void startclk() {                  //////////////////////// game clock
		time.schedule(task, 1000, 1000);
	}

	public  Robot[]  getrobotsgame() {       //////////////////////// get the current robots in game
		JSONObject line;
		String info = game.toString();
		try {
			line = new JSONObject(info);
			JSONObject ttt = line.getJSONObject("GameServer");
			int rs = ttt.getInt("robots");	
			int src_node = 0;  
			for(int a = 0;a<rs;a++) {
				game.addRobot(src_node+a);
			}
		}
		catch (JSONException e) {e.printStackTrace();}
		gamerobot = new Robot[game.getRobots().size()];

		int i=0;
		for(String s : game.getRobots()) {
			Robot r = new Robot(s ,mygraph); 
			this.gamerobot[i] = r;
			i++;
		}
		return gamerobot;
	}

	public void  setrobotintterupt() {			////////////////////////// set the current threads 
		this.run = new robotrun[game.getRobots().size()];
		int i=0;
		for(Robot r : this.gamerobot) {
			run[i] = new robotrun(r,mygraph); 
			i++;
		}
	}

	public static List<Integer>  getRandomPath(int num , graph g) { ///////// get random path for auto play
		List<node_data> nl = new ArrayList<node_data>();
		Graph_Algo d = new Graph_Algo(g);
		fruitspah = getfruits();
		nl = d.shortestPath(num, Fruit.fruitlocation(game, g ,fruitspah));	
		List<Integer> l = new ArrayList<Integer>(nl.size());
		
		for (int j = nl.size()-1; j >0  ; j--) {
			l.add(nl.get(j).getKey());
		}
		l.add(nl.get(0).getKey());
		return l;
	}

	public static List<Integer>  getuserpath(Robot r , graph g) {   ////////////////// path for manual game
		Graph_Algo G = new Graph_Algo(Gameplay.mygraph);
		List<node_data> nl = new ArrayList<node_data>();
		nl = G.shortestPath(r.getID(), r.getway());
		List<Integer> l = new ArrayList<Integer>(nl.size());
		for (int j = nl.size()-1; j >=0  ; j--) {
			l.add(nl.get(j).getKey());
		}
		l.add(nl.get(0).getKey());
		return l;
	}

	public static void userintpath(Robot[] gamerobot) {            ////////////// user manual input path while playing
		for(Robot r : gamerobot) {
			String startpath = JOptionPane.showInputDialog("choose robot["+r.getID()+"] first location");
			int way = Integer.parseInt(startpath);
			r.setway(way);
		}
	}


}
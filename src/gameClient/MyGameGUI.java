package gameClient;


import Server.Game_Server;
import Server.game_service;
import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.graph;
import dataStructure.node_data;
import gui.Graph_GUI;
import utils.StdDraw;
import java.util.List;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JOptionPane;
import org.json.JSONException;
import org.json.JSONObject;

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
	public static  graph mygraph;
	private Fruit[] fruits;
	private Robot[] gamerobot;
	private robotrun[] run;
	public static game_service game;
	//	private Robot[] robots;
	//	private edge_data[] edgesfruit;

	public static void main(String[] args) {
		MyGameGUI m = new MyGameGUI();

	}

	public MyGameGUI() {

		String gamenumber = JOptionPane.showInputDialog("please choose game number - [0,23]");
		this.gamenum = Integer.parseInt(gamenumber);
		game_service Game = Game_Server.getServer(this.gamenum); // you have [0,23] games
		game = Game;
		String g = game.getGraph();
		mygraph = setgamegraph(g);

		Graph_GUI G = new Graph_GUI(mygraph);
		G.drawGraph();

		this.fruits = getfruits();
		G.drawfruits(this.fruits);

		this.gamerobot = getrobotsgame();
		setrobotintterupt();
		G.drawRobots(this.gamerobot);
		
		game.startGame();
		
		while(game.isRunning()) {
					
			for(robotrun r : this.run) {
				r.interrupt();
			}
			
			
			game.move();
			
			StdDraw.clear();
			this.gamerobot = getrobotsgame();
			G.drawRobots(this.gamerobot);
			G.drawGraph();
			StdDraw.show();
		}		
	}

	public static graph setgamegraph(String g) {
		DGraph gg = new DGraph();
		gg.init(g);
		return gg;
	}	

	public  static Fruit[] getfruits() {
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

	//	public void startclk() {
	//		time.schedule(task, 1000, 1000);
	//
	//	}

	public  Robot[]  getrobotsgame() {
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
	
	public void  setrobotintterupt() {
		this.run = new robotrun[game.getRobots().size()];
		int i=0;
		for(Robot r : this.gamerobot) {
			run[i] = new robotrun(r,mygraph); 
			i++;
		}
	}
	public static List<Integer>  getRandomPath(int num , graph g) {

		List<node_data> nl = new ArrayList<node_data>();
		int i = (int) (Math.random()*(g.getV().size()));
		Graph_Algo d = new Graph_Algo(g);
		nl = d.shortestPath(num, i);	
		List<Integer> l = new ArrayList<Integer>(nl.size());
		for (int j = nl.size()-2; j >=0  ; j--) {
			l.add(nl.get(j).getKey());
		}
		return l;
	}
}


package gameClient;

import Server.Game_Server;
import Server.game_service;
import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.graph;
import dataStructure.node_data;
import gui.MyGame_GUI;
import utils.StdDraw;
import java.util.List;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JOptionPane;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * This class represents game play of Robot Fruit!
 * in this game you can choose scenario form [0,23]
 * all you need is to construct in main the game play constructor and your good to go!
 * @author OfirBador & ElnatanBerenson
 */
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
	private Robot[] gamerobot;
	private robotrun[] run;
	public static game_service game;
	public static int gamenum;
	public static String gameset;

	public static void main(String[] args) {
		Gameplay m = new Gameplay();
	}
	/**
	 * main class constructor!! this is where all the magic happens
	 * construct in main for start game
	 */
	public Gameplay() {	
		int id = 302639281;
		Game_Server.login(id);
		String gamenumber = JOptionPane.showInputDialog("please choose game number - [0,23]");
		int num = Integer.parseInt(gamenumber);
		gamenum = num;
		game  = Game_Server.getServer(num); // you have [0,23] games
		String g = game.getGraph();

		mygraph = setgamegraph(g);

		MyGame_GUI G = new MyGame_GUI(mygraph);
		G.drawGraph();

		this.fruits = getfruits(game);
		G.drawfruits(this.fruits);
		this.gamerobot = getrobotsgame(game);
		G.drawRobots(this.gamerobot);

		StdDraw.show();

		gameset = JOptionPane.showInputDialog("m - MANUAL PLAY , a - AUTO PLAY ");
		long dt = 120;
		if(gameset.equals("a")) {/////////////////////////////// auto play
			this.gamerobot = getrobotsgame(game);
			setrobotintterupt();
			G.drawRobots(this.gamerobot);
			game.startGame();
			startclk();
		
			while(game.isRunning()) {	
				
				game.move();
				StdDraw.clear();
				this.fruits = getfruits(game);
				G.drawfruits(this.fruits);
				this.gamerobot = getrobotsgame(game);
				G.drawRobots(this.gamerobot);
				G.drawGraph();
				G.printclk();
				G.drawRobotsinfo(game.getRobots());
				StdDraw.show();
					
				try {
					
					for(Robot r : gamerobot) {
						if(r.getNextNode() == -1 ) run[r.getID()].run();	
						else Thread.sleep(dt);
						}
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}
		}

		else if(gameset.equals("m")) {  //////////////////////// manual play
			this.gamerobot = getrobotsgame(game);
			userintpath(gamerobot);

			setrobotintterupt();
			G.drawRobots(this.gamerobot);

			game.startGame();
			startclk();
			while(game.isRunning()) {	
				game.move();

				StdDraw.clear();
				this.fruits = getfruits(game);
				G.drawfruits(this.fruits);
				this.gamerobot = getrobotsgame(game);
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
			String res = Gameplay.game.toString();
			Gameplay.game.sendKML("game0.kml"); 
			System.out.println(res);
	}
	/**
	 * This method builds the graph from the selected game from String
	 * @return the game graph
	 */
	public static  graph setgamegraph(String g) { //////////////////// set the game graph
		DGraph gg = new DGraph();
		gg.init(g);
		return gg;
	}	
	/**
	 * This method builds the fruit from the selected game from String
	 * @return the game fruits
	 */
	public static Fruit[] getfruits(game_service game) {       
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
	/**
	 * This method start clocking , use to show game time passed
	 */
	public void startclk() {                 
		time.schedule(task, 1000, 1000);
	}
	/**
	 * This method builds the Robots from the selected game from String
	 * @return the game Robots
	 */
	public static Robot[]  getrobotsgame(game_service game) {     
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
		Robot[] gamerobot1 = new Robot[game.getRobots().size()];

		int i=0;
		for(String s : game.getRobots()) {
			Robot r = new Robot(s ,mygraph); 
			gamerobot1[i] = r;
			i++;
		}
		return gamerobot1;
	}
	/**
	 * This method builds run scenario for each Robot int the game
	 */
	public void  setrobotintterupt() {			 
		this.run = new robotrun[game.getRobots().size()];
		int i=0;
		for(Robot r : this.gamerobot) {
			run[i] = new robotrun(r,mygraph ,game); 
			i++;
		}
	}
	/**
	 * This method builds path from selected vertex to fruit destination
	 * @return List<Integer> for robot
	 */
	public static List<Integer>  getPathtofruit(int num , graph g , Robot r) {
		List<node_data> nl = new ArrayList<node_data>();
		Graph_Algo d = new Graph_Algo(g);
		nl = d.shortestPath(num, Fruit.fruitlocation(game, g ));

		List<Integer> l = new ArrayList<Integer>(nl.size());
		int k=0;
		for (int j = nl.size()-1; j > 0  ; j--) {
			l.add(nl.get(j).getKey());
			if(l.size() > 1 ) k = l.get(1);
		}
		l.add(nl.get(0).getKey());
		if(r.getSrcNode() == l.get(0) && l.size() == 1 )  {
			l.add(k);
		}
		return l;
	}
	/**
	 * This method builds path from selected vertex to user input
	 * in the start game
	 * @return user List<Integer> for robot
	 */
	public static List<Integer>  getuserpath(Robot r , graph g) { 
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
	/**
	 * This method builds path from selected vertex to user input
	 * will playing
	 * @return user List<Integer> for robot
	 */
	public static void userintpath(Robot[] gamerobot) {          
		for(Robot r : gamerobot) {
			String startpath = JOptionPane.showInputDialog("choose robot["+r.getID()+"] first location");
			int way = Integer.parseInt(startpath);
			r.setway(way);
		}
	}


}
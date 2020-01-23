package Tests;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import org.junit.Test;
import gameClient.Fruit;
import Server.Game_Server;
import Server.game_service;
import dataStructure.graph;
import gameClient.Gameplay;
import gameClient.Robot;
import gui.MyGame_GUI;

public class TestGamePlay {
	
	game_service game = Game_Server.getServer(0);
	Fruit[] f = Gameplay.getfruits(game);
	String g = game.getGraph();
	graph h =Gameplay.setgamegraph(g);
	Robot[] r =Gameplay.getrobotsgame(game);
	
	MyGame_GUI G = new MyGame_GUI(h);
	
	//////////////////////////////test game objects
	@Test
	public void TestFruitlocationNode() {
		assertEquals(Fruit.fruitlocation(game, h), 8);
	}
	@Test
	public void TestFruit() {
		f[0].setdest(8);
		double[] arr = {5.0 , -1 , 8};
		double[] arrF = {f[0].getValue() , f[0].getType() , f[0].getdest() };
		assertArrayEquals(arr, arrF);
	}
	@Test
	public void TestRobot() {
		double[] arr = {  0 , h.getNode(0).getLocation().x()};
		double[] arrF = { r[0].getSrcNode() , r[0].getLocation().x()};
		assertArrayEquals(arr, arrF);
	}
    //////////////////////////////test game drawing
	@Test
	public void TestGraphdraw() {
		assertEquals(G.drawGraph(), true);
	}
	@Test
	public void TestFruitdraw() {
		assertEquals(G.drawfruits(f), true);
	}
	@Test
	public void TestRobotdraw() {
		assertEquals(G.drawRobots(r), true);
	}
	@Test
	public void TestGameoverdraw() {
		assertEquals(G.Gameover(r), true);
	}
	@Test
	public void TestClockdraw() {
		assertEquals(G.printclk(), true);
	}

}

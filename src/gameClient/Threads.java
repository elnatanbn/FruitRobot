package gameClient;

import java.util.ArrayList;
import java.util.List;

import Server.robot;
import algorithms.Graph_Algo;
import dataStructure.graph;
import dataStructure.node_data;
import utils.StdDraw;

class robotrun extends Thread{

	private Robot r;
	private graph g;
	public int id;
	private boolean flag;
	private List<Integer> l;
	public robotrun(Robot r , graph g ) {
		this.flag = false;
		this.r = r;
		this.g = g;
		List<Integer> l = MyGameGUI.getRandomPath(this.r.getID() , this.g);
		this.l = l;
		System.out.println(l);
	}

	@Override
	public void interrupt() {
		super.run();
		node_data n = this.g.getNode(r.getNextNode());

		if(this.r.getNextNode() != -1) {
			if(r.getLocation().x() == n.getLocation().x()) this.flag = true;	
		}
		else if(this.r.getNextNode() == -1){this.flag = false;}
		 if(this.flag == false && !this.l.isEmpty()) {
			MyGameGUI.game.chooseNextEdge(this.r.getID(), (int) l.get(0));
			this.r.setID((int) l.get(0));
			r.setNextNode((int) l.get(0));
			l.remove(0);
			System.out.println(l);
		}	
	}
}

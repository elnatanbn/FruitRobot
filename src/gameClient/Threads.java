package gameClient;

import Server.robot;
import dataStructure.graph;
import dataStructure.node_data;
import utils.StdDraw;

class robotrun extends Thread{

	private robot r;
	public node_data nsrc;
	public node_data ndest;
	private graph g;
	public int id;
	
	public robotrun(robot r ,node_data ns ,node_data nd, graph g  ) {
		this.r = r;
		this.nsrc = ns;
		this.g = g;
		this.id = ns.getKey();
		
		StdDraw.setPenColor(StdDraw.PINK);
		StdDraw.filledCircle(this.nsrc.getLocation().x(), this.nsrc.getLocation().y(), 0.00015);
	}
	
	@Override
	public void run() {
		super.run();	
	}
	
	@Override
	public void interrupt() {
		super.run();	
			StdDraw.setPenColor(StdDraw.PINK);
			 StdDraw.filledCircle(this.nsrc.getLocation().x(), this.nsrc.getLocation().y(), 0.00015);
	}
	public void setsrc(int nkey) {
		node_data n = this.g.getNode(nkey);
		this.nsrc = n;
	}
}

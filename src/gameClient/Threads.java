
package gameClient;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

import Server.game_service;
import dataStructure.DGraph;
import dataStructure.graph;
import oop_utils.OOP_Point3D;
/**
 * This class represents the way of the robot in game play
 * @author OfirBador & ElnatanBerenson
 */
class robotrun extends Thread{
	public List<OOP_Point3D> ptokml = new ArrayList<>();
	private Robot r;
	private DGraph g;
	public int id;
	private List<Integer> l;
	/**
	 * Constructor for game robot 
	 * place the robot, graph and game play 
	 * and automatic path for the robot
	 */
	public robotrun(Robot r , graph g ,game_service game) {
		this.r = r;
		this.g = (DGraph) g;
		if(r.getway() == -1) this.l  = Gameplay.getPathtofruit(this.r.getSrcNode() , this.g , this.r);
		else if (r.getway() != -1) this.l = Gameplay.getuserpath(this.r, this.g);		
	}
	@Override
	public void run() {
		super.run();
		OOP_Point3D p = new OOP_Point3D((this.g.getNode(this.r.getSrcNode()).getLocation()).toString());
		this.ptokml.add(p);
		super.run();
		if(Gameplay.gameset.equals("a")) {
			if(!this.l.isEmpty()) {
				Gameplay.game.chooseNextEdge(this.r.getID(), this.l.get(0));
				this.r.setSrcNode(this.l.get(0));
				this.l.remove(0);
			}

			else if(this.l.isEmpty()) {
				this.l.clear();
				this.l = Gameplay.getPathtofruit(this.r.getSrcNode() , this.g , this.r);
			}
		}

		else if (Gameplay.gameset.equals("m")) {
			if(!this.l.isEmpty()) {
				Gameplay.game.chooseNextEdge(this.r.getID(), this.l.get(0));
				this.l.remove(0);
			}

			if(this.l.isEmpty()) {
				String startpath = JOptionPane.showInputDialog("quikley choose robot["+this.r.getID()+"] next location");
				int way = Integer.parseInt(startpath);
				this.r.setway(way);
				this.l = Gameplay.getuserpath(this.r, Gameplay.mygraph);	
			}
		}
	}
}

package gameClient;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import dataStructure.DGraph;
import dataStructure.graph;
import oop_utils.OOP_Point3D;

class robotrun extends Thread{
public List<OOP_Point3D> ptokml = new ArrayList<>();
	private Robot r;
	private DGraph g;
	public int id;
	private List<Integer> l;

	public robotrun(Robot r , graph g ) {
		this.r = r;
		this.g = (DGraph) g;
		if(r.getway() == -1) this.l  = Gameplay.getRandomPath(this.r.getSrcNode() , this.g);
		else if (r.getway() != -1) this.l = Gameplay.getuserpath(this.r, this.g);		
	}

	@Override
	public void interrupt() {
		OOP_Point3D p = new OOP_Point3D((this.g.getNode(this.r.getSrcNode()).getLocation()).toString());
		this.ptokml.add(p);
		super.run();
		if(Gameplay.gameset.equals("a")) {
			if(!this.l.isEmpty()) {
				Gameplay.game.chooseNextEdge(this.r.getID(), this.l.get(0));
				this.r.setSrcNode(this.l.get(0));
				this.l.remove(0);
			}

			if(this.l.isEmpty())this.l = Gameplay.getRandomPath(this.r.getSrcNode() , this.g);
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

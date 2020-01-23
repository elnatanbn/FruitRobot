package gameClient;

import Server.Game_Server;

public class Ex4_Client implements Runnable{
	public static void main(String[] a) {
		int id = 302639281;
		Game_Server.login(id);
		Gameplay m = new Gameplay();
		String res = Gameplay.game.toString();
		Gameplay.game.sendKML("game0.kml"); 
		System.out.println(res);
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}

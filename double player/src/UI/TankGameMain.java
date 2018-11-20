package UI;

import Gamebody.Music;
import Gamebody.*;
public class TankGameMain {

	/**
	 * @param args
	 */
	
	public static void main(String args[]) {
		GameMenuUI gui = new GameMenuUI();
		gui.initUI();
//		
		// Æô¶¯Ë¢ÆÁÏß³Ì
		Thread thread = new Thread(gui);
		thread.start();


	}

}


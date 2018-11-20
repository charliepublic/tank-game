package Gamebody;
import java.io.InputStream;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class Music implements Runnable {
	private String url = "";
	private String music ="" ;
	Thread thread;

	public Music(String amusic) {
		this.music = amusic;
		switch (music) {
		case "fire":
			url = "music/aFire.mid";
			break;
		case "explode": 
			url = "music/explode.wav";
			break;
		case "lose": 
			url = "music/lose.wav";
			break;
		case "win":
			url = "music/win.wav";
			break;
		case "click":
			url = "music/click.wav";
			break;
		case "main":
			url = "music/main.wav";
			break;
		case "sdas":
			url = "music/bFire.wav";
		}
		thread = new Thread(this);
		thread.start();
	}

	public void stop() {
		if (thread != null) {
			thread.interrupt();
			try {
			thread.join();
			} catch(Exception e) {
				
			}
			thread = null;
		}
	}


	public void run() {
		AudioStream ais = null;
		InputStream is = null;
				try {
					if (music == "main") {
						while (true) {
							is = ClassLoader.getSystemResourceAsStream(url);
							ais = new AudioStream(is);
							AudioPlayer.player.start(ais);
							
							Thread.sleep(106000);
							
						}
					} else if(music == "win") { 
						while (true) {
							is = ClassLoader.getSystemResourceAsStream(url);
							ais = new AudioStream(is);
							AudioPlayer.player.start(ais);
							Thread.sleep(300);
						}
						
					}else {
						is = ClassLoader.getSystemResourceAsStream(url);
						ais = new AudioStream(is);
						AudioPlayer.player.start(ais);
						
						Thread.sleep(50);
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						if (is != null) {
							is.close();
						}
						if (ais != null) {
							ais.close();
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}
		}
	}
}
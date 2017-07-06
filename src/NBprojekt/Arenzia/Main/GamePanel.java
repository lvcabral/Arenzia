/**
  *     This file is part of Arenzia.
  *
  *   Arenzia is free software: you can redistribute it and/or modify
  *   it under the terms of the GNU General Public License as published by
  *   the Free Software Foundation, either version 3 of the License, or
  *   (at your option) any later version.**
  *
  *   Arenzia is distributed in the hope that it will be useful,
  *   but WITHOUT ANY WARRANTY; without even the implied warranty of
  *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  *   GNU General Public License for more details.
  *
  *   You should have received a copy of the GNU General Public License
  *   along with Arenzia.  
  *   If not, see <http://www.gnu.org/licenses/>. 
  */

/** Paketname */
package NBprojekt.Arenzia.Main;

/** Importierete Pakete */ 
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage; 
import javax.swing.JPanel; 
import NBprojekt.Arenzia.GameState.GameStateManager;


// Erweitern der Klasse 'JPanel', ausführbar machen und Tastatur auslesen hinzufügen
@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable, KeyListener {
		/* Variablendeklaration */
	 
	public static int WIDTH = 1280 ;
	public static int HEIGHT = 800 ;
	public static int SCALE = 1;
	 
	private Thread gameThread;
	private boolean running;
	private static int FPS = 0;
	private static int fps = 130;	// Die echte fps anzahl die andere sind nur für die ausgabe
	private long targetTime = 1000 / fps;
	 
	private BufferedImage bufferedImage;
	private Graphics2D graphics;
	 
	private GameStateManager gameStateManager;
	
		/* Standartkonstruktor */
	
	public GamePanel() {
		super();
		setPreferredSize( new Dimension( WIDTH * SCALE, HEIGHT * SCALE ) );
		setFocusable(true);
		requestFocus();
		System.out.println("Arenzia  Copyright (C) 2017  Norbert Bartko"+
					"This program comes with ABSOLUTELY NO WARRANTY."+
					"This is free software, and you are welcome to redistribute it"+
					"under certain conditions; type `ctrl + c' for details."
					);
	} 
	
	// Initialisierungs-Methode
	public void init() {
		bufferedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		graphics = (Graphics2D) bufferedImage.getGraphics();
		
		running = true; 
		
		gameStateManager = new GameStateManager();
	}
	
		/* Set- und Get-Methoden*/
	
	public static void setFPS (int fps) {
		if (fps < 190 && fps > 60)
			GamePanel.fps = fps;
		else 
			GamePanel.fps = fps;
	}
	public static int getFps() {
		return fps;
	}
	public static int getFPS () {
		return FPS;
	}
	public static void setScale(int scale){
		if (scale < 0 && scale < 5)
			SCALE = scale;
	}
	public static int getScale() {
		return SCALE;
	}
	
		/* Sonstige benötigte Methoden */
	
	// Ausfuehr-Methode mit der Spielschleife
	public void run() {   
		init();
		
		long start, departed, period, lastFpsTime = 0, framesLength = 0;
		long lastLoopTime = System.nanoTime();

		do{
			start = System.nanoTime();
			long now = System.nanoTime();
		    long updateLength = now - lastLoopTime;
		    lastLoopTime = now;
			// update the frame counter
		    lastFpsTime += updateLength;
		    framesLength++;
		    
		    if (lastFpsTime >= 1000000000) {
		    	lastFpsTime = 0;
		    	FPS = (int) framesLength;
		    	framesLength -= framesLength;
	        }	
			
			update();
			draw();
			drawToScreen();
			departed = System.nanoTime() - start;
			period = targetTime + departed / 1000000;
			
			try{
				Thread.sleep(period);
			} catch (Exception e){ 
				e.printStackTrace();
			}
		} while (running);
	}
	 
	
	// Meldung hinzufuegen
	public void addNotify() {
		super.addNotify();
		if (gameThread == null ){
			gameThread = new Thread(this);
			addKeyListener(this);
			gameThread.start();
		}
	}
	
		/* Methoden fuer das Tastatur mitlesen */
	
	public void keyTyped(KeyEvent key){}
	
	public void keyPressed(KeyEvent key){
		gameStateManager.keyPressed(key.getKeyCode());
	}
	
	public void keyReleased(KeyEvent key){
		gameStateManager.keyReleased(key.getKeyCode());
	}
	
	
		/* Aktualisierungs- und Zeichnugs-Methoden */
	
	public void update(){
		gameStateManager.update();
	}
	
	public void draw(){
		gameStateManager.draw(graphics);
	}
	 
	public void drawToScreen(){
		Graphics g = getGraphics();
		g.drawImage(bufferedImage, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		g.dispose();
	}  
} // End of GamePanel

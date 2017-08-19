/**
  * 		Arenzia is a 2D platformer game.
  *  	Copyright (C) 2017  Norbert Bartko
  *  	
  *  	
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
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JFrame; 
  

public class Game {
		/* Variablendeklaration */
	private static Font version;
	public static boolean debugConsole;
	public static JFrame game;
	public static boolean gameStarted;
	public static int playerX, playerY, action;  
	@SuppressWarnings("unused")
	private static SystemDetails sys;
		/* Main Methode */ 
	
	public static void main ( String [] args ){ 
		game = new JFrame(" Die Abenteuer in Arenzia ");
		game.setContentPane( new GamePanel() );  
		game.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		game.setResizable(false);  
		game.pack();  
		game.setVisible(true); 
		
		InputStream imgStream = Game.class.getResourceAsStream("/icon.png" );
		BufferedImage ico = null;
		try {
			ico = ImageIO.read(imgStream);
		} catch (IOException e) { 
			System.out.println("Das Spielicon konnte nicht geladen werden.");
			e.printStackTrace();
		} 
		game.setIconImage(ico);
		
		game.addWindowListener(new GameListener()); 
		centerScreen(game); 
		version = new Font("Rockwell", Font.PLAIN, 15);
		debugConsole = gameStarted = false;
		
		playerX = playerY = 0; 

		sys = new SystemDetails();

	}
	
	
		/* Methode um das Spiel zu Zentrieren */
	
	public static void centerScreen( Window window ) {
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - window.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - window.getHeight()) / 2);
	    window.setLocation(x, y);
	} 
	
		/* Die Debug - Console */
	
	public static void setDebug ( int x, int y , int currentAction) {
		playerX = x;
		playerY = y;
		action = currentAction ;
	}
	
	public static void debug( Graphics2D graphics) {
		graphics.setColor(Color.red);
		graphics.setFont(new Font("Rockwell", Font.PLAIN, 15));

		graphics.drawString("FPS          : " + GamePanel.getFPS(), 20, 30); 
		if ( gameStarted ) {
			graphics.drawString("Position X : " + playerX, 20, 60);
			graphics.drawString("Position Y : " + playerY, 20, 94); 
			String string = "";
			if ( action == 0) string = "Idle";
			if ( action == 1) string = "Walking";
			if ( action == 2) string = "Jumping";
			if ( action == 3) string = "Falling";
			if ( action == 4) string = "Firing";
			if ( action == 5) string = "Dead";
			if ( action == 6) string = "Hit";
			graphics.drawString("Current Action : " + string, 20, 134);
		}
		/* Die Klasse muss noch bearbeitet Werden */
//		sys.drawSystemDetails(graphics);
	}
	public static void drawVerison ( Graphics2D graphics ){
		graphics.setFont(version);
		graphics.setColor(Color.DARK_GRAY);
		graphics.drawString(" version 0.1.0", 10, GamePanel.HEIGHT -20); 
	}
		
		/* Ueberschriften Zentrieren */
	
	public static void drawCenteredString( String string, int width, int height, Graphics graphics ) {
	    FontMetrics fm = graphics.getFontMetrics();
	    int x = (width - fm.stringWidth(string)) / 2;
	    int y = height;
	    graphics.drawString(string, x, y);
	} 
} // End of Game

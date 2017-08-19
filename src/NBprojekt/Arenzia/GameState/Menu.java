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
package NBprojekt.Arenzia.GameState;

/** Importierete Pakete */ 
import java.awt.Color; 
import java.awt.Font;
import java.awt.Graphics2D; 
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage; 

import javax.imageio.ImageIO; 

import NBprojekt.Arenzia.Main.Game;
import NBprojekt.Arenzia.Main.GameListener;
import NBprojekt.Arenzia.Main.GamePanel; 
import NBprojekt.Arenzia.TileMap.Background; 


public class Menu extends GameState {
		/* Variablendeklaration */
	private Background background;
	private BufferedImage titleBanner;
	private int currentOption;
	private String [] options = { "Start story mode", "Level select ", "Help", "Exit"}; 
	   
	private Font font, credits; 
		/* Standartkonstruktor */
	
	public Menu( GameStateManager gameStateManager ) {  
		GamePanel.WIDTH = 1280;
		GamePanel.HEIGHT = 800;
		 
		this.gameStateManager = gameStateManager;
		
		try {
			background = new Background( "/menu/bg.gif", 99999); 
			background.setVector(-0.2, 0);
			
			titleBanner = ImageIO.read(getClass().getResourceAsStream("/titleBanner.png")); 
			  
			font = new Font("3D Techno Front", Font.TRUETYPE_FONT, 40); 
			credits  = new Font("Rockwell", Font.PLAIN, 15); 
		} catch (Exception e) {  
			e.printStackTrace();
		}
		 
		start = System.nanoTime();  
	}
	
	// Initialisierungs-Methode
	public void init() { } 
	
		/* Methoden fuer das Tastatur mitlesen */
	
	@Override
	public void keyPressed(int key) { 
		if ( key == KeyEvent.VK_ENTER )
			select();
		
		if ( key == KeyEvent.VK_UP){
			currentOption--;
			if ( currentOption < 0 )
				currentOption = options.length - 1 ;
		}
		if ( key == KeyEvent.VK_DOWN){
			currentOption++;
			if ( currentOption > options.length - 1 )
				currentOption = 0 ;
		}
		if ( key == KeyEvent.VK_F3){
			if (Game.debugConsole)
				Game.debugConsole = false;
			else 
				Game.debugConsole = true;
		}
	}

	@Override
	public void keyReleased(int key) { 
		
	}
	
		/* Sonstige benötigte Methoden */
	
	// Auswahl was geschehen soll
	private void select() {
		switch ( currentOption ) {
			// Spiel Starten
			case 0 : 
				Game.gameStarted = true;
				gameStateManager.setState(GameStateManager.STORY);
				break;
			// Level auswahl
			case 1 : 
//				gameStateManager.setState(GameStateManager.LEVELSELECT);
				GameListener.windowClose();
				break;
			// Hilfe
			case 2 : 
//				gameStateManager.setState(GameStateManager.HELP);
				GameListener.windowClose();
				break; 
			// Spiel Verlassen
			case 3 :
				GameListener.windowClose();
				break;
		}
	}
	 
		/* Aktualisierungs- und Zeichnugs-Methoden */
	long now= 0,departed,start;
	@Override
	public void update() { 
		background.update();
		 
//		now  = System.nanoTime() - start;   
//		System.out.println(""+ now / 1000000000); 
//		if ( now / 1000000000 > 148 ){ 
//			start = System.nanoTime();
//			now = 0;
//			background = new Background( "/background/menu/bg.gif", 99999); 
//			background.setVector(-0.2, 0);
//		} 
	}

	@Override
	public void draw(Graphics2D graphics) { 
		background.draw(graphics);
		// Ueberschrift  
		graphics.drawImage(titleBanner, GamePanel.WIDTH / 2 - 230, 130, 450, 135, null);
		
		// Optionen
		graphics.setFont(font);
		for (int i = 0 ; i < options.length ; i++) {
			if ( i == currentOption ) {
				graphics.setColor(Color.GRAY); 
				Game.drawCenteredString("››  "+options[i]+"  ‹‹", GamePanel.WIDTH, 450 + i * 60, graphics);   
			}
			else {
				graphics.setColor(Color.DARK_GRAY);
				Game.drawCenteredString(options[i], GamePanel.WIDTH, 450 + i * 60, graphics); 
			} 
		} 
		
		graphics.setColor( Color.DARK_GRAY );
		graphics.setFont( credits );

		Game.drawCenteredString("Coding : 60h", GamePanel.WIDTH,  720, graphics); 
		Game.drawCenteredString("Created by Norbert Bartko", GamePanel.WIDTH,  740, graphics);
		Game.drawCenteredString("Code : github.com/NBprojekt ", GamePanel.WIDTH,  760, graphics); 
		Game.drawCenteredString("Intended for a 1280 x 800 Arcade Machine", GamePanel.WIDTH,  780, graphics);
		
		// Debug-Console
		if (Game.debugConsole)
			Game.debug(graphics);
		
		Game.drawVerison(graphics);
	} 
} // End of Menu

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

import NBprojekt.Arenzia.Main.Game; 
import NBprojekt.Arenzia.Main.GamePanel;
import NBprojekt.Arenzia.TileMap.Background; 


public class Help extends GameState {
		/* Variablendeklaration */
	
	private Background backgroundTop;
	private Background backgroundBottom; 
	private String [] actions = { "Laufen", 
								  "Springen", 
								  "Nachkampf", 
								  "Fernkapf",
								  "Fliegen",
								  "Pause"};  
	private String [] buttons = { "A / D / Pfeil links /  Pfeil rechts ",
								  "W / Leertaste / Pfeil hoch",
								  "R",
								  "F",
								  "wenn in der Luft  E",
								  "ESC"};
								  
	   
	private Font titleFont; 
	private Font font; 
	
		/* Standartkonstruktor */
	
	public Help( GameStateManager gameStateManager ) {  
		System.out.println("____________________________________________");
		System.out.println("\nHilfe wird geladen.");
		System.out.println("\nKomponenten werden Initialisiert .\n");
		this.gameStateManager = gameStateManager;
		
		try {
			backgroundTop = new Background( "/Hintergruende/Menue/grasBoden.gif", 0 );
			backgroundBottom = new Background( "/Hintergruende/Menue/boden.gif", 0 );
			
			backgroundTop.setVector(0, 0);
			backgroundBottom.setVector(0, 0); 
			
			System.out.print("Fonts werden geladen ..."); 
			titleFont = new Font("Optima", Font.PLAIN, 17);
			font = new Font("Apple Casual", Font.PLAIN, 12);
			System.out.println("\tLaden der Fonts erfolgreich abgeschlossen.");
		} catch (Exception e) { 
			System.out.println("\tFonts konnten leider nicht geladen werden.");
			e.printStackTrace();
		} 
		
		System.out.println("\nKomponenten wurden erfolgreich geladen."); 
	}
	
	// Initialisierungs-Methode
	public void init() { } 
	
		/* Methoden fuer das Tastatur mitlesen */
	
	@Override
	public void keyPressed(int key) { 
		if ( key == KeyEvent.VK_ENTER || key == KeyEvent.VK_ESCAPE)
			gameStateManager.setState(GameStateManager.MENU);
		
		
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
	 
		/* Aktualisierungs- und Zeichnugs-Methoden */
	
	@Override
	public void update() {   
	}

	@Override
	public void draw(Graphics2D graphics) {  
		backgroundBottom.draw(graphics);
		backgroundTop.draw(graphics);
		
		// Ueberschrift
		graphics.setColor(Color.WHITE);	
		graphics.setFont(titleFont); 
		Game.drawCenteredString("Hilfe", GamePanel.WIDTH, 30, graphics); 
		
		// Optionen 
		graphics.setFont(font);
		graphics.drawString("Steuerung : ", 30, 50);
		for (int i = 0 ; i < actions.length ; i++) { 
			
			graphics.drawString(actions[i], 30, 75 + i * 14);
			graphics.drawString(" : ", 105, 75 + i * 14);
			Game.drawCenteredString(buttons[i], GamePanel.WIDTH + 105, 75 + i * 14, graphics);  
		} 
		 
		graphics.setColor(Color.LIGHT_GRAY); 
		graphics.drawString("← Zurueck", 30 , GamePanel.HEIGHT - 50);
		
		// Debug-Console
		if (Game.debugConsole){
			Game.debug(graphics);
		}
	}
} // End of Help

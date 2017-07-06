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
package NBprojekt.Arenzia.GameState.Map;

/** Importierete Pakete */  
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import NBprojekt.Arenzia.GameState.GameState;
import NBprojekt.Arenzia.GameState.GameStateManager;
import NBprojekt.Arenzia.Main.Game;
import NBprojekt.Arenzia.Main.GamePanel;
import NBprojekt.Arenzia.Object.Archer;
import NBprojekt.Arenzia.TileMap.Background;
import NBprojekt.Arenzia.TileMap.Map; 


public class TestMap extends GameState{
		/* Variablendeklaration */
	
	private Map map;
	private Background background;
	private Archer archer;
	
		/* Standartkonstruktor */
	
	public TestMap (GameStateManager gameStateManager){
		this.gameStateManager = gameStateManager; 
		init();
	}

	// Initialisierungs-Methode
	@Override
	public void init() { 
		map = new Map(100);
//		map.loadTiles("/map/level1/level1_tileset.gif");
		map.loadTiles("/test_.gif");

//		map.loadMap("/map/level1/level1.map");
		map.loadMap("/test_.map");
		map.setPosition(0, 0);
		
		background = new Background("/background/menu/bg.gif", 0.09);
		
		archer = new Archer ( map );
		archer.setPosition(300, 400);
	}
	
		/* Methoden fuer das Tastatur mitlesen */

	@Override
	public void keyPressed(int key) { 
		
		if ( key == KeyEvent.VK_F3){
			if (Game.debugConsole)
				Game.debugConsole = false;
			else 
				Game.debugConsole = true;
		} 
		
		if ( key == KeyEvent.VK_LEFT ) 
			archer.setLeft(true);
		if ( key == KeyEvent.VK_RIGHT )
			archer.setRight(true); 
		if ( key == KeyEvent.VK_SPACE ) 
			archer.setJumping(true);
		if ( key == KeyEvent.VK_R )
			archer.setFiring();
	}

	@Override
	public void keyReleased(int key) { 
		if ( key == KeyEvent.VK_LEFT ) 
			archer.setLeft(false);
		if ( key == KeyEvent.VK_RIGHT )
			archer.setRight(false); 
		if ( key == KeyEvent.VK_SPACE ) 
			archer.setJumping(false);
	}
	
		/* Aktualisierungs- und Zeichnugs-Methoden */
	
	@Override
	public void update() { 
		archer.update();
		map.setPosition( GamePanel.WIDTH / 2 - archer.getX(), GamePanel.HEIGHT / 2 - archer.getY()); 
	}

	@Override
	public void draw(Graphics2D graphics) { 
		background.draw(graphics); 
		map.draw(graphics);
		archer.draw(graphics);
		
		// Debug-Console
		if (Game.debugConsole)
			Game.debug(graphics);
		
		Game.drawVerison(graphics);
	} 
} // End of Tutorial

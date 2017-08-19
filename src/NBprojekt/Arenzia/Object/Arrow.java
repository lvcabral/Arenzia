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
package NBprojekt.Arenzia.Object;

/** Importierete Pakete */ 

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import NBprojekt.Arenzia.TileMap.Map;

public class Arrow extends Entity {
	
		/* Variablendeklaration */
	private boolean hit, remove, shoot; 
	private long start, delay;
	private BufferedImage[] sprites; 
	
		/* Standartkonstruktor */
	
	public Arrow( Map map, boolean right, int animationDelay) {
		super(map); 
		
		facingRight = right;
		start = System.nanoTime();
		delay = (int) animationDelay * 4;
		moveSpeed = 12;   
	}
	
	private void init(){
		width = 100;
		height = 21;
		collisionWidth = 95;
		collisionHeight = 20;
		
		if ( facingRight ) 
			xSpeed = moveSpeed;
		else 
			xSpeed = - moveSpeed; 
		
		try {
			
			BufferedImage bufferedImage = ImageIO.read(getClass().getResourceAsStream("/player/arrow.gif"));
			
			sprites = new BufferedImage[ 1 ];
			for ( int i = 0 ; i < sprites.length ; i++ ) {
				sprites [ i ] = bufferedImage.getSubimage( i * width, 0, width, height ); 
			}
			
			animation = new Animation();
			animation.setFrames(sprites);
			animation.setDelay(-1); 
			
		} catch ( Exception e ) {
			e.printStackTrace();
			System.out.println("Can't load arrow");
		} 
	}
	
		/* Sonstige benoetigte Methoden */
	
	public void hit () {
		if ( hit ) 
			return;
		remove = hit = true; 
		xSpeed = 0; 
	}
	
	public boolean remove () {
		return remove;
	}

		/* Aktualisierungs- und Zeichnugs-Methoden */
	
	public void update () { 
		long elapsed = ( System.nanoTime() - start ) / 1000000;
		if (elapsed > delay ) {
			init();
			shoot = true;
		}
		
		if ( shoot ){
			checkMapCollision();
			setPosition( xTemp, yTemp );
			
			if ( xSpeed == 0 && !hit )
				hit();
			
			animation.update();
			
			if ( hit && animation.hasPlayed() ) 
				remove = true; 
		}
	}
	public void draw ( Graphics2D graphics ) {

		if ( shoot ) {
			setMapPosition(); 
		
			if ( facingRight )
				graphics.drawImage( animation.getImage(), (int) ( x + xMap - width / 2 ), 
							(int) ( y + yMap - height / 2 + 22), null);   
			else 
				 graphics.drawImage( animation.getImage(), (int) ( x + xMap - width / 2 + width ) , 
						(int) ( y + yMap - height / 2 + 22 ), -width , height, null); 
		}
	}
} // End of Aniamtion
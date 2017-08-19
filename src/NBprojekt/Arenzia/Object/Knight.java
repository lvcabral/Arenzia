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
import java.util.ArrayList;

import javax.imageio.ImageIO; 

import NBprojekt.Arenzia.Main.Game;
import NBprojekt.Arenzia.TileMap.Map;


@SuppressWarnings("unused")
public class Knight extends Entity {
	
		/* Variablendeklaration */ 
	
	private int health, maxHealth, damage, doubleJumpStart;	 
	private boolean dead, flinching, doubleJump, alreadyDoubleJump;
	private long flinchTime, start, delay;

	private boolean attacking;  
	private int maxArmor, numArmor;
	private ArrayList < Arrow > arrows;
	 
	private static final int IDLE = 0;
	private static final int WALKING = 1;
	private static final int JUMPING = 2;
	private static final int FALLING = 3;
	private static final int ATTACKING = 4;
	private static final int DEAD = 5;
	private static final int HIT = 6;
	 
	private ArrayList < BufferedImage [] > sprites;
	private final int [] numFrames = { 4, 4, 1, 1, 4, 3, 1 };
	
		/* Standartkonstruktor */
	
	public Knight (Map map) {
		super(map); 
		
		width = 105;
		height = 168;
		collisionWidth = 80;
		collisionHeight = 150;
		
		moveSpeed = 1.9;
		maxSpeed = 4;
		stopSpeed = 1.6;
		fallSpeed = 0.3;
		maxFallSpeed = 9.0;
		jumpStart = -10.8;
		stopJumpSpeed = 6;
		doubleJumpStart = -8;
		
		facingRight = true; 
		
		health = maxHealth = 10;
		numArmor = maxArmor = 25;
		damage = 3;
		arrows = new ArrayList < Arrow >();
		delay = 95;
		start = 0;
		
		 
		try { 
			BufferedImage spriteSheet = ImageIO.read( 
					getClass().getResourceAsStream("/player/knight.gif") );
			
			sprites = new ArrayList < BufferedImage[] > ();
			
			for ( int i = 0 ; i < 7 ; i++ ) {
				BufferedImage [] bufferedImage = new BufferedImage [ numFrames[i] ] ;
				
				for ( int j = 0 ; j < numFrames[i] ; j++ ) {
					if ( i <= FALLING || i == HIT )
						bufferedImage [j] = 
							spriteSheet.getSubimage( j * width, i * height, width, height );
					else if ( i == ATTACKING )
						bufferedImage [j] = 
							spriteSheet.getSubimage( j * 190, i * height, 180, height );
					else if ( i == DEAD )
						bufferedImage [j] = 
							spriteSheet.getSubimage( j * 180, i * height, 170, height );					
				}
				sprites.add(bufferedImage);
			}
		} catch ( Exception e ) { 
			e.printStackTrace(); 
			System.out.println("Can't load archer");
		}
		
		animation = new Animation();
		currentAction = IDLE; 
		animation.setFrames(sprites.get(IDLE));;
		animation.setDelay(500);   
	}
	
		/* Set- und Get-Methoden*/
		
	public void setAttacking () {
		attacking = true;
	}
	@Override
	public void setJumping ( boolean bool ) { 
		if( bool && !jumping && falling && !alreadyDoubleJump ) {
			doubleJump = true;
		} 
		jumping = bool;
	}
	public int getHealth () {
		return health;
	}
	public int getMaxHealth () {
		return maxHealth;
	}
	public int getnumArmor () {
		return numArmor;
	}
	public int getmaxArmor () {
		return maxArmor;
	}
	

	private void getNextPosition () {
		if(left) { 
			xSpeed -= moveSpeed ;
			if(xSpeed < -maxSpeed) {
				xSpeed = -maxSpeed;
			}  
		}
		else if(right) {  
			xSpeed += moveSpeed;
			if(xSpeed > -maxSpeed) {
				xSpeed = maxSpeed;
			} 
		}
		else {
			if(xSpeed > 0) {
				xSpeed -= stopSpeed;
				if(xSpeed < 0) {
					xSpeed = 0;
				}
			}
			else if( xSpeed < 0) {
				xSpeed += stopSpeed;
				if( xSpeed > 0) {
					xSpeed = 0;
				}
			}
		} 
		if ( currentAction == ATTACKING && !( jumping || falling )) {
			xSpeed = 0;
		}
				
		if( jumping && !falling ) { 
			ySpeed = jumpStart;
			falling = true; 
		}
		
		if( doubleJump ) {
			ySpeed = doubleJumpStart;
			alreadyDoubleJump = true;
			doubleJump = false;  
		}
		
		if( !falling ) 
			alreadyDoubleJump = false;
		 
		if(falling) {
			ySpeed += fallSpeed;
			
			if	( ySpeed > 0 ) 
				jumping = false;
			if ( ySpeed < 0 && !jumping ) 
				ySpeed += stopJumpSpeed;
			if ( ySpeed > maxFallSpeed ) 
				ySpeed = maxFallSpeed;
		}
		
			
	} 
	
		/* Aktualisierungs- und Zeichnugs-Methoden */
	
	public void update () {
		getNextPosition();
		checkMapCollision();
		setPosition( xTemp, yTemp );
		
		if ( currentAction == ATTACKING )
			if ( animation.hasPlayed() )
				attacking = false;
		 
		
		if ( numArmor > maxArmor )
			numArmor = maxArmor;
		
		
		if ( attacking ) {
			if ( currentAction != ATTACKING ) {
				currentAction = ATTACKING ;
				animation.setFrames(sprites.get( ATTACKING ));
				animation.setDelay(100);
				width = 140;
			}
		} 
		
		else if ( ySpeed > 0) {
			if ( currentAction != FALLING ) {
				currentAction = FALLING;
				animation.setFrames(sprites.get( FALLING ) );
				animation.setDelay(-1);
				width = 90;
			}
		}
		
		else if (ySpeed < 0) {
			if (currentAction != JUMPING ) {
				currentAction = JUMPING;
				animation.setFrames(sprites.get( JUMPING ));
				animation.setDelay(-1);
				width = 90;
			}
		}
		
		else if ( left || right ) {
			if ( currentAction != WALKING ) {
				currentAction = WALKING;
				animation.setFrames(sprites.get( WALKING ));
				animation.setDelay(150);
				width = 90;
			}
		}
		
		else {
			if ( currentAction != IDLE ) {
				currentAction = IDLE;
				animation.setFrames(sprites.get(  IDLE ));
				animation.setDelay(400);
				width = 90;
			}
		}
		
		animation.update();
		
		if ( currentAction != ATTACKING ) { 
			if ( right )
				facingRight = true;
			if ( left )
				facingRight = false;
		}
		 
		
		Game.setDebug( (int) x , (int) y , currentAction ); 
	}
	
	 
	public void draw ( Graphics2D graphics ) {
		setMapPosition(); // call this first in every draw method
		
		if ( flinching ) {
			long elapsed = ( System.nanoTime() - flinchTime ) / 1000000;
			if ( elapsed / 100 % 2 == 0 )
				return;
		}
		
		if ( facingRight ){ 
			graphics.drawImage( animation.getImage(), (int) ( x + xMap - width / 2 )- 15, 
					(int) ( y + yMap - height / 2 ) + 15, null); 
		}
			
		else { 
			graphics.drawImage( animation.getImage(), (int) ( x + xMap - width / 2 + width ) + 15, 
				(int) ( y + yMap - height / 2 ) + 15, -width - 10, height, null);
		}
		
		
			

		// HitBox
		if ( Game.debugConsole ) {
//			Rectangle r = newRectangle(); 
//			graphics.draw(r);
		} 
	}  
} // End of Aniamtion
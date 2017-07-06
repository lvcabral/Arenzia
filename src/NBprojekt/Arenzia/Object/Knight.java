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

package NBprojekt.Arenzia.Object;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import NBprojekt.Arenzia.TileMap.Map;

public class Knight extends Entity {
		/* Variablendeklaration */
	
	// Standart Variablen
	private int health, maxHealth, arrows, damage;
	private boolean dead, flinching;
	private long flinchTime;
	
	// Variablen nur für den Ritter
	private boolean striking; 
	private int range;  
	
	public Knight (Map map) {
		super(map); 
		
	}

}

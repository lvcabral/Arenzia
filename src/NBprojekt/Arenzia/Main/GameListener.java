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
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener; 


public class GameListener implements WindowListener {  
		/* Überschreiben der Schliessungs Methode um eine Nachricht auszugeben */
	
	@Override
	public void windowClosing(WindowEvent arg0) { 
		windowClose();
	} 
	
	/** Nothing to do here */
	public void windowClosed(WindowEvent arg0) {} 
	public void windowActivated(WindowEvent arg0) {} 
	public void windowDeactivated(WindowEvent arg0) {} 
	public void windowDeiconified(WindowEvent arg0) {} 
	public void windowIconified(WindowEvent arg0) {} 
	public void windowOpened(WindowEvent arg0) {}  
	
		/* Eigene Methode um das Programm zu schliessen*/
	
	public static void windowClose() { 
		System.exit(0);
	}
}// End of GameListener 

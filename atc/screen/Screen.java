/**
 * 
 */
package atc.screen;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import atc.atc.Board;
import atc.atc.GameState;
import atc.atc.Gate;
import atc.atc.Plane;

/**
 * @author sa
 *
 */
public class Screen implements Observer,Runnable{
	
	private static Board map;
	private static Terminal term;
	private static Screen instance; 
	
	
		
	public Screen(){
		map = Board.readMap(GameState._defaultBoardFile);
		term = Terminal.getTerminal();
	}
	
	public Screen(String filename){
		map = Board.readMap(filename);
	}
	
	
	public void run(){
		initMap();
	}
	
		
	public void update(Observable obj, Object arg){		
		// Clear the terminal
		term.clearScreen();
		
		// Draw the borders and gates
		drawMap();
		
		// Draw planes
		Map<Character,Plane> planes = (Map<Character,Plane>) arg;
		
		//System.out.println("----------DESENHAR AVIOES----------");
		for(Plane p : planes.values())
			if(p.getSymbol()=='+')
				term.setData(p.getxCoord(), p.getyCoord(), Terminal.RED, Terminal.WHITE, '+');
			else{
				//if(p.getSymbol()=='A') System.out.println("Avi‹o: "+p.getSymbol()+" ( "+p.getxCoord()+" , "+p.getyCoord()+" , " + p.getAltitude() +" )");
				term.setData(p.getxCoord(), p.getyCoord(), p.getSymbol());
			}
		// Render
		term.repaint();
	}
	
	private static void initMap(){
		final Frame f = new Frame("ATC");
		f.addWindowListener( new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				f.dispose();
			}
		} );

		
		// Terminal settings
		term = new Terminal(map.getWidth(),map.getHeight());
		term.setScroll(false);
		term.setCursorVisible(false);

		drawMap();

		// Frame settings
		f.setSize(term.getTileWidth()*map.getWidth(),term.getTileHeight()*map.getHeight());
		f.setResizable(false);
		f.add(term, BorderLayout.CENTER);
		term.validate();
		f.pack();
		f.setVisible(true);
	}
	
	private static void drawMap(){
		int x,w=map.getWidth(),h=map.getHeight();
		
		// Draw borders
		for(x=0;x<h;x++){
			term.gotoxy(0, x);
			term.print('|');
		}
		for(x=0;x<h;x++){
			term.gotoxy(w-1, x);
			term.print('|');
		}
		for(x=0;x<w;x++){
			term.gotoxy(x, 0);
			term.print('_');
		}
		for(x=0;x<w;x++){
			term.gotoxy(x, h-1);
			term.print('_');
		}		
		
		// Draw ports
		for(Gate p : map.getPorts().values()){
			term.gotoxy(p.getxCoord(), p.getyCoord());
			term.print(p.getSymbol());
		}
		
		term.repaint();
	}	
}

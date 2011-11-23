package atc.screen;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;
import java.util.TreeSet;

import atc.atc.GameState;
import atc.atc.Plane;
import atc.atc.PlaneComparator;

public class Scoreboard implements Runnable, Observer{

	private GameState gs;
	private static Terminal term;
	
	public Scoreboard(GameState gs){
		this.gs=gs;
	}
	
	@Override
	public void run() {

		initScoreboard();
		
	}

	@Override
	public void update(Observable o, Object arg) {
		term.clearScreen();
		term.gotoxy(26, 1);
		term.println("ATC");
		term.gotoxy(1, 3);
		term.println("Saidas com sucesso: "+gs.getSuccessfulExits());
		term.gotoxy(1, 4);
		term.println("Saidas sem sucesso: "+gs.getUnsucessfulExits());
		printPlaneInfo();
	}
	
	private void initScoreboard(){
		final Frame f = new Frame("Score");
		f.addWindowListener( new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				f.dispose();
			}
		} );

		// Terminal settings
		term = new Terminal(55,40);
		term.setScroll(false);
		term.setCursorVisible(false);

		// Frame settings
		f.setSize(term.getTileWidth()*term.getWidth(),term.getTileHeight()*term.getHeight());
		f.setResizable(false);
		f.add(term, BorderLayout.CENTER);
		term.validate();
		f.pack();
		f.setVisible(true);
	}
	
	private void printPlaneInfo(){
		int i=6;
		
		TreeSet<Plane> set = new TreeSet<Plane>(new PlaneComparator());
		
		for(Plane p : gs.getFrontBuffer().values())
			set.add(p.clone());
		
		for(Plane p : set){
			term.gotoxy(2,i++);
			term.println(p.toString());
		}
	}

}

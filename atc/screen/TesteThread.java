package atc.screen;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Random;

import atc.atc.Gate;
import atc.atc.Plane;

public class TesteThread extends Observable implements Runnable{
	
	public void run(){
	
		Random r = new Random();
		Map<Character,Plane> buf = new HashMap<Character,Plane>();
		char id='A';
		
		for(int i=0;i<15;i++,id++){
			int x = r.nextInt(50);
			int y = r.nextInt(30);
			
			synchronized(this){
				try {
					this.wait(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			buf.put(id, new Plane(id, new Gate('c',0,15), 3000,	3000, 3000, x, y, 3));
			setChanged();
			notifyObservers(buf);
		}
		
	}

}

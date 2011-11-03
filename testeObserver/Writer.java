package testeObserver;

import java.io.IOException;
import java.util.Observable;

public class Writer implements Runnable{
	private Observable obs;
	
	
	public Writer(ReaderThread rd){
		obs = new Observable();
		obs.addObserver(rd);
	}
	
	public void run(){
		int i=0;
		
		System.out.println(obs.countObservers());
		
		while(i<15){
			try {
				System.in.read();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(i+": Acordei!");
			obs.setChanged();
			obs.notifyObservers(i);
			i++;
		}
	}
}

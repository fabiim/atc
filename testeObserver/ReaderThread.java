package testeObserver;

import java.util.Observable;
import java.util.Observer;

public class ReaderThread implements Runnable,Observer{

	public void run(){
		int i=0;
		
		while(true){
			i++;
		}
		
		/*
		try {
			this.wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		System.out.println(arg + "weleeee");
	}
	
}

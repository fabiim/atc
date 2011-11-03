package testeObserver;

public class Main {

	public static void main(String[] args){		
		ReaderThread rd = new ReaderThread();
		Thread t0 = new Thread(rd);
		Thread t1 = new Thread(new Writer(rd));
		
		t0.start();
		t1.start();
		try {
			t1.join();
			t0.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

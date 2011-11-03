package testeObserver;

public class Main {

    public static void main(String args[]) {
        System.out.println("Enter Text >");
 
        // create an event source - reads from stdin
        final Writer writer = new Writer();
 
        // create an observer
        final Reader respHandler = new Reader();
 
        // subscribe the observer to the event source
        writer.addObserver( respHandler );
 
        // starts the event thread
        Thread thread = new Thread(writer);
        thread.start();
    }
	
}

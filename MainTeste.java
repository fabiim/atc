import java.io.IOException;

import atc.atc.GameState;
import atc.util.SerializableInterface;


public class MainTeste {
	public static void main (String[] args) throws IOException{
		
		GameState g = new GameState(); 
		byte[] bytes = SerializableInterface.objectToByte(g);
		try {
			Object o = (GameState ) SerializableInterface.byteToObject(bytes);
			
		} catch (ClassNotFoundException e) {
			System.err.println(e.getMessage());
			e.printStackTrace(); 
		}
		System.out.println("ola"); 
		System.in.read(); 
	}
}


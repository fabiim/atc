
import java.io.IOException;

import atc.atc.GameState;
import atc.dispatchers.ChannelCreator;
import atc.messages.StateMessage;
import atc.screen.Console;
import atc.screen.Screen;
import atc.util.SerializableInterface;
import net.sf.appia.jgcs.*; 
import net.sf.jgcs.*;
import net.sf.jgcs.membership.BlockSession;

import org.apache.log4j.*;

public class BootStrap {
	static Logger logger = Logger.getLogger(BootStrap.class); 
	public static void main (String[] args) {
		GameState gg = new GameState(); 
		; 
		try {
			byte[] bbs = SerializableInterface.objectToByte(gg);
			GameState b = (GameState) SerializableInterface.byteToObject(bbs); 
			
			StateMessage st = new StateMessage(b);  
			SerializableInterface.byteToObject(SerializableInterface.objectToByte(st));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		
		try {
		    AppiaProtocolFactory pf=new AppiaProtocolFactory();
		    Protocol p=pf.createProtocol();
		    AppiaGroup g=new AppiaGroup();
		    g.setConfigFileName("appia.xml");
		    g.setGroupName("tfd000group");
		   
			AppiaService servTotal = new AppiaService("vsc+total");
		    BlockSession controlS = (BlockSession)p.openControlSession(g);
		    DataSession dataS = p.openDataSession(g);
		    GameState game = new GameState(); 
		    ChannelCreator.CreateChannel(game, servTotal, controlS, dataS);
		    controlS.join();
		    Screen scr = new Screen();
		    
		    game.addObserver(scr);
		    
		    Console con = new Console(game, ChannelCreator.getSenderDispatcher());
		    new Thread(scr).start();
		    new Thread(con).start();
	}catch(Exception e){
		System.err.println(e.getMessage());
		e.printStackTrace(); 
	}
	}
}

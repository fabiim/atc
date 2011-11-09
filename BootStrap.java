
import atc.atc.GameState;
import atc.dispatchers.ChannelCreator;
import atc.screen.Console;
import atc.screen.Screen;
import net.sf.appia.jgcs.*; 
import net.sf.jgcs.*;
import net.sf.jgcs.membership.BlockSession;

import org.apache.log4j.*;

public class BootStrap {
	static Logger logger = Logger.getLogger(BootStrap.class); 
	public static void main (String[] args) {
		BasicConfigurator.configure(); 
		logger.info("Starting the bootstrap ");
		
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
		    
		    Screen scr = new Screen();
		    Console con = new Console(game, ChannelCreator.getSenderDispatcher());
		    
		    game.addObserver(scr); 
		    new Thread(scr).start();
		    new Thread(con).start();
		    
		    ChannelCreator.CreateChannel(game, servTotal, controlS, dataS); 
		    controlS.join();
	}catch(Exception e){
		System.err.println(e.getMessage());
		e.printStackTrace(); 
	}
	}
}

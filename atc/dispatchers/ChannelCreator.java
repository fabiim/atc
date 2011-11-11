package atc.dispatchers;
import atc.messages.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import net.sf.appia.jgcs.AppiaService;
import net.sf.jgcs.ClosedSessionException;
import net.sf.jgcs.DataSession;
import net.sf.jgcs.JGCSException;
import net.sf.jgcs.membership.BlockSession;

import atc.atc.GameState;

/**
 * Will Create proper dual channel. 
 * Use this class to get ReceiverDispatcher and SendDispatcher created. 
 * @author fabiobotelho
 *
 */
public class ChannelCreator {

	private static SendDispatcher sender; 
	private static ReceiverDispatcher receiver; 
	
	public static SendDispatcher getSenderDispatcher(){
		return sender; 
	}
	public static ReceiverDispatcher getReceiverDispatcher(){
		return receiver; 
	}
	
	public static void CreateChannel(GameState game, AppiaService servTotal,
			BlockSession controlS, DataSession dataS) throws JGCSException{
		
		
		BlockingQueue<LpcMessage> queue = new LinkedBlockingQueue<LpcMessage>();
			receiver = new ReceiverDispatcher(game,queue, controlS); 
		 sender = new SendDispatcher(controlS, dataS, servTotal);
		 
		(new Thread(new ReceiverSenderChannelThread(queue, sender))).start(); 
		
		
		dataS.setMessageListener(receiver);
		dataS.setExceptionListener(receiver);
		controlS.setMembershipListener(receiver);
	    controlS.setBlockListener(receiver);
	    
	}

	public static void CreateChannel() {
		// TODO Auto-generated method stub
		
	}
}

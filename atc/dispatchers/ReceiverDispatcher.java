package atc.dispatchers;
import java.util.concurrent.BlockingQueue;

import net.sf.jgcs.ExceptionListener;
import net.sf.jgcs.JGCSException;
import net.sf.jgcs.Message;
import net.sf.jgcs.MessageListener;
import net.sf.jgcs.membership.BlockListener;
import net.sf.jgcs.membership.MembershipListener;
import atc.atc.*;

/**
 * This entitie is registered under JGCS as the following: 
 * MessageListener, ExceptionListener, MembershipListener, BlockListener
 * This means that it is the one responsible for receiving all messages concerning communication.
 * As an MessageListener it semi-parses received messages from the underlying jgcs channel and applies them to the game.  
 * As a Membership and BlockListener it will produce appropriate inter layer communications messages and send them to the SendDispatcher trough the ReceiverSenderChannel
 * 
 * Creation is trough ChannelCreator
 *  
 * @author fabiobotelho
 *
 */

public class ReceiverDispatcher implements MessageListener, ExceptionListener, MembershipListener, BlockListener {
	private GameState game;
	private final BlockingQueue<ReceiverSenderMessage> queue; 

	//Protected constructor. ChannelCreator can call him
	protected ReceiverDispatcher(GameState game, BlockingQueue<ReceiverSenderMessage> queue) {
		this.game = game; 
		this.queue = queue; 
	}
	
	
	@Override
	public void onBlock(){
		queue.add(new ReceiverSenderMessage("block") ); 
	}
	
	@Override
	public void onExcluded(){
		// TODO - Auto-generated 
	}

	@Override
	public void onMembershipChange() {
		queue.add(new ReceiverSenderMessage("new membership")); 
	}

	@Override
	public void onException(JGCSException arg0){
		System.err.println("JGCS Exception: " + arg0.getMessage());
		arg0.printStackTrace(System.err); 
	}

	
	@Override
	public Object onMessage(Message arg0){
	// TODO - make me
		return null; 
	}
	
}

package atc.dispatchers;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

import net.sf.jgcs.ExceptionListener;
import net.sf.jgcs.JGCSException;
import net.sf.jgcs.Message;
import net.sf.jgcs.MessageListener;
import net.sf.jgcs.membership.BlockListener;
import net.sf.jgcs.membership.MembershipListener;
import atc.atc.*;
import atc.messages.StateMessage;
import atc.util.ProducerConsumer;

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
	private final BlockingQueue<LpcMessage> queue; 
	private final LpcMessage lpcFactory = new LpcMessage(); 
	//Protected constructor. ChannelCreator can call him

	protected ReceiverDispatcher(GameState game, BlockingQueue<LpcMessage> queue) {
		this.game = game; 
		this.queue = queue; 
	}
	
	
	
	@Override
	public void onBlock(){
		LpcMessage.Block m = lpcFactory.new Block(); 
		produce(m); 
	}
	
	@Override
	public void onExcluded(){
		//TODO - AUto generated
	}

	
	@Override
	public void onMembershipChange() {
		StateMessage sm = new StateMessage(game);
		//This is important. A SyncQueue will hold APPIA (the thread running this method) until the item is consumed in the queue.   
		SynchronousQueue<Integer> qi = new SynchronousQueue<Integer>();
		LpcMessage.MembershipChange lm = lpcFactory.new MembershipChange(sm,qi); 
		
		produce(lm); // produced LPC message containing above payload. 
		
		//Now wait for someone to produce answer on qi.
		boolean consumed = false; 
		
		//ProducerConsumer.consume(consume); 
		
		while(!consumed){
			
		}
	
		// Send message with state.
		//Send SyncQueue with integer. 
		//save that inside
		//consume the integer.
		// We are synced and ready to go. 
		
		//queue.add(new ReceiverSenderMessage("new membership")); 
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
	

	private void produce(final LpcMessage m){
	}
	
	private void consume(final LpcMessage m){
	}
}

package atc.dispatchers;
import java.io.IOException;
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
import atc.util.SerializableInterface;
import org.apache.log4j.*;
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

	// If true we are in the state where we must wait for all stateGame messages
	private boolean resolving_states_state = false;
	private boolean blocked_state = false; 
	private Integer received_states = null; 
	private Integer membership_size = null;
	private Logger logger = Logger.getLogger("ReceiverDispatcher");

	
	protected ReceiverDispatcher(GameState game, BlockingQueue<LpcMessage> queue) {
		this.game = game; 
		this.queue = queue; 
	}
	
	
	
	@Override
	public void onBlock(){
		LpcMessage.Block m = lpcFactory.new Block(); 
		ProducerConsumer.produce(queue, m); 

		//TODO - block state never read locally
		blocked_state = true;
		resolving_states_state = false;  
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
		
		ProducerConsumer.produce(queue,lm); // produced LPC message containing above payload. 
		
		//Now wait for someone to produce answer on qi.

		
		//ProducerConsumer.consume(consume); 
		
		membership_size = ProducerConsumer.consume(qi);
		received_states = new Integer(0); 
		resolving_states_state = true;
		blocked_state = false; 
	}

	@Override
	public void onException(JGCSException arg0){
		System.err.println("JGCS Exception: " + arg0.getMessage());
		arg0.printStackTrace(System.err); 
	}

	
	@Override
	public Object onMessage(Message msg){
		atc.messages.Message  game_message = null; 
		try {
			  game_message	= (atc.messages.Message) SerializableInterface.byteToObject(msg.getPayload());
			game.processMsg(game_message); 
		} catch (Exception e) {
			logger.fatal("Could not deserialize received message");
			logger.fatal(e.getMessage() + e.getStackTrace()); 
		} 
		
		if (resolving_states_state){
			if (game_message instanceof StateMessage){
				//we accept those messages in this state
				
				//TODO - if not in total order we have the problem of processes choosing different states in different ticks
				StateMessage sm = (StateMessage) game_message; 
				if (sm.getGame().getEpoch() > game.getEpoch()){
					game = sm.getGame(); 
				}
				
				//check to see if it is the last message
				received_states ++; 
				if (received_states == membership_size) {
					resolving_states_state = false; 
				}
			}
		}
		else{
			game.processMsg(game_message); 
		}
		return null; 
	}
}

package atc.dispatchers;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

import net.sf.jgcs.ExceptionListener;
import net.sf.jgcs.JGCSException;
import net.sf.jgcs.Message;
import net.sf.jgcs.MessageListener;
import net.sf.jgcs.NotJoinedException;
import net.sf.jgcs.membership.BlockListener;
import net.sf.jgcs.membership.BlockSession;
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
	private Logger logger = Logger.getLogger(ReceiverDispatcher.class);
	private BlockSession control;

	protected ReceiverDispatcher(GameState game, BlockingQueue<LpcMessage> queue, BlockSession controlS) {
		logger.setLevel(Level.INFO); 
		this.game = game; 
		this.queue = queue;
		this.control = controlS; 
	}
	
	@Override
	public void onBlock(){
		logger.info("Received onBlock..."); 
		LpcMessage.Block m = lpcFactory.new Block(); 
		ProducerConsumer.produce(queue, m); 

		//TODO - block state never read locally
		blocked_state = true;
		resolving_states_state = false; 
		logger.info("...returning control of execution to appia" );
	}
	
	@Override
	public void onExcluded(){
		//TODO - Auto generated
	}

	
	@Override
	public void onMembershipChange() {
		logger.info("Membership changing... "); 
		
		StateMessage sm = new StateMessage(game);
		//This is important. A SyncQueue will hold APPIA (the thread running this method) until the item is consumed in the queue.   
		LpcMessage.MembershipChange lm = lpcFactory.new MembershipChange(sm); 
		
		synchronized(control){
			try {
				membership_size = control.getMembership().getMembershipList().size();
			} catch (NotJoinedException e) {
				logger.fatal("Not joined ");
				//TODO - should end here now. 
				return; 
			} 
		}
		
		ProducerConsumer.produce(queue,lm); // produced LPC message containing above payload.
				

		logger.info("consumed the membership size:" + membership_size ); 
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
		logger.info("Received message..."); 
		try {
			game_message	= (atc.messages.Message) SerializableInterface.byteToObject(msg.getPayload());
		} catch (Exception e) {
			logger.fatal("Could not deserialize received message");
			
			e.printStackTrace(); 
			logger.fatal(e.getMessage() + e.getStackTrace());
			return null; 
		} 
		logger.info("Going to process message: "+ game_message.toString()); 
		
		//game.processMsg(game_message); 		
		
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

					//We have finished receiving all the states from the players. We MUST warn senderDispatcher that it is ok to send messages
					 LpcMessage.UnBlock  m = lpcFactory.new UnBlock() ;
					 ProducerConsumer.produce(queue, m); 
				}
			}
		}
		else{
			game.processMsg(game_message); 
		}
		return null; 
	}
}

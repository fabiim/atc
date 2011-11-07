package atc.dispatchers;


import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import atc.atc.GameState;
import atc.messages.*;

import com.sun.jmx.remote.util.Service;

import net.sf.jgcs.ClosedSessionException;
import net.sf.jgcs.DataSession;
import net.sf.jgcs.JGCSException;
import net.sf.jgcs.NotJoinedException;
import net.sf.jgcs.UnsupportedServiceException;
import net.sf.jgcs.membership.BlockSession;

class Ticker extends TimerTask{
	private final SendDispatcher sender; 
	//When running sends tick to members of the game

	public Ticker(final SendDispatcher sender){
		this.sender = sender; 
	}
	
	public void run() {
		sender.send(new Tick()); 
	}
	
}

public  class SendDispatcher{
	private static  long TICK_RATE_MSS = 100;
	public static void setTickRate(long rate) { TICK_RATE_MSS = rate ;}
	public static long getTickRate(){ return TICK_RATE_MSS ; }
	
	
	private BlockSession controlChannel;
	private DataSession dataChannel;
	private net.sf.jgcs.Service commandService; 
	
	private Lock  lock = new ReentrantLock(); // acquire to do anything. Sequential behaviour for now.

	 //INVARIANT: when true no messages are send by this object.
	private Boolean can_send_messages; // Refers to the blocked state on view synchronous protocol. 
	 

	 
	 //INVARIANT : If ticker is different from null the process is the current leader. 
	 Timer ticker= null ;   //Responsible for ticker thread. Sends tick.  
	
	 
	 protected SendDispatcher(BlockSession controlChannel, DataSession dataChannel, net.sf.jgcs.Service gameService) {
		 this.controlChannel = controlChannel; 
		 this.dataChannel = dataChannel; 
		 this.commandService = gameService; 
	 }

	 public void send(Message cmd){
		 lock.lock();
		 	if (can_send_messages) {
		 		try{
		 			broadcastMessage(cmd);  
		 		}
		 		finally{
		 			lock.unlock(); 
		 		}
		 	}		 	
	 }
	 
	 
	 public void block(){
		 lock.lock(); 
		 try{
		 		can_send_messages = false;
		 		controlChannel.blockOk(); // tell appia we shut up.

		 		// shutdown possible ticker
		 		if (ticker != null){ 
		 			ticker.cancel(); 
		 		}
		 		ticker = null; 
		 		
		 } catch (NotJoinedException e) {
			// TODO Auto-generated catch block
			 e.printStackTrace();
		} catch (JGCSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 finally{
			 lock.unlock();
			 
		 }
	 }
	 /**
	  * Inform new membership. 
	  * From now on only ticks messages are received. No messages are sended.
	  * As soon as the tick messages 
	  * @param state
	  */

	 public void membershipChange(StateMessage msg, SynchronousQueue<Integer> queue){
		 boolean produced = false; 
		 lock.lock();
		 
		 try{
			 int n = controlChannel.getMembership().getMembershipList().size();
			 while(!produced){
				 try{
					 queue.put(n);
					 produced = true; 
				 }catch(InterruptedException e){
					 continue; 
				 }
			 }
			 
			 //TODO - clean me up scotty. broadcastMessage checks can_send_messages 
			 //We can not send messages until we receive all states. 
			 can_send_messages = true; 
			 broadcastMessage(msg); 
			 can_send_messages = false; 
		 } catch (NotJoinedException e){
			 e.printStackTrace();
		 }
		 finally{
			 lock.unlock(); 
		 }
	 }
	 
	 
	 public void unBlock(){
		 lock.lock(); 
		 try{
			 can_send_messages = true;
		 }
		 finally{
			 lock.unlock();
		 }
	 }

	 	 /*
	 	  * Use this method to activate the leader mode in process. 
	 	  */
	 	public  void CreateTick(){
	 		ticker = new Timer();
	 		ticker.scheduleAtFixedRate(new Ticker(this), SendDispatcher.TICK_RATE_MSS, SendDispatcher.TICK_RATE_MSS); 
	 	}
	
	 	
	 private void  broadcastMessage(final Message command){
		 net.sf.jgcs.Message m;
		 byte[] payload;
		 try {
			lock.lock(); 
				if (!can_send_messages) {
					return; // Discards all command messages when in blocked state.   
				}
			m = dataChannel.createMessage();			
			payload = SerializableInterface.objectToByte(command);
			m.setPayload(payload);
			dataChannel.multicast(m, commandService, null, (net.sf.jgcs.Annotation[]) null);
		} catch (Exception e){
			System.err.println(e.getMessage()); 
			e.printStackTrace(System.err);
		}
		finally{
			lock.unlock(); 
		}
	 }
	 
    }

package atc.dispatchers;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

import atc.messages.*;;
/**
 * MOVE ALONG! NOTHING TO SEE HERE...
 * WE SHALL NOT USE THE HAMMER ANYMORE! 
 * @author shame
 *
 * Ok still here? 
 * The classes represent the methods (class name ) and parameters (class fields ) 
 * tha will be called on the SendDispatcher by ReceiverSenderChannel thread.
 */

public  class LpcMessage{
	
	public  void invokeMethod(SendDispatcher sender){
		System.err.println("Invoking empty methods"); 
	}
	
	public class Send extends LpcMessage{
		public final Message m; 
		Send(Message m) {
			this.m = m; 
		}
		
		public void invokeMethod(SendDispatcher sender){
			sender.send(m); 
		}
	}
	
	public class MembershipChange extends LpcMessage{ 
		public final StateMessage m; 
		public final SynchronousQueue<Integer> queue; 
		
		public MembershipChange(StateMessage m, SynchronousQueue<Integer> queue){
			this.m = m; 
			this.queue = queue; 
		}
	
		public void invokeMethod(SendDispatcher sender){
			sender.membershipChange(m, queue); 
		}
	
	}
	
	public class Block extends LpcMessage{
		public Block(){
			
		}
		public void invokeMethod(SendDispatcher sender){
			sender.block(); 
		}
	
	}
	
	public class UnBlock extends LpcMessage {
		public UnBlock(){
			
		}
		public void invokeMethod(SendDispatcher sender){
			sender.unBlock(); 
		}
	
	}
	
	public class CreateTick extends LpcMessage {
		public CreateTick(){
			
		}

		public void invokeMethod(SendDispatcher sender){
			sender.CreateTick(); 
		}
	}
}

package atc.dispatchers;

import java.util.concurrent.BlockingQueue;

/**
 * This class listens on the buffer for view synchronous and leader election related messages.
 *  
 * @author fabiobotelho
 *
 */

public class ReceiverSenderChannelThread implements Runnable{

	private final BlockingQueue<ReceiverSenderMessage> buffer; 
	private final SendDispatcher sender;  
	
	protected  ReceiverSenderChannelThread(BlockingQueue<ReceiverSenderMessage> buffer,
			SendDispatcher sender ){
		this.buffer = buffer; 
		this.sender = sender; 
	}
	
	public void run(){
		ReceiverSenderMessage bucket;  
		
		while (true){
			try{
				bucket = buffer.take();
			}catch(InterruptedException e){
				continue; 
			}
			// Consume the bucket -> Invoke methods on sender. 
		}
		
	}
	
}

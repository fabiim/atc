package atc.dispatchers;

import java.util.concurrent.BlockingQueue;

/**
 * This class listens on the buffer for view synchronous and leader election related messages.
 *  
 * @author fabiobotelho
 *
 */


public class ReceiverSenderChannelThread implements Runnable{

	private final BlockingQueue<LpcMessage> buffer; 
	private final SendDispatcher sender;  

	protected  ReceiverSenderChannelThread(BlockingQueue<LpcMessage> buffer,
			SendDispatcher sender ){
		this.buffer = buffer; 
		this.sender = sender; 
	}

	public void run(){
		LpcMessage  bucket = null;  
		while(true){
			boolean consumed=false; 

			while (!consumed){
				try{
					bucket = buffer.take();
				}catch(InterruptedException e){
					continue; 
				}
			}
			// Consume the bucket -> Invoke methods on sender.
			bucket.invokeMethod(sender); 
		}
	}
}


package atc.dispatchers;

import java.util.concurrent.BlockingQueue;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import atc.util.ProducerConsumer;

/**
 * This class listens on the buffer for view synchronous and leader election related messages.
 *  
 * @author fabiobotelho
 *
 */


public class ReceiverSenderChannelThread implements Runnable{

	private final BlockingQueue<LpcMessage> queue; 
	private final SendDispatcher sender;  
	private Logger logger = Logger.getLogger(ReceiverSenderChannelThread.class); 
	protected  ReceiverSenderChannelThread(BlockingQueue<LpcMessage> queue,
			SendDispatcher sender ){
		this.queue = queue; 
		this.sender = sender;
		logger.setLevel(Level.INFO); 
	}

	public void run(){
		LpcMessage  bucket = null;  
		while(true){
			bucket = ProducerConsumer.consume(queue);
			// Consume the bucket -> Invoke methods on sender.
			bucket.invokeMethod(sender); 
		}
	}
}


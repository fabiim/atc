package atc.util;

import java.util.concurrent.BlockingQueue;

public class ProducerConsumer {
	
	public static <E> void produce(BlockingQueue<E> queue,  E value){
		boolean produced = false; 
		while (!produced){
				try {
					queue.put(value);
					produced = true; 
				} catch (InterruptedException e){
					continue;
				}
		}
	}
	
	public static <E> E consume(BlockingQueue<E> queue){
		boolean consumed = false;
		E value = null; 
		while (!consumed){
				try {
					value = queue.take();
					consumed = true;
				} catch (InterruptedException e) {
					continue;
				}
		}
		return value;
	}
}

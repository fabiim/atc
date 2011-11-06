package atc.dispatchers;



import com.sun.jmx.remote.util.Service;

import net.sf.jgcs.DataSession;
import net.sf.jgcs.membership.BlockSession;


public  class SendDispatcher{

	
	 BlockSession controlChannel;
	 DataSession dataChannel;
	 Service gameService; 

	 
	 protected SendDispatcher(BlockSession controlChannel, DataSession dataChannel, Service gameService){
		 this.controlChannel = controlChannel; 
		 this.dataChannel = dataChannel; 
		 this.gameService = gameService; 
	 }
	 
	 
    }

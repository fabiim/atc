package atc.screen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import atc.messages.*;
import atc.atc.*;

import java.util.HashMap;
import java.util.Map;

public class Console implements Runnable{
	
	public void run(){
		String input;
		
        try {
            final InputStreamReader isr = new InputStreamReader( System.in );
            final BufferedReader br = new BufferedReader( isr );
            while(true){
                input = br.readLine();
                input = input.trim();
        		if(input.startsWith("@"))
        			sendChatMsg(input);
        		else
        			sendCommand(input);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void sendCommand(String input){
		String cmdRegex = "^[A-Z][at][1-9]";
		if(!input.matches(cmdRegex)) return;
		char idAviao = input.charAt(0);
		char comando = input.charAt(1);
		String value = input.substring(2);		
		
		switch(comando){
			case 'a':
				// Create SetHeightGoal message
				SetHeightGoal hmsg = new SetHeightGoal();
				hmsg.setPlaneId(idAviao);
				hmsg.setObjectiveHeight(Integer.valueOf(value)*1000);
				// Send message
				System.out.println(hmsg.toString());
				break;
			case 't':
				// Create Turn message
				Turn tmsg = new Turn();
				tmsg.setPlaneId(idAviao);
				if(Integer.valueOf(value)==5) return;
				tmsg.setDirection(Integer.valueOf(value));
				// Send message
				System.out.println(tmsg.toString());
				break;
			default:
				return;
		}
	}
	
	public void sendChatMsg(String input){
		input = input.substring(1);
		String nickRegex = "^[A-Za-z0-9]{2,16}"; // Nicknames are 1 alphanumeric words
		String[] splited = input.split(" ", 2);
		
		if(!splited[0].matches(nickRegex)) return;
		else{
			// Create chat message
			
			// Send message
			System.out.println(splited[0]+" says: "+splited[1]);
		}
	}
}

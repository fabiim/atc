package atc.screen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
	import java.util.Timer;

import atc.atc.GameState;
import atc.dispatchers.SendDispatcher;
import atc.messages.Chat;
import atc.messages.SetHeightGoal;
import atc.messages.Turn;

public class Console implements Runnable{
	
	private Timer timer;
	private NewPlaneTimer task;
	private SendDispatcher sd;
	
	public Console(GameState gs, SendDispatcher sd){
		timer=new Timer();
		task=new NewPlaneTimer(gs,sd);
		timer.scheduleAtFixedRate(task, 1000, 1000);
		this.sd = sd;
	}
	
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
		String cmdRegex = "^[A-Za-z][at][1-9]";
		if(!input.matches(cmdRegex)) return;
		char idAviao = input.charAt(0);
		char comando = input.charAt(1);
		String value = input.substring(2);		
		
		// Uppercase IDs
		if(Character.isLowerCase(idAviao)) idAviao = Character.toUpperCase(idAviao);
		switch(comando){
			case 'a':
				// Create SetHeightGoal message
				SetHeightGoal hmsg = new SetHeightGoal();
				hmsg.setPlaneId(idAviao);
				hmsg.setObjectiveHeight(Integer.valueOf(value)*1000);
				// Send message
				sd.send(hmsg);
				break;
			case 't':
				// Create Turn message
				Turn tmsg = new Turn();
				tmsg.setPlaneId(idAviao);
				if(Integer.valueOf(value)==5) return;
				tmsg.setDirection(Integer.valueOf(value));
				// Send message
				sd.send(tmsg);
				break;
			default:
				return;
		}
	}
	
	public void sendChatMsg(String input){
		input = input.substring(1);
		//String nickRegex = "^[A-Za-z0-9]{2,16}"; // Nicknames are 1 alphanumeric words
		//String[] splited = input.split(" ", 2);
		
		// Create chat message
		Chat cmsg = new Chat();
		// TODO Adicionar um nick ou identificador ao gajo
		cmsg.setText(input);
		// Send message
		sd.send(cmsg);
	}
}

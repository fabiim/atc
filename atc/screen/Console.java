package atc.screen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

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
        			createChatMsg(input);
        		else
        			createCommand(input);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void createCommand(String input){
		char idAviao = input.charAt(0);
		char comando = input.charAt(1);
		char value = input.charAt(2);

		switch(comando){
			case 'a':
				break;
			case 't':
				break;
			default:
				return;
		}
	}
	
	public void createChatMsg(String input){
		input = input.substring(1);
		String nickRegex = "^[A-Za-z0-9]{2,16}"; // Nicknames are 1 alphanumeric words
		
		/* Code de regex
		 * String regex1 = "^[0-9]{3}$";// any three digits
		 * Pattern pattern1 = Pattern.compile(regex1);
		 * pattern1.matcher(param1).matches()
		 */
		
		if(!input.matches(nickRegex));
	}
	
	public void parse(String input){
		String parsed;
				
	}

}

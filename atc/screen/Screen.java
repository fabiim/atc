/**
 * 
 */
package atc.screen;

import java.awt.*;
import java.awt.event.*;
//import java.util.Collections;
import java.util.Map;
import java.util.HashMap;

// Read Map
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import atc.atc.Gate;
import atc.atc.Board;

/**
 * @author sa
 *
 */
public class Screen {
	
	//Terminal screen;
	//Map map
	
	private static Board map;
	private static Terminal term;

	public static void main(String[] args){
		readMap("teste.map");
		initMap();
	}
	
	private static void readMap(String fileName){
		int width=20; // TODO valores default tem de ser discutidos
		int height=20;
		Map<Character,Gate> ports = new HashMap<Character,Gate>();
		String s;
		BufferedReader in;
		String[] porta;
		
		try {
			in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
			
			// TODO validar syntax
			
			// Ler
			// Width
			s = in.readLine();
			if(s==null); // TODO mega error
			s = s.substring(6); // "width=" has 6 chars
			s = s.trim();
			width = (Integer.valueOf(s));
			// Height
			s = in.readLine();
			if(s==null); // TODO mega error
			s = s.substring(7); // "height=" has 7 chars
			s = s.trim();
			height = (Integer.valueOf(s));			
			// Portas
			s = in.readLine();
			
			if(!s.equals("portas:")) System.out.println("erro"); // TODO mega error
			else{
				for(s = in.readLine();s!=null;s = in.readLine()){
					s.trim();
					porta = s.split(",");
					ports.put(porta[0].charAt(0),new Gate(porta[0].charAt(0),Integer.valueOf(porta[1]),Integer.valueOf(porta[2])));
				}
			}
			in.close();			
		} catch (FileNotFoundException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Verificar se as portas est�o em coordenadas v�lidas
		for(Map.Entry<Character, Gate> p : ports.entrySet()){
			int x=p.getValue().getxCoord(),y=p.getValue().getyCoord();
			System.out.println(width+" "+height);
			System.out.println(x+" "+y);
			if((x==0&&y==0) ||
				(x==width-1&&y==height-1) ||
				(x==0&&y==height-1) ||
				(x==width-1&&y==0) ||
				x<0 || x>=width ||
				y<0 || y>=height ||
				(x>0&&y>0&&x!=width-1&&y!=height-1)){
				
				// TODO mega error
				System.out.println("Mapa inv�lido - Porta: "+p.getValue().getSymbol());
				System.exit(-1);
			}
			for(Map.Entry<Character, Gate> p2 : ports.entrySet()){
				if(p.getValue()!=p2.getValue()&&p.getValue().getSymbol()==p2.getValue().getSymbol()){
					// TODO mega error
					System.out.println("Mapa inv�lido - Porta repetida: "+p.getValue().getSymbol());
					System.exit(-1);
				}
			}
			
		}
		
		map = new Board(width, height, ports);
	}
	
	private static void initMap(){
		 final Frame f = new Frame("ATC");
		    f.addWindowListener( new WindowAdapter() {
		            public void windowClosing(WindowEvent we) {
		                f.dispose();
		            }
		        } );

		    term = new Terminal(map.getWidth(),map.getHeight());
		    
		    // disabling forward focus traversal allows TAB to reach the component
		    //term.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, Collections.EMPTY_SET);
		    term.addKeyListener(new KeyAdapter() {
		            public void keyPressed(KeyEvent ke) {
		                // handle special keys
		                switch(ke.getKeyCode()) {
		                    case KeyEvent.VK_UP:
		                        term.print("\033A");
		                        term.repaint();
		                        break;
		                    case KeyEvent.VK_DOWN:
		                        term.print("\033B");
		                        term.repaint();
		                        break;
		                    case KeyEvent.VK_RIGHT:
		                        term.print("\033C");
		                        term.repaint();
		                        break;
		                    case KeyEvent.VK_LEFT:
		                        term.print("\033D");
		                        term.repaint();
		                        break;
		                }
		            }

		            public void keyTyped(KeyEvent ke) {
		                char c = ke.getKeyChar();
		                if( c == KeyEvent.CHAR_UNDEFINED ) {
		                    // handle special keys
		                    switch(ke.getKeyCode()) {
		                        case KeyEvent.VK_TAB:
		                            c = '\t';
		                            break;
		                        case KeyEvent.VK_ESCAPE:
		                            c = (char)27;
		                            break;
		                    }
		                }
		                if( c != KeyEvent.CHAR_UNDEFINED ) {
		                    term.print(c);
		                    term.repaint();
		                }
		            }
		        } );

		    term.setScroll(false);
		    
		    drawMap();
		    
		    f.setSize(term.getTileWidth()*map.getWidth(),term.getTileHeight()*map.getHeight());
		    f.add(term, BorderLayout.CENTER);
		    term.validate();
		    f.pack();
		    f.setVisible(true);		    
		   
		    term.gotoxy(9, 11);
		    term.print('O');
		    term.repaint();
		    System.out.println(term.getMaximumSize() +"\n" +
		    		term.getMinimumSize() + "\n" + 
		    		term.getSize() + "\n" + 
		    		term.getWidth() + "\n" + 
		    		term.getHeight() + "\n" +
		    		term.WIDTH+"\n" +
		    		term.HEIGHT
		    );
		    
		    term.gotoxy(0, 0);
		    //term.print(map.toString());
	}
	
	private static void drawMap(){
		int x,w=map.getWidth(),h=map.getHeight();
		
		// Draw borders
		for(x=0;x<h;x++){
			term.gotoxy(0, x);
			term.print('|');
		}
		for(x=0;x<h;x++){
			term.gotoxy(w-1, x);
			term.print('|');
		}
		for(x=0;x<w;x++){
			term.gotoxy(x, 0);
			term.print('_');
		}
		for(x=0;x<w;x++){
			term.gotoxy(x, h-1);
			term.print('_');
		}		
		
		// Draw ports
		for(Gate p : map.getPorts().values()){
			term.gotoxy(p.getxCoord(), p.getyCoord());
			term.print(p.getSymbol());
		}
	}	
}

package atc.atc;

import java.util.Map;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.StringBuilder;


public class Board{
	
	private int width;
	private int height;
	private Map<Character,Gate> ports;

	public Board(int w, int h, Map<Character,Gate> p){
		width=w;
		height=h;
		ports = new HashMap<Character,Gate>();
		for(Map.Entry<Character, Gate> e : p.entrySet())
			ports.put(e.getKey().charValue(),e.getValue().clone());
	}
	
	public static Board readMap(String fileName){
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
				System.out.println("Mapa invalido - Porta: "+p.getValue().getSymbol());
				System.exit(-1);
			}
			for(Map.Entry<Character, Gate> p2 : ports.entrySet()){
				if(p.getValue()!=p2.getValue()&&p.getValue().getSymbol()==p2.getValue().getSymbol()){
					// TODO mega error
					System.out.println("Mapa invalido - Porta repetida: "+p.getValue().getSymbol());
					System.exit(-1);
				}
			}
			
		}
		
		return(new Board(width, height, ports));
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Map<Character,Gate> getPorts() {
		Map<Character,Gate> ret = new HashMap<Character,Gate>();
		
		for(Map.Entry<Character, Gate> e : ports.entrySet())
			ret.put(e.getKey().charValue(), e.getValue().clone());
		
		return(ret);
	}

	public void setPorts(HashMap<Character,Gate> ports) {
		this.ports = new HashMap<Character,Gate>();
		
		for(Map.Entry<Character, Gate> e : ports.entrySet())
			this.ports.put(e.getKey().charValue(), e.getValue().clone());
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		int x,y;
		boolean portFound;
		
		for(x=0,portFound=false; x<width; x++,portFound=false){
			for(Gate p : ports.values()){
				if(p.getxCoord()==x && p.getyCoord()==0){
					s.append(p.getSymbol());
					portFound=true;
				}
			}
			if(!portFound) s.append('-');
		}
		s.append('\n');
		
		for(y=1; y<height-1; y++,s.append('\n')){
			for(x=0,portFound=false; x<width; x++,portFound=false){
				if(x==0){
					for(Gate p : ports.values()){
						if(p.getxCoord()==x && p.getyCoord()==y){
							s.append(p.getSymbol());
							portFound=true;
						}
					}
					if(!portFound) s.append('|');
				}else{
					if(x==width-1){
						for(Gate p : ports.values()){
							if(p.getxCoord()==x && p.getyCoord()==y){
								s.append(p.getSymbol());
								portFound=true;
							}
						}
						if(!portFound) s.append('|');
					}else{
						s.append(' ');
					}
				}
			}
		}
		
		for(x=0,portFound=false; x<width; x++,portFound=false){
			for(Gate p : ports.values()){
				if(p.getxCoord()==x && p.getyCoord()==y){
					s.append(p.getSymbol());
					portFound=true;
				}
			}
			if(!portFound) s.append('-');
		}
		return(s.toString());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + height;
		result = prime * result + ((ports == null) ? 0 : ports.hashCode());
		result = prime * result + width;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Board other = (Board) obj;
		if (height != other.height)
			return false;
		if (ports == null) {
			if (other.ports != null){
				return false;
			}
		} else if (!ports.equals(other.ports))
			return false;
		if (width != other.width)
			return false;
		return true;
	}
	
	public Board clone(){
		return(new Board(width,height,ports));
	}
	
}

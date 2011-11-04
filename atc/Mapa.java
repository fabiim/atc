package atc;

import java.util.Map;
import java.util.HashMap;
import java.lang.StringBuilder;


public class Mapa{
	
	private int width;
	private int height;
	private Map<Character,Gate> ports;

	public Mapa(int w, int h, Map<Character,Gate> p){
		width=w;
		height=h;
		ports = new HashMap<Character,Gate>();
		for(Map.Entry<Character, Gate> e : p.entrySet())
			ports.put(e.getKey().charValue(),e.getValue().clone());
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
		Mapa other = (Mapa) obj;
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
	
	public Mapa clone(){
		return(new Mapa(width,height,ports));
	}
	
}

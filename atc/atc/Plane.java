package atc.atc;

import java.io.Serializable;

public class Plane implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7475859020747234664L;
	/**
	 * 
	 */

	private Character ID;
	private Gate exitGate;
	private int exitAltitude;
	private int altitudeObjectivo;
	private int altitude;
	private int xCoord;
	private int yCoord;
	private int direction;
	private char symbol;
	private int speed; // Casas/Tick	
	
	public Plane(Character iD, Gate exitGate, int exitAltitude,
			int altitudeObjectivo, int altitude, int xCoord, int yCoord,
			int direction) {
		super();
		ID = iD;
		this.exitGate = exitGate.clone();
		this.exitAltitude = exitAltitude;
		this.altitudeObjectivo = altitudeObjectivo;
		this.altitude = altitude;
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.direction = direction;
		symbol = ID.charValue();
		speed = 1;
	}
	
	public Plane(Character iD, Gate exitGate, int exitAltitude,
			int altitudeObjectivo, int altitude, int xCoord, int yCoord,
			int direction, char symbol, int speed) {
		super();
		ID = iD;
		this.exitGate = exitGate.clone();
		this.exitAltitude = exitAltitude;
		this.altitudeObjectivo = altitudeObjectivo;
		this.altitude = altitude;
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.direction = direction;
		this.symbol = symbol;
		this.speed = speed;
	}

	public Character getID() {
		return ID;
	}

	public void setID(Character iD) {
		ID = iD;
	}

	public Gate getExitGate() {
		return exitGate.clone();
	}

	public void setExitGate(Gate exitGate) {
		this.exitGate = exitGate.clone();
	}

	public int getExitAltitude() {
		return exitAltitude;
	}

	public void setExitAltitude(int exitAltitude) {
		this.exitAltitude = exitAltitude;
	}

	public int getAltitudeObjectivo() {
		return altitudeObjectivo;
	}

	public void setAltitudeObjectivo(int altitudeObjectivo) {
		this.altitudeObjectivo = altitudeObjectivo;
	}

	public int getAltitude() {
		return altitude;
	}

	public void setAltitude(int altitude) {
		this.altitude = altitude;
	}

	public int getxCoord() {
		return xCoord;
	}

	public void setxCoord(int xCoord) {
		this.xCoord = xCoord;
	}

	public int getyCoord() {
		return yCoord;
	}

	public void setyCoord(int yCoord) {
		this.yCoord = yCoord;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public char getSymbol() {
		return symbol;
	}

	public void setSymbol(char symbol) {
		this.symbol = symbol;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ID == null) ? 0 : ID.hashCode());
		result = prime * result + altitude;
		result = prime * result + altitudeObjectivo;
		result = prime * result + direction;
		result = prime * result + exitAltitude;
		result = prime * result
				+ ((exitGate == null) ? 0 : exitGate.hashCode());
		result = prime * result + symbol;
		result = prime * result + speed;
		result = prime * result + xCoord;
		result = prime * result + yCoord;
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
		Plane other = (Plane) obj;
		if (ID == null) {
			if (other.ID != null)
				return false;
		} else if (!ID.equals(other.ID))
			return false;
		if (altitude != other.altitude)
			return false;
		if (altitudeObjectivo != other.altitudeObjectivo)
			return false;
		if (direction != other.direction)
			return false;
		if (exitAltitude != other.exitAltitude)
			return false;
		if (exitGate == null) {
			if (other.exitGate != null)
				return false;
		} else if (!exitGate.equals(other.exitGate))
			return false;
		if (symbol != other.symbol)
			return false;
		if (speed != other.speed)
			return false;
		if (xCoord != other.xCoord)
			return false;
		if (yCoord != other.yCoord)
			return false;
		return true;
	}

	@Override
	public String toString() {
		
		String xCoord = Integer.toString(this.xCoord);
		String yCoord = Integer.toString(this.yCoord);
		if(this.xCoord<10) xCoord = "0"+xCoord;
		if(this.yCoord<10) yCoord = "0"+yCoord;
		
		if(altitude!=altitudeObjectivo)
			return ID.charValue()+" ("+xCoord+","+yCoord+") "+"Altitude: "+altitude+"/"+altitudeObjectivo+ " Dir: "+direction +" Exit: "+exitGate.getSymbol()+" @Ê"+ exitAltitude;
		else
			return ID.charValue()+" ("+xCoord+","+yCoord+") "+"Altitude: "+altitude+"      Dir: "+direction +" Exit: "+exitGate.getSymbol()+" @Ê"+ exitAltitude;
	}

	public Plane clone(){
		return(new Plane(ID, exitGate, exitAltitude,
			 altitudeObjectivo, altitude, xCoord, yCoord,
			 direction, symbol, speed));
	}

	public void move(){
		// Altura
		if(altitude<altitudeObjectivo) // Aqui metes 1000
			altitude+=1000;
		if(altitude>altitudeObjectivo) // Aqui tiras 1000
			altitude-=1000;
	
		// Andar
		// As coordenadas em yy sao trocadas por causa dos eixos da classe Terminal
		switch(direction){
			case 1:
				yCoord--;
				break;
			case 2:
				yCoord--;
				xCoord++;
				break;
			case 3:
				xCoord++;
				break;
			case 4:
				xCoord++;
				yCoord++;
				break;
			case 6:
				yCoord++;
				break;
			case 7:
				xCoord--;
				yCoord++;
				break;
			case 8:
				xCoord--;
				break;
			case 9:
				xCoord--;
				yCoord--;
				break;
			default:
				break;
		}	
	}
}

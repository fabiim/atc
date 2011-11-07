package atc.atc;

public class Plane {

	private Character ID;
	private Gate exitGate;
	private PlaneHeight exitAltitude;
	private PlaneHeight altitudeObjectivo;
	private PlaneHeight altitude;
	private int xCoord;
	private int yCoord;
	private PlaneDirection direction;
	private char symbol;
	private int speed; // Casas/Tick
	
	
	
	
	public Plane(Character iD, Gate exitGate, PlaneHeight exitAltitude,
			PlaneHeight altitudeObjectivo, PlaneHeight altitude, int xCoord, int yCoord,
			PlaneDirection direction) {
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
	
	public Plane(Character iD, Gate exitGate, PlaneHeight exitAltitude,
			PlaneHeight altitudeObjectivo, PlaneHeight altitude, int xCoord, int yCoord,
			PlaneDirection direction, char symbol, int speed) {
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

	public PlaneHeight getExitAltitude() {
		return exitAltitude;
	}

	public void setExitAltitude(PlaneHeight exitAltitude) {
		this.exitAltitude = exitAltitude;
	}

	public PlaneHeight getAltitudeObjectivo() {
		return altitudeObjectivo;
	}

	public void setAltitudeObjectivo(PlaneHeight altitudeObjectivo) {
		this.altitudeObjectivo = altitudeObjectivo;
	}

	public PlaneHeight getAltitude() {
		return altitude;
	}

	public void setAltitude(PlaneHeight altitude) {
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

	public PlaneDirection getDirection() {
		return direction;
	}

	public void setDirection(PlaneDirection direction) {
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
		result = prime * result + altitude.ordinal();
		result = prime * result + altitudeObjectivo.ordinal();
		result = prime * result + direction.ordinal();
		result = prime * result + exitAltitude.ordinal();
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
		return "Plane [ID=" + ID + ", exitGate=" + exitGate + ", exitAltitude="
				+ exitAltitude + ", altitudeObjectivo=" + altitudeObjectivo
				+ ", altitude=" + altitude + ", xCoord=" + xCoord + ", yCoord="
				+ yCoord + ", direction=" + direction + ", symbol=" + symbol
				+ ", speed=" + speed + "]";
	}

	public Plane clone(){
		return(new Plane(ID, exitGate, exitAltitude,
			 altitudeObjectivo, altitude, xCoord, yCoord,
			 direction, symbol, speed));
	}
	
	// TODO pra que que isto serve?
	public void turn(){
		
	}

	public void move(){
		// Altura
		if(altitude.getHeight()<altitudeObjectivo.getHeight()) // Aqui metes 1000
			// TODO FABIO TRATA DISTO
		if(altitude.getHeight()>altitudeObjectivo.getHeight()) // Aqui tiras 1000
			// TODO FABIO TRATA DISTO
	
		// Andar
		switch(direction){
			case N:
				yCoord++;
				break;
			case NE:
				yCoord++;
				xCoord++;
				break;
			case E:
				xCoord++;
				break;
			case SE:
				xCoord++;
				yCoord--;
				break;
			case S:
				yCoord--;
				break;
			case SW:
				xCoord--;
				yCoord--;
				break;
			case W:
				xCoord--;
				break;
			case NW:
				xCoord--;
				yCoord++;
				break;
			default:
				break;
		}	
	}
}

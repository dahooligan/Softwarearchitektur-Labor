package de.airport.ejb.model;

@javax.persistence.Entity(name = "airplane")
public class Airplane {
	@javax.persistence.Id
	@javax.persistence.GeneratedValue
	private int id;

	private final String name;
	//private Airline airline;
	//private StartingDirection startingdirection;
	//private Runway runway;
	//private ParkingPosition parkingPosition;
	
	private AirplaneState state;

	public Airplane(String name) {
		super();
		this.name = name;
	}

	public void startAircraft() {
		// TODO: start the aircraft
	}
/*
	public StartingDirection getStartingdirection() {
		return startingdirection;
	}

	public void setStartingdirection(StartingDirection startingdirection) {
		this.startingdirection = startingdirection;
	}
*/
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	/*
	public Airline getAirline() {
		return airline;
	}*/
/*
	public Runway getRunway() {
		return runway;
	}

	public void setRunway(Runway runway) {
		this.runway = runway;
	}

	public ParkingPosition getParkingPosition() {
		return parkingPosition;
	}

	public void setParkingPosition(ParkingPosition parkingPosition) {
		this.parkingPosition = parkingPosition;
	}
*/
}

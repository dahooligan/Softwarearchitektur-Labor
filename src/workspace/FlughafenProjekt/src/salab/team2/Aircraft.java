package salab.team2;

public class Aircraft {

	private final int id;
	private final String name;
	private final Airline airline;
	private StartingDirection startingdirection;
	private Runway runway;
	private ParkingPosition parkingPosition;
	
	
	public Aircraft(int id, String name, Airline airline) {
		super();
		this.id = id;
		this.name = name;
		this.airline = airline;
		airline.addAircraft(this);
	}

	
	public void startAircraft()
	{
	//TODO: start the aircraft
	}


	public StartingDirection getStartingdirection() {
		return startingdirection;
	}


	public void setStartingdirection(StartingDirection startingdirection) {
		this.startingdirection = startingdirection;
	}


	public int getId() {
		return id;
	}


	public String getName() {
		return name;
	}


	public Airline getAirline() {
		return airline;
	}


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
	
	
}

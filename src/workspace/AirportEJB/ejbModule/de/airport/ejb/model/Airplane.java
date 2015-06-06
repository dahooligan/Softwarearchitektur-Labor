package de.airport.ejb.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import de.airport.ejb.model.Airline;

@javax.persistence.Entity(name = "airplane")
public class Airplane {
	@javax.persistence.Id
	@javax.persistence.GeneratedValue
	private int id;

	private final String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "aircraftCollection")
	private Airline airline;

	@Enumerated(EnumType.STRING) 
	private StartingDirection startingdirection;
	// private Runway runway;
	// private ParkingPosition parkingPosition;

	@Enumerated(EnumType.STRING) 
	 private AirplaneState state;

	public Airplane() {
		super();
		this.name="unknown";
		System.err.println("Airline-Default-Constr. executed");
	}
	
	public Airplane(String name) {
		super();
		this.name = name;
	}

	public Airline getAirline() {
		return airline;
	}

	public void setAirline(Airline airline) {
		this.airline = airline;
	}

	public String getAirlineName() {
		
		/* Funktioniert nicht. 
		 * @ Matze: Hier is ne hibernate-Exc... Iwas mit dem LazyInitializer. 
		 * Kannst du das mal checken?
		 * 
		 * return airline.getName();
		 */
		return "dummy";
	}
	public AirplaneState getState() {
		return state;
	}

	public void setState(AirplaneState state) {
		this.state = state;
	}

	public void startAircraft() {
		// TODO: start the aircraft
	}

	/*
	 * public StartingDirection getStartingdirection() { return
	 * startingdirection; }
	 * 
	 * public void setStartingdirection(StartingDirection startingdirection) {
	 * this.startingdirection = startingdirection; }
	 */
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	/*
	 * public Airline getAirline() { return airline; }
	 */
	/*
	 * public Runway getRunway() { return runway; }
	 * 
	 * public void setRunway(Runway runway) { this.runway = runway; }
	 * 
	 * public ParkingPosition getParkingPosition() { return parkingPosition; }
	 * 
	 * public void setParkingPosition(ParkingPosition parkingPosition) {
	 * this.parkingPosition = parkingPosition; }
	 */

}

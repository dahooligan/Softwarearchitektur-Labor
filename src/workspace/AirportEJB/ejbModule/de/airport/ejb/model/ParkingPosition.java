package de.airport.ejb.model;

import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;


@javax.persistence.Entity(name = "parkingposition")
public class ParkingPosition {
	@javax.persistence.Id
	@GeneratedValue
	private int id;
	
	@OneToOne
	@JoinColumn(name = "parkingPosition")
	private Airplane airplane;
	
	private boolean isFree;
	
	public ParkingPosition() {
		super();
		this.isFree = true;
	}

	public boolean isFree() {
		return isFree;
	}

	public void setFree(boolean isFree) {
		this.isFree = isFree;
	}

	public int getId() {
		return id;
	}

	public Airplane getAirplane() {
		return airplane;
	}

	public void setAirplane(Airplane airplane) {
		this.airplane = airplane;
	}

	

}

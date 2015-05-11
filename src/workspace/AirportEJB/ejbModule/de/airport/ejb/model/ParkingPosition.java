package de.airport.ejb.model;

public class ParkingPosition {
	private final int id;
	private boolean isFree;
	
	public ParkingPosition(int id) {
		super();
		this.id = id;
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

	

}

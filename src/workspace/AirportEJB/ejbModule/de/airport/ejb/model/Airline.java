package de.airport.ejb.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.OneToMany;

@javax.persistence.Entity(name = "airline")
public class Airline {

	@javax.persistence.Id
	@javax.persistence.GeneratedValue
	private int id;
	private final String name;
	@OneToMany(mappedBy = "airline")
	private final Collection<Airplane> aircraftCollection;
	private String cityName;
	private String streetName;
	
	public Airline() {
		super();
		this.name = "Unknown";
		this.aircraftCollection = new ArrayList<Airplane>();
	}


	public Airline(String name) {
		super();
		this.name = name;
		this.aircraftCollection = new ArrayList<Airplane>();
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	/**
	 * Add an {@link Aircraft} to this {@link Airline}
	 * 
	 * @param aircraft
	 *            the aircraft to add to the {@link Collection} of aircrafts of
	 *            this {@link Airline}
	 * @return @see {@link Collection#add(Object))
	 */
	public boolean addAircraft(Airplane airplane) {
		return aircraftCollection.add(airplane);
	}

}

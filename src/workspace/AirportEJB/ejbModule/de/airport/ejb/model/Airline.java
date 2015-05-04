package de.airport.ejb.model;

import java.util.List;

@javax.persistence.Entity (name="airline")
public class Airline {
	
    @javax.persistence.Id
    @javax.persistence.GeneratedValue
	private int id;
	
	private String name;
	private String street;
	private String city;
	/*private List<Airplane> airplanes;
	
	public List<Airplane> getAirplanes() {
		return airplanes;
	}
	public void setAirplanes(List<Airplane> airplanes) {
		this.airplanes = airplanes;
	} */
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
}

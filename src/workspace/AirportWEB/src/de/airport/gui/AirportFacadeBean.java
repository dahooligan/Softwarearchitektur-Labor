package de.airport.gui;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import de.airport.ejb.AirportFacade;
import de.airport.ejb.model.Airline;
import de.airport.ejb.model.Airplane;

@ManagedBean
@SessionScoped
public class AirportFacadeBean {
	private String name;
	private String street;
	private String city;
	
	@EJB
	private AirportFacade facade;

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
	
	public void createAirline() {
		facade.createAirline(name, street, city);
	}
	
	public List<Airline> getAirlines() {
		return facade.getAirlines();
	}

	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void createAirplane() {
		facade.createAirplane(name);
	}
	
	public List<Airplane> getAirplanes() {
		return facade.getAirplanes();
	}
	
	
}

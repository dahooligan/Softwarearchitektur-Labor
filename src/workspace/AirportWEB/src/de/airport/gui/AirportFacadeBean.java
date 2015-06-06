package de.airport.gui;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import de.airport.ejb.AirportFacade;
import de.airport.ejb.controller.StartAirplaneController;
import de.airport.ejb.model.Airline;
import de.airport.ejb.model.Airplane;

@ManagedBean
@SessionScoped
public class AirportFacadeBean {
	private String name;
	private String streetName;
	private String cityName;
	
	//@EJB
	//private StartAirplaneController controller;
	
	@EJB
	private AirportFacade facade;

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String street) {
		this.streetName = street;
	}
	
	public String getCityName() {
		return cityName;
	}

	public void setCityName(String city) {
		this.cityName = city;
	}

	public void createAirline() {
		System.err.println("AFB: Creating Airline with: " + name + " - " + streetName + " - " + cityName);
		facade.createAirline(name, streetName, cityName);
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

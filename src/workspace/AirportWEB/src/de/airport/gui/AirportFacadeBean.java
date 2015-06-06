package de.airport.gui;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import de.airport.ejb.AirportFacade;
import de.airport.ejb.controller.StartAirplaneController;
import de.airport.ejb.model.Airline;
import de.airport.ejb.model.Airplane;

@ManagedBean
@SessionScoped
public class AirportFacadeBean {
	private String name;
	private String fname;
	private String streetName;
	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	private String cityName;
	private String airline;
	
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
	
	public List<SelectItem> getAirlineItems() {
		List<SelectItem> lst = new ArrayList<SelectItem>();		
		List<Airline> tmp = new ArrayList<Airline>();
		tmp = facade.getAirlines();
		
		for(int i=0; i<tmp.size(); i++) {
			SelectItem item = new SelectItem();
			item.setLabel(tmp.get(i).getName());
			item.setValue(String.valueOf(tmp.get(i).getId()));
			lst.add(item);
		}		
		return lst;
	}
	
	/*
	public List<String> getAirlines() {
		//return facade.getAirlines();
		System.err.println("Querying Airlines");
		List<Airline> tmp = new ArrayList<Airline>();
		tmp = facade.getAirlines();
		System.err.println("Got Airlines");
		List<String> names = new ArrayList<String>();
		
		if(tmp.size()>0) {
			for(int i=0; i<tmp.size(); i++) {
				System.err.println("Found" + tmp.size() + " Airlines");
				names.add(tmp.get(i).getName());
				System.err.println("Added Name");
			}
		} else {
			System.err.println("No Airlines");
			names.add("Keine Airlines angelegt.");
			System.err.println("Entered random Text");
		}
		
		
		return names;
		//List<String>
	}*/
	
	public String getAirline() {
		return airline;
	}

	public void setAirline(String airline) {
		this.airline = airline;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void createAirplane() {
		facade.createAirplane(fname, airline);
	}
	
	public List<Airplane> getAirplanes() {
		return facade.getAirplanes();
	}
	
	
}

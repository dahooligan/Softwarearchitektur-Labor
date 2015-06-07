package de.airport.gui;

import java.beans.EventHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;

import de.airport.ejb.AirportFacade;
import de.airport.ejb.controller.StartAirplaneController;
import de.airport.ejb.model.Airline;
import de.airport.ejb.model.Airplane;

@ManagedBean
@SessionScoped
public class AirportFacadeBean {
	
	private Boolean renderAirlines;
	private String name;
	private String fname;
	private String streetName;
	private String cityName;
	private String airline;
	private String airplane;
	private List<InformationOutput> airplaneInfo = new ArrayList<InformationOutput>();
	private Airplane currentAirplane;
	
	private List<String> keys = new ArrayList<String>();
	private List<String> values = new ArrayList<String>();	
	//@EJB
	//private StartAirplaneController controller;
	
	@EJB
	private AirportFacade facade;
	
	public AirportFacadeBean()
	{
		keys.add("hallo");
		values.add("welt");
	}
	
	
	
	/*
	public Airplane getCurrentAirplane() {
		return currentAirplane;
	}

	public void setCurrentAirplane(Airplane currentAirplane) {
		this.currentAirplane = currentAirplane;
	} */
	
	

	public List<InformationOutput> getAirplaneInfo() {
		System.err.println("**************");
		//List<InformationOutput> tmp = new ArrayList<InformationOutput>();
		//airplaneInfo.add(new InformationOutput("teewurst", "1"));
		//airplaneInfo.add(new InformationOutput("wurst", "2"));
		for(int i=0; i<airplaneInfo.size(); i++) {
			System.err.println(airplaneInfo.get(i).getKey() + ":" + airplaneInfo.get(i).getValue());
		}
		System.err.println("**************");
		return airplaneInfo;
	}



	public void setAirplaneInfo(List<InformationOutput> airplaneInfo) {
		this.airplaneInfo = airplaneInfo;
	}



	public String getAirplane() {
		System.err.println("++++++called get Airplane");
		return airplane;
	}

	public Airplane getCurrentAirplane() {
		return currentAirplane;
	}


	public void setCurrentAirplane(Airplane currentAirplane) {
		this.currentAirplane = currentAirplane;
	}


	public void setAirplane(String airplane) {
		this.airplane = airplane;
		airplaneSelectionChanged();
	}

	public String getFname() {
		return fname;
	}

	public Boolean getRenderAirlines() {
		return renderAirlines;
	}

	public void setRenderAirlines(Boolean renderAirlines) {
		this.renderAirlines = renderAirlines;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

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
		RequestContext.getCurrentInstance().update("airlineDropdown");
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
	
	public List<SelectItem> getAirplaneItems() {
		List<SelectItem> lst = new ArrayList<SelectItem>();		
		List<Airplane> tmp = new ArrayList<Airplane>();
		tmp = facade.getAirplanes();
		
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
	
	public void airplaneSelectionChanged() {
		
		airplaneInfo.clear();
		
		System.err.println("########!!!selection Changed. Id: " + airplane);
		Airplane ap = facade.getAirplaneById(airplane);
		System.err.println("Got id: " + ap.getId() + ", name: " + ap.getName() + ", al: " + ap.getAirlineName() );
		airplane = String.valueOf(ap.getId());
		/* currentAirplane = ap;
		keys.add("name");
		keys.add("id");
		
		values.add(ap.getAirlineName());
		values.add(ap.getName()); */
		airplaneInfo.add(new InformationOutput("ID", String.valueOf(ap.getId())));
		airplaneInfo.add(new InformationOutput("Name", ap.getName()));
		airplaneInfo.add(new InformationOutput("Airline", ap.getAirlineName()));
		
		System.err.println("?!?!? AirplaneInfo.size() = " + airplaneInfo.size());
		
		
	}
	
	
	/*
	public Map<String,String> getItems() {
		   return items;
		}
		public List<Integer> getItemKeys() {
		   List keys = new ArrayList();
		   keys.addAll(getItems().keySet());
		   return keys;
		} */
	
}

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
import javax.faces.component.html.HtmlOutputText;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;

import de.airport.ejb.AirportFacade;
import de.airport.ejb.controller.ControllerState;
import de.airport.ejb.controller.StartAirplaneController;
import de.airport.ejb.model.Airline;
import de.airport.ejb.model.Airplane;
import de.airport.ejb.model.Runway;
import de.airport.ejb.model.StartingDirection;

@ManagedBean
@SessionScoped
public class AirportFacadeBean {
	
	private Boolean renderAirlines;
	private HtmlOutputText logText;
	private String name;
	private String fname;
	private String streetName;
	private String cityName;
	private String airline;
	private String airplane;
	private String direction;
	private String runway;
	private List<InformationOutput> airplaneInfo = new ArrayList<InformationOutput>();
	private Airplane currentAirplane;
	
	private List<String> keys = new ArrayList<String>();
	private List<String> values = new ArrayList<String>();	
	//@EJB
	private StartAirplaneController controller;
	
	@EJB
	private AirportFacade facade;
	
	public void init() {
		//Testen ob Airlines vorhanden. Wenn nicht, dann wahrscheinlich erster Start.
		List<Airline> al = new ArrayList<Airline>();
		al = facade.getAirlines();
		
		if(al.size()==0) {
			//Initialer Logtext
			logText.setValue("Keine Aktivit‰ten vorhanden.");
			
			//Controller holen
			controller = StartAirplaneController.getInstance();
			
			//Runways anlegen
			facade.createRunways();
			//ParkingPositions anlegen
			//facade.createParkpositions();
			
			//Airlines anlegen
			facade.createAirline("Lufthansa", "Geb.Wright-Weg 3", "Lufthausen");
			facade.createAirline("Germanwings", "Antonow-Straﬂe 7", "Airplane City");
			
			//Flugzeuge anlegen
			al = facade.getAirlines();
			facade.createAirplane("AER788", String.valueOf(al.get(0).getId()));
			facade.createAirplane("4U-338", String.valueOf(al.get(1).getId()));
			
			
		}
		
	}
	
	
	
	/*
	public Airplane getCurrentAirplane() {
		return currentAirplane;
	}

	public void setCurrentAirplane(Airplane currentAirplane) {
		this.currentAirplane = currentAirplane;
	} */
	
	

	public List<InformationOutput> getAirplaneInfo() {
		//System.Err.println("**************");
		//List<InformationOutput> tmp = new ArrayList<InformationOutput>();
		//airplaneInfo.add(new InformationOutput("teewurst", "1"));
		//airplaneInfo.add(new InformationOutput("wurst", "2"));
		for(int i=0; i<airplaneInfo.size(); i++) {
			//System.Err.println(airplaneInfo.get(i).getKey() + ":" + airplaneInfo.get(i).getValue());
		}
		//System.Err.println("**************");
		return airplaneInfo;
	}



	public void setAirplaneInfo(List<InformationOutput> airplaneInfo) {
		this.airplaneInfo = airplaneInfo;
	}

	

	public HtmlOutputText getLogText() {
		return logText;
	}



	public void setLogText(HtmlOutputText logText) {
		this.logText = logText;
	}



	public StartAirplaneController getController() {
		return controller;
	}



	public void setController(StartAirplaneController controller) {
		this.controller = controller;
	}



	public String getAirplane() {
		//System.Err.println("++++++called get Airplane");
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
		//System.Err.println("AFB: Creating Airline with: " + name + " - " + streetName + " - " + cityName);
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

	public String getDirection() {
		return direction;
	}



	public void setDirection(String direction) {
		this.direction = direction;
	}



	public String getRunway() {
		return runway;
	}



	public void setRunway(String runway) {
		this.runway = runway;
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
		
		//System.Err.println("########!!!selection Changed. Id: " + airplane);
		Airplane ap = facade.getAirplaneById(airplane);
		//System.Err.println("Got id: " + ap.getId() + ", name: " + ap.getName() + ", al: " + ap.getAirlineName() );
		airplane = String.valueOf(ap.getId());
		/* currentAirplane = ap;
		keys.add("name");
		keys.add("id");
		
		values.add(ap.getAirlineName());
		values.add(ap.getName()); */
		airplaneInfo.add(new InformationOutput("ID", String.valueOf(ap.getId())));
		airplaneInfo.add(new InformationOutput("Name", ap.getName()));
		airplaneInfo.add(new InformationOutput("Airline", ap.getAirlineName()));
		
		//System.Err.println("?!?!? AirplaneInfo.size() = " + airplaneInfo.size());
		
		
	}
	
	public void requestStart(){
		
		//Parameter verarbeiten
		StartingDirection dir = null;
		Runway runw;
		Boolean parametersOk=true;
		
		switch(direction){
		case "EW":
			dir = StartingDirection.EASTWEST;
			break;
		case "WE":
			dir = StartingDirection.WESTEAST;
			break;
		default:
			System.err.println("Failure: Unknown Startingdirection!");
			parametersOk = false;
			break;
		}
		
		runw = facade.getRunwayById(runway);
			
		if(runw==null) {
			System.err.println("Failure: Unknown Runway!");
			parametersOk=false;
		}
		
		Airplane ap = facade.getAirplaneById(airplane);
		
		if(parametersOk) {
			ControllerState cs = controller.initiateStart(ap, runw, 14, 16, dir, false);
			
			switch(cs) {
			case AirplaneNotAvailable: 
				System.err.println("InitiateStart failed: Airplane not found."); break;
			case DirectionForbidden: System.err.println("InitiateStart failed: Forbidden Direction."); break;
			case RunwayOccupied: System.err.println("InitiateStart failed: Runway is occupied."); break;
			case StartingTimeError: System.err.println("InitiateStart failed: Invalid Startingtime."); break;
			default: 
				System.err.println("Airplane started"); 
				logText.setValue("started ap");
				break;
			}
		}	
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

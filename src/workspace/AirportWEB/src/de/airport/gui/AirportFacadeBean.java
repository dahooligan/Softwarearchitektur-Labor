package de.airport.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;

import de.airport.ejb.AirportFacade;
import de.airport.ejb.controller.ControllerState;
import de.airport.ejb.controller.StartAirplaneController;
import de.airport.ejb.controller.simulation.StartSimulation.simulationState;
import de.airport.ejb.model.Airline;
import de.airport.ejb.model.Airplane;
import de.airport.ejb.model.AirplaneState;
import de.airport.ejb.model.ParkingPosition;
import de.airport.ejb.model.Runway;
import de.airport.ejb.model.StartingDirection;

@ManagedBean
@SessionScoped
public class AirportFacadeBean implements Observer {
	
	private String displayStyle = "";
	private String releaseDisplayStyle = "display:none";
	private String cancelDisplayStyle = "display:none";
	private String startButtonText = "Start anfordern";
	
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
	private int startingHour;
	private int startingMin;
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
			logText.setValue("Keine Aktivitäten vorhanden.");
			
			//Controller holen
			controller = StartAirplaneController.getInstance();
			controller.setFacade(facade);
			
			//Runways anlegen
			facade.createRunways();
			//ParkingPositions anlegen
			facade.createParkpositions();
			
			//Airlines anlegen
			facade.createAirline("Lufthansa", "Geb.Wright-Weg 3", "Lufthausen");
			facade.createAirline("Germanwings", "Antonow-Straße 7", "Airplane City");
			
			//Flugzeuge anlegen
			al = facade.getAirlines();
			facade.createAirplane("AER788", String.valueOf(al.get(0).getId()));
			facade.createAirplane("4U-338", String.valueOf(al.get(1).getId()));
			
			
		}
		airplane = String.valueOf(facade.getAirplanes().get(1).getId());
		this.airplaneSelectionChanged();
		
	}
	
	
	
	/*
	public Airplane getCurrentAirplane() {
		return currentAirplane;
	}

	public void setCurrentAirplane(Airplane currentAirplane) {
		this.currentAirplane = currentAirplane;
	} */
	
	public String getStartButtonText() {
		return startButtonText;
	}



	public void setStartButtonText(String startButtonText) {
		this.startButtonText = startButtonText;
	}



	public String getCancelDisplayStyle() {
		return cancelDisplayStyle;
	}



	public void setCancelDisplayStyle(String cancelDisplayStyle) {
		this.cancelDisplayStyle = cancelDisplayStyle;
	}



	public String getReleaseDisplayStyle() {
		return releaseDisplayStyle;
	}



	public void setReleaseDisplayStyle(String releaseDisplayStyle) {
		this.releaseDisplayStyle = releaseDisplayStyle;
	}



	public String getDisplayStyle() {
		return displayStyle;
	}

	public void setDisplayStyle(String displayStyle) {
		this.displayStyle = displayStyle;
	}

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



	public int getStartingHour() {
		return startingHour;
	}



	public void setStartingHour(int startingHour) {
		this.startingHour = startingHour;
	}



	public int getStartingMin() {
		return startingMin;
	}



	public void setStartingMin(int startingMin) {
		this.startingMin = startingMin;
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
		//System.Error.println("########!!!selection Changed. Id: " + airplane);
		Airplane ap = facade.getAirplaneById(airplane);
		//System.Err.println("Got id: " + ap.getId() + ", name: " + ap.getName() + ", al: " + ap.getAirlineName() );
		airplane = String.valueOf(ap.getId());
		/* currentAirplane = ap;
		keys.add("name");
		keys.add("id");
		
		values.add(ap.getAirlineName());
		values.add(ap.getName()); */
		//airplaneInfo.add(new InformationOutput("ID", String.valueOf(ap.getId())));
		airplaneInfo.add(new InformationOutput("Name", ap.getName()));
		airplaneInfo.add(new InformationOutput("Airline", ap.getAirlineName()));
		switch(ap.getState())
		{
		case PARKED:
			airplaneInfo.add(new InformationOutput("Status","Parkend"));
			List<ParkingPosition> pps = facade.getParkingPositions();
			for (ParkingPosition p : pps) {
				try {
					int airplaneId = p.getAirplane().getId();
					System.err.println("AP-ID :" + airplaneId + "; selected ID: " + ap.getId());
					if(ap.getId() == airplaneId) {
						airplaneInfo.add(new InformationOutput("Parkbox", String.valueOf(p.getId())));
					}
				} catch (NullPointerException e) { System.err.println("caught NPE");}
			}
				
			displayStyle="";
			releaseDisplayStyle="display:none";
			cancelDisplayStyle="display:none";
			startButtonText = "Start anfordern";
			System.err.println("ParkedState");
			break;
		case WAITING_ON_RUNWAY:
			airplaneInfo.add(new InformationOutput("Status","Wartet auf Freigabe"));
			displayStyle="display:none";
			releaseDisplayStyle="";
			cancelDisplayStyle="";
			startButtonText = "Start freigeben";

			System.err.println("WaitingState");
			break;
		case STARTING:
			airplaneInfo.add(new InformationOutput("Status","Started"));
			displayStyle="display:none";
			releaseDisplayStyle="display:none";
			cancelDisplayStyle="";

			System.err.println("StartigState");
			break;
		case GOING_TO_RUNWAY:
			airplaneInfo.add(new InformationOutput("Status","Going to Runway"));
			displayStyle="display:none";
			releaseDisplayStyle="display:none";
			cancelDisplayStyle="";
		}

		//System.Err.println("?!?!? AirplaneInfo.size() = " + airplaneInfo.size());
		RequestContext.getCurrentInstance().update("tv:form1");
		RequestContext.getCurrentInstance().update("tv:form1:outtable1");
	}
	
	public void requestStart(){
		//facade.persistenceTest();
		
		System.err.println("Requesting Start. Schedulded Time: " + startingHour + " : " + startingMin);
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
			ControllerState cs = controller.initiateStart(ap, runw, startingHour, startingMin, dir, false);
			
			switch(cs) {
			case AirplaneNotAvailable: 
				System.err.println("InitiateStart failed: Airplane not found."); 
				logText.setValue("Flugzeug " + ap.getName() + " konnte nicht gestartet werden. Es wurde nicht im System gefunden."
						 + "\n" + logText.getValue());
				break;
			case DirectionForbidden: 
				System.err.println("InitiateStart failed: Forbidden Direction."); 
				logText.setValue("Flugzeug " + ap.getName() + " konnte nicht gestartet werden. Es wurde eine falsche Startrichtung eingegeben."
						 + "\n" + logText.getValue());
				break;
			case RunwayOccupied: 
				System.err.println("InitiateStart failed: Runway is occupied."); 
				logText.setValue("Flugzeug " + ap.getName() + " konnte nicht gestartet werden. Die Startbahn "
						+ (runw.getId()+1) + "ist belegt."
						 + "\n" + logText.getValue());
				break;
			case StartingTimeError: 
				System.err.println("InitiateStart failed: Invalid Startingtime."); 
				logText.setValue("Flugzeug " + ap.getName() + " konnte nicht gestartet werden. Die Startzeit liegt vor der aktuellen Zeit."
						 + "\n" + logText.getValue());
				break;
			default: 
				facade.setAirplaneState(ap.getName(), ap.getId(), simulationState.GoingToRunway);
				System.err.println("Airplane goes to Runway"); 
				logText.setValue("Flugzeug " + ap.getName() + " ist auf dem Weg zu Startbahn "
						+ (runw.getId()+1) + "\n" + logText.getValue());
				displayStyle="display:none";
				releaseDisplayStyle="display:none";
				cancelDisplayStyle=""; 
				break;
			}
		}
		
		
		
		RequestContext.getCurrentInstance().update("ID62");
		RequestContext.getCurrentInstance().update(":tv:form1");
	}

	public void releaseAirplane() {
		// Hier wird der Start freigegeben
	}
	
	public void cancelStart() {
		// Hier wird der Start abgebrochen
		controller.cancelStart(airplane);
		/* 	TO-DO:
		 * 		- Sim Beenden
		 *		- Flugzeug wieder in Parkbox
		 *		- Runway wieder freigeben
		 *		- Startübersicht "cancelled" eintragen
		 *		
		 */
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
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
	
	
	//##############################
	
} 
	

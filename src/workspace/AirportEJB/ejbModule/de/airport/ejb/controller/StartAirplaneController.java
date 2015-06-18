package de.airport.ejb.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import javax.persistence.EntityManager;

import de.airport.ejb.AirportFacade;
import de.airport.ejb.controller.simulation.StartSimulation.simulationState;
import de.airport.ejb.model.*;

public class StartAirplaneController implements Observer {
	
	private AirportFacade facade;
	private static StartAirplaneController instance;
	private ArrayList<StartWrapper> runningStartProcesses = new ArrayList<StartWrapper>();
	
	public static StartAirplaneController getInstance(){
		if(instance==null){
			instance = new StartAirplaneController();
		}
		return instance;
	}
	
	private StartAirplaneController() {
		runningStartProcesses = new ArrayList<StartWrapper>();
	}
	

	public ControllerState initiateStart(Airplane airplane, Runway runway, int startingHour, int startingMin, 
			StartingDirection direction,
			boolean freeParkingPosition) {
		
		if(runway.isFree())
		{
			if(airplane.getState()==AirplaneState.PARKED)
			{
				// Flugzeug kann prinzipiell gestartet werden
				
				if(runway.getPermittedStartingDirections().contains(direction)) {
					
					// Startzeit festlegen
					Calendar startingTime = Calendar.getInstance();
					startingTime.set(Calendar.HOUR_OF_DAY, startingHour);
					startingTime.set(Calendar.MINUTE, startingMin);

					if(startingTime.getTimeInMillis() >	System.currentTimeMillis()) {
						// Parameter auch ok. Jetzt wirklich starten.
						
						// EntitiyManager besorgen
						//EntityManager em = AirportFacade.getEm();
						
						// StartWrapperObjekt anlegen
						runningStartProcesses.add(0, new StartWrapper(true));
						runningStartProcesses.get(0).setAirplaneID(airplane.getId());
						runningStartProcesses.get(0).setNameOfAirplane(airplane.getName());
						runningStartProcesses.get(0).setNameOfAirline(airplane.getAirlineName());
						//runningStartProcesses.get(0).setNameOfAirplane(airplane.getAirline());
						runningStartProcesses.get(0).setNrOfRunway(runway.getId());
						runningStartProcesses.get(0).setPlannedStartTime(startingTime);
						
						runningStartProcesses.get(0).addObserver(this);
						
						facade.persistStartProcess(runningStartProcesses.get(0));
						
						
						// Runway blockieren + festlegen
						facade.reserveRunway(runway.getId());
						//runway.setFree(false);
						//em.merge(runway);
						
						
						//airplane.setRunway(runway);
						
						//airplane.setStartingdirection(direction)
						//airplane.setStartingTime(startingTime)
						
						//airplane.goToRunway();
						
						// Evtl. Parkbox freigeben
						// if(freeParkingPosition) airplane.getParkingPosition().setFree(true);
						
					} else {
						return ControllerState.StartingTimeError;
					}
				
				} else {
					return ControllerState.DirectionForbidden;
				}
				
				
				
				
				//if(freeParkingPosition) 
			} else {
				return ControllerState.AirplaneNotAvailable;
			}
			
			
		} else {
			return ControllerState.RunwayOccupied;
		}
		
		return ControllerState.OK;
	}

	public void setFacade(AirportFacade facade) {
		this.facade = facade;
	}

	@Override
	public void update(Observable o, Object arg) {
		System.err.println("Controller was also notified!");
		
		// TODO Auto-generated method stub
		StartWrapper sw = (StartWrapper)o;
		facade.setAirplaneState(sw.getNameOfAirplane(), sw.getAirplaneID(), sw.getSimulationState());
	}
	
	public void releaseAirplane() {
		// Hier wird der Start freigegeben
	}
	
	public void cancelStart(String airplane) {
		// Hier wird der Start abgebrochen
		
		for(StartWrapper s : runningStartProcesses) {
			if(s.getAirplaneID()==Integer.parseInt(airplane, 10)) {
				s.setStatus("Cancelled");
				s.cancelSimulation();
				facade.mergeStartProcess(s);
				
				facade.setAirplaneState(s.getNameOfAirplane(), s.getAirplaneID(), simulationState.ReturningToParkingPosition);
			}
		}
		
		
		
		/* 	TO-DO:
		 * 		- Sim Beenden
		 *		- Flugzeug wieder in Parkbox
		 *		- Runway wieder freigeben
		 *		- Startübersicht "cancelled" eintragen
		 *		
		 */
		
		
	}
	
}

package de.airport.ejb.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import de.airport.ejb.model.*;

public class StartAirplaneController {
	
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
		System.err.println("Initiating-Start");
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
						
						// StartWrapperObjekt anlegen
						runningStartProcesses.add(0, new StartWrapper());
						runningStartProcesses.get(0).setAirplaneID(airplane.getId());
						runningStartProcesses.get(0).setNameOfAirplane(airplane.getName());
						//runningStartProcesses.get(0).setNameOfAirplane(airplane.getAirline());
						runningStartProcesses.get(0).setNrOfRunway(runway.getId());
						runningStartProcesses.get(0).setPlannedStartTime(startingTime);
						
						
						// Runway blockieren + festlegen
						runway.setFree(false);
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
	
	public ControllerState performStart(Airplane airplane){
		
		airplane.startAircraft(); 
		
		return ControllerState.OK;
	}
}

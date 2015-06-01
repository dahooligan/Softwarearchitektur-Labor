package de.airport.ejb.controller;

import java.util.Calendar;
import java.util.Date;

import de.airport.ejb.model.*;

public class StartAirplaneController {
	
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
						
						// Runway blockieren + festlegen
						runway.setFree(false);
						//airplane.setRunway(runway);
						
						//airplane.setStartingdirection(direction)
						//airplane.setStartingTime(startingTime)
						
						//airplane.gotToRunway();
						
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

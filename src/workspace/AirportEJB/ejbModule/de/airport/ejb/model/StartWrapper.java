package de.airport.ejb.model;

import java.util.Calendar;
import java.util.Observable;
import java.util.Observer;

import javax.persistence.Transient;

import de.airport.ejb.controller.simulation.StartSimulation;
import de.airport.ejb.controller.simulation.StartSimulation.simulationState;

/**
 * Wrapper class containing information about erstwhile starts including
 * information about the aircraft, time, parkposition and runway
 * 
 * @author Matthias / Timo
 *
 */
@javax.persistence.Entity(name = "startwrapper")
public class StartWrapper extends Observable implements Observer {
	
	@javax.persistence.Id
	@javax.persistence.GeneratedValue
	private int id;
	
	private int airplaneID;
	private String nameOfAirplane; 
	private String nameOfAirline;
	private int nrOfRunway;
	private String plannedStartTime;
	private String actualStartTime;
	private String status;
	
	@Transient
	private StartSimulation sim;
	
	
	public StartWrapper()
	{
		//sim = new StartSimulation();
		//sim.addObserver(this);
		
	}
	
	public StartWrapper(boolean userGenerated)
	{
		if(userGenerated) {
			sim = new StartSimulation();
			sim.addObserver(this);
		}
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getAirplaneID() {
		return airplaneID;
	}

	public void setAirplaneID(int airplaneID) {
		this.airplaneID = airplaneID;
	}

	public String getNameOfAirplane() {
		return nameOfAirplane;
	}

	public void setNameOfAirplane(String nameOfAirplane) {
		this.nameOfAirplane = nameOfAirplane;
	}

	public String getNameOfAirline() {
		return nameOfAirline;
	}

	public void setNameOfAirline(String nameOfAirline) {
		this.nameOfAirline = nameOfAirline;
	}

	public String getPlannedStartTime() {
		return plannedStartTime;
	}

	public void setPlannedStartTime(Calendar startingTime) {
		
		String conversion = "";
		conversion += startingTime.getTime().toString();
		
		this.plannedStartTime = conversion;
	}

	public String getActualStartTime() {
		return actualStartTime;
	}

	public void setActualStartTime(String actualStartTime) {
		this.actualStartTime = actualStartTime;
	}

	public int getNrOfRunway() {
		return nrOfRunway;
	}

	public void setNrOfRunway(int nrOfRunway) {
		this.nrOfRunway = nrOfRunway;
	}
	
	public simulationState getSimulationState() {
		return sim.getState();
	}
	
	public void cancelSimulation() {
		sim.cancelSimulation();
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
		System.err.println("Called! CurrentState: " + sim.getState());
		//sim.resetTimers();
		sim.continueSimulation();
		this.setChanged();
		notifyObservers();
		
	}
	
	
	

}

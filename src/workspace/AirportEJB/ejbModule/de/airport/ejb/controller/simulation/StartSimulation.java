package de.airport.ejb.controller.simulation;

import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import de.airport.ejb.controller.DummyTask;

public class StartSimulation extends Observable {

	public enum simulationState {Init, Waiting, GoingToRunway, Starting, Started, ReturningToParkingPosition, Cancelled};
	
	private simulationState state;
	private Timer simulationTimer;
	private TimerTask timerAction = new TimerTask() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			switch(state) {
			case Init:
				state = simulationState.GoingToRunway;
				simulationTimer.schedule(new DummyTask(timerAction), 14000); 
				break;
			case GoingToRunway:
				state = simulationState.Waiting;
				//Keine Weitere Aktion notwendig, Simulation muss vom Benutzer fortgesetzt o. abgebrochen werden
				//simulationTimer.schedule(timerAction, 15000);
				break;
			case Waiting:
				state = simulationState.Starting;
				simulationTimer.schedule(new DummyTask(timerAction), 14000); 
				break;
			case Starting:
				state = simulationState.Started;
				//simulationTimer.schedule(timerAction, 10000);
				break;
			case ReturningToParkingPosition:
				state = simulationState.Cancelled;
				resetTimers();
				break;
			default:
				break;
			}
			setChanged();
			notifyObservers();
		}
	};;
	
	
	public StartSimulation() {
		// Beim Erstellen des Objektes wird das Flugzeug auf die Startbahn geschickt
		state = simulationState.Init;
		simulationTimer = new Timer();
		simulationTimer.schedule(new DummyTask(timerAction), 1000); //(timerAction, 15000);
		//setChanged();
		//notifyAll();
	}

	public simulationState getState() {
		return state;
		//return state.name();
	}

	public void setState(simulationState state) {
		this.state = state;
	}
	
	public void continueSimulation() {
		//simulationTimer.cancel();
		//simulationTimer = new Timer();
		simulationTimer.schedule(new DummyTask(timerAction), 100);
		//simulationTimer.schedule(new  , time);
	}

	public void resetTimers() {
		// TODO Auto-generated method stub
		simulationTimer.cancel();
		simulationTimer.purge();
	}
	
	public void cancelSimulation() {
		resetTimers();
		state = simulationState.ReturningToParkingPosition;
		simulationTimer.schedule(new DummyTask(timerAction), 15000);
	}
	
	
}

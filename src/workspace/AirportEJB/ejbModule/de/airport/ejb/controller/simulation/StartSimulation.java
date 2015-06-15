package de.airport.ejb.controller.simulation;

import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import de.airport.ejb.controller.DummyTask;

public class StartSimulation extends Observable {

	public enum simulationState {Waiting, GoingToRunway, Starting, Started};
	
	private simulationState state;
	private Timer simulationTimer;
	private TimerTask timerAction = new TimerTask() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			switch(state) {
			case GoingToRunway:
				state = simulationState.Waiting;
				setChanged();
				notifyObservers();
				//simulationTimer.schedule(timerAction, 15000);
				break;
			case Starting:
				state = simulationState.Started;
				simulationTimer.schedule(timerAction, 10000);
				setChanged();
				notifyObservers();
				break;
			case Waiting:
				state = simulationState.Starting;
				setChanged();
				notifyObservers();
				break;
			default:
				break;
			}
			
		}
	};;
	
	
	public StartSimulation() {
		// Beim Erstellen des Objektes wird das Flugzeug auf die Startbahn geschickt
		state = simulationState.GoingToRunway;
		simulationTimer = new Timer();
		simulationTimer.schedule(new DummyTask(timerAction), 15000); //(timerAction, 15000);
		
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
		simulationTimer.schedule(new DummyTask(timerAction), 15000);
		//simulationTimer.schedule(new  , time);
	}

	public void resetTimers() {
		// TODO Auto-generated method stub
		simulationTimer.cancel();
		simulationTimer.purge();
	}
	
	
}

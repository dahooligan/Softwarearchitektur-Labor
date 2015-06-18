package de.airport.ejb.controller;

import java.util.TimerTask;

public class DummyTask extends TimerTask {
	private TimerTask tt = null;

	public DummyTask(TimerTask tt) {
		this.tt = tt;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		tt.run();
	}
	
}

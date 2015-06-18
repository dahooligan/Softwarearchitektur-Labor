package de.airport.ejb;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import de.airport.ejb.controller.simulation.StartSimulation.simulationState;
import de.airport.ejb.model.Airline;
import de.airport.ejb.model.Airplane;
import de.airport.ejb.model.AirplaneState;
import de.airport.ejb.model.ParkingPosition;
import de.airport.ejb.model.Runway;
import de.airport.ejb.model.StartWrapper;
import de.airport.ejb.model.StartingDirection;

@Stateless
@LocalBean
public class AirportFacade {

	@PersistenceContext(unitName = "airport")
	private static EntityManager em;

	public void createAirline(String name, String street, String city) {
	Airline airline = new Airline(name);
	airline.setStreetName(street);
	airline.setCityName(city);
	//System.Err.println("AF: Creating Airline with: " + name + " - " + street + " - " + city);
	
	em.persist(airline);	}

	public void createAirplane(String name, String airline) { 
		Airplane airplane = new Airplane(name);
		airplane.setAirline(getAirlineById(airline));
		airplane.setState(AirplaneState.PARKED);
		ParkingPosition p = getFreeParkingPosition();
		airplane.setParkingPosition(getFreeParkingPosition());
		p.setFree(false);
		p.setAirplane(airplane);
		em.merge(p);
		/*
		List<ParkingPosition> pp = getParkingPositions();
		
		for(ParkingPosition p : pp) {
			if(p.isFree()) {
				// ParkingPosition setzen! Dafür muss Model angepasst werden. => Matze
			}
			
		}*/
		
		em.persist(airplane);
		//System.err.println("Airplane persistiert");
	}
	
	public void createParkpositions() {
		// TODO Auto-generated method stub
		for(int i = 0; i<4; i++) {
			ParkingPosition tmp = new ParkingPosition();
			//tmp.setFree(true);
			em.persist(tmp);
		}
	}

	public void createRunways() {
		// TODO Auto-generated method stub
		for(int i = 0; i<3; i++) {
			Runway tmp = new Runway(i);
			tmp.addStartingDirection(StartingDirection.EASTWEST);
			tmp.addStartingDirection(StartingDirection.WESTEAST);
			em.persist(tmp);
		}
	}

	public Runway getRunwayById(String id){
		return em.find(Runway.class, Integer.parseInt(id, 10));
	}
	
	public Airline getAirlineById(String id) {
		//List<Airline> airlineById = new ArrayList<Airline>();
		//TypedQuery<Airplane> query = em.createQuery("select e from airplane e order by e.name", Airplane.class);
		//PreparedStatement s = em.
		Airline al = em.find(Airline.class, Integer.parseInt(id, 10));
		//TypedQuery<Airplane> query1;
		
		return al;
	}
	
	public Airplane getAirplaneById(String id) {
		//List<Airline> airlineById = new ArrayList<Airline>();
		//TypedQuery<Airplane> query = em.createQuery("select e from airplane e order by e.name", Airplane.class);
		//PreparedStatement s = em.
		Airplane ap = em.find(Airplane.class, Integer.parseInt(id, 10));
		//TypedQuery<Airplane> query1;
		
		return ap;
	}
	
	
	public List<Airplane> getAirplanes() {
		TypedQuery<Airplane> query = em.createQuery("select e from airplane e order by e.name", Airplane.class);
		//System.Err.println("here I am");
		List<Airplane> res = query.getResultList();
		return res;
	}
	
	public List<Airline> getAirlines() {
		TypedQuery<Airline> query = em.createQuery("select e from airline e order by e.name", Airline.class);
		
		//System.Err.println("Ailines abgefragt");
		
		List<Airline> res = query.getResultList();
		/*
		Iterator<Airline> it = res.iterator();
		int i = 1;
		while(it.hasNext()) {
			Airline element = it.next();
			System.err.println("Result " + i + ":");
			System.err.println(element.getName());
			System.err.println(element.getCityName());
			System.err.println(element.getStreetName());
		}
		*/
		return res;
	}
	
	public List<ParkingPosition> getParkingPositions() {
		TypedQuery<ParkingPosition> query = em.createQuery("select e from parkingposition e order by e.id", ParkingPosition.class);
		
		System.err.println("ParkingPosition abgefragt");
		
		List<ParkingPosition> res = query.getResultList();

		return res;
	}
	
	public ParkingPosition getFreeParkingPosition() {
		List<ParkingPosition> tmp = getParkingPositions();
		
		for(ParkingPosition p: tmp) {
			if (p.isFree()) {
				return p;
			}
		}
		return null;
	}

	public static EntityManager getEm() {
		return em;
	}
	
	public boolean reserveRunway(int id){
		Runway tmp = em.find(Runway.class, id);
		
		if(tmp.isFree()){
			tmp.setFree(false);
			em.merge(tmp);
			return true;
		} else {
			return false;
		}
		
	}
	
	//public void freeRunway
	
	public void persistenceTest()
	{
		Runway rw = em.find(Runway.class, 0);
		rw.setFree(false);
		em.merge(rw);
	}

	public void persistStartProcess(StartWrapper startWrapper) {
		// TODO Auto-generated method stub
		em.persist(startWrapper);
	}
	
	public void mergeStartProcess(StartWrapper startWrapper) {
		// TODO Auto-generated method stub
		em.merge(startWrapper);
	}

	public void setAirplaneState(String nameOfAirplane, int id, simulationState s) {
		// TODO Auto-generated method stub
		Airplane ap = em.find(Airplane.class, id);
		switch(s) {
		case GoingToRunway:
			ap.setState(AirplaneState.GOING_TO_RUNWAY);
			break;
		case Started:
			em.remove(ap);
			break;
		case Starting:
			ap.setState(AirplaneState.STARTING);
			break;
		case Waiting:
			ap.setState(AirplaneState.WAITING_ON_RUNWAY);
			break;
		case ReturningToParkingPosition:
			ap.setState(AirplaneState.RETURNING_TO_PARKINGPOSITION);
		case Cancelled:
			ap.setState(AirplaneState.PARKED);
			break;
		default:
			ap.setState(AirplaneState.PARKED);
		em.merge(ap);
		}
		//for(StartWrapper s:)
		//ap.setState(state);
		
	}
	
	
}

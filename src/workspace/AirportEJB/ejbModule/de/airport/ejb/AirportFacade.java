package de.airport.ejb;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import de.airport.ejb.model.*;

@Stateless
@LocalBean
public class AirportFacade {

	@PersistenceContext(unitName = "airport")
	private EntityManager em;

	public void createAirplane(String name, String airline) { 
		Airplane airplane = new Airplane(name);
		airplane.setAirline(getAirlineById(airline));
		
		/*
		List<ParkingPosition> pp = getParkingPositions();
		
		for(ParkingPosition p : pp) {
			if(p.isFree()) {
				// ParkingPosition setzen! Dafür muss Model angepasst werden. => Matze
			}
			
		}*/
		
		em.persist(airplane);
		System.err.println("Airplane persistiert");
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
	
	
	public void createAirline(String name, String street, String city) {
		Airline airline = new Airline(name);
		airline.setStreetName(street);
		airline.setCityName(city);
		System.err.println("AF: Creating Airline with: " + name + " - " + street + " - " + city);
		
		em.persist(airline);	}

	public List<Airplane> getAirplanes() {
		TypedQuery<Airplane> query = em.createQuery("select e from airplane e order by e.name", Airplane.class);
		System.err.println("here I am");
		List<Airplane> res = query.getResultList();
		return res;
	}
	
	public List<Airline> getAirlines() {
		TypedQuery<Airline> query = em.createQuery("select e from airline e order by e.name", Airline.class);
		
		System.err.println("Ailines abgefragt");
		
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
		TypedQuery<ParkingPosition> query = em.createQuery("select e from parkingposition e order by e.name", ParkingPosition.class);
		
		System.err.println("ParkingPosition abgefragt");
		
		List<ParkingPosition> res = query.getResultList();

		return res;
	}

	public void createRunways() {
		// TODO Auto-generated method stub
		for(int i = 0; i<3; i++) {
			Runway tmp = new Runway(i+1);
			tmp.addStartingDirection(StartingDirection.EASTWEST);
			tmp.addStartingDirection(StartingDirection.WESTEAST);
			em.persist(tmp);
		}
	}
	
	public void createParkpositions() {
		// TODO Auto-generated method stub
		for(int i = 0; i<4; i++) {
			ParkingPosition tmp = new ParkingPosition(i);
			tmp.setFree(true);
			//em.persist(tmp);
		}
	}
}

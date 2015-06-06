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
}

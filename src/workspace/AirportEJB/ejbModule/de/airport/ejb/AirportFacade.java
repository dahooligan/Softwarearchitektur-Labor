package de.airport.ejb;

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

	public void createAirplane(String name) { 
		Airplane airplane = new Airplane(name);
		em.persist(airplane);
	}
	
	public void createAirline(String name, String street, String city) {
		Airline airline = new Airline(name);
		airline.setStreetName(street);
		airline.setCityName(city);
		System.err.println("AF: Creating Airline with: " + name + " - " + street + " - " + city);
		em.persist(airline);	}

	public List<Airplane> getAirplanes() {
		Query query = em.createQuery("select e from airplane e order by e.name");
		System.err.println("here I am");
		return query.getResultList();
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

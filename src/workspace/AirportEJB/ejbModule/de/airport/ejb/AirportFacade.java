package de.airport.ejb;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import de.airport.ejb.model.*;

@Stateless
@LocalBean
public class AirportFacade {

	@PersistenceContext(unitName = "airport")
	private EntityManager em;

	public void createAirplane(String name) { 
		Airplane airplane = new Airplane();
		airplane.setName(name);
		em.persist(airplane);
	}
	
	public void createAirline(String name, String street, String city) {
		Airline airline = new Airline();
		airline.setName(name);
		airline.setStreet(street);
		airline.setCity(city);
		em.persist(airline);	}

	public List<Airplane> getAirplanes() {
		Query query = em.createQuery("select e from airplane e order by e.name");
		return query.getResultList();
	}
	
	public List<Airline> getAirlines() {
		Query query = em.createQuery("select e from airline e order by e.name");
		return query.getResultList();
	}
}

package salab.team2;

import java.util.ArrayList;
import java.util.Collection;

public class Airline {

	private final int id;
	private final String name;
	private final Collection<Aircraft> aircraftCollection;
	private String cityName;
	private String streetName;

	public Airline(int id, String name) {
		super();
		this.id = id;
		this.name = name;
		this.aircraftCollection = new ArrayList<Aircraft>();
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	/**
	 * Add an {@link Aircraft} to this {@link Airline}
	 * 
	 * @param aircraft
	 *            the aircraft to add to the {@link Collection} of aircrafts of
	 *            this {@link Airline}
	 * @return @see {@link Collection#add(Object))
	 */
	public boolean addAircraft(Aircraft aircraft) {
		return aircraftCollection.add(aircraft);
	}

}

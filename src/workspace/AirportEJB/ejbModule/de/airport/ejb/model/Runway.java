package de.airport.ejb.model;

import java.util.ArrayList;
import java.util.Collection;

public class Runway {

	private final int id;
	private boolean isFree;
	private final Collection<StartingDirection> permittedStartingDirections;

	public Runway(int id) {
		super();
		this.permittedStartingDirections = new ArrayList<StartingDirection>();
		this.id = id;
	}

	public boolean isFree() {
		return isFree;
	}

	public void setFree(boolean isFree) {
		this.isFree = isFree;
	}

	public int getId() {
		return id;
	}

	public Collection<StartingDirection> getPermittedStartingDirections() {
		return permittedStartingDirections;
	}

	/**
	 * Adds a {@link StartingDirection} to the list of permitted
	 * {@link StartingDirection}s for this {@link Runway}.
	 * 
	 * @param direction
	 *            The {@link StartingDirection} to be added to the list of
	 *            permitted {@link StartingDirection}s.
	 * @return @see {@link Collection#add(Object)}
	 */
	public boolean addStartingDirection(StartingDirection direction) {
		return permittedStartingDirections.add(direction);
	}

	/**
	 * Removes a {@link StartingDirection} from the {@link Collection} of
	 * permitted {@link StartingDirection}s
	 * 
	 * @param direction
	 *            the {@link StartingDirection} to be removed
	 * @return @see {@link Collection#remove(Object)}
	 */
	public boolean removeStartingDirection(StartingDirection direction) {
		return permittedStartingDirections.remove(direction);
	}

}

package de.ifgi.lodum.domain;

/**
 * 
 * @author steffan
 *
 */
public interface RDFRepresentable {
	
	/**
	 * Returns the representation of the object in turtle.
	 * @return the representation as turtle.
	 */
	String toTurtle();
}

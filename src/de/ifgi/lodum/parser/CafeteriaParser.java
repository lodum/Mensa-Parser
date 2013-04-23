package de.ifgi.lodum.parser;

import java.util.List;

import de.ifgi.lodum.domain.Menu;

/**
 * The entry points for API users who wants to get a {@link List} of {@link Menu} for a specific {@link CafeteriaTyp}.
 * 
 * @author steffan
 *
 */
public class CafeteriaParser {
	
	private CafeteriaParser() {}
	
	/**
	 * Returns a {@link List} of {@link Menu} for a {@link CafeteriaTyp}.
	 * @param cafeteriaTyp the {@link CafeteriaTyp}
	 * @return a {@link List} of {@link Menu}
	 */
	public static List<Menu> getCafeteria(CafeteriaTyp cafeteriaTyp) {
		
		if(cafeteriaTyp.equals(CafeteriaTyp.Aasee) || cafeteriaTyp.equals(CafeteriaTyp.Ring))
			return new RingAaseeParser().getCafeteria(cafeteriaTyp);
		if(cafeteriaTyp.equals(CafeteriaTyp.DaVinci))
			return new DaVinciParser().getCafeteria(cafeteriaTyp);
		if(cafeteriaTyp.equals(CafeteriaTyp.Bispinghof))
			return new BispinghofParser().getCafeteria(cafeteriaTyp);
		if(cafeteriaTyp.equals(CafeteriaTyp.Oeconomicum))
			return new OeconomicumParser().getCafeteria(cafeteriaTyp);
		
		return null;	
	}
}

package org.dieschnittstelle.ess.basics.annotations.stockitemtypes;

import org.dieschnittstelle.ess.basics.DisplayAs;
import org.dieschnittstelle.ess.basics.annotations.Initialise;
import org.dieschnittstelle.ess.basics.annotations.Brandname;
import org.dieschnittstelle.ess.basics.annotations.StockItem;
import org.dieschnittstelle.ess.basics.annotations.Units;

@DisplayAs("Milk")
@StockItem
public class Milch {

	@Units
	private int menge;

	@Brandname
	@DisplayAs("brand")
	private String markenname;

	public int getMenge() {
		return menge;
	}

	public void setMenge(int menge) {
		this.menge = menge;
	}

	public String getMarkenname() {
		return markenname;
	}

	public void setMarkenname(String markenname) {
		this.markenname = markenname;
	}

	@Initialise
	public void lagern(int units, String brandname) {
		this.markenname = brandname;
		this.menge = units;
	}
	
	/**
	 * our own toString method
	 */
	public String toString() {
		return "<Milch " + this.markenname + " " + this.menge + ">";
	}

}

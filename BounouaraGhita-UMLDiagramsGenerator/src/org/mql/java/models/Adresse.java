package org.mql.java.models;

public class Adresse {
	 private String neighborhood;
	    private String city;

	    public Adresse(String neighborhood, String city) {
	        this.neighborhood = neighborhood;
	        this.city = city;
	    }

	    public String getNeighborhood() {
	        return neighborhood;
	    }

	    public String getCity() {
	        return city;
	    }
}

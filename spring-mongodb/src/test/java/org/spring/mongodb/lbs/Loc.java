package org.spring.mongodb.lbs;

import java.util.Arrays;

public class Loc {
	
	private String type = "Point";
	
	private double[] coordinates;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double[] getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(double[] coordinates) {
		this.coordinates = coordinates;
	}

	@Override
	public String toString() {
		return "Loc [type=" + type + ", coordinates=" + Arrays.toString(coordinates) + "]";
	}

	public Loc(String type, double[] coordinates) {
		super();
		this.type = type;
		this.coordinates = coordinates;
	}

	public Loc() {
		super();
	}

	public Loc(double[] coordinates) {
		super();
		this.coordinates = coordinates;
	}
}

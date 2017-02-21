package org.spring.es.geo;

public class GeoVO {

	private long id;
	
	private double lat;
	
	private double lng;
	
	private String name;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public GeoVO() {
		super();
	}

	public GeoVO(long id, double lng,double lat,String name) {
		super();
		this.id = id;
		this.lat = lat;
		this.lng = lng;
		this.name = name;
	}
	
}

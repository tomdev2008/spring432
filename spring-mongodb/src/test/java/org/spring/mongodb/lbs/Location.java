package org.spring.mongodb.lbs;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "location")
public class Location {

	private String address;//其它属性

	private Loc loc;//地址位置

	@Override
	public String toString() {
		return "Location [address=" + address + ", loc=" + loc + "]";
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Loc getLoc() {
		return loc;
	}

	public void setLoc(Loc loc) {
		this.loc = loc;
	}

	public Location(String address, Loc loc) {
		super();
		this.address = address;
		this.loc = loc;
	}

	public Location() {
		super();
	}

}
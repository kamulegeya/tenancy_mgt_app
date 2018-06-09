package meters.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Locations  implements Serializable{
private int _id;
private String location;


public Locations() {
	
}
public Locations(int _id, String location) {
		this._id = _id;
	this.location = location;
}
/**
 * @return the _id
 */
public int get_id() {
	return _id;
}
/**
 * @param _id the _id to set
 */
public void set_id(int _id) {
	this._id = _id;
}
/**
 * @return the location
 */
public String getLocation() {
	return location;
}
/**
 * @param location the location to set
 */
public void setLocation(String location) {
	this.location = location;
}
@Override
public String toString() {
	return "Location:" + this.location;
}

}

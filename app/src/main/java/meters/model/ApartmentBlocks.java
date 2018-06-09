package meters.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ApartmentBlocks implements Serializable {
private  int _id;
private String location;
private String blocknumber;
public ApartmentBlocks() {
	
}
public ApartmentBlocks(int _id, String location, String blocknumber) {
	super();
	this._id = _id;
	this.location = location;
	this.blocknumber = blocknumber;
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
 * @return the location_id
 */
public String getLocation() {
	return location;
}
/**
 * @param location_id the location_id to set
 */
public void setLocation(String location_id) {
	this.location = location_id;
}
/**
 * @return the blocknumber
 */
public String getBlocknumber() {
	return blocknumber;
}
/**
 * @param blocknumber the blocknumber to set
 */
public void setBlocknumber(String blocknumber) {
	this.blocknumber = blocknumber;
}
@Override
public String toString() {
	return  "Location:"+ this.location + "\n"+
			"Block:"+ this.blocknumber;
            	
}

}

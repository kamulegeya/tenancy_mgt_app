package meters.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Apartments implements Serializable {
protected int _id;
protected int block_id;
protected String number;
protected String block;
protected String location;
protected int location_id;
protected String inuse;
public Apartments(int _id, int block_id, String number,String b,String l, int locationid,String s) {
	
	this._id = _id;
	
	this.block_id = block_id;
	this.number = number;
	this.block=b;
	this.location=l;
	this.location_id=locationid;
	this.inuse=s;
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
public Apartments() {
	
}
/**
 * @return the block
 */
public String getBlock() {
	return block;
}
/**
 * @param block the block to set
 */
public void setBlock(String block) {
	this.block = block;
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
 * @return the block_id
 */
public int getBlock_id() {
	return block_id;
}
/**
 * @param block_id the block_id to set
 */
public void setBlock_id(int block_id) {
	this.block_id = block_id;
}
/**
 * @return the number
 */
public String getNumber() {
	return number;
}
/**
 * @param number the number to set
 */
public void setNumber(String number) {
	this.number = number;
}
/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Override
public String toString() {
	return "ID:"+ this._id + "\n"+
            "Number:"+ this.number + "\n"+
			"Block:"+ this.block + "\n"+
            "Location:"+ this.location+  "\n"+	
			"Status:"  + this.inuse;
			
			
}
public int getLocation_id() {
	return location_id;
}
public void setLocation_id(int location_id) {
	this.location_id = location_id;
}
public String getInuse() {
	return inuse;
}
public void setInuse(String inuse) {
	this.inuse = inuse;
}

}

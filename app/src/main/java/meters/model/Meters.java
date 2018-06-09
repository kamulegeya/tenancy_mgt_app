package meters.model;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class Meters implements Serializable {
int _id;
String SerialNumber;
String MeterNumber;
Date DateAdded;
Date DateInActive;
Boolean InActive;

public Meters() {
	super();
}
public Meters(int _id, String serialNumber, String meterNumber, Date dateAdded,
		Date dateInActive, Boolean inActive) {
	super();
	this._id = _id;
	SerialNumber = serialNumber;
	MeterNumber = meterNumber;
	DateAdded = dateAdded;
	DateInActive = dateInActive;
	InActive = inActive;
}




/**
 * @param serialNumber
 * @param meterNumber
 * @param dateAdded
 */
public Meters(String serialNumber, String meterNumber, Date dateAdded) {
	
	SerialNumber = serialNumber;
	MeterNumber = meterNumber;
	DateAdded = dateAdded;
}
public String getSerialNumber() {
	return SerialNumber;
}
public void setSerialNumber(String serialNumber) {
	SerialNumber = serialNumber;
}
public String getMeterNumber() {
	return MeterNumber;
}
public void setMeterNumber(String meterNumber) {
	MeterNumber = meterNumber;
}
public Date getDateAdded() {
	return DateAdded;
}
public void setDateAdded(Date dateAdded) {
	DateAdded = dateAdded;
}
public Date getDateInActive() {
	return DateInActive;
}
public void setDateInActive(Date dateInActive) {
	DateInActive = dateInActive;
}
public Boolean getInActive() {
	return InActive;
}
public void setInActive(Boolean inActive) {
	InActive = inActive;
}
public int get_id() {
	return _id;
}
/**
 * @param _id the _id to set
 */
public void set_id(int _id) {
	this._id = _id;
}



}

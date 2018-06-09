package meters.model;

import java.util.Date;

public class RentalInvoices {
private int _id;
private int tenancy_id;
private Date startdate;
private Date enddate;
private Date duedate;
private double rentalamout;
public RentalInvoices() {
	
}
public RentalInvoices(int _id, int tenancy_id, Date startdate, Date enddate,
		Date duedate, double rentalamout) {
	super();
	this._id = _id;
	this.tenancy_id = tenancy_id;
	this.startdate = startdate;
	this.enddate = enddate;
	this.duedate = duedate;
	this.rentalamout = rentalamout;
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
 * @return the tenancy_id
 */
public int getTenancy_id() {
	return tenancy_id;
}
/**
 * @param tenancy_id the tenancy_id to set
 */
public void setTenancy_id(int tenancy_id) {
	this.tenancy_id = tenancy_id;
}
/**
 * @return the startdate
 */
public Date getStartdate() {
	return startdate;
}
/**
 * @param startdate the startdate to set
 */
public void setStartdate(Date startdate) {
	this.startdate = startdate;
}
/**
 * @return the enddate
 */
public Date getEnddate() {
	return enddate;
}
/**
 * @param enddate the enddate to set
 */
public void setEnddate(Date enddate) {
	this.enddate = enddate;
}
/**
 * @return the duedate
 */
public Date getDuedate() {
	return duedate;
}
/**
 * @param duedate the duedate to set
 */
public void setDuedate(Date duedate) {
	this.duedate = duedate;
}
/**
 * @return the rentalamout
 */
public double getRentalamout() {
	return rentalamout;
}
/**
 * @param rentalamout the rentalamout to set
 */
public void setRentalamout(double rentalamout) {
	this.rentalamout = rentalamout;
}

}

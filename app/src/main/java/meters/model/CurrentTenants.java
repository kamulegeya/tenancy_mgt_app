package meters.model;

public class CurrentTenants {
private int _id;
private String FirstName;
private String SurName;
@SuppressWarnings("unused")
private String FullName;
public CurrentTenants(int _id, String firstName, String surName, String fullName) {
	super();
	this._id = _id;
	FirstName = firstName;
	SurName = surName;
	FullName = fullName;
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
 * @return the firstName
 */
public String getFirstName() {
	return FirstName;
}
/**
 * @param firstName the firstName to set
 */
public void setFirstName(String firstName) {
	FirstName = firstName;
}
/**
 * @return the surName
 */
public String getSurName() {
	return SurName;
}
/**
 * @param surName the surName to set
 */
public void setSurName(String surName) {
	SurName = surName;
}
/**
 * @return the fullName
 */
public String getFullName() {
	return  this.FirstName + "  "+ this.SurName;
}
public CurrentTenants() {
	
}
/**
 * @param fullName the fullName to set
 */
public void setFullName(String fname, String sname) {
	FullName = fname+ "  " + sname;
}

}

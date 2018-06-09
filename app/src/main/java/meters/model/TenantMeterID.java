package meters.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TenantMeterID implements Serializable{
protected int _id;
protected String  FirstName;
protected String Surname;
protected String FullName;
public TenantMeterID() {
	super();
}
public TenantMeterID(int _id, String firstName, String surname, String fullName) {
	super();
	this._id = _id;
	FirstName = firstName;
	Surname = surname;
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
	if (firstName==null){
		FirstName="";
}else
{
	FirstName = firstName;
}
}
/**
 * @return the surname
 */
public String getSurname() {
	return Surname;
}
/**
 * @param surname the surname to set
 */
public void setSurname(String surname) {
	if(surname==null)
	{
		Surname="";
	}else
	{
	Surname = surname;
	}
}
/**
 * @return the fullName
 */
public String getFullName() {
	return  this.FirstName + "  " + this.Surname;
}
/**
 * @param fullName the fullName to set
 */
public void setFullName(String fname,String  sname) {
	if(fname==null)
	{
		FullName =  sname;
	}
	else if(sname==null)
	{
		FullName=fname;
	}
		else
		{
			FullName = fname + "  "+ sname;
		}
	
}




}

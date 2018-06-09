package meters.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TenantList implements Serializable {
protected int _id;
protected String FirstName;
protected String Surname;
protected String email;
protected String FullName;
protected String mobile;
protected String mobile2;
public TenantList(int _id, String firstName, String surname, String fullName,
		String mobile, String mobile2,String myemail) {
	
	this._id = _id;
	this.email=myemail;
	FirstName = firstName;
	Surname = surname;
	FullName = fullName;
	this.mobile = mobile;
	this.mobile2 = mobile2;
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
	if(firstName==null)
	{
	FirstName = "";
	}else
	{
		FirstName=firstName;
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
	}
	else{
	Surname = surname;
	}
}

public TenantList() {
	
}
/**
 * @return the fullName
 */
public String getFullName() {
	if (this.FirstName==null)
	{
	return  this.Surname;
}else if(this.Surname==null)
{
	return  this.FirstName;
}else
{
	return this.FirstName + " "+ this.Surname;
}

}
/**
 * @param fullName the fullName to set
 */
public void setFullName(String fname, String sname) {
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
			FullName = fname + " "+ sname;
		}
	}

/**
 * @return the mobile
 */
public String getMobile() {
	return mobile;
}
/**
 * @param mobile the mobile to set
 */
public void setMobile(String mobile) {
	this.mobile = mobile;
}
/**
 * @return the mobile2
 */
public String getMobile2() {
	return mobile2;
}
/**
 * @param mobile2 the mobile2 to set
 */
public void setMobile2(String mobile2) {
	this.mobile2 = mobile2;
}
/**
 * @return the email
 */
public String getEmail() {
	return email;
}
/**
 * @param email the email to set
 */
public void setEmail(String email) {
	this.email = email;
}



}

package meters.model;

import java.util.Date;

public class SearchResults {
private String firstname;
private String surname;
private String type;
private String fullname;
private Date date;
private double amount;
/**
 * @return the date
 */
public Date getDate() {
	return date;
}
/**
 * @param date the date to set
 */
public void setDate(Date date) {
	this.date = date;
}
/**
 * @return the amount
 */
public double getAmount() {
	return amount;
}
/**
 * @param amount the amount to set
 */
public void setAmount(double amount) {
	this.amount = amount;
}

public SearchResults(String firstname, String surname, String type,
		String fullname,Date d,double a) {	
	this.firstname = firstname;
	this.surname = surname;
	this.type = type;
	this.fullname = fullname;
	this.date=d;
	this.amount=a;
}
public SearchResults() {
	
}
/**
 * @return the firstname
 */
public String getFirstname() {
	return firstname;
}
/**
 * @param firstname the firstname to set
 */
public void setFirstname(String firstname) {
	if(firstname==null)
	{
		this.firstname="";
	}else
	{
	this.firstname = firstname;
	}
}
/**
 * @return the surname
 */
public String getSurname() {
	return surname;
}
/**
 * @param surname the surname to set
 */
public void setSurname(String surname) {
	if(surname==null)
	{
		this.surname = "";
	}else
	{
	this.surname = surname;
	}
}
/**
 * @return the type
 */
public String getType() {
	return type;
}
/**
 * @param type the type to set
 */
public void setType(String type) {
	this.type = type;
}
/**
 * @return the fullname
 */
public String getFullname() {
	return fullname;
}
/**
 * @param fullname the fullname to set
 */
public void setFullname(String fname, String sname) {
	
	this.fullname=fname+ " "+ sname;
}



}

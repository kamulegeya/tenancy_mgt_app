package meters.model;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class Tenants implements Serializable {
	private int _id;
	private String FirstName;
	private String SurName;
	private String OtherNames;
	private String Mobile1;
	private String Mobile2;
	private Date DateAdded;
	private String email;
	private Boolean InActive=false;
	private Date DateInActive;
	
	public Tenants( int _id,String FirstName, String SurName,String OtherNames,String Mobile1,
			       String Mobile2, Date DateAdded,Boolean InActive,Date DateInActive,String email)
	{
		this._id=_id;
		this.FirstName=FirstName;
		this.SurName=SurName;
		this.OtherNames=OtherNames;
		this.Mobile1=Mobile1;
		this.Mobile2=Mobile2;
		this.DateAdded=DateAdded;
		this.InActive=InActive;
		this.DateInActive=DateInActive;	
		this.email=email;
	}
	
	/**
	 * @param firstName
	 * @param surName
	 * @param otherNames
	 * @param mobile1
	 * @param mobile2
	 * @param dateAdded
	 * @param inActive
	 * @param dateInActive
	 */
	public Tenants(String firstName, String surName, String otherNames,
			String mobile1, String mobile2, Date dateAdded, Boolean inActive,
			Date dateInActive) {
		super();
		FirstName = firstName;
		SurName = surName;
		OtherNames = otherNames;
		Mobile1 = mobile1;
		Mobile2 = mobile2;
		DateAdded = dateAdded;
		InActive = inActive;
		DateInActive = dateInActive;
	}

	public Tenants()
	{
		
	}
	public Tenants(String FirstName, String SurName,String OtherNames,String Mobile1,
		       String Mobile2,String email)
{
   this.FirstName=FirstName;
	this.SurName=SurName;
	this.OtherNames=OtherNames;
	this.Mobile1=Mobile1;
	this.Mobile2=Mobile2;
	this.email=email;
	
		
}

	public String getFirstName() {
		return FirstName;
	}

	public void setFirstName(String firstName) {
		FirstName = firstName;
	}

	public String getSurName() {
		return SurName;
	}

	public void setSurName(String surName) {
		SurName = surName;
	}

	public String getOtherNames() {
		return OtherNames;
	}

	public void setOtherNames(String otherNames) {
		OtherNames = otherNames;
	}

	public String getMobile1() {
		return Mobile1;
	}

	public void setMobile1(String mobile1) {
		Mobile1 = mobile1;
	}

	public String getMobile2() {
		return Mobile2;
	}

	public void setMobile2(String mobile2) {
		Mobile2 = mobile2;
	}

	public Date getDateAdded() {
		return DateAdded;
	}

	public void setDateAdded(Date dateAdded) {
		DateAdded = dateAdded;
	}

	public Boolean getInActive() {
		return InActive;
	}

	public void setInActive(Boolean inActive) {
		InActive = inActive;
	}

	public Date getDateInActive() {
		return DateInActive;
	}

	public void setDateInActive(Date dateInActive) {
		DateInActive = dateInActive;
	}

	public int get_id() {
		return _id;
	}
	
	public void set_id(int myid){_id=myid;}

	/**
	 * @param _id
	 * @param firstName
	 * @param surName
	 * @param otherNames
	 * @param mobile1
	 * @param mobile2
	 */
	public Tenants(int _id, String firstName, String surName,
			String otherNames, String mobile1, String mobile2) {
		super();
		this._id = _id;
		FirstName = firstName;
		SurName = surName;
		OtherNames = otherNames;
		Mobile1 = mobile1;
		Mobile2 = mobile2;
	}
	
	
	public String FullName(String fname, String sname)
	{
		if(fname==null)
		{
			return  sname;
		}
		else if(sname==null)
		{
			return fname;
		}
			else
			{
				return fname + " "+ sname;
			}
		
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

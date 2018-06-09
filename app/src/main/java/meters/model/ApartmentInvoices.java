package meters.model;

import java.util.Date;

public class ApartmentInvoices extends RentalInvoices {
private int apartmentid;
private String fname;
private String sname;
	public ApartmentInvoices(int _id, int tenancy_id, Date startdate,
			Date enddate, Date duedate, double rentalamout,int id,String f, String s) {
		super(_id, tenancy_id, startdate, enddate, duedate, rentalamout);
		// TODO Auto-generated constructor stub
		this.apartmentid=id;
		this.fname=f;
		this.sname=s;
	}
	public ApartmentInvoices() {
		
	}
	public int getApartmentid() {
		return apartmentid;
	}
	public void setApartmentid(int apartmentid) {
		this.apartmentid = apartmentid;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
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
	

}

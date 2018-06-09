package meters.model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.os.Parcel;
import android.os.Parcelable;

@SuppressWarnings("serial")
public class ActiveTenancy extends TenantList implements Serializable,Parcelable {
protected int tenancy_id;
protected int apartmentid;
protected Date start_date;
protected  Date end_date;
protected Date lastInvoiceDate;
protected String block;
protected String location;
protected  double monthly_rental;
protected  String apartment;
static SimpleDateFormat fm = new SimpleDateFormat(
        "dd-MM-yyyy", Locale.getDefault());	

public ActiveTenancy() {
	
}
public ActiveTenancy(int _id, String firstName, String surname,String email,
		String fullName, String mobile, String mobile2,Date sDate, Date eDate,double rental,String apartment1,
		int tenancyid,int roomid,String b, String l,Date lldate) {
	super(_id, firstName, surname, fullName, mobile, mobile2,email);
	// TODO Auto-generated constructor stub
	this.tenancy_id=tenancyid;
	this.start_date=sDate;
	this.end_date=eDate;
	this.monthly_rental=rental;
	this.apartment=apartment1;
	this.apartmentid=roomid;
	this.block=b;
	this.location=l;
	this.lastInvoiceDate=lldate;
}
/**
 * @return the start_date
 */
public Date getStart_date() {
	return start_date;
}
/**
 * @return the apartment
 */
public String getApartment() {
	return apartment;
}
/**
 * @param apartment the apartment to set
 */
public void setApartment(String apartment) {
	this.apartment = apartment;
}
/**
 * @param start_date the start_date to set
 */
public void setStart_date(Date start_date) {
	this.start_date = start_date;
}
/**
 * @return the end_date
 */
public Date getEnd_date() {
	return end_date;
}
/**
 * @param end_date the end_date to set
 */
public void setEnd_date(Date end_date) {
	this.end_date = end_date;
}
/**
 * @return the monthly_rental
 */
public double getMonthly_rental() {
	return monthly_rental;
}
/**
 * @param monthly_rental the monthly_rental to set
 */
public void setMonthly_rental(double monthly_rental) {
	this.monthly_rental = monthly_rental;
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
public int getApartmentid() {
	return apartmentid;
}
public void setApartmentid(int apartmentid) {
	this.apartmentid = apartmentid;
}
public String getBlock() {
	return block;
}
public void setBlock(String block) {
	this.block = block;
}
public String getLocation() {
	return location;
}
public void setLocation(String location) {
	this.location = location;
}


public Date getLastInvoiceDate() {
	return lastInvoiceDate;
}
public void setLastInvoiceDate(Date lastInvoiceDate) {
	this.lastInvoiceDate = lastInvoiceDate;
}
@Override
public int describeContents() {
	// TODO Auto-generated method stub
	return 0;
}
@Override
public void writeToParcel(Parcel p, int arg1) {
	// TODO Auto-generated method stub
	p.writeString(FirstName);
	p.writeString(Surname);
	p.writeString(FullName);
	p.writeString(apartment);	
	p.writeInt(tenancy_id);
	p.writeString(fm.format(start_date));
	p.writeString(fm.format(end_date));
	
	
	
	
}
public final static Parcelable.Creator<ActiveTenancy>CREATOR = 
 new Creator<ActiveTenancy>()
 {
	
	@Override
	public ActiveTenancy createFromParcel(Parcel s) {
		ActiveTenancy a= new ActiveTenancy();
		a.FirstName=s.readString();
		a.Surname=s.readString();
		a.FullName=s.readString();
		a.apartment=s.readString();
		a.tenancy_id=s.readInt();
		try {
			a.start_date=fm.parse(s.readString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			a.end_date=fm.parse(s.readString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return a;
	}

	@Override
	public ActiveTenancy[] newArray(int size) {
		// TODO Auto-generated method stub
		return new ActiveTenancy[size];
	}
	
 };
 public void readFromParcel(Parcel in) throws ParseException
 {
	 FirstName=in.readString();
	 Surname=in.readString();	 
	 FullName=in.readString();
	 apartment=in.readString();
	 tenancy_id=in.readInt();
	 start_date=fm.parse(in.readString());
	 end_date= fm.parse(in.readString());
	
	 
 }

}

package meters.model;

import java.util.Date;

import android.util.Log;

@SuppressWarnings("serial")

public class RentalPaymentStatus extends ActiveTenancy {
private double amoutpaid;
private Date duedate;
private Date datepaid;
private long datelate;
public RentalPaymentStatus() {
	// TODO Auto-generated constructor stub
}
	public RentalPaymentStatus(int _id, String firstName, String surname,
			String email, String fullName, String mobile, String mobile2,
			Date sDate, Date eDate, double rental, String apartment1,
			int tenancyid, int roomid, String b, String l,double a,Date dd, Date paid,long dlate,Date linvoice) {
		super(_id, firstName, surname, email, fullName, mobile, mobile2, sDate, eDate,
				rental, apartment1, tenancyid, roomid, b, l,linvoice);
		this.amoutpaid=a;
		this.duedate=dd;
		this.datepaid=paid;
		this.datelate=dlate;
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @return the datepaid
	 */
	public Date getDatepaid() {
		return datepaid;
	}

	/**
	 * @param datepaid the datepaid to set
	 */
	public void setDatepaid(Date datepaid) {
		this.datepaid = datepaid;
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
	 * @return the amoutpaid
	 * 
	 */
	public double getAmoutpaid() {
		return amoutpaid;
	}

	/**
	 * @param amoutpaid the amoutpaid to set
	 */
	public void setAmoutpaid(double amoutpaid) {
		this.amoutpaid = amoutpaid;
	}
	public long getDatelate() {
		// not paid invoices...
		if(this.datepaid==null)
		{
			// get due date
			long dd=this.duedate.getTime();
			// get current date
			Date date= new Date();
			long today= date.getTime();
			// is today after due date?
			// if so...calculate 
			
			  if(today> dd)
			  {
				 return (today-dd) /(24 * 60 * 60 * 1000);
			  }else
			  {
			// ddue date not reached
		  return 0;
			  }
		}else
		{
			
		long diff= (this.datepaid.getTime())-(this.duedate.getTime());
		long diffDays = diff / (24 * 60 * 60 * 1000);
		return diffDays;
		}
	}
	public void setDatelate() {
		this.datelate = this.getDatelate();
	}
	
	
	
}

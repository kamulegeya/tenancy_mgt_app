package meters.model;

import android.annotation.SuppressLint;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
@SuppressWarnings("serial")
public class UnPaidInvoices extends ActiveTenancy implements Serializable {
	private Date datedue;
	private int invoice_id ;	
	@SuppressLint("SimpleDateFormat")
	SimpleDateFormat fm =new SimpleDateFormat("dd-MM-yyyy");
	DecimalFormat df = new DecimalFormat("#,###,###,###");

	
	
	public UnPaidInvoices(int _id, String firstName, String surname,
			String email, String fullName, String mobile, String mobile2,
			Date sDate, Date eDate, double rental, String apartment1,
			int tenancyid, int roomid, String b, String l,Date dd,int id,Date lastinvpoice) {
		super(_id, firstName, surname, email, fullName, mobile, mobile2, sDate, eDate,
				rental, apartment1, tenancyid, roomid, b, l,lastinvpoice);
		this.datedue=dd;
		this.invoice_id=id;
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the datedue
	 */
	public Date getDatedue() {
		return datedue;
	}

	/**
	 * @param datedue the datedue to set
	 */
	public void setDatedue(Date datedue) {
		this.datedue = datedue;
	}
	
	public UnPaidInvoices() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the invoice_id
	 */
	public int getInvoice_id() {
		return invoice_id;
	}

	/**
	 * @param invoice_id the invoice_id to set
	 */
	public void setInvoice_id(int invoice_id) {
		this.invoice_id = invoice_id;
	}
	

	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String full = this.FirstName + " "+ this.Surname;
		return "Invoice No#:"+ this.invoice_id + "\n"+
	            "Tenant:"+ full + "\n"+
				"TelNumbers:"+ this.mobile + "\\" + this.mobile2 + "\n"+
	            "Invoice Period:"+ " from "+ fm.format(this.start_date) + " to "+ fm.format(this.end_date)+ "\n"+
				"Invoice Due Date"+ fm.format(this.datedue)+ "\n"+
	            "Invoice Amount"+ df.format(this.monthly_rental)+ "\n"+
	            "Apartment/Room:"+ this.apartment ;
	}
public String Period()
{
	return  "From "+ fm.format(this.start_date )+ " To "+ fm.format( this.end_date);
	
}
}

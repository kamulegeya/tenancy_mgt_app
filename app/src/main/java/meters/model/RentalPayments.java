package meters.model;

import java.util.Date;

public class RentalPayments  extends  TenantList {
private int payment_id;
private int invoice_id;
private Date payment_date;
private double amount;
private String narration;
String period;
public RentalPayments() {
	
}

/**
 * @return the period
 */
public String getPeriod() {
	return period;
}

/**
 * @param period the period to set
 */
public void setPeriod(String period) {
	this.period = period;
}

/**
 * @return the _id
 */

public RentalPayments(int _id, String firstName, String surname,
		String fullName, String mobile, String mobile2,String email,
		int payment_id, int invoice_id, Date payment_date,
		double amount, String narration	,String p
		
		) {
	super(_id, firstName, surname, fullName, mobile, mobile2,email);
	// TODO Auto-generated constructor stub
	this.payment_id= _id;
	this.invoice_id = invoice_id;
	this.payment_date = payment_date;
	this.amount = amount;
	this.narration = narration;
	this.period=p;
	
}



/**
 * @return the payment_id
 */
public int getPayment_id() {
	return payment_id;
}

/**
 * @param payment_id the payment_id to set
 */
public void setPayment_id(int payment_id) {
	this.payment_id = payment_id;
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
/**
 * @return the payment_date
 */
public Date getPayment_date() {
	return payment_date;
}
/**
 * @param payment_date the payment_date to set
 */
public void setPayment_date(Date payment_date) {
	this.payment_date = payment_date;
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
/**
 * @return the narration
 */
public String getNarration() {
	return narration;
}
/**
 * @param narration the narration to set
 */
public void setNarration(String narration) {
	this.narration = narration;
}


}

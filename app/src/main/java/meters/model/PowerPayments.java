package meters.model;

import java.util.Date;

@SuppressWarnings("serial")
public class PowerPayments extends TenantList {
int paymentid;
Date PaymentDate;
double Amount;
String Narration;

public PowerPayments(int _id, String firstName, String surname,
		String fullName, String email,String mobile, String mobile2, int payment_id, Date Payment_Date,double amount, String narration) {
	super(_id, firstName, surname, fullName, mobile, mobile2,email);
	// TODO Auto-generated constructor stub
	this.paymentid=payment_id;
	this.PaymentDate=Payment_Date;
	this.Amount=amount;
	this.Narration=narration;
}

public PowerPayments() {
	// TODO Auto-generated constructor stub
}


public PowerPayments( int paymentid,
		Date paymentDate, double amount, String narration) {	
	this.paymentid = paymentid;
	PaymentDate = paymentDate;
	Amount = amount;
	Narration = narration;
}

/**
 * @return the paymentid
 */
public int getPaymentid() {
	return paymentid;
}

/**
 * @param paymentid the paymentid to set
 */
public void setPaymentid(int paymentid) {
	this.paymentid = paymentid;
}

public Date getPaymentDate() {
	return PaymentDate;
}
public void setPaymentDate(Date paymentDate) {
	PaymentDate = paymentDate;
}
public double getAmount() {
	return Amount;
}
public void setAmount(double amount) {
	Amount = amount;
}
public String getNarration() {
	return Narration;
}
public void setNarration(String narration) {
	Narration = narration;
}


}

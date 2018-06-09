package meters.model;

import java.util.Date;

public class UmemePayments {
int _id;
Date payment_date;
double amount;
String ref;
public UmemePayments() {
	
}
public UmemePayments(Date payment_date, double amount, String ref) {
	
	this.payment_date = payment_date;
	this.amount = amount;
	this.ref = ref;
}
public UmemePayments(int _id, Date payment_date, double amount, String ref) {
	super();
	this._id = _id;
	this.payment_date = payment_date;
	this.amount = amount;
	this.ref = ref;
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
 * @return the ref
 */
public String getRef() {
	return ref;
}
/**
 * @param ref the ref to set
 */
public void setRef(String ref) {
	this.ref = ref;
}

}

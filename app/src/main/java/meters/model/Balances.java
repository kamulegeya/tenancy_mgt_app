package meters.model;

public class Balances extends TenantList {
double total_bill;
double total_payment;
double amount_due;

	public Balances() {
		
	}
	

	public Balances(int _id, String firstName, String surname, String fullName,String email,
			String mobile, String mobile2 ,double tbill,double tpayment,double amount) {
		super(_id, firstName, surname, fullName, mobile, mobile2,email);
		// TODO Auto-generated constructor stub
		
		this.total_bill=tbill;
		this.total_payment=tpayment;
		this.amount_due=amount;
	}



	/**
	 * @return the total_bill
	 */
	public double getTotal_bill() {
		return total_bill;
	}

	/**
	 * @param total_bill the total_bill to set
	 */
	public void setTotal_bill(double total_bill) {
		this.total_bill = total_bill;
	}

	/**
	 * @return the total_payment
	 */
	public double getTotal_payment() {
		return total_payment;
	}

	/**
	 * @param total_payment the total_payment to set
	 */
	public void setTotal_payment(double total_payment) {
		this.total_payment = total_payment;
	}

	/**
	 * @return the amount_due
	 */
	public double getAmount_due() {
		return amount_due;
	}

	/**
	 * @param amount_due the amount_due to set
	 */
	public void setAmount_due(double amount_due) {
		this.amount_due = amount_due;
	}

}

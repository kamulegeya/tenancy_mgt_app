package meters.model;

import java.util.Date;

public class ApartmentPayments extends RentalPayments {
private String number;
private int apartmentid;
	public ApartmentPayments(int _id, String firstName, String surname,
			String fullName, String mobile, String mobile2, String email,
			int payment_id, int invoice_id, Date payment_date, double amount,
			String narration, String p,String number,int id) {
		super(_id, firstName, surname, fullName, mobile, mobile2, email, payment_id,
				invoice_id, payment_date, amount, narration, p);
		// TODO Auto-generated constructor stub
		this.number=number;
		this.apartmentid=id;
	}

	public ApartmentPayments()
	{
		
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public int getApartmentid() {
		return apartmentid;
	}

	public void setApartmentid(int apartmentid) {
		this.apartmentid = apartmentid;
	}
	
	
}

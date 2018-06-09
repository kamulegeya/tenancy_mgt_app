package meters.model;

import java.util.Date;

public class VReadings {
	int _id;
	String meter_number;
	Date ReadingDate;
	double Reading;
	double amount;
	double Rate;
	
	public VReadings() {
		// TODO Auto-generated constructor stub
	}
	public VReadings(int _id, String meter_number, Date readingDate,
			double reading, double rate,double a) {		
		this._id = _id;
		this.meter_number = meter_number;
		ReadingDate = readingDate;
		Reading = reading;
		Rate = rate;
		amount=a;
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
	 * @return the meter_number
	 */
	public String getMeter_number() {
		return meter_number;
	}
	/**
	 * @param meter_number the meter_number to set
	 */
	public void setMeter_number(String meter_number) {
		this.meter_number = meter_number;
	}
	/**
	 * @return the readingDate
	 */
	public Date getReadingDate() {
		return ReadingDate;
	}
	/**
	 * @param readingDate the readingDate to set
	 */
	public void setReadingDate(Date readingDate) {
		ReadingDate = readingDate;
	}
	/**
	 * @return the reading
	 */
	public double getReading() {
		return Reading;
	}
	/**
	 * @param reading the reading to set
	 */
	public void setReading(double reading) {
		Reading = reading;
	}
	/**
	 * @return the rate
	 */
	public double getRate() {
		return Rate;
	}
	/**
	 * @param rate the rate to set
	 */
	public void setRate(double rate) {
		Rate = rate;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}

}

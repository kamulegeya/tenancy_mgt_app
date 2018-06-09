package meters.model;

import java.util.Date;

public class VMeterReadings {
	// tenant name
private int _id;
private String Tenant;
private Date ReadingDate;
private  double currentReading;
private Date PreviousReadingDate;
private  double PreviousMeterReading;
private double  Units;
private double Rate;
private double Amount;
public VMeterReadings() {
	super();
}
/**
 * @param Tenant name
 * @param ReadingDate
 * @param CurrentReading
 * @param PreviousReadingDate
 * @param PreviousMeterReading
 * @param Units
 * @param Rate
 * @param Amount
 */
public VMeterReadings(int id,String tenant, Date readingDate, double currentReading,
		Date previousReadingDate, double previousMeterReading, double units,
		double rate, double amount) {
	super();
	_id=id;
	Tenant = tenant;
	ReadingDate = readingDate;
	this.currentReading = currentReading;
	PreviousReadingDate = previousReadingDate;
	PreviousMeterReading = previousMeterReading;
	Units = units;
	Rate = rate;
	Amount = amount;
}

public VMeterReadings( Date readingDate, double currentReading,
		Date previousReadingDate, double previousMeterReading, double units,
		double rate, double amount) {
	super();	
		ReadingDate = readingDate;
	this.currentReading = currentReading;
	PreviousReadingDate = previousReadingDate;
	PreviousMeterReading = previousMeterReading;
	Units = units;
	Rate = rate;
	Amount = amount;
}

public String getTenant() {
	return Tenant;
}
public void setTenant(String tenant) {
	Tenant = tenant;
}
public Date getReadingDate() {
	return ReadingDate;
}
public void setReadingDate(Date readingDate) {
	ReadingDate = readingDate;
}
public double getCurrentReading() {
	return currentReading;
}
public void setCurrentReading(double currentReading) {
	this.currentReading = currentReading;
}
public Date getPreviousReadingDate() {
	return PreviousReadingDate;
}
public void setPreviousReadingDate(Date previousReadingDate) {
	PreviousReadingDate = previousReadingDate;
}
public double getPreviousMeterReading() {
	return PreviousMeterReading;
}
public void setPreviousMeterReading(double previousMeterReading) {
	PreviousMeterReading = previousMeterReading;
}
public double getUnits() {
	return Units;
}
public void setUnits(double units) {
	Units = units;
}
public double getRate() {
	return Rate;
}
public void setRate(double rate) {
	Rate = rate;
}
public double getAmount() {
	return Amount;
}
public void setAmount(double amount) {
	Amount = amount;
}
public int get_id() {
	return _id;
}
public void set_id(int _id) {
	this._id = _id;
}


}

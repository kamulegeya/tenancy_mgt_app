package meters.model;

import java.util.Date;

public class MeterReading {
	int _id;
	int TenantMeter_id;
	Date ReadingDate;
	double Reading;
	double Rate;
	/**
	 * 
	 */
	public MeterReading() {
		super();
	}
	
	/**
	 * @param _id the _id to set
	 */
	

	public MeterReading(int _id, int tenantMeter_id, Date readingDate,
			double reading, double rate) {
		super();
		this._id = _id;
		TenantMeter_id = tenantMeter_id;
		ReadingDate = readingDate;
		Reading = reading;
		Rate = rate;
	}
	

	
	public int getTenantMeter_id() {
		return TenantMeter_id;
	}
	public void setTenantMeter_id(int tenantMeter_id) {
		TenantMeter_id = tenantMeter_id;
	}
	public Date getReadingDate() {
		return ReadingDate;
	}
	public void setReadingDate(Date readingDate) {
		ReadingDate = readingDate;
	}
	public double getReading() {
		return Reading;
	}
	public void setReading(double reading) {
		Reading = reading;
	}
	public double getRate() {
		return Rate;
	}
	public void setRate(double rate) {
		Rate = rate;
	}
	public int get_id() {
		return _id;
	}	
	public void set_id(int _id) {
		this._id = _id;
	}

	public MeterReading(int tenantMeter_id, Date readingDate, double reading,
			double rate) {	
		TenantMeter_id = tenantMeter_id;
		ReadingDate = readingDate;
		Reading = reading;
		Rate = rate;
	}
}

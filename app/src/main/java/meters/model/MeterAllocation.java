package meters.model;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class MeterAllocation extends TenantMeterID implements Serializable {
private String meter_number;
private Date start_date;
private double reading;
private double rate;
private int reading_id;
private Date enddate;
private Date endreadingdate;
private double endreading;
	public MeterAllocation() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the rate
	 */
	public double getRate() {
		return rate;
	}

	/**
	 * @param rate the rate to set
	 */
	public void setRate(double rate) {
		this.rate = rate;
	}

	public MeterAllocation(int _id, String firstName, String surname,Date edate,double ereading,Date endrd,
			String fullName, String m_number, Date s_Date, double _reading,double _rate,int readingid) {
		super(_id, firstName, surname, fullName);
		// TODO Auto-generated constructor stub
		this.meter_number=m_number;
		this.start_date=s_Date;
		this.reading=_reading;
		this.rate=_rate;
		this.reading_id=readingid;
		this.enddate=edate;
		this.endreading=ereading;
		this.endreadingdate=endrd;
	}
	public String getmeternumber() {
		return meter_number;
	}
	/**
	 * @param surname the surname to set
	 */
	public void setmeternumber(String m) {
		meter_number=m;
	}
	/**
	 * @return the reading
	 */
	public double getReading() {
		return reading;
	}

	/**
	 * @param reading the reading to set
	 */
	public void setReading(double reading) {
		this.reading = reading;
	}

	public Date getStartDate() {
		return start_date;
	}
	/**
	 * @param surname the surname to set
	 */
	public void setStartDate(Date sd) {
		start_date=sd;
	}

	public int getReading_id() {
		return reading_id;
	}

	public void setReading_id(int reading_id) {
		this.reading_id = reading_id;
	}

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public double getEndreading() {
		return endreading;
	}

	public void setEndreading(double endreading) {
		this.endreading = endreading;
	}

	public Date getEndreadingdate() {
		return endreadingdate;
	}

	public void setEndreadingdate(Date endreadingdate) {
		this.endreadingdate = endreadingdate;
	}
	
}

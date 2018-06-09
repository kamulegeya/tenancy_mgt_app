package meters.model;

import java.util.Date;

public class TenantMeters {
	int _id;
	int Tenant_id;
	int Meter_id;
	Date StartDate;
	Date EndDate;
	/**
	 * @param tenant_id
	 * @param meter_id
	 * @param startDate
	 */
	public TenantMeters(int tenant_id, int meter_id, Date startDate) {
		super();
		Tenant_id = tenant_id;
		Meter_id = meter_id;
		StartDate = startDate;
	}

	Boolean InActive;	
	
public int getTenant_id() {
		return Tenant_id;
	}

	public void setTenant_id(int tenant_id) {
		Tenant_id = tenant_id;
	}

	public int getMeter_id() {
		return Meter_id;
	}

	public void setMeter_id(int meter_id) {
		Meter_id = meter_id;
	}

	public Date getStartDate() {
		return StartDate;
	}

	public void setStartDate(Date startDate) {
		StartDate = startDate;
	}

	public Date getEndDate() {
		return EndDate;
	}

	public void setEndDate(Date endDate) {
		EndDate = endDate;
	}

	public Boolean getInActive() {
		return InActive;
	}

	public void setInActive(Boolean inActive) {
		InActive = inActive;
	}

	public int get_id() {
		return _id;
	}

public TenantMeters(int _id, int tenant_id, int meter_id, Date startDate,
			Date endDate, Boolean inActive) {
		super();
		this._id = _id;
		Tenant_id = tenant_id;
		Meter_id = meter_id;
		StartDate = startDate;
		EndDate = endDate;
		InActive = inActive;
	}

}

package meters.model;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class Tenancy extends ActiveTenancy implements Serializable {

private boolean inactive;
public Tenancy() {
	
}

public Tenancy(int _id, String firstName, String surname, String email,
		String fullName, String mobile, String mobile2, Date sDate, Date eDate,
		double rental, String apartment1, int tenancyid, int roomid, String b,
		String l,boolean a,Date last) {
	super(_id, firstName, surname, email, fullName, mobile, mobile2, sDate, eDate,
			rental, apartment1, tenancyid, roomid, b, l,last);
	// TODO Auto-generated constructor stub
	this.inactive=a;
}


/**
 * @return the inactive
 */
public boolean isInactive() {
	return inactive;
}
/**
 * @param inactive the inactive to set
 */
public void setInactive(boolean inactive) {
	this.inactive = inactive;
}

}

package meter.manager.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import meters.model.ActiveTenancy;
import meters.model.AnnualTotals;
import meters.model.ApartmentBlocks;
import meters.model.ApartmentInvoices;
import meters.model.ApartmentPayments;
import meters.model.Apartments;
import meters.model.Balances;
import meters.model.CurrentTenants;
import meters.model.Locations;
import meters.model.MeterAllocation;
import meters.model.MeterReading;
import meters.model.Meters;
import meters.model.PowerPayments;
import meters.model.RentalPaymentStatus;
import meters.model.RoomInvoicingStatus;
import meters.model.SearchResults;
import meters.model.TenantList;
import meters.model.TenantMeterID;
import meters.model.TenantMeters;
import meters.model.Tenants;
import meters.model.UnPaidInvoices;
import meters.model.VMeterReadings;
import meters.model.RentalPayments;
import meters.model.VReadings;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DatabaseHelperClass extends SQLiteAssetHelper {
// table names
private static final String TABLE_Tenants="Tenants";
private static final String TABLE_METERS="Meters";
private static final String TABLE_TENANT_METERs="TenantMeters";
private static final String TABLE_METERREADING="MeterReading";
private static final String TABLE_PAYMENTS="PowerPayments";
// view names
private static final String  VIEW_DETAILEDREADING="vDetailedReading";
private static final String  VIEWEDITMETERREADING="vEditMeterReading";
// Common field
private static final String  KEY_ID="_id";
private static final String  DATEADDED="DateAdded";
private static final String  TENANT_ID="Tenant_id";
private static final String  INACTIVE="InActive";
private static final String TENANT="Tenant";
// Tenants table
private static final String  FIRSTNAME="FirstName";
private static final String  SURTNAME="SurName";
private static final String  OTHERNAME="OtherName";
private static final String  MOBILE1="Mobile1";
private static final String  MOBILE2="Mobile2";
private static final String  EMAIL="email";

private static final String  DATEINACTIVE="DateInActive";
//Meters
private static final String  SERIALNUMBER="SerialNumber";
private static final String  METERNUMBER="MeterNumber";
// TenantMeters
private static final String  METER_ID="Meter_id";
private static final String  STARTDATE="StartDate";
private static final String  ENDDATE="EndDate";
// meter reading
private static final String  TENANTMETER_ID="TenantMeter_id";
private static final String  READINGDATE="ReadingDate";
// meter reading units
private static final String  READING="Reading";
private static final String  RATE="Rate";
// power payment
private static final String  PAYMENTDATE="PaymentDate";
private static final String  AMOUNT="Amount";
private static final String  NARRATION="Narration";

// meter reading list
private static final String  CurrentREADING="CurrentReading";
private static final String  READINGDATEVIEW="ReadingDate";
private static final String  PrevREADINGDATE="PrevReadingDate";
private static final String  prevREADING="PrevMeterReading";
//calculated values
private static final String  UNITs="Units";
// searching
private static final String  TABLE_NAME_VIRTUAL="searchtable";
private static final String  FNAME="F";
private static final String  SNAME="S";
private static final String  SDATE="Date";
private static final String  SAMOUNT="Amount";
private static final String  STYPE="Type";
private static final String  QUERY= "vSearchAmounts";


@SuppressLint("SimpleDateFormat")
SimpleDateFormat fm =new SimpleDateFormat("yyyy-MM-dd");

// database helper variables

private  static   String DATABASENAME="meterapp.sqlite";	
private static final int DATABASEVERSION=8;

private static DatabaseHelperClass sInstance;
private SQLiteDatabase db = null;
public static DatabaseHelperClass getInstance(Context context) {
	    
	    if (sInstance == null) {
	      sInstance = new  DatabaseHelperClass(context.getApplicationContext(),DATABASENAME,DATABASEVERSION);
	    }
	    return sInstance;
	  }

	private  DatabaseHelperClass(final Context context,final String name,final int version) {
		super(context,DATABASENAME,null, DATABASEVERSION);
		this.setForcedUpgrade();
			
		
				
		
	}
	@Override
   public synchronized void close() {
       if (sInstance != null)
           db.close();
   }
		

public String getDateTime() {
    SimpleDateFormat dateFormat = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.getDefault());
    Date date = new Date();
    return dateFormat.format(date);
}
public long AddTenant(Tenants tenant){
	SQLiteDatabase db=this.getWritableDatabase();
	ContentValues values = new ContentValues();
	values.put(FIRSTNAME,tenant.getFirstName().toString());
	values.put(SURTNAME, tenant.getSurName().toString());
	values.put(OTHERNAME, tenant.getOtherNames().toString());
	values.put(MOBILE1, tenant.getMobile1().toString());
	values.put(MOBILE2, tenant.getMobile2().toString());
	values.put(EMAIL, tenant.getEmail());
	long tenant_id =db.insert(TABLE_Tenants, null, values);
	//db.close();
	return tenant_id;
}

public long AddMeter(Meters meter){
	SQLiteDatabase db=this.getWritableDatabase();
	ContentValues values = new ContentValues();
	values.put(SERIALNUMBER, meter.getSerialNumber().toString());
	values.put(METERNUMBER, meter.getMeterNumber().toString());
	values.put(DATEADDED, getDateTime());
	long meterid=db.insert(TABLE_METERS, null, values);
	//db.close();
	return meterid;
	
	
}

public long EditMeter(Meters meter)
{
	SQLiteDatabase db=this.getWritableDatabase();
	ContentValues values = new ContentValues();
	values.put(SERIALNUMBER, meter.getSerialNumber().toString());
	values.put(METERNUMBER, meter.getMeterNumber().toString());		
	return db.update(TABLE_METERS, values, KEY_ID+"=?", new String[]{String.valueOf(meter.get_id())});
	
}
public void meterInActive(int meterid,String mydate)
{
	SQLiteDatabase db=this.getWritableDatabase();
	String sql= " update Meters Set "+
	            " DateInActive "+ "='"+ mydate + "'"+","+
			    " InActive"+ "= 1"+
	            " Where _id"+ "="+ meterid;
	String sql1= " update TenantMeters Set "+
				" EndDate "+ "='"+ mydate + "'"+","+
				" InActive"+ "= 1"+
				" Where Meter_id"+ "="+ meterid;
	
	try{
		db.beginTransaction();
		db.execSQL(sql);
		db.execSQL(sql1);	
		
		db.setTransactionSuccessful();
		
		} catch( Exception e)
		{
			e.printStackTrace();
			
			
		}
		finally{
			db.endTransaction();
		}
	
}

public long AllocateMeter(TenantMeters tenantmeters)

{
	//allocate meter for a tenant without one	
 
    
    SQLiteDatabase db=this.getWritableDatabase();
	ContentValues values = new ContentValues();
	values.put(TENANT_ID, tenantmeters.getTenant_id());
	values.put(METER_ID, tenantmeters.getMeter_id());
	values.put(STARTDATE, fm.format(tenantmeters.getStartDate()));
	long tenantmeterid=db.insert(TABLE_TENANT_METERs, null, values);	
	return tenantmeterid;
	
	
	
	
}

public boolean HasMeter(int id)
{
	// get a recordset aka cursor...if some records....return true
	
	// for testing whether there is a record which is true
		String selectQuery = "Select * From " + TABLE_TENANT_METERs + " Where " + TENANT_ID+ "=?"+" And " + INACTIVE +"=0";
		// ...
		String[] args = new String[] { Integer.toString(id) };
		Log.e("LOG", selectQuery);	    
	    SQLiteDatabase db = this.getReadableDatabase();
	    Cursor c = db.rawQuery(selectQuery, args);
	    
	    if(c.moveToFirst()){
	    	c.close();
		return true;
	    }
	    else
	    {
	    	c.close();
	    	return false;
	    }
	
}

public boolean CanAllocateMeter(int meterid,int tenantid)
{
	// get a recordset aka cursor...if some records....return true
	
	// for testing whether there is a record which is true
		String selectQuery = "Select * From " + TABLE_TENANT_METERs + " Where " + METER_ID+ "=?"+" And " + INACTIVE +"=0" + " and  " + TENANT_ID+ "=?";
		// ...
		String[] args = new String[] { Integer.toString(meterid),Integer.toString(tenantid) };
		//Log.e("Allocation query", selectQuery);	    
	    SQLiteDatabase db = this.getReadableDatabase();
	    Cursor c = db.rawQuery(selectQuery, args);
	    if(c.moveToFirst()){
	    	c.close();
		return false;
	    }
	    else
	    {
	    	c.close();
	    	return true;
	    }
}
public boolean canRellocateMeter(int meterid,int tenantid,int tenant_meterid)
{
	// get a recordset aka cursor...if some records....return true
	
	// for testing whether there is a record which is true
		String selectQuery = "Select * From " + TABLE_TENANT_METERs + " Where " + METER_ID+ "=?"+" And " + INACTIVE +"=0" + " and " + TENANT_ID+ "=?"+
	                         " and " + KEY_ID + "<>"+ tenant_meterid;
		// ...
		String[] args = new String[] { Integer.toString(meterid),Integer.toString(tenantid) };
		Log.e("Allocation query", selectQuery);	    
	    SQLiteDatabase db = this.getReadableDatabase();
	    Cursor c = db.rawQuery(selectQuery, args);
	    if(c.moveToFirst()){
	    	c.close();
		return false;
	    }
	    else
	    {
	    	c.close();
	    	return true;
	    }
}
public long SetTenantInActive(int t_id,String dateinactive)
{
	//set tenant inactive. Also make da meter inactive for that tenant
	final int inactivevalue=1;
	SQLiteDatabase db=this.getWritableDatabase();	
	String SQL= " update " + TABLE_Tenants + " Set " +  DATEINACTIVE + "='"+ dateinactive +"'"+","+ INACTIVE +"="+inactivevalue +" Where "+ KEY_ID+"="+ t_id;
	String SQL1=" update " +TABLE_TENANT_METERs + " Set " + ENDDATE + "='"+ dateinactive +"'"+","+ INACTIVE +"="+inactivevalue +" Where "+ TENANT_ID+"="+ t_id;
	String SQL2 =" update Tenancy set enddate"+ "='" +  dateinactive + "' " + "," + "InActive"+ "= 1 " + " Where tenant_id " + " =" + t_id;
	
	try{
	db.beginTransaction();
	db.execSQL(SQL);
	db.execSQL(SQL1);	
	db.execSQL(SQL2);	
	
	db.setTransactionSuccessful();
	return 1;
	} catch( Exception e)
	{
		Log.e("update fail", e.toString());		
		return 0;
	}
	finally{
		db.endTransaction();
	}
	
}
public long EditTenantRecords(Tenants tenant)
{
	SQLiteDatabase db=this.getWritableDatabase();
	ContentValues values = new ContentValues();
	values.put(FIRSTNAME, tenant.getFirstName());
	values.put(SURTNAME, tenant.getSurName());
	values.put(OTHERNAME, tenant.getOtherNames());
	values.put(MOBILE1, tenant.getMobile1());
	values.put(MOBILE2, tenant.getMobile2());	
	values.put(EMAIL, tenant.getEmail());
	return db.update(TABLE_Tenants, values, KEY_ID+"=?", new String[]{String.valueOf(tenant.get_id())});
	
}
public long EditPayment(PowerPayments payment)
{
	SQLiteDatabase db=this.getWritableDatabase();
	ContentValues values = new ContentValues();
	values.put(PAYMENTDATE,fm.format( payment.getPaymentDate()));
	values.put(TENANT_ID, payment.get_id());
	values.put(AMOUNT, payment.getAmount());
	values.put(NARRATION, payment.getNarration());
	return db.update(TABLE_METERREADING, values, KEY_ID+"=?", new String[]{String.valueOf(payment.get_id())});
	
}


public long editMeterReading(MeterReading metereading)
{
	SQLiteDatabase db=this.getWritableDatabase();
	ContentValues values = new ContentValues();	
	values.put(READINGDATE, fm.format(metereading.getReadingDate()));
	values.put(READING, metereading.getReading());
	values.put(RATE, metereading.getRate());
	
	return  db.update(TABLE_METERREADING, values,KEY_ID+"=?", new String[]{String.valueOf(metereading.get_id())});
	
}
public long AddMeterReading(MeterReading reading){
	SQLiteDatabase db=this.getWritableDatabase();
	ContentValues values = new ContentValues();
	values.put(TENANTMETER_ID,reading.getTenantMeter_id());
	values.put(READINGDATE, fm.format( reading.getReadingDate()));
	values.put(READING, reading.getReading());
	values.put(RATE, reading.getRate());	
	long reading_id =db.insert(TABLE_METERREADING, null, values);
	//db.close();
	return reading_id;
}

public long ReceivePayment(PowerPayments payments)
{
	SQLiteDatabase db=this.getWritableDatabase();
	ContentValues values = new ContentValues();
	values.put(TENANT_ID, payments.get_id());
	values.put(PAYMENTDATE, fm.format(payments.getPaymentDate()));
	values.put(AMOUNT, payments.getAmount());
	values.put(NARRATION, payments.getNarration());
	long paymentid=db.insert(TABLE_PAYMENTS, null, values);
	//db.close();
	return paymentid;
}

public Tenants GetTenant(int id )

{
	// get individual tenant
	SQLiteDatabase db=this.getReadableDatabase();
	String selectqry ="select * from "+ TABLE_Tenants + " where "
			         +KEY_ID+ "=" +id;
	//Log.e("log",selectqry);
	Cursor c=db.rawQuery(selectqry, null);
	if(c !=null)
	{
		c.moveToFirst();
		Tenants t =new Tenants();		
		t.set_id(c.getInt(c.getColumnIndex(KEY_ID)));
		t.setFirstName(c.getString(c.getColumnIndex(FIRSTNAME)));	
		t.setSurName(c.getString(c.getColumnIndex(SURTNAME)));
		t.setOtherNames(c.getString(c.getColumnIndex(OTHERNAME)));
		t.setEmail(c.getString(c.getColumnIndex(EMAIL)));
		t.setMobile1(c.getString(c.getColumnIndex(MOBILE1)));
		t.setMobile2(c.getString(c.getColumnIndex(MOBILE2)));
		try {
			t.setDateAdded(fm.parse((c.getString(c.getColumnIndex(DATEADDED)))));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		t.setInActive(Boolean.parseBoolean((c.getString(c.getColumnIndex(INACTIVE)))));
		try {
			t.setDateInActive(fm.parse((c.getString(c.getColumnIndex(DATEINACTIVE)))));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		c.close();
		//db.close();
		return t;
	}
	
	else
	{
		//c.close();
		return null;
	}
	
	
	
	
}
@SuppressLint("SimpleDateFormat")
public List<Tenants> GetAllTenants(){
	 List<Tenants> tenants = new ArrayList<Tenants>();
	 // list of tenants 
	    String selectQuery = "SELECT  * FROM " +TABLE_Tenants;
	    //Log.e("LOG", selectQuery);	  	    
	    SQLiteDatabase db = this.getReadableDatabase();    
	    		    
	    Cursor c = db.rawQuery(selectQuery, null);
	    SimpleDateFormat fm =new SimpleDateFormat("yyyy-MM-dd");	 
	    try{
	 if(c.moveToFirst()){
		do{
			Tenants t =new Tenants();
			t.set_id(Integer.parseInt(c.getString(c.getColumnIndex(KEY_ID))));
			t.setFirstName(c.getString(c.getColumnIndex(FIRSTNAME)));
			t.setSurName(c.getString(c.getColumnIndex(SURTNAME)));			
				if(c.getString(c.getColumnIndex(OTHERNAME))==null)
				{
					t.setOtherNames("Not Given");
				}
				else
				{
					t.setOtherNames(c.getString(c.getColumnIndex(OTHERNAME)));
				}
			
			if(c.getString(c.getColumnIndex(MOBILE1))==null)
			{
				t.setMobile1("0");
			}
			else
			{
			t.setMobile1(c.getString(c.getColumnIndex(MOBILE1)));
			}
			
			if(c.getString(c.getColumnIndex(MOBILE2))==null)
			{
				t.setMobile2("0");
			}
			else
			{
			t.setMobile2(c.getString(c.getColumnIndex(MOBILE2)));
			}
			
			
			try {
				t.setDateAdded(fm.parse(c.getString(c.getColumnIndex(DATEADDED))));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			t.setInActive(Boolean.parseBoolean(c.getString(c.getColumnIndex(INACTIVE))));
			
			
			
			try {
				
				if(c.getString(c.getColumnIndex(DATEINACTIVE))!=null)
				{
					
				t.setDateInActive(fm.parse(c.getString(c.getColumnIndex(DATEINACTIVE))));
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			t.setEmail(c.getString(c.getColumnIndex(EMAIL)));
			//String test= t.getFirstName().toString();
			//Log.e("FirstName", test);
			tenants.add(t);
		} while(c.moveToNext());
		}
	 
	    }catch(Exception e)
	    {
	    	Log.e("TenantLoading err", e.toString());
	    }
	    
		 c.close();
		// db.close();
		return tenants;
}


public List<TenantMeterID> GetTenantMeterIds()

{
	// get a list of tenants and allocated meters
	List<TenantMeterID> meterlist = new ArrayList<TenantMeterID>();
	String selectQuery = " Select TenantMeters._id,Tenants.FirstName , Tenants.SurName  "+
                         " from Tenants" +
                          " Inner Join TenantMeters on TenantMeters.Tenant_id=Tenants._id" ;
             
    Log.e("LOG", selectQuery);	    
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor c = db.rawQuery(selectQuery, null);
    if(c.moveToFirst()){
		do{
			TenantMeterID t = new TenantMeterID();
			t.set_id(Integer.parseInt(c.getString(c.getColumnIndex(KEY_ID))));
			t.setFirstName(c.getString(c.getColumnIndex("Tenants.FirstName")));
			t.setSurname(c.getString(c.getColumnIndex("Tenants.SurName")));
			t.setFullName(c.getString(c.getColumnIndex("Tenants.FirstName")),c.getString(c.getColumnIndex("Tenants.SurName")));
			meterlist.add(t);
		} while(c.moveToNext());


		
    }
    c.close();
    //db.close();
    return meterlist;
}

public List<VMeterReadings> GetDetailedReading(){
	//get all metereadings
	 List<VMeterReadings> tenants = new ArrayList<VMeterReadings>();
	    String selectQuery = " SELECT "  + KEY_ID +"," + TENANT + "," + READINGDATEVIEW + "," + CurrentREADING + ","+ PrevREADINGDATE +","+ prevREADING +","+ UNITs+ ","+ RATE + ","+ AMOUNT + " FROM "+ VIEW_DETAILEDREADING;
	    	    
	    SQLiteDatabase db = this.getReadableDatabase();	    
	    Cursor c = db.rawQuery(selectQuery, null);
	 if(c.moveToFirst()){
		
		do{
			VMeterReadings t =new VMeterReadings();	
			t.set_id(c.getInt(0));
			t.setTenant(c.getString(c.getColumnIndex(TENANT)));		
			try {
				t.setReadingDate(fm.parse(c.getString(c.getColumnIndex(READINGDATE))));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				Log.e("parsing Reading date",e.toString());
			}
			t.setCurrentReading(c.getDouble(c.getColumnIndex(CurrentREADING)));
			try {
				t.setPreviousReadingDate(fm.parse(c.getString(c.getColumnIndex(PrevREADINGDATE))));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				Log.e("parsing current reading",e.toString());
			}
			t.setPreviousMeterReading(c.getDouble(c.getColumnIndex(prevREADING)));
			t.setUnits(c.getDouble(c.getColumnIndex(UNITs)));
			t.setRate(c.getDouble(c.getColumnIndex(RATE)));
			t.setAmount(c.getDouble(c.getColumnIndex(AMOUNT)));
			tenants.add(t);
		} while(c.moveToNext());
		}
	 c.close();
	// db.close();
	 return tenants;
}
public List<TenantList> GetTenantIDs(){
	List<TenantList> tenants = new ArrayList<TenantList>();
	// get Tenant ids and fullname
	    String selectQuery = "Select Tenants._id as ID,Tenants.email as Email, Tenants.FirstName as F, Tenants.SurName as S, Tenants.Mobile1 as M, Tenants.Mobile2 as M2 from Tenants";
	   // Log.e("LOG In Open Db", selectQuery);	 
	    
	    SQLiteDatabase db = this.getReadableDatabase();
	    Cursor c = db.rawQuery(selectQuery, null);
	 if(c.moveToFirst()){
		do{
			TenantList t =new TenantList();
			t.set_id(Integer.parseInt(c.getString(c.getColumnIndex("ID"))));
			
			if(c.getString(c.getColumnIndex("F"))==null)
			{
				 t.setFirstName("");
					}else{			 
				 					 
			t.setFirstName(c.getString(c.getColumnIndex("F")));
				 }
				
			if(c.getString(c.getColumnIndex("S"))==null)
			{
		           t.setSurname("");
			  }
		      else
		             {
			 
	            t.setSurname(c.getString(c.getColumnIndex("S")));
		               }
			if(c.getString(c.getColumnIndex("Email"))==null)
			{
		           t.setEmail("");
			  }
		      else
		             {
			 
	            t.setEmail(c.getString(c.getColumnIndex("Email")));
		               }
			
			
			t.setFullName(t.getFirstName(),t.getSurname());
			if (c.getString(c.getColumnIndex("M"))==null){
				t.setMobile("0");
		    } 
			else
			{
				t.setMobile(c.getString(c.getColumnIndex("M")));
			}
			if (c.getString(c.getColumnIndex("M2"))==null){
				t.setMobile2("0");
		    } 
			else
			{
				t.setMobile2(c.getString(c.getColumnIndex("M2")));
			}
			
			
			
			tenants.add(t);
		} while(c.moveToNext());
		}
	 c.close();
	 //db.close();
	 
	 return tenants;
}

public List<VMeterReadings> GetIndividualReading(int id){
	 List<VMeterReadings> individual = new ArrayList<VMeterReadings>();
	 // list meter reading for a particular Tenant.
	    String selectQuery = " SELECT " + TENANT + "," + READINGDATEVIEW + "," + CurrentREADING + ","+ PrevREADINGDATE +","+ prevREADING +","+ UNITs+ ","+ RATE + ","+ AMOUNT + " FROM "
	                       + VIEW_DETAILEDREADING + " Where " + KEY_ID + " = "+id + "  Order by " + READINGDATE + " DESC ";
	    	 //Log.e("Reading", selectQuery) ;  
	    SQLiteDatabase db = this.getReadableDatabase();	    
	    Cursor c = db.rawQuery(selectQuery, null);
	 if(c.moveToFirst()){
		
		do{
			VMeterReadings t =new VMeterReadings();					
			try {
				t.setReadingDate(fm.parse(c.getString(c.getColumnIndex(READINGDATE))));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				Log.e("parsing Reading date",e.toString());
			}
			t.setTenant(c.getString(c.getColumnIndex(TENANT)));
			t.setCurrentReading(c.getDouble(c.getColumnIndex(CurrentREADING)));
			try {
				t.setPreviousReadingDate(fm.parse(c.getString(c.getColumnIndex(PrevREADINGDATE))));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				Log.e("parsing current reading",e.toString());
			}
			t.setPreviousMeterReading(c.getDouble(c.getColumnIndex(prevREADING)));
			t.setUnits(c.getDouble(c.getColumnIndex(UNITs)));
			t.setRate(c.getDouble(c.getColumnIndex(RATE)));
			t.setAmount(c.getDouble(c.getColumnIndex(AMOUNT)));
			individual.add(t);
		} while(c.moveToNext());
		}
	 c.close();
	 //db.close();
	 return individual;
}
public List<VMeterReadings> getDateReading(String date){
	 List<VMeterReadings> individual = new ArrayList<VMeterReadings>();
	 // list meter reading for a particular Tenant.
	    String selectQuery = " SELECT " + TENANT + "," + READINGDATEVIEW + "," + CurrentREADING + ","+ PrevREADINGDATE +","+ prevREADING +","+ UNITs+ ","+ RATE + ","+ AMOUNT + " FROM "
	                       + VIEW_DETAILEDREADING + " Where " +  READINGDATE+ "=" + date;
	    	 Log.e("Reading", selectQuery) ;
	    //String [] args= new String[]{date};
	    SQLiteDatabase db = this.getReadableDatabase();	    
	    Cursor c = db.rawQuery(selectQuery, null);
	 if(c.moveToFirst()){
		
		do{
			VMeterReadings t =new VMeterReadings();					
			try {
				t.setReadingDate(fm.parse(c.getString(c.getColumnIndex(READINGDATE))));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				Log.e("parsing Reading date",e.toString());
			}
			t.setTenant(c.getString(c.getColumnIndex(TENANT)));
			t.setCurrentReading(c.getDouble(c.getColumnIndex(CurrentREADING)));
			try {
				t.setPreviousReadingDate(fm.parse(c.getString(c.getColumnIndex(PrevREADINGDATE))));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				Log.e("parsing current reading",e.toString());
			}
			t.setTenant(c.getString(c.getColumnIndex(TENANT)));
			t.setPreviousMeterReading(c.getDouble(c.getColumnIndex(prevREADING)));
			t.setUnits(c.getDouble(c.getColumnIndex(UNITs)));
			t.setRate(c.getDouble(c.getColumnIndex(RATE)));
			t.setAmount(c.getDouble(c.getColumnIndex(AMOUNT)));
			individual.add(t);
		} while(c.moveToNext());
		}
	 c.close();
	 //db.close();
	 return individual;
}

public boolean ReadingMade(int id, String readingdate)
{
	// for testing whether a reading was made for a date and meter
	String selectQuery = "Select * From " + TABLE_METERREADING + " Where " +TENANTMETER_ID+ "=?" + " And " + READINGDATE +"=?";
	// ...
	String[] args = new String[] { Integer.toString(id), readingdate };
	Log.e("LOG", selectQuery);	    
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor c = db.rawQuery(selectQuery, args);
    
    if(c.moveToFirst()){
    	c.close();
	return true;
    }
    else
    {
    	c.close();
    	return false;
    }
	
}
public Tenants GetEditTenants(int id )

{
	// get individual tenant
	SQLiteDatabase db=this.getReadableDatabase();
	String selectqry ="select * from "+ TABLE_Tenants + " where "
			         +KEY_ID+ "=" +id;
	//Log.e("log",selectqry);
	Cursor c=db.rawQuery(selectqry, null);
	if(c !=null)
	{
		c.moveToFirst();
		Tenants t =new Tenants();		
		t.set_id(c.getInt(c.getColumnIndex(KEY_ID)));		
		t.setFirstName(c.getString(c.getColumnIndex(FIRSTNAME)));	
		t.setEmail(c.getString(c.getColumnIndex(EMAIL)));
		
		t.setSurName(c.getString(c.getColumnIndex(SURTNAME)));
		if (c.getString(c.getColumnIndex(OTHERNAME))==null)
		{
			t.setOtherNames("Not Given");
		}
		else
		{
			t.setOtherNames(c.getString(c.getColumnIndex(OTHERNAME)));
		}
	
		t.setMobile1(c.getString(c.getColumnIndex(MOBILE1)));
		
		if (c.getString(c.getColumnIndex(MOBILE2))==null)
		{
			t.setMobile2("0");
		}
		else{
		t.setMobile2(c.getString(c.getColumnIndex(MOBILE2)));
		}	
		c.close();
		db.close();
		return t;
	}
	
	else
	{
		//db.close();
		return null;
	}
}

public List<VReadings> GetMeterReadings(String  myReadingDate){
	 List<VReadings> editReading = new ArrayList<VReadings>();
	 String ID="_id";
	 // list meter reading for a particular date
	    String selectQuery = " SELECT " + ID + ","+ READINGDATE +"," + READING + "," + RATE + ","+ METERNUMBER + " from "
	                          + VIEWEDITMETERREADING + " Where " + READINGDATE+ " = '"+ myReadingDate+ "'" ;
	      
	    SQLiteDatabase db = this.getReadableDatabase();	 
	    //Log.e("Meter Readings",selectQuery);
	    Cursor c = db.rawQuery(selectQuery, null);
	    try{
	 if(c.moveToFirst()){
		
		do{
			VReadings t =new VReadings();					
			
			t.set_id(c.getInt(c.getColumnIndex(ID)));			
			
			try {
				t.setReadingDate(fm.parse(c.getString((c.getColumnIndex(READINGDATE)))));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			t.setReading(c.getDouble(c.getColumnIndex(READING)));	
			
			t.setRate(c.getDouble(c.getColumnIndex(RATE)));
			
			t.setMeter_number(c.getString(c.getColumnIndex(METERNUMBER)));
			
			editReading.add(t);
		} while(c.moveToNext());
		}
	    } catch(Exception e)
	    {
	    	Log.e("Annoying Error", e.toString());
	    }
	    finally {
	    	  c.close();
	    		 //db.close();
	    }
	 
	 return editReading;
}
public List<Meters> GetMeters(){
	List<Meters> meters = new ArrayList<Meters>();
	
	    String selectQuery = " SELECT "+ KEY_ID +", "+ SERIALNUMBER +  ","+ METERNUMBER +","+DATEADDED +
	    		","+ INACTIVE+ ","+  DATEINACTIVE + " from " + TABLE_METERS;
	  //  Log.e("LOG Meter Collections ", selectQuery);	 
	    
	    SQLiteDatabase db = this.getReadableDatabase();
	    Cursor c = db.rawQuery(selectQuery, null);
	 if(c.moveToFirst()){
		do{
			Meters m =new Meters();
			m.set_id(c.getInt(c.getColumnIndex(KEY_ID)));
			m.setSerialNumber(c.getString(c.getColumnIndex(SERIALNUMBER)));
			m.setMeterNumber(c.getString(c.getColumnIndex(METERNUMBER)));
			try {
				m.setDateAdded(fm.parse(c.getString(c.getColumnIndex(DATEADDED))));
				String s= c.getString(c.getColumnIndex(DATEADDED));
				Log.e("Date added", s);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			m.setInActive(Boolean.parseBoolean(c.getString(c.getColumnIndex(INACTIVE))));
			if(c.getString(c.getColumnIndex(DATEINACTIVE))!=null)
			{
				
				try {
					m.setDateInActive(fm.parse(c.getString(c.getColumnIndex(DATEINACTIVE))));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		
			meters.add(m);
		} while(c.moveToNext());
		}
	 c.close();
	 //db.close();
	 
	 return meters;
}
public List<Balances> GetBalances(){
	 List<Balances> balance = new ArrayList<Balances>();
	 // list of tenants 
	    String selectQuery =  "Select ID,F,S,M,M2,W,P,A from vTotalBalances"; 
	    //Log.e("LOG", selectQuery);	  	    
	    SQLiteDatabase db = this.getReadableDatabase();  
	   	   //Log.e("LOG", selectQuery1);	
	    Cursor c = db.rawQuery(selectQuery, null);
	    
	   	 try{
	 if(c.moveToFirst()){
		do{
			Balances b =new Balances();
			b.set_id(Integer.parseInt(c.getString(c.getColumnIndex("ID"))));
			b.setFirstName(c.getString(c.getColumnIndex("F")));
			b.setSurname(c.getString(c.getColumnIndex("S")));
			b.setFullName(c.getString(c.getColumnIndex("F")),c.getString(c.getColumnIndex("S")));
			if(c.getString(c.getColumnIndex("M"))==null)
			{
				b.setMobile("0");
			}
			else
			{
				b.setMobile(c.getString(c.getColumnIndex("M")));
			}
			if(c.getString(c.getColumnIndex("M2"))==null)
			{
				b.setMobile2("0");
			}
			else
			{
				b.setMobile2(c.getString(c.getColumnIndex("M2")));
			}
						
		    b.setTotal_bill(c.getDouble(c.getColumnIndex("W")));	
		    b.setTotal_payment(c.getDouble(c.getColumnIndex("P")));
		    b.setAmount_due(c.getDouble(c.getColumnIndex("A")));	    
								
			balance.add(b);
		} while(c.moveToNext());
		}
	 
	    }catch(Exception e)
	    {
	    	Log.e("Balance Loading err", e.toString());
	    }
	    
		 c.close();
		// db.close();
		return balance;
}
public boolean PaymentMade(int id, String paymentdate)
{
	// for testing whether a reading was made for a date and meter
	String selectQuery = "Select * From " + TABLE_PAYMENTS + " Where " + TENANT_ID+ "=?" + " And " + PAYMENTDATE +"=?";
	// ...
	String[] args = new String[] { Integer.toString(id), paymentdate };
	Log.e("LOG", selectQuery);	    
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor c = db.rawQuery(selectQuery, args);
    
    if(c.moveToFirst()){
    	c.close();
	return true;
    }
    else
    {
    	c.close();
    	return false;
    }
	
}
public List<Meters> GetMeterIDs(){
	List<Meters> meters = new ArrayList<Meters>();
	
	    String selectQuery = " SELECT " +  KEY_ID + ","+ METERNUMBER + " from " + TABLE_METERS;
	  //  Log.e("LOG MeterId Collections ", selectQuery);	 
	    
	    SQLiteDatabase db = this.getReadableDatabase();
	    Cursor c = db.rawQuery(selectQuery, null);
	 if(c.moveToFirst()){
		do{
			Meters m =new Meters();
			m.set_id(c.getInt(c.getColumnIndex(KEY_ID)));			
			m.setMeterNumber(c.getString(c.getColumnIndex(METERNUMBER)));						
		   meters.add(m);
		} while(c.moveToNext());
		}
	 c.close();
	 //db.close();
	 
	 return meters;
}

public long getTenantMeterID()
{
	String selectQuery = " SELECT  Max(_id) from " + TABLE_TENANT_METERs;
	 SQLiteDatabase db = this.getReadableDatabase();
	 Cursor c = db.rawQuery(selectQuery, null);
	 c.moveToFirst();
	 int ID = c.getInt(0);
	 c.close();
	 return ID;
}

public List<VReadings> getReadings()
{
	List<VReadings> v= new ArrayList<VReadings>();
	
	String sql="Select _id , ReadingDate,Sum(Units) as Reading,Rate,Rate*(Sum(Units)) as Amount  from vDetailedReading "+ 
			    "Group by ReadingDate Order by ReadingDate desc";
	 SQLiteDatabase db = this.getReadableDatabase();
	 //Log.e("ss", sql);
	 Cursor c = db.rawQuery(sql, null);
	
	if(c.moveToFirst())
	{
		do
		{
			VReadings r= new VReadings();
			r.setReading(Double.parseDouble(c.getString(c.getColumnIndex("Reading"))));
			r.setAmount(Double.parseDouble(c.getString(c.getColumnIndex("Amount"))));
			r.setRate(Double.parseDouble(c.getString(c.getColumnIndex("Rate"))));
			try {
				r.setReadingDate(fm.parse(c.getString(c.getColumnIndex("ReadingDate"))));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			v.add(r);
		}while(c.moveToNext());
		
	}
	return v;
}
public boolean IsvalidDate(String stringtomatch)
{


	String pattern="^(19|20)\\d\\d[- /.](0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])$";
	
	if(stringtomatch.matches(pattern))
			{
		     return true;
			}
			
	else
	{
	return false;
	}
}
public void updateMeterReading(int id, String rdate, double reading ,double rate)
{
	String sql= " Update " +  TABLE_METERREADING + " set " + READINGDATE +"='"+ rdate+"'"+ "," +READING +" ="+ reading + "," + RATE+"="+rate + " Where "+ KEY_ID+"="+  id;
	 SQLiteDatabase db = this.getReadableDatabase();	
     db.execSQL(sql);


}
public void deleteMeterReading(int id)
{
	String sql= "Delete  from  " +  TABLE_METERREADING +  " Where "+ KEY_ID+"="+  id;
	 SQLiteDatabase db = this.getReadableDatabase();
	 //Log.e("ss", sql);
     db.execSQL(sql);


}
public void deletePowerPayment(int id)
{
	String sql= "Delete  from  " +  TABLE_PAYMENTS+  " Where "+ KEY_ID+"="+  id;
	 SQLiteDatabase db = this.getReadableDatabase();
	 //Log.e("ss", sql);
     db.execSQL(sql);


}
public boolean deleteMeter(int id)
{
	String sql= "Delete  from  " +  TABLE_METERS +  " Where "+ KEY_ID+"=" + id;
	 SQLiteDatabase db = this.getReadableDatabase();	
	// meter allocated?
		 // can not deleted alocated meter
	String alloca="Select *  from  " +  TABLE_TENANT_METERs +  " Where "+ METER_ID+"="+  id;
	Cursor c = db.rawQuery(alloca, null);
    if(c.moveToFirst())
    {
    	// meter allocated...can not delete it.
    	return false;
    	
    }else
    {
     db.execSQL(sql);
	return true;
    }

}
public boolean deleteLocation(int id)
{
	String sql= "Delete  from  Locations  Where _id "+ "="+ id;
	String sql1="Select location_id from ApartmentBlocks" +
	             " Where location_id " + "="+ id;
	 SQLiteDatabase db = this.getReadableDatabase();	
	 Cursor c=  db.rawQuery(sql1, null);
	 if(c.moveToFirst())
	 {
		return false; 
	 }else
	 {
	 
     db.execSQL(sql);
 	return true;
	 }

}

public boolean deleteBlock(int id)
     {
     	String sql= "Delete  from  ApartmentBlocks  Where _id "+ "="+ id;
     	String mysql= "Select block_id from Apartments where block_id" + "="+ id;
     	//Log.e("sql", sql1);
     	 SQLiteDatabase db = this.getReadableDatabase();     	 
     	 Cursor c=  db.rawQuery(mysql, null);
    	 if(c.moveToFirst())
    	 {
    		return false; 
    	 }else
    		 
    	 {    	 
         db.execSQL(sql);
     	return true;
    	 }

}
public boolean deleteTenant(int id)
{
	String sql= "Delete  from  Tenants  Where _id "+ "="+id;
	String mysql=" SELECT Tenants._id ,TenantMeters.Tenant_id, Tenancy.tenant_id from Tenants "+
			       " left join TenantMeters on TenantMeters.Tenant_id=Tenants._id "+
			       " left join Tenancy on  Tenancy.tenant_id=Tenants._id "+
			       " where TenantMeters.Tenant_id"+ "="+id+
			       " Or Tenancy.tenant_id" + "="+id;
	Log.e("mysql", mysql);
	//String[]args= new String[]{Integer.toString(id)};
	 SQLiteDatabase db = this.getReadableDatabase();     	 
	 Cursor c=  db.rawQuery(mysql, null);
	 if(c.moveToFirst())
	 {
		 c.close();
		return false; 
	 }else
	 {
		 c.close();
	    db.execSQL(sql);    
		return true;
		 
	  
	
	 }

}
public boolean deleteTenancy(int id)
{
	String sql= "Delete  from  Tenancy  Where _id "+ "="+id;
	String mysql=" SELECT * from Rental_invoices where tenancy_id"+ "="+ id;			       
	//Log.e("mysql", mysql);
	//String[]args= new String[]{Integer.toString(id)};
	 SQLiteDatabase db = this.getReadableDatabase();     	 
	 Cursor c=  db.rawQuery(mysql, null);
	 if(c.moveToFirst())
	 {
		 c.close();
		return false; 
	 }else
	 {
		 c.close();
	    db.execSQL(sql);    
		return true;
		 
	  
	
	 }

}
public void deleteRentalInvoice(int id)
{
	String sql= "Delete  from  Rental_invoices   Where _id "+ "="+ id;
	 SQLiteDatabase db = this.getReadableDatabase();	
     db.execSQL(sql);


}
public boolean  deleteApartment(int id)
{
	String sql= "Delete  from  Apartments    Where _id "+ "="+ id;
	String sql1=" Select apartment_id from Tenancy " +
	            " Where apartment_id"+ "="+ id;	
	 SQLiteDatabase db = this.getReadableDatabase();	
	 Cursor c=  db.rawQuery(sql1, null);
	 if(c.moveToFirst())
	 {
		return false; 
	 }else
	 {
	 
     db.execSQL(sql);
 	return true;
	 }


}

public void deleteRentalPayment(int id)
{
	String sql= "Delete  from  RentalPayments   Where _id "+ "="+ id;
	 SQLiteDatabase db = this.getReadableDatabase();	
     db.execSQL(sql);


}

public void EditLocation(int id,String mylocation)
{
	String sql= "update Locations set Location " + "='"+ mylocation + "'" + " Where  _id "+ "="+ id;
	 SQLiteDatabase db = this.getReadableDatabase();	
     db.execSQL(sql);


}

public void EditApartment(int id,int blockid, String number)
{
	String sql= "update Apartments set Number " + "='"+ number + "'" + ","+
                "block_id "+ "="+ blockid +
                " Where  _id "+ "="+ id;
	 SQLiteDatabase db = this.getReadableDatabase();	
     db.execSQL(sql);


}
public void EditBlock(int id,int locationid, String block)
{
	String sql= "update ApartmentBlocks set Block " + "='"+ block + "'" + ","+
                "location_id "+ "="+ locationid +
                " Where  _id "+ "="+ id;
	 SQLiteDatabase db = this.getReadableDatabase();	
     db.execSQL(sql);


}
public void EditRentalInvoice(int id, String sdate, String edate, double renta, String dd)
{
	String sql= "update Rental_invoices set "+
			   "startdate"+ "='"+ sdate +"'"+ ","+
			   "enddate"+ "='"+ edate + "'"+ ","+
			   "rental"+ "="+  renta + ","+
			   "duedate"+ "='"+ dd + "'"+ "Where  _id"+ "="+  id;
	//Log.e("sql", sql);
	 SQLiteDatabase db = this.getReadableDatabase();	
     db.execSQL(sql);


}
public void EditMeterAllocation(int TenantMeter_id,int tenantid,int meterid, String sdate)
{
	String sql= "update TenantMeters set Tenant_id"+ "="+ tenantid + ","+
                 "Meter_id"+ "="+ meterid +","+ "StartDate"+ "='"+ sdate + "'"+ 
			       " Where " + KEY_ID+ "="+ TenantMeter_id;
			    
	 SQLiteDatabase db = this.getReadableDatabase();	
     db.execSQL(sql);


}
public void endMeterAllocation(int TenantMeter_id, String sdate)
{
	String sql= "update TenantMeters set EndDate"+ "='"+ sdate + "'"+ ","+
                  "InActive=1"+
			       " Where " + KEY_ID+ "="+ TenantMeter_id;
			    
	 SQLiteDatabase db = this.getReadableDatabase();	
     db.execSQL(sql);


}

public void EditRentalPayment(int id,int invoiceid, String paydate,  double amount, String naration)
{
	String sql= "update RentalPayments set invoice_id" + "=" + invoiceid +","+
			   "PaymentDate"+ "='"+ paydate +"'"+ ","+			  
			   "Amount"+ "="+  amount + ","+
			   "Narration"+ "='"+ naration + "'"+ "Where  _id"+ "="+  id;
	 SQLiteDatabase db = this.getReadableDatabase();	
     db.execSQL(sql);


}


public void EditTenancy(int id,int apartmentid, String sdate, String edate, double renta)
{
	String sql= "update Tenancy set apartment_id"+ "="+  apartmentid + ","+
			   "startdate"+ "='"+ sdate +"'"+ ","+
			   "enddate"+ "='"+ edate + "'"+ ","+
			   "monthlyrental"+ "="+  renta + " Where  _id "+ " = "+  id;
	 //Log.e("Test Row String", sql);		   
	 SQLiteDatabase db = this.getReadableDatabase();	
     db.execSQL(sql);


}

public List<TenantMeterID> GetTenantMeters_Reading()

{
	// get a list of tenants and allocated meters
	List<TenantMeterID> meterlist = new ArrayList<TenantMeterID>();
	 String selectQuery =" Select TenantMeters._id as ID,Tenants.FirstName as F , Tenants.SurName  as S "+
                           " from Tenants" +
                           " Inner Join TenantMeters on TenantMeters.Tenant_id=Tenants._id" +
                             " WHERE TenantMeters.InActive=0";
  //  Log.e("Test Row String", selectQuery);	    
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor c = db.rawQuery(selectQuery, null);
    if(c.moveToFirst()){
		do{
			TenantMeterID t = new TenantMeterID();
			t.set_id(Integer.parseInt(c.getString(c.getColumnIndex("ID"))));
			t.setFirstName(c.getString(c.getColumnIndex("F")));
			t.setSurname(c.getString(c.getColumnIndex("S")));
			t.setFullName(c.getString(c.getColumnIndex("F")),c.getString(c.getColumnIndex("S")));
			meterlist.add(t);
		} while(c.moveToNext());
		
    }
    c.close();
    //db.close();
    return meterlist;
}

public List<ApartmentPayments> getApartmentReceipts(int apartmentid) 
{
	List<ApartmentPayments> payements= new ArrayList<ApartmentPayments>();
	String sql =" SELECT Apartments.Number as N,RentalPayments.PaymentDate as Payment,RentalPayments.Amount as Amount," +
			     "RentalPayments.Narration as Narration,Apartments._id as ID,Tenants.FirstName as F,Tenants.SurName  as S"+
                 " from Apartments " +
                 " inner join Tenancy on Apartments._id=Tenancy.apartment_id " +
                  " inner join Rental_invoices on Tenancy._id=Rental_invoices.tenancy_id  "+
                  " inner join RentalPayments  on Rental_invoices._id=RentalPayments.invoice_id " +
                  " inner Join Tenants on Tenants._id=Tenancy.tenant_id  "+
                  " where Apartments._id"+ "=" + apartmentid +
                  " Order by RentalPayments.PaymentDate DESC ";
	//Log.e("sql", sql);
	 SQLiteDatabase db = this.getReadableDatabase();
	    Cursor c = db.rawQuery(sql, null);
	    if(c.moveToFirst())
	    	
	    {
	    	do{
	    ApartmentPayments p= new ApartmentPayments();
	      p.setApartmentid(Integer.parseInt(c.getString(c.getColumnIndex("ID"))));
	      try {
			p.setPayment_date(fm.parse(c.getString(c.getColumnIndex("Payment"))));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      p.setAmount(Double.parseDouble(c.getString(c.getColumnIndex("Amount"))));
	      p.setNumber(c.getString(c.getColumnIndex("N")));
	      p.setNarration(c.getString(c.getColumnIndex("Narration")));
	      p.setFirstName(c.getString(c.getColumnIndex("F")));
	      p.setSurname(c.getString(c.getColumnIndex("S")));
	      p.setFullName(c.getString(c.getColumnIndex("F")),c.getString(c.getColumnIndex("S")));	     
	      payements.add(p);
	    }while(c.moveToNext());
	    }
	    
	return payements;
	
}
public List<ApartmentInvoices> getApartmentInvoices(int aprtmentid)
{
	List<ApartmentInvoices> mylist= new ArrayList<ApartmentInvoices>();
	String sql ="SELECT  Apartments._id as ID,Tenants.FirstName as F,Tenants.Surname as S,Rental_invoices.startdate as ST,"+
			  	" Rental_invoices.enddate as ED,Rental_invoices.rental as Amount"+
                " from Tenants"+
                " inner join Tenancy on Tenancy.tenant_id=Tenants._id "+
                " inner join Apartments on Apartments._id=Tenancy.apartment_id "+
                " inner join Rental_invoices on Rental_invoices.tenancy_id=Tenancy._id "+
                " Where Apartments._id"+ "="+ aprtmentid + 
                " order by Rental_invoices.startdate DESC";
	//Log.e("sql", sql);
	 SQLiteDatabase db = this.getReadableDatabase();
	    Cursor c = db.rawQuery(sql, null);
	    if(c.moveToFirst())
	    	
	    {
	    	do{    		
	    		ApartmentInvoices    a= new ApartmentInvoices();
	    		a.set_id(Integer.parseInt(c.getString(c.getColumnIndex("ID"))));
	    		a.setFname(c.getString(c.getColumnIndex("F")));
	    		a.setSname(c.getString(c.getColumnIndex("S")));
	    		try {
					a.setStartdate(fm.parse(c.getString(c.getColumnIndex("ST"))));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    		try {
					a.setEnddate(fm.parse(c.getString(c.getColumnIndex("ED"))));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
	    		a.setRentalamout(Double.parseDouble(c.getString(c.getColumnIndex("Amount"))));
	    		mylist.add(a);
	    	}while(c.moveToNext());
	    }
	return mylist;
	
}
public Cursor getAnnualTotals(String y)

{
	
	String sql= "SELECT  Sum(Case when  Rental_invoices.rental isnull then  0 else Rental_invoices.rental  end ) as I,"+
                "  Sum(Case when  RentalPayments.Amount isnull then  0 else RentalPayments.Amount end  ) as A,"+
			    " substr(Rental_invoices.startdate,1,4) as myYear"+
                 " FROM Rental_invoices   "+                    
               "  left Join RentalPayments on Rental_invoices._id=RentalPayments.invoice_id"+
             " Where  substr(Rental_invoices.startdate,1,4)"+ "=" + y +
              "  Group by   substr(Rental_invoices.startdate,1,4)";
	//Log.e("sql", sql);
	 SQLiteDatabase db = this.getReadableDatabase();
	    Cursor c = db.rawQuery(sql, null);
	  
	return c;
	
}
public Cursor getAnnualTotals()

{
	
	String sql= "SELECT  Sum(Case when  Rental_invoices.rental isnull then  0 else Rental_invoices.rental  end ) as I,"+
                "  Sum(Case when  RentalPayments.Amount isnull then  0 else RentalPayments.Amount end  ) as A"+			    
                 " FROM Rental_invoices   "+                    
               "  left Join RentalPayments on Rental_invoices._id=RentalPayments.invoice_id";           
             
	//Log.e("sql", sql);
	 SQLiteDatabase db = this.getReadableDatabase();
	    Cursor c = db.rawQuery(sql, null);
	  
	return c;
	
}
public Cursor getAnnualTotals(int id)

{
	
	String sql= " SELECT   Sum(Case when  Rental_invoices.rental isnull then  0 else Rental_invoices.rental  end ) as Invoices,"+
                " Sum(Case when  RentalPayments.Amount isnull then  0 else RentalPayments.Amount end  ) as Amount "+
                        "  FROM Apartments"+
                       "  inner join Tenancy on Apartments._id=Tenancy.apartment_id  "+                    
                      " inner Join Rental_invoices  on Tenancy._id= Rental_invoices.tenancy_id"+
                       " left Join RentalPayments on Rental_invoices._id=RentalPayments.invoice_id"+
                       " inner join ApartmentBlocks on ApartmentBlocks._id=Apartments.block_id"+
                      " where ApartmentBlocks._id " + "=" + id +
                      " Or ApartmentBlocks.location_id"+ "=" +  id ;
                    
	Log.e("sql", sql);
	 SQLiteDatabase db = this.getReadableDatabase();
	    Cursor c = db.rawQuery(sql, null);
	  
	return c;
	
}
public boolean TenantStatus(int id)
{
	String strSQL= "Select * from Tenants where _id= "+ id + " and InActive =1";
	 SQLiteDatabase db = this.getReadableDatabase();
	    Cursor c = db.rawQuery(strSQL, null);
	    if(c.moveToFirst())
	    {
	    	c.close();
	return true;
	    }
	    else
	    {
	    	c.close();
		return false;
	    }
}
public List<CurrentTenants> GetCurrentTenants(){
	List<CurrentTenants> tenants = new ArrayList<CurrentTenants>();
	// get Tenant ids and fullname
	    String selectQuery = " select " + KEY_ID+ "," + FIRSTNAME + "," + SURTNAME + " from  " +  TABLE_Tenants + " Where " + INACTIVE + "=" + 0;
	   // Log.e("LOG In Open Db", selectQuery);	
	   SQLiteDatabase db = this.getReadableDatabase();
	    Cursor c = db.rawQuery(selectQuery, null);
	 if(c.moveToFirst()){
		do{
			CurrentTenants  t =new CurrentTenants ();
			t.set_id(Integer.parseInt(c.getString(c.getColumnIndex(KEY_ID))));
			t.setFirstName(c.getString(c.getColumnIndex(FIRSTNAME )));	
			t.setSurName(c.getString(c.getColumnIndex(SURTNAME )));	
			t.setFullName(c.getString(c.getColumnIndex(FIRSTNAME )),c.getString(c.getColumnIndex(SURTNAME )));
			tenants.add(t);
		} while(c.moveToNext());
		}
	 c.close();
	 //db.close();
	return tenants;
	 
	
}
public List<MeterAllocation> GetMeterAllocation(){
	 List<MeterAllocation> meterallocations = new ArrayList<MeterAllocation>();
		    String selectQuery = "SELECT distinct  TM._id as ID,"+
		    					" Tenants.FirstName as fName,"+
		    					" Tenants.Surname as sName," +
		    					" Meters.MeterNumber as M,MeterReading.Rate as Rate,"+
		    					" (Select Min(MeterReading.Reading) from MeterReading  "+
		    					   " Where MeterReading.TenantMeter_id=TM._id )as R,"+
		    					"(Select Min(MeterReading.ReadingDate) from MeterReading "+
		    					" Where MeterReading.TenantMeter_id=TM._id )as sDate ," +
		    					"(Select Min(MeterReading._id) from MeterReading "+
		    					" Where MeterReading.TenantMeter_id=TM._id )as readingid, " +	
		    					"(Select Max(MeterReading.ReadingDate) from MeterReading "+
		    					" Where MeterReading.TenantMeter_id=TM._id )as ERD, "+
		    					"(Select Max(MeterReading.Reading) from MeterReading"+		    					
		    					" Where MeterReading.TenantMeter_id=TM._id )as ER,"+
		    					"TM.EndDate as ED"+
		    					" from TenantMeters as TM"+
		    					" inner join Meters on Meters._id=TM.Meter_id "+
		    					" inner join Tenants on Tenants._id=TM.Tenant_id "+
		    					" inner join MeterReading on MeterReading.TenantMeter_id=TM._id"+		    						    					
		    					" and TM._id=MeterReading.TenantMeter_id";
	    		
	    SQLiteDatabase db = this.getReadableDatabase();	 
	    //Log.e("Meter Allocation",selectQuery);
	    Cursor c = db.rawQuery(selectQuery, null);
	    try{
	 if(c.moveToFirst()){
		
		do{
			MeterAllocation t =new MeterAllocation();					
			t.set_id(c.getInt(c.getColumnIndex("ID")));	
			t.setReading_id((c.getInt(c.getColumnIndex("readingid"))));
			t.setFirstName(c.getString(c.getColumnIndex("fName")));
			t.setSurname(c.getString(c.getColumnIndex("sName")));
			t.setmeternumber(c.getString(c.getColumnIndex("M")));
			if(c.getString(c.getColumnIndex("sDate"))!=null)
			{
			t.setStartDate(fm.parse(c.getString(c.getColumnIndex("sDate"))));
			}
			if(c.getString(c.getColumnIndex("ED"))!=null)
			{
			t.setEnddate(fm.parse(c.getString(c.getColumnIndex("ED"))));
			}
			if(c.getString(c.getColumnIndex("ERD"))!=null)
			{
			t.setEndreadingdate(fm.parse(c.getString(c.getColumnIndex("ERD"))));
			}
			t.setEndreading(Double.parseDouble(c.getString(c.getColumnIndex("ER"))));
			t.setFullName(t.getFirstName(),t.getSurname());
			t.setReading(Double.parseDouble(c.getString(c.getColumnIndex("R"))));
			t.setRate(Double.parseDouble(c.getString(c.getColumnIndex("Rate"))));
			meterallocations.add(t);
		} while(c.moveToNext());
		}
	    } catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
	    finally {
	    	  c.close();
	    		 //db.close();
	    }
	 
	 return meterallocations;
}
public List<PowerPayments> GetPayment(String  paymentDate){
	 List<PowerPayments> payments = new ArrayList<PowerPayments>();	 
	 // list meter reading for a particular date
	    String selectQuery = "SELECT PowerPayments._id as ID, PowerPayments.PaymentDate as PaymentDate," +
	                          " PowerPayments.Amount as Amount, PowerPayments.Narration as Narration, "+
	    		              " Tenants.FirstName as F,Tenants.SurName as S "+
	    		              " from PowerPayments "+
	    		              " Inner join Tenants on Tenants._id=PowerPayments.Tenant_id "+
	                           "Where PaymentDate " + "='"+ paymentDate + "'";
	      
	    SQLiteDatabase db = this.getReadableDatabase();	 
	    //Log.e("Payments",selectQuery);
	    Cursor c = db.rawQuery(selectQuery, null);
	    try{
	 if(c.moveToFirst()){
		
		do{
			PowerPayments t =new PowerPayments();					
			t.setPaymentid(c.getInt(c.getColumnIndex("ID")));	
			t.setFirstName(c.getString(c.getColumnIndex("F")));
			t.setSurname(c.getString(c.getColumnIndex("S")));
			t.setFullName(c.getString(c.getColumnIndex("F")), c.getString(c.getColumnIndex("S")));
			
			try {
				t.setPaymentDate((fm.parse(c.getString((c.getColumnIndex("PaymentDate"))))));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			t.setAmount(c.getDouble(c.getColumnIndex("Amount")));	
			t.setNarration(c.getString(c.getColumnIndex("Narration")));
			
			
			payments.add(t);
		} while(c.moveToNext());
		}
	    } catch(Exception e)
	    {
	    	Log.e("Payments err", e.toString());
	    }
	    finally {
	    	  c.close();
	    		 //db.close();
	    } 
	 
	 return payments;
}
public List<PowerPayments> GetPayment(int   tenantid){
	 List<PowerPayments> payments = new ArrayList<PowerPayments>();	 
	 // list meter reading for a particular date
	    String selectQuery = "SELECT Tenants._id as ID, PowerPayments.PaymentDate as PaymentDate," +
	                          " PowerPayments.Amount as Amount, PowerPayments.Narration as Narration, "+
	    		              " Tenants.FirstName as F,Tenants.SurName as S "+
	    		              " from PowerPayments "+
	    		              " Inner join Tenants on Tenants._id=PowerPayments.Tenant_id "+
	                           "Where Tenants._id " + "=" + tenantid + " Order by PowerPayments.PaymentDate Desc";
	      
	    SQLiteDatabase db = this.getReadableDatabase();		    
	    //Log.e("Payments",selectQuery);
	    Cursor c = db.rawQuery(selectQuery, null);
	    try{
	 if(c.moveToFirst()){
		
		do{
			PowerPayments t =new PowerPayments();					
			t.set_id(c.getInt(c.getColumnIndex("ID")));	
			t.setFirstName(c.getString(c.getColumnIndex("F")));
			t.setSurname(c.getString(c.getColumnIndex("S")));
			t.setFullName(c.getString(c.getColumnIndex("F")), c.getString(c.getColumnIndex("S")));
			
			try {
				t.setPaymentDate((fm.parse(c.getString((c.getColumnIndex("PaymentDate"))))));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			t.setAmount(c.getDouble(c.getColumnIndex("Amount")));	
			t.setNarration(c.getString(c.getColumnIndex("Narration")));
			
			
			payments.add(t);
		} while(c.moveToNext());
		}
	    } catch(Exception e)
	    {
	    	Log.e("Payments err", e.toString());
	    }
	    finally {
	    	  c.close();
	    		 //db.close();
	    } 
	 
	 return payments;
}
public List<PowerPayments> GetPayment(){
	 List<PowerPayments> payments = new ArrayList<PowerPayments>();	 
	 // list meter reading for a particular date
	    String selectQuery = "SELECT Tenants._id as ID, PowerPayments.PaymentDate as PaymentDate," +
	                          " PowerPayments.Amount as Amount, PowerPayments.Narration as Narration, "+
	    		              " Tenants.FirstName as F,Tenants.SurName as S "+
	    		              " from PowerPayments "+
	    		              " Inner join Tenants on Tenants._id=PowerPayments.Tenant_id "+
	                            " Order by PowerPayments.PaymentDate Desc";
	      
	    SQLiteDatabase db = this.getReadableDatabase();		    
	    //Log.e("Payments",selectQuery);
	    Cursor c = db.rawQuery(selectQuery, null);
	    try{
	 if(c.moveToFirst()){
		
		do{
			PowerPayments t =new PowerPayments();					
			t.set_id(c.getInt(c.getColumnIndex("ID")));	
			t.setFirstName(c.getString(c.getColumnIndex("F")));
			t.setSurname(c.getString(c.getColumnIndex("S")));
			t.setFullName(c.getString(c.getColumnIndex("F")), c.getString(c.getColumnIndex("S")));
			
			try {
				t.setPaymentDate((fm.parse(c.getString((c.getColumnIndex("PaymentDate"))))));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			t.setAmount(c.getDouble(c.getColumnIndex("Amount")));	
			t.setNarration(c.getString(c.getColumnIndex("Narration")));
			
			
			payments.add(t);
		} while(c.moveToNext());
		}
	    } catch(Exception e)
	    {
	    	Log.e("Payments err", e.toString());
	    }
	    finally {
	    	  c.close();
	    		 //db.close();
	    } 
	 
	 return payments;
}

public void UpdatePower_Payments(int id, String rdate, double amount ,String narra)
{
	String sql= " Update " + TABLE_PAYMENTS + " set " + PAYMENTDATE +"='"+ rdate+"'"+ "," +AMOUNT +" ="+ amount + "," +  NARRATION+"='"+ narra + " '" + " Where "+ KEY_ID+"="+  id;
	 SQLiteDatabase db = this.getReadableDatabase();
	 Log.e("Edit Payments", sql);
     db.execSQL(sql);


}
public void AddPowerPayments(int Tenant_id, String rdate, double amount ,String narra)
{
	String sql= " Insert into PowerPayments(Tenant_id,PaymentDate,Amount,Narration)Values(" +
                 Tenant_id + ",'"+ rdate  + "'" + "," + amount  +",' "+ narra +"'" + ")";
	 SQLiteDatabase db = this.getReadableDatabase();
	 //Log.e("Insert  Payments", sql);
     db.execSQL(sql);


}
public boolean existsLocation(String l)
{
	 String selectQuery = "SELECT  Location from Locations"+
                           " Where Location"+ "='"+ l +"'";
	  //  Log.e("LOG In Open Db", selectQuery);	 	    
	    SQLiteDatabase db = this.getReadableDatabase();
	    Cursor c = db.rawQuery(selectQuery, null);
	    if(c.moveToNext())
	    {
	    	c.close();
	    	return true;
	    } else
	    {
	    c.close();
		return false;
	    }
}
public List<Locations> GetLocations(){
	List<Locations> location = new ArrayList<Locations>();
	
	    String selectQuery = "SELECT  _id as ID, Location from Locations";
	  //  Log.e("LOG In Open Db", selectQuery);	 
	    
	    SQLiteDatabase db = this.getReadableDatabase();
	    Cursor c = db.rawQuery(selectQuery, null);
	 if(c.moveToFirst()){
		do{
			Locations l =new Locations();
			l.set_id(Integer.parseInt(c.getString(c.getColumnIndex("ID"))));
			l.setLocation(c.getString(c.getColumnIndex("Location")));		
			l.toString();
			location .add(l);
			
		} while(c.moveToNext());
		}
	 c.close();
	 //db.close();
	 
	 return location ;
}

public List<Apartments> GetFreeApartment(){
	List<Apartments> blocks= new ArrayList<Apartments>();
	
	    String selectQuery = "SELECT ApartmentBlocks.Block as Block," +
	                       " Locations.Location as L,Apartments._id as ID,Apartments.block_id,Apartments.Number as Number "+
	    		          " from Apartments"+
	    		"  inner join ApartmentBlocks on ApartmentBlocks._id=Apartments.block_id " +
	    		" inner join Locations on Locations._id= ApartmentBlocks.location_id ";
	                         
	   //Log.e("LOG In Open Db", selectQuery);	 
	    
	    SQLiteDatabase db = this.getReadableDatabase();
	    Cursor c = db.rawQuery(selectQuery, null);
	 if(c.moveToFirst()){;
		do{
			Apartments B =new Apartments();
			B.set_id(Integer.parseInt(c.getString(c.getColumnIndex("ID"))));
			B.setLocation(c.getString(c.getColumnIndex("L")));
			B.setBlock((c.getString(c.getColumnIndex("Block"))));
			B.setNumber(c.getString(c.getColumnIndex("Number")));
			
			blocks .add(B);
		} while(c.moveToNext());
		}
	 c.close();
	 //db.close();
	 
	 return blocks;
}

public List<ActiveTenancy> GetActiveTenancy(){
	List<ActiveTenancy> blocks= new ArrayList<ActiveTenancy>();
	
	    String selectQuery =" Select Tenants._id as ID, Tenancy._id as TID,Tenants.FirstName as F,"+
                             " Tenants.SurName as S,Tenants.Mobile1 as M,Tenants.Mobile2 as M1,"+
	                        " Tenancy.startdate as SD,Tenancy.enddate as ED,Tenancy.monthlyrental as Rent,"+
	                   "  Apartments.Number as Apartment, Apartments._id as RoomID,"+
                       " ApartmentBlocks.Block as B, Locations.Location as L "+
                         "  from Locations "+
                 "   inner join ApartmentBlocks on Locations._id=ApartmentBlocks.location_id "+
                 "   inner Join Apartments on Apartments.block_id=ApartmentBlocks._id "+
	             "  inner join Tenancy on Tenancy.apartment_id=Apartments._id "+
	             " inner join Tenants on Tenants._id=Tenancy.tenant_id "+
	            "  Where Tenancy.InActive=0 ";
	    //Log.e("LOG Tenancy Collections", selectQuery);	 
	    
	    SQLiteDatabase db = this.getReadableDatabase();
	    Cursor c = db.rawQuery(selectQuery, null);
	 if(c.moveToFirst()){
		do{
			ActiveTenancy B =new ActiveTenancy();
			B.setApartmentid(Integer.parseInt(c.getString(c.getColumnIndex("RoomID"))));
			B.set_id(Integer.parseInt(c.getString(c.getColumnIndex("ID"))));	
			B.setTenancy_id(Integer.parseInt(c.getString(c.getColumnIndex("TID"))));
			B.setFirstName(c.getString(c.getColumnIndex("F")));
			B.setBlock(c.getString(c.getColumnIndex("B")));
			B.setLocation(c.getString(c.getColumnIndex("L")));			
			B.setSurname(c.getString(c.getColumnIndex("S")));			
			B.setFullName(B.getFirstName(),B.getSurname());
			if(c.getString(c.getColumnIndex("M"))==null)
			{
				B.setMobile("0");
			}
			
			else
			{
				B.setMobile(c.getString(c.getColumnIndex("M")));
			}
			if(c.getString(c.getColumnIndex("M1"))==null)
			{
				B.setMobile2("0");
			}
			
			else
			{
				B.setMobile2(c.getString(c.getColumnIndex("M1")));
			}
			
			
			try {
				B.setStart_date(fm.parse(c.getString(c.getColumnIndex("SD"))));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				B.setEnd_date(fm.parse(c.getString(c.getColumnIndex("ED"))));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			B.setMonthly_rental(Double.parseDouble(c.getString(c.getColumnIndex("Rent"))));
			B.setApartment(c.getString(c.getColumnIndex("Apartment")));
			blocks .add(B);
		} while(c.moveToNext());
		}
	 c.close();
	 //db.close();
	 
	 return blocks;
}
public List<ActiveTenancy> GetActiveTenancy(String mydate){
	List<ActiveTenancy> blocks= new ArrayList<ActiveTenancy>();
	
	    String selectQuery =" Select Tenants._id as ID, Tenancy._id as TID,Tenants.FirstName as F,"+
                             " Tenants.SurName as S,Tenants.Mobile1 as M,Tenants.Mobile2 as M1,"+
	                        " Tenancy.startdate as SD,Tenancy.enddate as ED,Tenancy.monthlyrental as Rent,"+
	                   "  Apartments.Number as Apartment, Apartments._id as RoomID,"+
                       " ApartmentBlocks.Block as B, Locations.Location as L "+
                         "  from Locations "+
                 "   inner join ApartmentBlocks on Locations._id=ApartmentBlocks.location_id "+
                 "   inner Join Apartments on Apartments.block_id=ApartmentBlocks._id "+
	             "  inner join Tenancy on Tenancy.apartment_id=Apartments._id "+
	             " inner join Tenants on Tenants._id=Tenancy.tenant_id "+
	            "  Where Tenancy.InActive=0 " + 
	             " and Tenancy.enddate " + "<=?";
	    //Log.e("LOG Tenancy Collections", selectQuery);	 
	    String[] args= new String[]{mydate};
	    SQLiteDatabase db = this.getReadableDatabase();
	    Cursor c = db.rawQuery(selectQuery, args);
	 if(c.moveToFirst()){
		do{
			ActiveTenancy B =new ActiveTenancy();
			B.setApartmentid(Integer.parseInt(c.getString(c.getColumnIndex("RoomID"))));
			B.set_id(Integer.parseInt(c.getString(c.getColumnIndex("ID"))));	
			B.setTenancy_id(Integer.parseInt(c.getString(c.getColumnIndex("TID"))));
			B.setFirstName(c.getString(c.getColumnIndex("F")));
			B.setBlock(c.getString(c.getColumnIndex("B")));
			B.setLocation(c.getString(c.getColumnIndex("L")));			
			B.setSurname(c.getString(c.getColumnIndex("S")));			
			B.setFullName(B.getFirstName(),B.getSurname());
			if(c.getString(c.getColumnIndex("M"))==null)
			{
				B.setMobile("0");
			}
			
			else
			{
				B.setMobile(c.getString(c.getColumnIndex("M")));
			}
			if(c.getString(c.getColumnIndex("M1"))==null)
			{
				B.setMobile2("0");
			}
			
			else
			{
				B.setMobile2(c.getString(c.getColumnIndex("M1")));
			}
			
			
			try {
				B.setStart_date(fm.parse(c.getString(c.getColumnIndex("SD"))));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				B.setEnd_date(fm.parse(c.getString(c.getColumnIndex("ED"))));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			B.setMonthly_rental(Double.parseDouble(c.getString(c.getColumnIndex("Rent"))));
			B.setApartment(c.getString(c.getColumnIndex("Apartment")));
			blocks .add(B);
		} while(c.moveToNext());
		}
	 c.close();
	 //db.close();
	 
	 return blocks;
}
public List<UnPaidInvoices> GetUnPaidInvoices(){
	List<UnPaidInvoices> blocks= new ArrayList<UnPaidInvoices>();
	 SimpleDateFormat dps = new SimpleDateFormat(
	            "yyyy-MM-dd", Locale.getDefault());
	    String selectQuery ="Select  Rental_invoices._id as ID,Tenants.FirstName as F,  Apartments.Number as Number,  Tenants.email as Mail,"+                     
	                        " Tenants.SurName as S,Tenants.Mobile1 as M,Tenants.Mobile2 as M1, Rental_invoices.tenancy_id as TID,"+
	                        " Rental_invoices.rental as Rent,Rental_invoices.startdate as SD,"+
	                       " Rental_invoices.enddate as ED,Rental_invoices.duedate as DD,"+
                           " Locations.Location as L"+
	                       " from Rental_invoices "+
	                      " inner join Tenancy on Tenancy._id=Rental_invoices.tenancy_id  "+
	                      " inner join Tenants on Tenants._id=Tenancy.tenant_id "+
	                     "inner join Apartments on Apartments._id=Tenancy.apartment_id "+
                       " inner join ApartmentBlocks on ApartmentBlocks._id=Apartments.block_id "+
                       "inner join Locations on Locations._id=ApartmentBlocks.location_id "+
	                   "left join RentalPayments on RentalPayments.invoice_id=Rental_invoices._id "+
	                  " Where  RentalPayments.invoice_id is null";

	   // Log.e("LOG Tenancy Collections", selectQuery);	 
	    
	    SQLiteDatabase db = this.getReadableDatabase();
	    Cursor c = db.rawQuery(selectQuery, null);
	 if(c.moveToFirst()){
		do{
			UnPaidInvoices B =new UnPaidInvoices();
			B.setInvoice_id(Integer.parseInt(c.getString(c.getColumnIndex("ID"))));
			B.setTenancy_id(Integer.parseInt(c.getString(c.getColumnIndex("TID"))));			
			B.setFirstName(c.getString(c.getColumnIndex("F")));
			B.setSurname(c.getString(c.getColumnIndex("S")));
			B.setFullName(B.getFirstName(),B.getSurname());
			if(c.getString(c.getColumnIndex("M"))==null)
			{
				B.setMobile("0");
			}
			
			else
			{
				B.setMobile(c.getString(c.getColumnIndex("M")));
			}
			if(c.getString(c.getColumnIndex("M1"))==null)
			{
				B.setMobile2("0");
			}
			
			else
			{
				B.setMobile2(c.getString(c.getColumnIndex("M1")));
			}
			
			
			try {
				B.setStart_date(dps.parse(c.getString(c.getColumnIndex("SD"))));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				B.setEnd_date(dps.parse(c.getString(c.getColumnIndex("ED"))));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				if(c.getString(c.getColumnIndex("DD"))==null)
				{
					B.setDatedue(fm.parse("2014-03-01"));
				
				}
				else
				{
				B.setDatedue(dps.parse(c.getString(c.getColumnIndex("DD"))));
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			B.setMonthly_rental(Double.parseDouble(c.getString(c.getColumnIndex("Rent"))));
			B.setApartment(c.getString(c.getColumnIndex("Number")));
			B.setEmail(c.getString(c.getColumnIndex("Mail")));
		    B.setLocation(c.getString(c.getColumnIndex("L")));
			B.Period();
			blocks .add(B);
		} while(c.moveToNext());
		}
	 c.close();
	 //db.close();
	 
	 return blocks;
}
public void AddLocation(String location)
{
String sql= " Insert into Locations(Location)Values('" + location + "'" +")";
SQLiteDatabase db = this.getReadableDatabase();
//Log.e("Insert  Location", sql);
db.execSQL(sql);

}

public void AddApartmentBlock(int location_id,String block)
{
	String sql= " Insert into ApartmentBlocks(location_id,Block)Values(" +
            location_id +",'" +  block + "'"+ ")";
SQLiteDatabase db = this.getReadableDatabase();
Log.e("Insert  ApartmentBlocks", sql);
db.execSQL(sql);

}

public void AddApartment(int block_id, String number)
{
	String sql= " Insert into Apartments(block_id, Number)Values(" +
            block_id+ ",'"+ number + "'"  + ")";
SQLiteDatabase db = this.getReadableDatabase();
//Log.e("Insert  Apartments", sql);
db.execSQL(sql);

}

public void AddTenancy(int Tenant_id, int apartment_id, String sdate, String edate,double rental, int status)
{
	String sql= " Insert into Tenancy(Tenant_id,apartment_id,startdate,enddate, monthlyrental,InActive)Values(" +
            Tenant_id + ","+ apartment_id +  ",'" + sdate + " '" + ",'"+ edate + "'" + ","+ rental +","+ status + ")";
SQLiteDatabase db = this.getReadableDatabase();
Log.e("Insert  Tenancy", sql);
db.execSQL(sql);

}
public void AddRentalInvoice( int tenancy_id,String sdate, String edate,double rental,String duedate)
{
	String sql= " Insert into Rental_invoices(tenancy_id,startdate,enddate, rental,duedate)Values(" +
			tenancy_id + ",'"+ sdate+"'"+ ",'"+ edate + "'" + ","+ rental + ",'"+ duedate+"'" + ")";
SQLiteDatabase db = this.getReadableDatabase();
//Log.e("Insert  rental invoice", sql);
db.execSQL(sql);
}
public void AddRentPayment( int invoice_id,String paymentdate, double amount,String narration)
{
	String sql= " Insert into RentalPayments(invoice_id,PaymentDate,Amount, Narration)Values(" +
			invoice_id + ",'"+ paymentdate+ "'"+ ","+ amount+ ",'"+  narration + "'" +")";
SQLiteDatabase db = this.getReadableDatabase();
//Log.e("Insert  rental payments", sql);
db.execSQL(sql);
}
public List<TenantList> GetTenencyIDs(){
	List<TenantList> tenants = new ArrayList<TenantList>();
	// get Tenant ids and fullname
	    String selectQuery = "Select Tenants._id as ID, Tenants.FirstName as F, Tenants.SurName as S, Tenants.Mobile1 as M, Tenants.Mobile2 as M2 from Tenants";
	                       
	    
	    SQLiteDatabase db = this.getReadableDatabase();
	    Cursor c = db.rawQuery(selectQuery, null);
	 if(c.moveToFirst()){
		do{
			TenantList t =new TenantList();
			t.set_id(Integer.parseInt(c.getString(c.getColumnIndex("ID"))));
			if(c.getString(c.getColumnIndex("F"))==null)
					{
				 t.setFirstName("");
					}
				 else
				 {
					 
			t.setFirstName(c.getString(c.getColumnIndex("F")));
				 }
				
			if(c.getString(c.getColumnIndex("S"))==null)
			{
		           t.setSurname("");
			  }
		      else
		             {
			 
	            t.setSurname(c.getString(c.getColumnIndex("S")));
		               }
		
			
			t.setFullName(t.getFirstName(),t.getSurname());
			if (c.getString(c.getColumnIndex("M"))==null){
				t.setMobile("0");
		    } 
			else
			{
				t.setMobile(c.getString(c.getColumnIndex("M")));
			}
			if (c.getString(c.getColumnIndex("M2"))==null){
				t.setMobile2("0");
		    } 
			else
			{
				t.setMobile2(c.getString(c.getColumnIndex("M2")));
			}
			
			
			
			tenants.add(t);
		} while(c.moveToNext());
		}
	 c.close();
	 //db.close();
	 
	 return tenants;
}
public List<Apartments> GetApartments(){
	List<Apartments> apartments = new ArrayList<Apartments>();
	Date d= new Date();
	String mydate= fm.format(d);
	
	    String selectQuery ="Select Apartments.Number as N, ApartmentBlocks.Block as B,"+
	                       " Locations.Location as L, Apartments._id as ID"+
	    		           " from Locations "+
	    		            " inner join ApartmentBlocks on  ApartmentBlocks.location_id=Locations._id"+
	    		            " inner join Apartments on Apartments.block_id=ApartmentBlocks._id";
	    		            
	    //Log.e("LOG In Open Db", selectQuery);	 
	    
	    SQLiteDatabase db = this.getReadableDatabase();
	    Cursor c = db.rawQuery(selectQuery, null);
	    int id=0;
	 if(c.moveToFirst()){
		do{
			Apartments l =new Apartments();
			id=Integer.parseInt(c.getString(c.getColumnIndex("ID")));
			l.set_id(id);
			l.setNumber(c.getString(c.getColumnIndex("N")));
			l.setBlock(c.getString(c.getColumnIndex("B")));
			l.setLocation(c.getString(c.getColumnIndex("L")));
			if(this.ApartmentNotFree(id, mydate)==true)
			{
				l.setInuse("Occupied");
				
			}else
			{
				l.setInuse("Free");
			}
			l.toString();
			apartments .add(l);
		} while(c.moveToNext());
		}
	 c.close();
	 //db.close();
	 
	 return apartments ;
}

public List<ApartmentBlocks> GetApartmentblocks(){
	List<ApartmentBlocks> blocks= new ArrayList<ApartmentBlocks>();
	
	    String selectQuery =" SELECT Locations.Location as Location,ApartmentBlocks._id as ID, " +
	                        " ApartmentBlocks.Block as Block from  ApartmentBlocks" + 
	    		            " inner join Locations on Locations._id=ApartmentBlocks.location_id";
	                         
	    //Log.e("LOG In Open Db", selectQuery);	 
	    
	    SQLiteDatabase db = this.getReadableDatabase();
	    Cursor c = db.rawQuery(selectQuery, null);
	 if(c.moveToFirst()){
		do{
			ApartmentBlocks B =new ApartmentBlocks();
			B.set_id(Integer.parseInt(c.getString(c.getColumnIndex("ID"))));
			B.setLocation(c.getString(c.getColumnIndex("Location")));
			B.setBlocknumber(c.getString(c.getColumnIndex("Block")));			
			blocks .add(B);
		} while(c.moveToNext());
		}
	 c.close();
	 //db.close();
	 
	 return blocks;
}
public void CreateInvoices()
{
	
	int year;
	int month;
	int day;
				
	String strSQL="SELECT Tenancy._id  as ID,Tenancy.startdate as SD,Tenancy.monthlyrental as M "+
	              " from Tenancy left join "+
			       "(Select tenancy_id from Rental_invoices where enddate > Date('now')) as W on "+
			       " W.tenancy_id=Tenancy._id where W.tenancy_id is null and Tenancy.InActive=0";

	SQLiteDatabase db = this.getReadableDatabase();
	//Log.e("Select", strSQL);
    Cursor c1 = db.rawQuery(strSQL, null);    
    if(c1.moveToFirst()){
    	do{
    		// extract the beginning date of the contract
    		String s = c1.getString(c1.getColumnIndex("SD"));
    		String sday = s.substring(9, 10);
    		day = Integer.parseInt(sday);    		
    		Calendar  b = Calendar.getInstance();
    		year=b.get(Calendar.YEAR);
    		month=b.get(Calendar.MONTH);
    		final Calendar c = new GregorianCalendar(year, month, day);
    		// get invoice start date. Always start of contract
    		Date startdate =c.getTime();	
    		// add a month
    		 c.add(Calendar.MONTH,1);
    		 // get the date
    		 Date enddate = c.getTime();
    		 // convert to strings for use in SQLite
    		String sdate= fm.format(startdate);
    		String edate= fm.format(enddate);
    		
    		final Calendar cal = new GregorianCalendar(year, month, day);
    		  cal.add(Calendar.DATE,5);
    		 Date dd= cal.getTime();
    		 String duedate= fm.format(dd);
    		double rent = c1.getDouble(c1.getColumnIndex("M"));
     		int tenancyid= c1.getInt(c1.getColumnIndex("ID"));
     		strSQL="Insert into Rental_invoices(tenancy_id,startdate,enddate,rental,duedate)"+
     		        "Values ("+ 
     				 tenancyid+ ",'"+ sdate + "'" + ",'" + edate + "'"+ ","+ rent + ",'" +   duedate + "'" + ")";
     		this.InvoiceOverLap(tenancyid, sdate, edate);
     		db.execSQL(strSQL);
     		Log.e("string", strSQL);
    	}while (c1.moveToNext());
    }
}
public List<PowerPayments> GetRecentPayment(String  sDate,String eDate){
	 List<PowerPayments> payments = new ArrayList<PowerPayments>();	 
	 // list meter reading for a particular date
	    String selectQuery = "SELECT PowerPayments._id as ID, PowerPayments.PaymentDate as PaymentDate," +
	                          " PowerPayments.Amount as Amount, PowerPayments.Narration as Narration, "+
	    		              " Tenants.FirstName as F,Tenants.SurName as S "+
	    		              " from PowerPayments "+
	    		              " Inner join Tenants on Tenants._id=PowerPayments.Tenant_id "+
	                           "Where PaymentDate  between " + " '" + sDate + "'" + "and" + "'" + eDate + "'";
	      
	    SQLiteDatabase db = this.getReadableDatabase();	 
	   // Log.e("Payments",selectQuery);
	    Cursor c = db.rawQuery(selectQuery, null);
	    try{
	 if(c.moveToFirst()){
		
		do{
			PowerPayments t =new PowerPayments();					
			t.setPaymentid(c.getInt(c.getColumnIndex("ID")));	
			t.setFirstName(c.getString(c.getColumnIndex("F")));
			t.setSurname(c.getString(c.getColumnIndex("S")));
			t.setFullName(c.getString(c.getColumnIndex("F")), c.getString(c.getColumnIndex("S")));
			
			try {
				t.setPaymentDate((fm.parse(c.getString((c.getColumnIndex("PaymentDate"))))));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			t.setAmount(c.getDouble(c.getColumnIndex("Amount")));	
			t.setNarration(c.getString(c.getColumnIndex("Narration")));
			
			
			payments.add(t);
		} while(c.moveToNext());
		}
	    } catch(Exception e)
	    {
	    	Log.e("Payments err", e.toString());
	    }
	    finally {
	    	  c.close();
	    		 //db.close();
	    } 
	 
	 return payments;
}
public boolean InvoiceOverLap(int id,String sDate, String eDate)
{
	String strSQL= "Select tenancy_id from Rental_invoices  where tenancy_id "+ "=" + id +
			        " and startdate "+ "< '"+ eDate + "'"+ "and enddate"+ " > '"+ sDate + "'";
	 SQLiteDatabase db = this.getReadableDatabase();
	    Cursor c = db.rawQuery(strSQL, null);
	    //Log.e("Overlapping", strSQL);
	    if(c.moveToFirst())
	    {
	    	c.close();
	        return true;
	    }
	    else
	    { 
	    	c.close();
		    return false;
	    }
}

public boolean InvoiceOverLap(int id,String sDate, String eDate,int invoiceid)
{
	String strSQL= "Select tenancy_id from Rental_invoices  where tenancy_id "+ "=" + id +
			        " and startdate "+ "< '"+ eDate + "'"+ "and enddate"+ " > '"+ sDate + "'" +
			        " and Rental_invoices._id " + "<>" + invoiceid;
	 SQLiteDatabase db = this.getReadableDatabase();
	    Cursor c = db.rawQuery(strSQL, null);
	   // Log.e("Overlapping", strSQL);
	    if(c.moveToFirst())
	    {
	    	c.close();
	return true;
	    }
	    else
	    {
	    	c.close();
		return false;
	    }
}

public List<RentalPayments> GetRentalPayments(String sdate) throws ParseException{
	List<RentalPayments> payments= new ArrayList<RentalPayments>();
	
	
	    String selectQuery ="SELECT  RentalPayments._id as ID,Tenants.FirstName as F,Rental_invoices.startdate as SD,"+
	                        " Tenants.SurName as S, Tenants.Mobile1 as M, Tenants.Mobile2 as M1,Rental_invoices.enddate ED,"+
	    		            " RentalPayments.PaymentDate as PD,RentalPayments.Amount as A ,RentalPayments.Narration as N"+
	    		             " from RentalPayments"+
	    		             " inner  join Rental_invoices on Rental_invoices._id=RentalPayments.invoice_id"+
	    		             " inner join Tenancy on Tenancy._id=Rental_invoices.tenancy_id"+
	    		             " inner join Tenants on Tenants._id=Tenancy.tenant_id"+
	    		             " Where RentalPayments.PaymentDate  >= "+ "'" + sdate + "'" +
	    		             " Order by   RentalPayments._id desc";

	    
         //Log.e("LOG Payments Collections", selectQuery);	
	    SQLiteDatabase db = this.getReadableDatabase();
	    Cursor c = db.rawQuery(selectQuery, null);
	 if(c.moveToFirst()){
		do{
			RentalPayments payment =new RentalPayments();			
			payment.setPayment_id(Integer.parseInt(c.getString(c.getColumnIndex("ID"))));		
			payment.setFirstName(c.getString(c.getColumnIndex("F")));
			payment.setSurname(c.getString(c.getColumnIndex("S")));
			payment.setFullName(payment.getFirstName(),payment.getSurname());			
			if(c.getString(c.getColumnIndex("M"))==null)
			{
				payment.setMobile("0");
			}
			
			else
			{
				payment.setMobile(c.getString(c.getColumnIndex("M")));
			}
			if(c.getString(c.getColumnIndex("M1"))==null)
			{
				payment.setMobile2("0");
			}
			
			else
			{
				payment.setMobile2(c.getString(c.getColumnIndex("M1")));
			}	
			payment.setPayment_date(fm.parse(c.getString(c.getColumnIndex("PD"))));
			payment.setAmount(c.getDouble(c.getColumnIndex("A")));
			payment.setNarration(c.getString(c.getColumnIndex("N")));
			payment.setPeriod(c.getString(c.getColumnIndex("SD"))+ " to "+ c.getString(c.getColumnIndex("ED")));
			
			payments .add(payment);
		} while(c.moveToNext());
		}
	 
	 c.close();
	 //db.close();
	 
	 return payments;
}
public List<RentalPayments> GetRentalPayments() throws ParseException{
	List<RentalPayments> payments= new ArrayList<RentalPayments>();
	
	
	    String selectQuery ="SELECT  RentalPayments._id as ID,Tenants.FirstName as F,Rental_invoices.startdate as SD,"+
	                        " Tenants.SurName as S, Tenants.Mobile1 as M, Tenants.Mobile2 as M1,Rental_invoices.enddate ED,"+
	    		            " RentalPayments.PaymentDate as PD,RentalPayments.Amount as A ,RentalPayments.Narration as N"+
	    		             " from RentalPayments"+
	    		             " inner  join Rental_invoices on Rental_invoices._id=RentalPayments.invoice_id"+
	    		             " inner join Tenancy on Tenancy._id=Rental_invoices.tenancy_id"+
	    		             " inner join Tenants on Tenants._id=Tenancy.tenant_id"+	    		             
	    		             " Order by   RentalPayments._id desc";

	    
         //Log.e("LOG Payments Collections", selectQuery);	
	    SQLiteDatabase db = this.getReadableDatabase();
	    Cursor c = db.rawQuery(selectQuery, null);
	 if(c.moveToFirst()){
		do{
			RentalPayments payment =new RentalPayments();			
			payment.setPayment_id(Integer.parseInt(c.getString(c.getColumnIndex("ID"))));		
			payment.setFirstName(c.getString(c.getColumnIndex("F")));
			payment.setSurname(c.getString(c.getColumnIndex("S")));
			payment.setFullName(payment.getFirstName(),payment.getSurname());			
			if(c.getString(c.getColumnIndex("M"))==null)
			{
				payment.setMobile("0");
			}
			
			else
			{
				payment.setMobile(c.getString(c.getColumnIndex("M")));
			}
			if(c.getString(c.getColumnIndex("M1"))==null)
			{
				payment.setMobile2("0");
			}
			
			else
			{
				payment.setMobile2(c.getString(c.getColumnIndex("M1")));
			}	
			payment.setPayment_date(fm.parse(c.getString(c.getColumnIndex("PD"))));
			payment.setAmount(c.getDouble(c.getColumnIndex("A")));
			payment.setNarration(c.getString(c.getColumnIndex("N")));
			payment.setPeriod(c.getString(c.getColumnIndex("SD"))+ " to "+ c.getString(c.getColumnIndex("ED")));
			
			payments .add(payment);
		} while(c.moveToNext());
		}
	 
	 c.close();
	 //db.close();
	 
	 return payments;
}

public boolean TenancyOverLap(int id,String sDate, String eDate)
{
	String strSQL= "Select tenant_id from Tenancy where tenant_id "+ "=" + id +
			        " and startdate "+ "< '"+ eDate + "'"+ "and enddate"+ " > '"+ sDate + "'";
	 SQLiteDatabase db = this.getReadableDatabase();
	    Cursor c = db.rawQuery(strSQL, null);
	   // Log.e("Overlapping", strSQL);
	    if(c.moveToFirst())
	    {
	return true;
	    }
	    else
	    {
		return false;
	    }
}
public boolean ApartmentNotFree(int id,String myenddate)
{
	String strSQL= "Select apartment_id from Tenancy where apartment_id "+ "=" + id +
			        " and enddate"+ " >='"+ myenddate  + "'";
	 SQLiteDatabase db = this.getReadableDatabase();
	    Cursor c = db.rawQuery(strSQL, null);
	    //Log.e("Overlapping", strSQL);
	    if(c.moveToFirst())
	    {
	    	c.close();
	return true;
	    }
	    else
	    {
	    	c.close();
		return false;
	    }
}
public boolean ApartmentNotFreeEdit(int id,int tenancyid)
{
	String strSQL= "Select apartment_id from Tenancy where apartment_id "+ "=" + id +
			        " and InActive=0 and "+ KEY_ID + " <> " + tenancyid;
	 SQLiteDatabase db = this.getReadableDatabase();
	    Cursor c = db.rawQuery(strSQL, null);
	    //Log.e("Overlapping", strSQL);
	    if(c.moveToFirst())
	    {
	return true;
	    }
	    else
	    {
		return false;
	    }
}
public boolean HasValidTenancy(int tenantid,int apartment_id)
{
	String strSQL= "Select apartment_id from Tenancy where tenant_id "+ "=" + tenantid +
			        " and InActive=0 " +
			        " and apartment_id"+ "="+ apartment_id;
	 SQLiteDatabase db = this.getReadableDatabase();
	    Cursor c = db.rawQuery(strSQL, null);
	    //Log.e("Overlapping", strSQL);
	    if(c.moveToFirst())
	    {
	return true;
	    }
	    else
	    {
		return false;
	    }
}
public void ExpiredTenancy(int tenancyid)
{
String strSQL= "Update Tenancy set InActive =1 where " + KEY_ID + "="+ tenancyid;	       
SQLiteDatabase db = this.getReadableDatabase();
db.execSQL(strSQL);
}
public long DeleteMeterAllocation(int tenantmeterid)
{
	//set tenant inactive. Also make da meter inactive for that tenant	
	SQLiteDatabase db=this.getWritableDatabase();	
	String SQL= "Delete from MeterReading where TenantMeter_id"+ "=" + tenantmeterid;
	String SQL1="Delete from TenantMeters where " + KEY_ID + "="+ tenantmeterid;
	try{
	db.beginTransaction();
	db.execSQL(SQL);
	db.execSQL(SQL1);			
	db.setTransactionSuccessful();
	return 1;
	} catch( Exception e)
	{
		Log.e("update fail", e.toString());		
		return 0;
	}
	finally{
		db.endTransaction();
	}
}

public List<RentalPaymentStatus> GetRentalPaymentsStatus(){
	List<RentalPaymentStatus> payments= new ArrayList<RentalPaymentStatus>();
	 SimpleDateFormat dps = new SimpleDateFormat(
	            "yyyy-MM-dd", Locale.getDefault());
	    String selectQuery 
	    ="SELECT Tenants.FirstName as F,Tenants.Surname as S,Tenants.Mobile1 as M,Tenants.Mobile2 as M2,"+
	     "(Case when Rental_invoices.rental isnull then 0 else Rental_invoices.rental end) as Rent ,"+
	      "Rental_invoices.startdate as SD,Rental_invoices.enddate as ED,"+
	      "(Case When RentalPayments.Amount isnull then 0 else RentalPayments.Amount end) as Amount,"+
	      "Apartments.Number as N ,RentalPayments.PaymentDate as D,Rental_invoices.duedate as DD "+
	      "from  Rental_invoices "+
	     "left join RentalPayments on RentalPayments.invoice_id=Rental_invoices._id "+
	     "inner join Tenancy on Tenancy._id=Rental_invoices.tenancy_id "+
	    "inner join Tenants on Tenants._id=Tenancy.tenant_id "+
	    "inner join Apartments on Apartments._id=Tenancy.apartment_id "+
	    " Order by Rental_invoices.startdate desc";

	   // Log.e("Payment Status", selectQuery);	 
	    
	    SQLiteDatabase db = this.getReadableDatabase();
	    Cursor c = db.rawQuery(selectQuery, null);
	 if(c.moveToFirst()){
		do{
			
			RentalPaymentStatus B =new RentalPaymentStatus();			
			B.setFirstName(c.getString(c.getColumnIndex("F")));
			B.setSurname(c.getString(c.getColumnIndex("S")));
			try {
				B.setDuedate(fm.parse(c.getString(c.getColumnIndex("DD"))));
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			B.setFullName(B.getFirstName(),B.getSurname());
			if(c.getString(c.getColumnIndex("M"))==null)
			{
				B.setMobile("0");
			}
			
			else
			{
				B.setMobile(c.getString(c.getColumnIndex("M")));
			}
			if(c.getString(c.getColumnIndex("M2"))==null)
			{
				B.setMobile2("0");
			}
			
			else
			{
				B.setMobile2(c.getString(c.getColumnIndex("M2")));
			}
			
			
			try {
				B.setStart_date(dps.parse(c.getString(c.getColumnIndex("SD"))));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				B.setEnd_date(dps.parse(c.getString(c.getColumnIndex("ED"))));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			try {
				if(c.getString(c.getColumnIndex("D"))!=null)
				{
				B.setDatepaid(dps.parse(c.getString(c.getColumnIndex("D"))));
				}else
				{
					B.setDatepaid(null);
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			B.setMonthly_rental(Double.parseDouble(c.getString(c.getColumnIndex("Rent"))));
			B.setApartment(c.getString(c.getColumnIndex("N")));
			B.setAmoutpaid(Double.parseDouble(c.getString(c.getColumnIndex("Amount"))));
			payments .add(B);
		} while(c.moveToNext());
		}
	 c.close();
	 //db.close();
	 
	 return payments;
}
@SuppressLint("UseValueOf")
public List<SearchResults> GetSearchList( String token){
	List<SearchResults> list = new ArrayList<SearchResults>();	
	SQLiteDatabase db= this.getWritableDatabase();	
	 String selectQuery ="select * from " + TABLE_NAME_VIRTUAL + " Where " + 
	                        TABLE_NAME_VIRTUAL + " Match " + " ? ";
	    String pattern="^(19|20)\\d\\d[- /.](0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])$";
	    String tokenedit=null;
	    String newtoken=null;
	    if (token.matches(pattern))
	    {
	    	tokenedit= token.replace("-", " ");
	    	//Log.e("testtoken", tokenedit);
	    	Character c=new Character((char) (34));
	    	//String i= c.toString();
	    	//Log.e("character", i);
	    	// add a quote to allow phrase search like '"2014 04 30"'
	    	 newtoken = c+ tokenedit+ c + "*";
	    }else
	    { 
	    	// normal string simply attach *
	    	 newtoken= token+ "*";
	    }



	    String [] args= new String[]{newtoken};                     
	    	 	    
	   
	    Cursor c = db.rawQuery(selectQuery, args);
	 if(c.moveToFirst()){
		do{
			SearchResults l =new SearchResults();
			l.setFirstname(c.getString(c.getColumnIndex("F")));
			l.setSurname(c.getString(c.getColumnIndex("S")));
			l.setFullname(l.getFirstname(),l.getSurname());
			l.setType(c.getString(c.getColumnIndex("Type")));
			try {
				l.setDate(fm.parse(c.getString(c.getColumnIndex("Date"))));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			l.setAmount(Double.parseDouble(c.getString(c.getColumnIndex("Amount"))));
					list .add(l);
		} while(c.moveToNext());
		}
	 c.close();
	 //db.close();
	 
	 return list ;
	 
}
public void createSearchTable()
{
	SQLiteDatabase db= this.getWritableDatabase();
	String sql0=" Drop Table if exists " +  TABLE_NAME_VIRTUAL;
	String sql=" CREATE VIRTUAL TABLE " + TABLE_NAME_VIRTUAL + "  USING fts3("+ KEY_ID + ","+  FNAME + "," + SNAME + "," + 
	         SDATE+ "," + SAMOUNT + "," + STYPE + ")";
	String sql1= "Insert into " + TABLE_NAME_VIRTUAL + "(" + FNAME + "," + SNAME + "," + SDATE + "," + SAMOUNT +
			      "," + STYPE + ")" +
			     " select "+ FNAME + "," + SNAME + "," + SDATE + ","+ SAMOUNT + "," + STYPE + " from " + 
	               QUERY;
	//Log.e("insert sql",sql1);
	db.execSQL(sql0);
	db.execSQL(sql);
	db.execSQL(sql1);
}
public List<VMeterReadings> GetReadings(){
	 List<VMeterReadings> individual = new ArrayList<VMeterReadings>();
	 // list meter reading for a particular Tenant.
	    String selectQuery = " Select " + TENANT + "," + READINGDATEVIEW + "," + CurrentREADING + ","+ PrevREADINGDATE +","+ prevREADING +","+ UNITs+ ","+ RATE + ","+ AMOUNT + " FROM "
	                       + VIEW_DETAILEDREADING +  "  Order by " + READINGDATE + " DESC ";
	    	 //Log.e("Reading", selectQuery) ;  
	    SQLiteDatabase db = this.getReadableDatabase();	    
	    Cursor c = db.rawQuery(selectQuery, null);
	 if(c.moveToFirst()){
		
		do{
			VMeterReadings t =new VMeterReadings();					
			try {
				t.setReadingDate(fm.parse(c.getString(c.getColumnIndex(READINGDATE))));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				Log.e("parsing Reading date",e.toString());
			}
			t.setCurrentReading(c.getDouble(c.getColumnIndex(CurrentREADING)));
			try {
				t.setPreviousReadingDate(fm.parse(c.getString(c.getColumnIndex(PrevREADINGDATE))));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				Log.e("parsing current reading",e.toString());
			}
			t.setPreviousMeterReading(c.getDouble(c.getColumnIndex(prevREADING)));
			t.setUnits(c.getDouble(c.getColumnIndex(UNITs)));
			t.setRate(c.getDouble(c.getColumnIndex(RATE)));
			t.setAmount(c.getDouble(c.getColumnIndex(AMOUNT)));
			t.setTenant(c.getString(c.getColumnIndex(TENANT)));
			individual.add(t);
		} while(c.moveToNext());
		}
	
	 //db.close();
	 return individual;
}
public Cursor getTotalReceipts(int roomid)


{
	 String selectQuery =" SELECT Apartments._id as ID,Sum(RentalPayments.Amount) as Amount"+		     
                         " from Apartments "+
                         " inner join Tenancy on Apartments._id=Tenancy.apartment_id "+
                          " inner join Rental_invoices on Tenancy._id=Rental_invoices.tenancy_id  "+
                          "  inner join RentalPayments  on Rental_invoices._id=RentalPayments.invoice_id"+
                          " Where  Apartments._id" + "=" + roomid  +
                          "  Group by  Apartments._id";           


  

SQLiteDatabase db = this.getReadableDatabase();	    
Cursor c = db.rawQuery(selectQuery, null);
	
	return c;
	
}
public Cursor getYear()
{
String sql="SELECT  DISTINCT substr(Rental_invoices.startdate,1,4) as year from Rental_invoices	"+
          " UNION "+
       " select " +  "\"All\"" + "  as D from Rental_invoices";

Log.e("sql",sql);
SQLiteDatabase db = this.getReadableDatabase();	    
Cursor c = db.rawQuery(sql, null);
return c;

}

public Cursor getTotalInvoices(int roomid)


{
	 String selectQuery = "SELECT  Apartments._id as ID,Sum(Rental_invoices.rental) as Amount"+
                          " from Rental_invoices "+
                          " inner join Tenancy on Tenancy._id=Rental_invoices.tenancy_id "+
                          " inner join Apartments on Apartments._id=Tenancy.apartment_id "+
                          " Where  Apartments._id" + "=" + roomid  +
                           " Group by  Apartments._id ";

 // Log.e("sql",selectQuery);

SQLiteDatabase db = this.getReadableDatabase();	    
Cursor c = db.rawQuery(selectQuery, null);
	
	return c;
	
}

public Date getLastPaidInvoiceDate(int id)


{
	 String selectQuery ="SELECT  max(enddate)  as DD from Rental_invoices "+
			            " inner join RentalPayments on RentalPayments.invoice_id=Rental_invoices._id "+
			             "  Where  Rental_invoices.tenancy_id" + "="+ id;


  //Log.e("sql",selectQuery);

SQLiteDatabase db = this.getReadableDatabase();	    
Cursor c = db.rawQuery(selectQuery, null);
Date d=null;
	if(c.moveToFirst()){
		try {
			if(c.getString(c.getColumnIndex("DD"))!=null)
			{
			d= fm.parse(c.getString(c.getColumnIndex("DD")));
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		c.close();
	return d;
	}else
	{
	c.close();
	return null;
	}
}
public Date getTenantLasInvoiceDate(int tid)


{
	 String selectQuery ="Select max(Rental_invoices.enddate) as DD from Rental_invoices"+
			             " inner join RentalPayments on RentalPayments.invoice_id=Rental_invoices._id"+
			             " inner  join Tenancy on Tenancy._id=Rental_invoices.tenancy_id"+
			             " where Tenancy.tenant_id"+ "="+ tid;


  //Log.e("sql",selectQuery);

SQLiteDatabase db = this.getReadableDatabase();	    
Cursor c = db.rawQuery(selectQuery, null);
Date d=null;
	if(c.moveToFirst()){
		try {
			if(c.getString(c.getColumnIndex("DD"))!=null)
			{
			d= fm.parse(c.getString(c.getColumnIndex("DD")));
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		c.close();
	return d;
	}else
	{
	c.close();
	return null;
	}
}
public List<AnnualTotals> getAnnualInvoices(String  year)
 

{
	 List<AnnualTotals> a= new ArrayList<AnnualTotals>();
	 String selectQuery =" SELECT Apartments._id,Apartments.Number as N, Sum(Case when  Rental_invoices.rental isnull then  0 else Rental_invoices.rental  end ) as Invoices,"+
                         "  Sum(Case when  RentalPayments.Amount isnull then  0 else RentalPayments.Amount end  ) as Amount,substr(Rental_invoices.startdate,1,4) as myYear"+
                         " FROM Apartments"+
                        " inner join Tenancy on Apartments._id=Tenancy.apartment_id  "+                     
                       " inner Join Rental_invoices  on Tenancy._id= Rental_invoices.tenancy_id"+
                      "  left Join RentalPayments on Rental_invoices._id=RentalPayments.invoice_id"+
                       " Where substr(Rental_invoices.startdate,1,4) " + "="+ year +                      
                     " Group by Apartments._id";
  //Log.e("sql",selectQuery);
//String[] args = new String[]{year};
SQLiteDatabase db = this.getReadableDatabase();	    
Cursor c = db.rawQuery(selectQuery, null);
if(c.moveToNext())
{
	do
	{
		AnnualTotals t= new AnnualTotals();
		t.setNumber((c.getString(c.getColumnIndex("N"))));
		//Log.e("room", c.getString(c.getColumnIndex("N")));
		t.setInvoices(Double.parseDouble(c.getString(c.getColumnIndex("Invoices"))));
		t.setReceipts(Double.parseDouble(c.getString(c.getColumnIndex("Amount"))));
		a.add(t);
	}while(c.moveToNext());
		
	
}
	
return a;
	
}
public List<AnnualTotals> getAnnualInvoices()


{
	 List<AnnualTotals> a= new ArrayList<AnnualTotals>();
	 String selectQuery =" SELECT Apartments._id,Apartments.Number as N, Sum(Case when  Rental_invoices.rental isnull then  0 else Rental_invoices.rental  end ) as Invoices,"+
                         "  Sum(Case when  RentalPayments.Amount isnull then  0 else RentalPayments.Amount end  ) as Amount,substr(Rental_invoices.startdate,1,4) as myYear"+
                         " FROM Apartments"+
                        " inner join Tenancy on Apartments._id=Tenancy.apartment_id  "+                     
                       " inner Join Rental_invoices  on Tenancy._id= Rental_invoices.tenancy_id"+
                      "  left Join RentalPayments on Rental_invoices._id=RentalPayments.invoice_id"+                                           
                     " Group by Apartments._id";
  //Log.e("sql",selectQuery);
//String[] args = new String[]{year};
SQLiteDatabase db = this.getReadableDatabase();	    
Cursor c = db.rawQuery(selectQuery, null);
if(c.moveToNext())
{
	do
	{
		AnnualTotals t= new AnnualTotals();
		t.setNumber((c.getString(c.getColumnIndex("N"))));
		//Log.e("room", c.getString(c.getColumnIndex("N")));
		t.setInvoices(Double.parseDouble(c.getString(c.getColumnIndex("Invoices"))));
		t.setReceipts(Double.parseDouble(c.getString(c.getColumnIndex("Amount"))));
		a.add(t);
	}while(c.moveToNext());
		
	
}
	
return a;
	
}
public List<AnnualTotals> getLocationBlockSales(int locationid)


{
	 List<AnnualTotals> a= new ArrayList<AnnualTotals>();
	 String selectQuery =" SELECT Apartments._id,Apartments.Number as N, Sum(Case when  Rental_invoices.rental isnull then  0 else Rental_invoices.rental  end ) as Invoices,"+
                         "  Sum(Case when  RentalPayments.Amount isnull then  0 else RentalPayments.Amount end  ) as Amount"+
                         " FROM Apartments"+
                        " inner join Tenancy on Apartments._id=Tenancy.apartment_id  "+                     
                       " inner Join Rental_invoices  on Tenancy._id= Rental_invoices.tenancy_id"+
                      "  left Join RentalPayments on Rental_invoices._id=RentalPayments.invoice_id"+
                      " inner join ApartmentBlocks on ApartmentBlocks._id=Apartments.block_id"+
                       " Where ApartmentBlocks.location_id " + "=" + locationid+
                       " Or ApartmentBlocks._id " + "=" + locationid+
                     " Group by Apartments._id";
  //Log.e("sql",selectQuery);
//String[] args = new String[]{year};
SQLiteDatabase db = this.getReadableDatabase();	    
Cursor c = db.rawQuery(selectQuery, null);
if(c.moveToNext())
{
	do
	{
		AnnualTotals t= new AnnualTotals();
		t.setNumber((c.getString(c.getColumnIndex("N"))));
		//Log.e("room", c.getString(c.getColumnIndex("N")));
		t.setInvoices(Double.parseDouble(c.getString(c.getColumnIndex("Invoices"))));
		t.setReceipts(Double.parseDouble(c.getString(c.getColumnIndex("Amount"))));
		a.add(t);
	}while(c.moveToNext());
		
	
}
	
return a;            
	
}

public List<AnnualTotals> getLocationBlockSales(int blockid,int locationid)


{
	 List<AnnualTotals> a= new ArrayList<AnnualTotals>();
	 String selectQuery =" SELECT Apartments._id,Apartments.Number as N, Sum(Case when  Rental_invoices.rental isnull then  0 else Rental_invoices.rental  end ) as Invoices,"+
                         "  Sum(Case when  RentalPayments.Amount isnull then  0 else RentalPayments.Amount end  ) as Amount"+
                         " FROM Apartments"+
                        " inner join Tenancy on Apartments._id=Tenancy.apartment_id  "+                     
                       " inner Join Rental_invoices  on Tenancy._id= Rental_invoices.tenancy_id"+
                      "  left Join RentalPayments on Rental_invoices._id=RentalPayments.invoice_id"+
                      " inner join ApartmentBlocks on ApartmentBlocks._id=Apartments.block_id"+
                       " Where ApartmentBlocks.location_id " + "=" + locationid+
                       " Or ApartmentBlocks._id " + "=" + blockid+
                     " Group by Apartments._id";
 // Log.e("sql",selectQuery);
//String[] args = new String[]{year};
SQLiteDatabase db = this.getReadableDatabase();	    
Cursor c = db.rawQuery(selectQuery, null);
if(c.moveToNext())
{
	do
	{
		AnnualTotals t= new AnnualTotals();
		t.setNumber((c.getString(c.getColumnIndex("N"))));
		//Log.e("room", c.getString(c.getColumnIndex("N")));
		t.setInvoices(Double.parseDouble(c.getString(c.getColumnIndex("Invoices"))));
		t.setReceipts(Double.parseDouble(c.getString(c.getColumnIndex("Amount"))));
		a.add(t);
	}while(c.moveToNext());
		
	
}
	
return a;
	
}


public List<UnPaidInvoices> GetUnPaidInvoices(String mydate){
	List<UnPaidInvoices> blocks= new ArrayList<UnPaidInvoices>();
	 SimpleDateFormat dps = new SimpleDateFormat(
	            "yyyy-MM-dd", Locale.getDefault());
	    String selectQuery ="Select  Rental_invoices._id as ID,Tenants.FirstName as F,  Apartments.Number as Number,  "+                     
	    		             " Tenants.SurName as S,Tenants.Mobile1 as M,Tenants.Mobile2 as M1,"+
	    		            "Rental_invoices.rental as Rent,Rental_invoices.startdate as SD,"+
	    		           " Rental_invoices.enddate as ED,Rental_invoices.duedate as DD"+
	    		          " from Rental_invoices "+
	    		         " inner join Tenancy on Tenancy._id=Rental_invoices.tenancy_id  "+
	    		        " inner join Tenants on Tenants._id=Tenancy.tenant_id "+
	    		      " inner join Apartments on Apartments._id=Tenancy.apartment_id "+
	    		    "left join RentalPayments on RentalPayments.invoice_id=Rental_invoices._id "+
	    		   " Where  RentalPayments.invoice_id is null " + 
	    		    " and Rental_invoices.duedate " + "<=?";

	    //Log.e("unpaid", selectQuery);	 
	    String[] args= new String[]{mydate};
	    
	    SQLiteDatabase db = this.getReadableDatabase();
	    Cursor c = db.rawQuery(selectQuery, args);
	 if(c.moveToFirst()){
		do{
			UnPaidInvoices B =new UnPaidInvoices();
			B.setInvoice_id(Integer.parseInt(c.getString(c.getColumnIndex("ID"))));
			B.setFirstName(c.getString(c.getColumnIndex("F")));
			B.setSurname(c.getString(c.getColumnIndex("S")));
			B.setFullName(B.getFirstName(),B.getSurname());
			if(c.getString(c.getColumnIndex("M"))==null)
			{
				B.setMobile("0");
			}
			
			else
			{
				B.setMobile(c.getString(c.getColumnIndex("M")));
			}
			if(c.getString(c.getColumnIndex("M1"))==null)
			{
				B.setMobile2("0");
			}
			
			else
			{
				B.setMobile2(c.getString(c.getColumnIndex("M1")));
			}
			
			
			try {
				B.setStart_date(dps.parse(c.getString(c.getColumnIndex("SD"))));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				B.setEnd_date(dps.parse(c.getString(c.getColumnIndex("ED"))));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				if(c.getString(c.getColumnIndex("DD"))!=null)
				{
					
				B.setDatedue(dps.parse(c.getString(c.getColumnIndex("DD"))));
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			B.setMonthly_rental(Double.parseDouble(c.getString(c.getColumnIndex("Rent"))));
			B.setApartment(c.getString(c.getColumnIndex("Number")));
			B.Period();
			blocks .add(B);
		} while(c.moveToNext());
		}
	 c.close();
	 //db.close();
	 
	 return blocks;
}
public List<ActiveTenancy> getUnInvoicedRooms(String mydate)
{
	List<ActiveTenancy> payments= new ArrayList<ActiveTenancy>();
	
	    String sql ="SELECT Apartments.Number as N,Tenants.FirstName as F,Tenants.SurName as S ,"+
	    		   " (select Max(Rental_invoices.enddate)  from Rental_invoices inner Join Tenancy on Tenancy._id=Rental_invoices.tenancy_id "+
	    		  " where Tenancy.tenant_id=Tenants._id ) as D,Tenancy._id as ID," +
	    		   " Tenancy.startdate as SS,Tenancy.enddate as ED"+
	    		  " from Tenants "+	    		  
	    		  " inner join Tenancy on Tenancy.tenant_id=Tenants._id "+
	    		 " inner join Apartments on Apartments._id=Tenancy.apartment_id "+
	    		" where Tenancy._id not  in(select  Rental_invoices.tenancy_id from Rental_invoices where enddate " + ">=?"+
	    		 ")"+
	    	    " and Tenancy.InActive=0";
  String[] args= new String[]{mydate};
	    Log.e("sql", sql);
	    SQLiteDatabase db = this.getReadableDatabase();
	    Cursor c = db.rawQuery(sql, args);
	    if(c.moveToFirst()){
			do{
				ActiveTenancy a= new ActiveTenancy();
				a.setTenancy_id(Integer.parseInt(c.getString(c.getColumnIndex("ID"))));
				a.setFirstName(c.getString(c.getColumnIndex("F")));
				a.setSurname(c.getString(c.getColumnIndex("S")));
				a.setApartment(c.getString(c.getColumnIndex("N")));
			
				if(c.getString(c.getColumnIndex("D"))!=null)
				{
					//Log.e("dddd",c.getString(c.getColumnIndex("D")));
				try {
					a.setLastInvoiceDate(fm.parse(c.getString(c.getColumnIndex("D"))));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
				try {
					a.setEnd_date(fm.parse(c.getString(c.getColumnIndex("ED"))));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					a.setStart_date(fm.parse(c.getString(c.getColumnIndex("SS"))));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				payments .add(a);
			} while(c.moveToNext());
			}
		 c.close();
	return payments;
	
}
public List<RentalPaymentStatus> GetRentalPaymentsStatus(int tenancyid){
	List<RentalPaymentStatus> payments= new ArrayList<RentalPaymentStatus>();
	 SimpleDateFormat dps = new SimpleDateFormat(
	            "yyyy-MM-dd", Locale.getDefault());
	    String selectQuery 
	    ="SELECT Tenants.FirstName as F,Tenants.Surname as S,Tenants.Mobile1 as M,Tenants.Mobile2 as M2,"+
	     "(Case when Rental_invoices.rental isnull then 0 else Rental_invoices.rental end) as Rent ,"+
	      "Rental_invoices.startdate as SD,Rental_invoices.enddate as ED,"+
	      "(Case When RentalPayments.Amount isnull then 0 else RentalPayments.Amount end) as Amount,"+
	      "Apartments.Number as N ,RentalPayments.PaymentDate as D,Rental_invoices.duedate as DD "+
	      "from  Rental_invoices "+
	     "left join RentalPayments on RentalPayments.invoice_id=Rental_invoices._id "+
	     "inner join Tenancy on Tenancy._id=Rental_invoices.tenancy_id "+
	    "inner join Tenants on Tenants._id=Tenancy.tenant_id "+
	    "inner join Apartments on Apartments._id=Tenancy.apartment_id " + 
	    " Where  Tenancy._id"+ "="+ tenancyid +
	    " Order by Rental_invoices.startdate desc";

	   // Log.e("Payment Status", selectQuery);	 
	    
	    SQLiteDatabase db = this.getReadableDatabase();
	    Cursor c = db.rawQuery(selectQuery, null);
	 if(c.moveToFirst()){
		do{
			
			RentalPaymentStatus B =new RentalPaymentStatus();			
			B.setFirstName(c.getString(c.getColumnIndex("F")));
			B.setSurname(c.getString(c.getColumnIndex("S")));
			try {
				B.setDuedate(fm.parse(c.getString(c.getColumnIndex("DD"))));
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			B.setFullName(B.getFirstName(),B.getSurname());
			if(c.getString(c.getColumnIndex("M"))==null)
			{
				B.setMobile("0");
			}
			
			else
			{
				B.setMobile(c.getString(c.getColumnIndex("M")));
			}
			if(c.getString(c.getColumnIndex("M2"))==null)
			{
				B.setMobile2("0");
			}
			
			else
			{
				B.setMobile2(c.getString(c.getColumnIndex("M2")));
			}
			
			
			try {
				B.setStart_date(dps.parse(c.getString(c.getColumnIndex("SD"))));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				B.setEnd_date(dps.parse(c.getString(c.getColumnIndex("ED"))));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			try {
				if(c.getString(c.getColumnIndex("D"))!=null)
				{
				B.setDatepaid(dps.parse(c.getString(c.getColumnIndex("D"))));
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			B.setMonthly_rental(Double.parseDouble(c.getString(c.getColumnIndex("Rent"))));
			B.setApartment(c.getString(c.getColumnIndex("N")));
			B.setAmoutpaid(Double.parseDouble(c.getString(c.getColumnIndex("Amount"))));
			payments .add(B);
		} while(c.moveToNext());
		}
	 c.close();
	 //db.close();
	 
	 return payments;
}

public long SetTenancyInActive(int t_id,String dateinactive)
{
	
	SQLiteDatabase db=this.getWritableDatabase();	
	String SQL2 =" update Tenancy set enddate"+ "='" +  dateinactive + "' " + "," + "InActive"+ "= 1 " + " Where " + KEY_ID + " =" + t_id;
	
	try{
	db.beginTransaction();		
	db.execSQL(SQL2);	
	
	db.setTransactionSuccessful();
	return 1;
	} catch( Exception e)
	{
		Log.e("update fail", e.toString());		
		return 0;
	}
	finally{
		db.endTransaction();
	}
	
}

public List<ActiveTenancy> GetInActiveTenancy(){
	List<ActiveTenancy> blocks= new ArrayList<ActiveTenancy>();
	
	    String selectQuery =" Select Tenants._id as ID, Tenancy._id as TID,Tenants.FirstName as F,"+
                             " Tenants.SurName as S,Tenants.Mobile1 as M,Tenants.Mobile2 as M1,"+
	                        " Tenancy.startdate as SD,Tenancy.enddate as ED,Tenancy.monthlyrental as Rent,"+
	                   "  Apartments.Number as Apartment, Apartments._id as RoomID,"+
                       " ApartmentBlocks.Block as B, Locations.Location as L "+
                         "  from Locations "+
                 "   inner join ApartmentBlocks on Locations._id=ApartmentBlocks.location_id "+
                 "   inner Join Apartments on Apartments.block_id=ApartmentBlocks._id "+
	             "  inner join Tenancy on Tenancy.apartment_id=Apartments._id "+
	             " inner join Tenants on Tenants._id=Tenancy.tenant_id "+
	            "  Where Tenancy.InActive=1 ";
	   // Log.e("LOG Tenancy Collections", selectQuery);	 
	    
	    SQLiteDatabase db = this.getReadableDatabase();
	    Cursor c = db.rawQuery(selectQuery, null);
	 if(c.moveToFirst()){
		do{
			ActiveTenancy B =new ActiveTenancy();
			B.setApartmentid(Integer.parseInt(c.getString(c.getColumnIndex("RoomID"))));
			B.set_id(Integer.parseInt(c.getString(c.getColumnIndex("ID"))));	
			B.setTenancy_id(Integer.parseInt(c.getString(c.getColumnIndex("TID"))));
			B.setFirstName(c.getString(c.getColumnIndex("F")));
			B.setBlock(c.getString(c.getColumnIndex("B")));
			B.setLocation(c.getString(c.getColumnIndex("L")));			
			B.setSurname(c.getString(c.getColumnIndex("S")));			
			B.setFullName(B.getFirstName(),B.getSurname());
			if(c.getString(c.getColumnIndex("M"))==null)
			{
				B.setMobile("0");
			}
			
			else
			{
				B.setMobile(c.getString(c.getColumnIndex("M")));
			}
			if(c.getString(c.getColumnIndex("M1"))==null)
			{
				B.setMobile2("0");
			}
			
			else
			{
				B.setMobile2(c.getString(c.getColumnIndex("M1")));
			}
			
			
			try {
				B.setStart_date(fm.parse(c.getString(c.getColumnIndex("SD"))));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			B.setMonthly_rental(Double.parseDouble(c.getString(c.getColumnIndex("Rent"))));
			B.setApartment(c.getString(c.getColumnIndex("Apartment")));
			try {
				B.setEnd_date(fm.parse(c.getString(c.getColumnIndex("ED"))));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			blocks .add(B);
		} while(c.moveToNext());
		}
	 c.close();
	 //db.close();
	 
	 return blocks;
}
public double totalUnpaidInvoices() 
{
	 String selectQuery = " select    sum(Rental_invoices.rental )as Rent " +	                       
                          " from Rental_invoices "+	                      
                          " left join RentalPayments on RentalPayments.invoice_id=Rental_invoices._id "+
                          "  Where  RentalPayments.invoice_id is null";
 Log.e("LOG Tenancy Collections", selectQuery);	 

SQLiteDatabase db = this.getReadableDatabase();
double amount=0;
Cursor c = db.rawQuery(selectQuery, null);
if(c.moveToFirst()){
	amount= Double.parseDouble(c.getString(c.getColumnIndex("Rent")));
	
}
c.close();
return amount;
}
	public double AnnualRental(String  startdate,String enddate)
	{
		String selectQuery = "SELECT Sum(Amount) as T from RentalPayments " +
				              " Where PaymentDate between"+"?"+ "and"+"?";
		//.e("LOG Tenancy Collections", selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();
		double amount=0;
		Cursor c = db.rawQuery(selectQuery, new String[]{String.valueOf(startdate),String.valueOf(enddate)});
		if(c.moveToFirst()){
			amount= Double.parseDouble(c.getString(c.getColumnIndex("T")));

		}
		c.close();
		return amount;
	}
	public List<RoomInvoicingStatus> InvoicingStatus()
	{
        List<RoomInvoicingStatus> statuses=new ArrayList<RoomInvoicingStatus>();
		String sql="SELECT  Apartments.Number as A,Max(Rental_invoices.enddate) as B  ,ApartmentBlocks.Block as C,Locations.Location as D"+
		           " from Locations"+
		            " Inner Join ApartmentBlocks on Locations._id=ApartmentBlocks.location_id"+
		              " Inner Join Apartments on ApartmentBlocks._id=Apartments.block_id"+
		              " inner join Tenancy on Apartments._id=Tenancy.apartment_id "+
		              " inner join Rental_invoices on Tenancy._id=Rental_invoices.tenancy_id"+
		              " Group by Apartments.Number";

		//Log.e("sql",sql);
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(sql, null);

		if(c.moveToFirst()){


            do{
                RoomInvoicingStatus s=new RoomInvoicingStatus();
				s.setNumber(c.getString(c.getColumnIndex("A")));
				s.setBlock(c.getString(c.getColumnIndex("C")));
				s.setLocation(c.getString(c.getColumnIndex("D")));
				s.setInvoiceDate(c.getString(c.getColumnIndex("B")));
                statuses.add(s);
			}while (c.moveToNext());
		}
    c.close();
		return statuses;
	}
}



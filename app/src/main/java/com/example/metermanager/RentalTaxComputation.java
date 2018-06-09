package com.example.metermanager;

import android.app.Activity;
import android.content.Intent;
import android.net.ParseException;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import meter.manager.helper.DatabaseHelperClass;

public class RentalTaxComputation extends Activity {
    Button button;
    EditText startdate;
    EditText endDate;
    EditText mythreshold;
    EditText mytaxrate;
    EditText percent;
    DatabaseHelperClass db;
    TextView guide;
    DecimalFormat df = new DecimalFormat("#,###,###,###");
    SimpleDateFormat fm =new SimpleDateFormat("yyyy-MM-dd");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rental_tax_compute);
        db= DatabaseHelperClass.getInstance(this);
        button=(Button)findViewById(R.id.Compute);
        startdate=(EditText) findViewById(R.id.editTextYearStart);
        endDate=(EditText) findViewById(R.id.editTextYearEnd);
       mythreshold=(EditText) findViewById(R.id.editTextAnnualT);
        mytaxrate=(EditText) findViewById(R.id.editTextTaxRate);
        //mythreshold.setText("2,800,000");
        percent=(EditText) findViewById(R.id.editTextAllowance);
        //percent.setText("20");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get start date
                String s=startdate.getText().toString().trim();
                //test date pattern
                //get end date
                String e= endDate.getText().toString().trim();
                if((db.IsvalidDate(s)==false)||(db.IsvalidDate(e)==false))
                {
                    CharSequence msg ="Enter Dates in format yyyy-mm-dd e.g 2016-12-31";
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(RentalTaxComputation.this, msg, duration);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    return;
                }
                Date sdate = null;
                try {
                    sdate = fm.parse(s);
                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

                Date edate = null;
                try {
                    edate = fm.parse(e);
                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

                //find out whether start date greater than end date;
                long startdate= sdate.getTime();
                long endate=edate.getTime();
                if(startdate>endate){
                    Toast.makeText(RentalTaxComputation.this,"End date should be after start date",Toast.LENGTH_LONG).show();
                    return;
                }
                //sif nothing entered
                if(TextUtils.isEmpty(percent.getText().toString())||TextUtils.isEmpty(mytaxrate.getText().toString())||
                        TextUtils.isEmpty(mythreshold.getText().toString())){
                    percent.setError("Required");
                    mytaxrate.setError("required");
                    mythreshold.setError("required");
                    return;
                }

                // get percentage tax applicable
                double percantage=Double.parseDouble(percent.getText().toString().trim())/100;
                double taxrate=Double.parseDouble(mytaxrate.getText().toString().trim())/100;
                // get annual threshold;
                double threshold=Double.parseDouble(mythreshold.getText().toString().trim().replace(",",""));

                double rental=db.AnnualRental(s,e);
                //Log.v("Rental Amont",df.format(rental));
                String out=getOutPut(s,e,percantage,threshold,rental,taxrate);
                Intent intent=new Intent(RentalTaxComputation.this,RentalTaxResults.class);
                intent.putExtra("result",out);
                startActivity(intent);

            }
        });
 guide=(TextView)findViewById(R.id.txtViewGuide);
        String s= "Enter both start date and end date in format yyyy-mm-dd e.g 2016-07-01 for 1st July 2016." + "\n\r"+
                   "The allowable cost percentage is currently 20%.Enter with out % symbol"+ "\n\r"+
                    "  Threshold is currently  UGX2,820,000,Tax rate is currently  20%. Enter with out % symbol"+"\n\r"+
                    "  The above given figures  might change  and  you should  consult the Income Tax Act";
        guide.setText(s);

    }

    private  String getOutPut(String start,String End,double percent,double threshold,double rent,double myrate)
    {
        // net is after deducting the percentage allowable as expenses
        double net =rent-(percent*rent);
        StringBuilder builder = new StringBuilder(64);
        // if net less than threshold...dont tax
        if (net>threshold) {
            double taxable = net - threshold;
            double tax = myrate * taxable;
            double allowable = percent * rent;
            builder.append("Your rental tax for the  year of income ");
            builder.append("starting " + start + " and ending " + End);
            builder.append(" is "+ "<b>" +" UGX " + df.format(tax)+"<b>" + "." + "\r\n");
            builder.append("Annual Gross Rent is"+ "<b>"+" UGX: " + df.format(rent)+"<b>" + "\r\n");
            builder.append("Annual Net  Rent is "+ "<b>" +"UGX: " + df.format(net)+"<b>" + "\r\n");
            builder.append("Allowable deduction is "+ "<b>"+" UGX: " + df.format(allowable)+"<b>" + "." + "\r\n");
            builder.append("Taxable amount is"+ "<b>"+"UGX: " + df.format(taxable)+"<b>");
        }else if(net<threshold)
        {
            builder.append("Your net rental income  for the  year of income ");
            builder.append("starting " + start + " and ending " + End);
            builder.append("<b>"+ " is   UGX " + df.format(net)+"<b>" + "." + "\r\n");
            builder.append("This is below the threshold of  UGX: " + df.format(threshold) + "\r\n");
            builder.append("Therefore no  tax payable to Uganda Revenue Authority"+"\r\n");
            builder.append("Your Annual Gross Rent is UGX: " + df.format(rent));


        }
       return  builder.toString();
    }
}

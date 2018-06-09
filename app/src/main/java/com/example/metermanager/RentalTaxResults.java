package com.example.metermanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.EditText;

public class RentalTaxResults extends Activity {
   EditText results;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rental_tax_results);
        results=(EditText)findViewById(R.id.txtResults);
        Intent i=getIntent();
        String myresults= i.getStringExtra("result");
        results.setText(Html.fromHtml(myresults));

    }
}

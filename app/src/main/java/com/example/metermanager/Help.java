package com.example.metermanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class Help extends Activity {
	
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.home);
	TextView tx= (TextView)
			findViewById(R.id.txtHelp);
	tx.setText(Html.fromHtml(getString(R.string.help)));
	tx.setMovementMethod(LinkMovementMethod.getInstance());
	
}

}

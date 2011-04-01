package com.huberstyle.DeVryAndroidScheduler;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.TextView;



public class DeVryAndroidScheduler extends Activity implements OnClickListener {
	
	//from :http://developer.android.com/guide/topics/ui/ui-events.html
	@Override
	 protected void onCreate(Bundle savedValues) {
		  super.onCreate(savedValues);
		 setContentView(R.layout.main);
		 
		  TextView txt1 = (TextView)findViewById(R.id.txtdept);
	     
		  try
		  {
		  txt1.setOnClickListener(new View.OnClickListener() {
	        	 @Override
	        	 public void onClick(View v) {
	        		/* Intent myIntent = new Intent(DeVryAndroidScheduler.this,
                             Departments.class);
                     //myIntent.putExtra("Department", txt1.getText().toString());
                     startActivity(myIntent);
                     //finish();*/
	        		 startActivity(new Intent(DeVryAndroidScheduler.this, Departments.class));
	        	 }
	        	 });
		  }
		  catch (Exception e)
		  {
			  //do something here with e...
			  
			  txt1.setText(e.getMessage());
		  }
	 }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	}
	
}


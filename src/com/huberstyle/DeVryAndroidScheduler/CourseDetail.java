package com.huberstyle.DeVryAndroidScheduler;




import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class CourseDetail extends ListActivity implements OnClickListener  {
	
	public class Cls {
	    String ClassName;
	    String Title;
	    String Day;
	    String Time;
	    String Room;
	}
 
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	 //example from : http://blogs.sitepoint.com/loading-twitter-data-into-android-with-lists/
        ArrayList<Cls> professors= new ArrayList<Cls>(); 
       // setContentView(R.layout.just);
        //from :http://blogs.sitepoint.com/loading-twitter-data-into-android-with-lists/
        HttpClient hc = new DefaultHttpClient();  
        HttpGet get = new  
      //  HttpGet("http://search.twitter.com/search.json?q=android");  
        	HttpGet("http://206.209.106.106/academics/registration/practice_schedule_mobile/getclasses2.asp?dept=undefined&term=SPR2011");
        HttpResponse rp;
		try {
			rp = hc.execute(get);
	        if(rp.getStatusLine().getStatusCode() == HttpStatus.SC_OK)  
	        {  
	                String result = EntityUtils.toString(rp.getEntity());  
	                JSONArray root = new JSONArray(result);  
	                //JSONArray results = root.getJSONArray(0);  
	                for (int i = 0; i < root.length(); i++) {
                        JSONObject session = root.getJSONObject(i);
                        Cls prof = new Cls();
                        prof.ClassName= session.getString("ClassName");
                        professors.add(prof);
	                }
	                ClsListAdaptor adaptor = new ClsListAdaptor(this,R.layout.justlist, professors); 
	        		 setListAdapter(adaptor);  
	        }       	

		} catch (Exception e) {
			Log.e("DeVryAndroidScheduler", "Error loading JSON", e); 		}  
        
    }
    @Override
    public void onClick(View v) {
    }
    
    private class ClsListAdaptor extends ArrayAdapter<Cls> {
        private ArrayList<Cls> professors;
        public ClsListAdaptor(Context context,
                                    int textViewResourceId,
                                    ArrayList<Cls> items) {
                 super(context, textViewResourceId, items);
                 this.professors = items;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
                View v = convertView;
                if (v == null) {
                        LayoutInflater vi = (LayoutInflater) getSystemService                        
(Context.LAYOUT_INFLATER_SERVICE);
                        v = vi.inflate(R.layout.classeslist , null);
                }

               final Cls o = professors.get(position);
                TextView tt = (TextView) v.findViewById(R.id.classname);
                tt.setText(o.ClassName);
                
                v.setOnClickListener(new OnClickListener() {
                    public void onClick(View arg0) { //--clickOnListItem
                        Intent myIntent = new Intent(CourseDetail.this,
                                CourseDetail.class);
                        myIntent.putExtra("ClsName", o.ClassName);
                        startActivity(myIntent);
                        finish();
                    }
                });
                return v;
        }
}}
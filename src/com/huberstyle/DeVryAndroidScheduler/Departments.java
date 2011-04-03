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

public class Departments extends ListActivity implements OnClickListener {
	
	public class Course {
        String CourseName;
}
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //example from : http://blogs.sitepoint.com/loading-twitter-data-into-android-with-lists/
        ArrayList<Course> professors= new ArrayList<Course>(); 
       // setContentView(R.layout.just);
        //from :http://blogs.sitepoint.com/loading-twitter-data-into-android-with-lists/
        HttpClient hc = new DefaultHttpClient();  
        HttpGet get = new  
      //  HttpGet("http://search.twitter.com/search.json?q=android");  
        	HttpGet("http://206.209.106.106/academics/registration/practice_schedule_mobile/getdepartments2.asp?dept=undefined&term=SPR2011");
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
                        Course prof = new Course();
                        prof.CourseName= session.getString("CourseName");
                        professors.add(prof);
	                }
	                CourseListAdaptor adaptor = new CourseListAdaptor(this,R.layout.departmentlist, professors); 
	        		 setListAdapter(adaptor);  
	        }       	

		} catch (Exception e) {
			Log.e("DeVryAndroidScheduler", "Error loading JSON", e); 		}  
        
    }
    
    @Override
    public void onClick(View v) {
    }
    
    private class CourseListAdaptor extends ArrayAdapter<Course> {
        private ArrayList<Course> professors;
        public CourseListAdaptor(Context context,
                                    int textViewResourceId,
                                    ArrayList<Course> items) {
                 super(context, textViewResourceId, items);
                 this.professors = items;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
                View v = convertView;
                if (v == null) {
                        LayoutInflater vi = (LayoutInflater) getSystemService                        
(Context.LAYOUT_INFLATER_SERVICE);
                        v = vi.inflate(R.layout.departmentlist , null);
                }

               final Course o = professors.get(position);
                TextView tt = (TextView) v.findViewById(R.id.text1);
                tt.setText(o.CourseName);
                
                v.setOnClickListener(new OnClickListener() {
                    public void onClick(View arg0) { //--clickOnListItem
                        Intent myIntent = new Intent(Departments.this,
                                Courses.class);
                        myIntent.putExtra("CourseName", o.CourseName);
                        startActivity(myIntent);
                        finish();
                    }
                });
                return v;
        }
}
}

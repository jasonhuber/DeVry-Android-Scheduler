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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Courses extends ListActivity implements OnClickListener  {
	
	public class Cls {
	    String ClassName;
	    String Title;	    
	}
 
	protected void SendToastMessage(String m)
	{
		Context context = getApplicationContext();
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context, m, duration);
		toast.show();
	}
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	   super.onCreate(savedInstanceState);
    	 //example from : http://blogs.sitepoint.com/loading-twitter-data-into-android-with-lists/
        ArrayList<Cls> classes= new ArrayList<Cls>(); 
       // setContentView(R.layout.just);
        //from :http://blogs.sitepoint.com/loading-twitter-data-into-android-with-lists/
        HttpClient hc = new DefaultHttpClient();  
        String URI = "";
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) 
        {
            URI = "http://206.209.106.106/academics/registration/practice_schedule_mobile/getcourses2.asp?term=SPR2011&dept=" + bundle.getString("CourseName");
        }
        else
        { 	
        	SendToastMessage("Sorry no Department was sent to retrieve Courses.");
        }

        HttpGet get = new HttpGet(URI);
        
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
                        Cls cls = new Cls();
                        cls.ClassName= session.getString("ClassName");
                        cls.Title= session.getString("Title");
                        classes.add(cls);
	                }
	                ClsListAdaptor adaptor = new ClsListAdaptor(this,R.layout.courseslist, classes); 
	        		 setListAdapter(adaptor);  
	        }  
	        else
	        {
	        	SendToastMessage("Error retrieving the JSON Feed. Please ensure you can access http://bit.ly/devrymobile");
	        }

		} catch (Exception e) {
			
        	SendToastMessage("An Error has ocurred." + e.getMessage());
		}  
        
    }
    @Override
    public void onClick(View v) {
    }
    
    private class ClsListAdaptor extends ArrayAdapter<Cls> {
        private ArrayList<Cls> clses;
        public ClsListAdaptor(Context context,
                                    int textViewResourceId,
                                    ArrayList<Cls> items) {
                 super(context, textViewResourceId, items);
                 this.clses = items;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
                View v = convertView;
                if (v == null) {
                        LayoutInflater vi = (LayoutInflater) getSystemService (Context.LAYOUT_INFLATER_SERVICE);
                        v = vi.inflate(R.layout.courseslist, null);
                }

               final Cls o = clses.get(position);
                TextView txtClassName = (TextView) v.findViewById(R.id.classname);
                txtClassName.setText(o.ClassName);
           /*     TextView txtTitle = (TextView) v.findViewById(R.id.Title);
                txtTitle.setText(o.Title);
             */   
                v.setOnClickListener(new OnClickListener() {
                    public void onClick(View arg0) { //--clickOnListItem
                        Intent myIntent = new Intent(Courses.this,
                                Classes.class);
                        myIntent.putExtra("ClsName", o.ClassName);
                        myIntent.putExtra("dept", o.ClassName.substring(0,o.ClassName.indexOf(" ")));
                        startActivity(myIntent);
                        finish();
                    }
                });
                return v;
        }
}}
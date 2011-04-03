package com.huberstyle.DeVryAndroidScheduler;
import java.util.ArrayList;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class CourseDetail extends ListActivity implements OnClickListener  {
	
	public class Cls {
	    String content;
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
        ArrayList<Cls> professors= new ArrayList<Cls>(); 
       // setContentView(R.layout.just);
        //from :http://blogs.sitepoint.com/loading-twitter-data-into-android-with-lists/
        HttpClient hc = new DefaultHttpClient();  
        String URI = "";
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) 
        {
            URI = "http://206.209.106.106/academics/registration/practice%5Fschedule%5Fmobile/getclass.asp?term=SPR2011&courseid=" + bundle.getString("ClsName").replace(" ", "%20");
        }
        else
        { 	
        	SendToastMessage("Sorry no Department was sent to retrieve Courses.");
        }
        HttpGet get = new  HttpGet(URI);
        HttpResponse rp;
		try {
			rp = hc.execute(get);
	        if(rp.getStatusLine().getStatusCode() == HttpStatus.SC_OK)  
	        {  
	                String result = EntityUtils.toString(rp.getEntity());  
	                //JSONArray results = root.getJSONArray(0);  
                    Cls prof = new Cls();
                    prof.content= result;
                    professors.add(prof);
	                ClsListAdaptor adaptor = new ClsListAdaptor(this,R.layout.coursedetail, professors); 
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
                        v = vi.inflate(R.layout.coursedetail, null);
                }
               final Cls o = professors.get(position);
                TextView tt = (TextView) v.findViewById(R.id.classname);
                tt.setText(o.content);
//no onclick listener. User can press back to go back to list.... right now at least.
                //in the future I guess I would like to have a "go back button" or something.
                return v;
        }
}}
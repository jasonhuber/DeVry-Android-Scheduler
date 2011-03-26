package com.huberstyle.DeVryAndroidScheduler;


import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class DeVryAndroidScheduler extends ListActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //example from : http://blogs.sitepoint.com/loading-twitter-data-into-android-with-lists/
        
        String[] elements = {"Line 1", "Line 2"};  
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, elements));  
        //setContentView(R.layout.main);
    }
}
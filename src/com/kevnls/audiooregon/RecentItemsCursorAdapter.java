package com.kevnls.audiooregon;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RecentItemsCursorAdapter extends SimpleCursorAdapter {

	public RecentItemsCursorAdapter(Context context, int layout, Cursor c,
			String[] from, int[] to, int flags) {
		super(context, layout, c, from, to, flags);
	}

	@Override 
	public View newView (Context context, Cursor cursor, ViewGroup parent){
		
	      int row = cursor.getPosition();
	      LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	      
	      if (row == 0 || ((row + 1) % 5) == 0 || (row % 5) == 0){
	           return inflater.inflate(R.layout.recent_items_large_template, null);
	      } else {
	    	  return inflater.inflate(R.layout.recent_items_small_template, null);
	      }
	 }
}

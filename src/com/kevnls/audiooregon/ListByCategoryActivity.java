package com.kevnls.audiooregon;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class ListByCategoryActivity extends ListActivity {
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    new UpdateDatabase().execute();
  }

  @Override
  protected void onListItemClick(ListView l, View v, int position, long id) {
    String item = (String) getListAdapter().getItem(position);
    Toast.makeText(this, item + " selected", Toast.LENGTH_LONG).show();
  }
  
  private class UpdateDatabase extends AsyncTask<Void, Integer, Cursor> {

	protected Cursor doInBackground(Void... params) {
		HelperUtilities hu = new HelperUtilities();
		hu.RefreshLocalData(ListByCategoryActivity.this);
		return hu.GetRecordItemsFromDB(null, ListByCategoryActivity.this);
	}
	
	protected void onProgressUpdate(Integer... progress) {
        //TODO do something with the progress[0] value
		//on the UI thread
    }

    protected void onPostExecute(Cursor cursor) {
//    	ArrayAdapter<Item> adapter = new ArrayAdapter<Item>(ListByCategoryActivity.this,
//	        R.layout.list_view_template, R.id.listTitle, result);
//    	setListAdapter(adapter);
    	
    	//TODO get the cursor and iterate over the DB result set
    	
    	String[] fromColumns = { "title", "description", "image_file_path" };
    	
    	int[] toViews = {R.id.listTitle, R.id.listDescription, R.id.listImage};
    	
    	SimpleCursorAdapter adapter = new SimpleCursorAdapter(ListByCategoryActivity.this, 
    			R.layout.list_view_template, cursor, fromColumns, toViews, 0);
    	ListView listView = getListView();
    	listView.setAdapter(adapter);
    }

  }
}

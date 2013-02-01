package com.kevnls.audiooregon;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends ListActivity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        new CreateList().execute();
    } 
	
    @Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		super.onListItemClick(l, v, position, id);
		
		String clickedId = ((TextView)v.findViewById(R.id.listId)).getText().toString();
	    
	    Intent intent = new Intent(this, DetailActivity.class);
	    intent.putExtra("id", clickedId);
	    startActivity(intent);
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    
    @Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
    	
		switch (item.getItemId()) {
		case R.id.menu_update:
			startActivity(new Intent(this, UpdateDataActivity.class));
			finish();
			break;
		case R.id.menu_about:
			startActivity(new Intent(this, AboutActivity.class));
			break;
		}	
		return true;
	}
    
	private class CreateList extends AsyncTask<Void, Integer, Cursor> {

		protected Cursor doInBackground(Void... params) {

			HelperUtilities hu = new HelperUtilities();
			return hu.GetRecordItemsFromDB(null, MainActivity.this);
		}

		protected void onPostExecute(Cursor cursor) {

			String[] fromColumns = { "id", "title", "description", "image_file_path" };
			int[] toViews = { R.id.listId, R.id.listTitle, R.id.listDescription, R.id.listImage };

			RecentItemsCursorAdapter adapter = new RecentItemsCursorAdapter(
					MainActivity.this, 0,
					cursor, fromColumns, toViews, 0);

			ListView listView = getListView();
			listView.setAdapter(adapter);
		}
	}
}
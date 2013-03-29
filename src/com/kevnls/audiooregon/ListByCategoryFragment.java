package com.kevnls.audiooregon;

import android.app.ListFragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class ListByCategoryFragment extends ListFragment {
	
    @Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		new CreateList().execute();
	} 
    
    @Override
	public void onListItemClick(ListView l, View v, int position, long id) {

		super.onListItemClick(l, v, position, id);
		
		//TODO this probably isn't looking for the right thing
		//String clickedId = ((TextView)v.findViewById(R.id.categoryList)).getText().toString();
		ListFragment listFragment = (ListFragment)getFragmentManager().findFragmentById(R.id.categoryList);
		String clickedId = ((TextView)listFragment.getView()).getText().toString();
	    
	    Intent intent = new Intent(ListByCategoryFragment.this.getActivity(), DetailActivity.class);
	    intent.putExtra("id", clickedId);
	    startActivity(intent);
	}

	private class CreateList extends AsyncTask<Void, Integer, Cursor> {

		protected Cursor doInBackground(Void... params) {

			HelperUtilities hu = new HelperUtilities();
			Cursor returnCursor = hu.GetRecordItemsFromDB(null, ListByCategoryFragment.this.getActivity());

			return returnCursor;
		}

		protected void onPostExecute(Cursor cursor) { 

			String[] fromColumns = { "id", "title", "description", "image_file_path" };
			int[] toViews = { R.id.listId, R.id.listTitle, R.id.listDescription, R.id.listImage };

			CategoryItemsCursorAdapter adapter = new CategoryItemsCursorAdapter(
					ListByCategoryFragment.this.getActivity(), 0,
					cursor, fromColumns, toViews, 0);
			
			ListFragment listFragment = (ListFragment)getFragmentManager().findFragmentById(R.id.categoryList);	    
		    listFragment.setListAdapter(adapter);
			adapter.notifyDataSetChanged();
		}
	}
}

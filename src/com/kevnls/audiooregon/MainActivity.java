package com.kevnls.audiooregon;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //this could be used to update data asynchronously
        //new UpdateData().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    
    @Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_about:
			startActivity(new Intent(this, AboutActivity.class));
			break;
		}	
		return true;
	}

	class UpdateData extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			//what to do asynchronously
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			//callback function
			//the result variable is the return of the doInBackground function
			super.onPostExecute(result);
		}
    	
    }
}

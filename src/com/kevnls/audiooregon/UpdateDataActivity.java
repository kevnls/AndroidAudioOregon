package com.kevnls.audiooregon;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;

public class UpdateDataActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        new UpdateData().execute();
    }
    
    class UpdateData extends AsyncTask<Void, Integer, Boolean> {

		protected Boolean doInBackground(Void... params) {
			HelperUtilities hu = new HelperUtilities();
			hu.RefreshLocalData(UpdateDataActivity.this);
			return true;
		}

		@Override
		protected void onPostExecute(Boolean result) {

			super.onPostExecute(result);
		}   	
    }
}
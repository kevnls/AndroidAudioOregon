package com.kevnls.audiooregon;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ProgressBar;

public class UpdateDataActivity extends Activity {
	
	private int maxProgress;
	private ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        new UpdateData().execute();
    }
    
    class UpdateData extends AsyncTask<Void, Integer, Boolean> {

		protected Boolean doInBackground(Void... params) {

			HelperUtilities hu = new HelperUtilities();
			
			//get records from REST service
			ArrayList<RecordItem> records = hu.GetRecordItemsFromRestService();
			
			//this tells me the max size of the progress indicator
			//I'm tacking-on 2 because I'll update progress after each delete operation too
			maxProgress = records.size() + 2;
			progressBar = (ProgressBar)findViewById(R.id.updateProgressBar);
			progressBar.setMax(maxProgress);
			int progress = 0;

			HelperUtilities.DatabaseHelper dbHelper = hu.new DatabaseHelper(UpdateDataActivity.this);
			SQLiteDatabase db = dbHelper.getWritableDatabase();
		
			File contextDirectory = new File(UpdateDataActivity.this.getFilesDir().toString());
			String fileDirectory = contextDirectory.toString() + "/";
			
			//delete DB records
			db.delete("items", null, null);
			progress +=1;
			publishProgress(progress);
			
			//delete all local files
			File[] files = contextDirectory.listFiles();
			
			for (File file : files)
			{
				file.delete();
			}
			progress +=1;
			publishProgress(progress);

			for (RecordItem item : records) {

				ContentValues dbMap = new ContentValues();
				
				//save local files
				if (item.getImageFilename() != "null")
				{
					hu.SaveRemoteFile(UpdateDataActivity.this, item.getImageFilename(), item.getId() + ".jpg", fileDirectory);
				}
				
				if (item.getAudioFilename() != "null")
				{
					hu.SaveRemoteFile(UpdateDataActivity.this, item.getAudioFilename(), item.getId() + ".mp3", fileDirectory);
				}

				//save DB records
				dbMap.put("id", item.getId());
				dbMap.put("category", item.getCategory());
				dbMap.put("title", item.getTitle());
				dbMap.put("description", item.getDescription());
				dbMap.put("latitude", item.getLatitude());
				dbMap.put("longitude", item.getLongitude());
				dbMap.put("image_file_path", fileDirectory.toString() + item.getId() + ".jpg");
				dbMap.put("audio_file_path", fileDirectory.toString() + item.getId()+ ".mp3");

				long rowId = db.insert("items", null, dbMap);
				
		        if (rowId == -1) {
		        	throw new SQLException("Failed to insert row");
		        }
		        progress +=1;
				publishProgress(progress);
			}
			return true;
		}
		
		@Override
		protected void  onProgressUpdate(Integer... values) 
        {
			
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);
        }

		@Override
		protected void onPostExecute(Boolean result) {

			super.onPostExecute(result);
			startActivity(new Intent(UpdateDataActivity.this, MainActivity.class));
			finish();
		}   	
    }
}
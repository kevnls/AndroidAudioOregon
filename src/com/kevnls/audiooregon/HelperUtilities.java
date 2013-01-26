package com.kevnls.audiooregon;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HelperUtilities {

	public ArrayList<RecordItem> GetRecordItemsFromRestService() {

		ArrayList<RecordItem> items = new ArrayList<RecordItem>();

		try {
			URL url = new URL("http://audiooregon.azurewebsites.net/items/");
			HttpURLConnection urlConnection = (HttpURLConnection) url
					.openConnection();
			urlConnection.setRequestMethod("GET");
			urlConnection.connect();
			
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(urlConnection.getInputStream()));
			String next;
			while ((next = bufferedReader.readLine()) != null) {
				JSONArray ja = new JSONArray(next);
				for (int i = 0; i < ja.length(); i++) {
					RecordItem item = new RecordItem();
					JSONObject jo = (JSONObject) ja.get(i);
					item.setId(jo.getString("id"));
					item.setCategory(jo.getString("category"));
					item.setTitle(jo.getString("title"));
					item.setDescription(jo.getString("description"));
					item.setLatitude(jo.getString("latitude"));
					item.setLongitude(jo.getString("longitude"));
					item.setImageFilename(jo.getString("image_file_path"));
					item.setAudioFilename(jo.getString("audio_file_path"));
					items.add(item);
				}
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return items;
	}
	
	public void SaveRemoteFile(Context context, String url, String fileName, String fileDirectory) {
		
		try {
			new DefaultHttpClient().execute(new HttpGet(url))
			.getEntity().writeTo(new FileOutputStream(new File(fileDirectory, fileName)));
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Cursor GetRecordItemsFromDB(String sqlFilter, Context context) {
		
		DatabaseHelper dbHelper = new DatabaseHelper(context);
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		
		if (sqlFilter == null) {
			sqlFilter = "";
		}
		
		Cursor items = db.rawQuery("SELECT rowid _id, * FROM items " + sqlFilter, null);
		
		return items;
	}

	public class DatabaseHelper extends SQLiteOpenHelper {

		private static final int VERSION = 1;
		private static final String DATABASE = "app.db";
		private static final String TABLE = "items";
		private static final String TABLE_DROP = 
				"DROP TABLE IF EXISTS " + TABLE + ";";
		private static final String TABLE_CREATE =
				"CREATE TABLE " + TABLE + " (" +
				"id INTEGER PRIMARY KEY, " +
				"category TEXT, " +
				"title TEXT, " +
				"description TEXT, " +
				"latitude TEXT, " +
				"longitude TEXT, " +
				"image_file_path TEXT, " +
				"audio_file_path TEXT);";

		public DatabaseHelper(Context context) {
			
			super(context, DATABASE, null, VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			
			db.execSQL(TABLE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			
			db.execSQL(TABLE_DROP);
			db.execSQL(TABLE_CREATE);
		} 
	}
}

package com.kevnls.audiooregon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ListByCategoryActivity extends ListActivity {
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    new GetData().execute();
  }

  @Override
  protected void onListItemClick(ListView l, View v, int position, long id) {
    String item = (String) getListAdapter().getItem(position);
    Toast.makeText(this, item + " selected", Toast.LENGTH_LONG).show();
  }
  
  private class GetData extends AsyncTask<Void, Integer, ArrayList<String>> {

	protected ArrayList<String> doInBackground(Void... params) {
		
	    ArrayList<String> items = new ArrayList<String>();
		 
	    try {
		    URL url = new URL
		    ("http://audiooregon.azurewebsites.net/items/");
		    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
		    urlConnection.setRequestMethod("GET");
		    urlConnection.connect();
		    // gets the server json data
	        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
		    String next;
		    while ((next = bufferedReader.readLine()) != null){
		    	JSONArray ja = new JSONArray(next);
		    	for (int i = 0; i < ja.length(); i++) {
		    		JSONObject jo = (JSONObject) ja.get(i);
		    		items.add(jo.getString("title"));
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
	
	protected void onProgressUpdate(Integer... progress) {
        //TODO do something with the progress[0] value
		//on the UI thread
    }

    protected void onPostExecute(ArrayList<String> result) {
    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(ListByCategoryActivity.this,
	        R.layout.list_view_template, R.id.listTitle, result);
	    setListAdapter(adapter);
    }

  }
}

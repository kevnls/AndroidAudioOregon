package com.kevnls.audiooregon;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_detail);
        
        Bundle bundle = getIntent().getExtras();
        String id = bundle.getString("id");
        
        ImageView detailImage = (ImageView)findViewById(R.id.detailImage);
        TextView detailTitle = (TextView)findViewById(R.id.detailTitle);
        TextView detailDescription = (TextView)findViewById(R.id.detailDescription);
        
        HelperUtilities hu = new HelperUtilities();
        Cursor cursor = hu.GetRecordItemsFromDB("WHERE id='" + id + "'", this);
        
        RecordItem recordItem = new RecordItem();
        
        if (cursor.moveToFirst())
        {
	        recordItem.setCategory(cursor.getString(2));
	        recordItem.setTitle(cursor.getString(3));
	        recordItem.setDescription(cursor.getString(4));
	        recordItem.setLatitude(cursor.getString(5));
	        recordItem.setLongitude(cursor.getString(6));
	        recordItem.setImageFilename(cursor.getString(7));
	        recordItem.setAudioFilename(cursor.getString(8));
        }
	
        File imgFile = new File(recordItem.getImageFilename());
        if(imgFile.exists())
        {
            detailImage.setImageURI(Uri.fromFile(imgFile));
        }
        
        detailTitle.setText(recordItem.getTitle());
        detailDescription.setText(recordItem.getDescription());
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
}

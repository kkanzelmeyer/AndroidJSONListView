package com.kanzelmeyer.json;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;

public class LogActivity extends Activity {


	final String FILENAME = "log.json";
	LogAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_log);

		// Show the Up button in the action bar.
		setupActionBar();

		// Create an entry class list from the log file
		List<Entry> entryList = getLogList();

		// Pass the context and Entry object list to the list view adapter
		LogAdapter adapter = new LogAdapter(this, entryList);

		// Populate the list view with the custom adapter
		ListView logView = (ListView) findViewById(R.id.logListView);
		logView.setAdapter(adapter);

	}

	public List<Entry> getLogList() {
		/*
		 * READS A FILE AND OUTPUTS an entry OBJECT
		 */

		// Initialize a list of Entry objects
		List<Entry> entryList = new ArrayList<Entry>();

		/* The goal is to parse the contents of the JSON file 
		 * into a JSON array. Then we can iterate through the 
		 * JSON array and convert each entry object into an 
		 * Entry object
		 */
		try {
			// Open the file and parse it into a string builder
			FileInputStream in = openFileInput(FILENAME);
			InputStreamReader inputStreamReader = new InputStreamReader(in);
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);
			StringBuilder sb = new StringBuilder();
			String line;

			while ((line = bufferedReader.readLine()) != null) {
				sb.append(line);
			}

			// Convert the string builder into a JSON array
			JSONArray completeArray = new JSONArray(sb.toString());
			
			// Initialize some temporary variables to store the data 
			// we want from the JSON objects
			String logDate, logTime, logDesc;
			
			/* Iterate through the JSON array, store each object in a
			 * temporary object, create an entry object from the temporary 
			 * object, then add the Entry object to the Entry object list 
			 */
			for (int i = 0; i < completeArray.length(); i++) {

				JSONObject dummyObj = completeArray.getJSONObject(i);
				// Get the object called 'entry'
				JSONObject tempObj = dummyObj.getJSONObject("entry");

				logDate = tempObj.getString("date");
				logTime = tempObj.getString("time");
				logDesc = tempObj.getString("description");

				// Create an entry objects (entryObj)
				Entry entryObj = new Entry();
				entryObj.date = logDate;
				entryObj.time = logTime;
				entryObj.description = logDesc;
				// Add the entry object to the list
				entryList.add(entryObj);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		catch (JSONException e) {
			e.printStackTrace();
		}
		
		// Return the entry list that contains an entry object for each entry in the 
		// JSON array from the JSON log file
		return entryList;

	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.log, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
			
		case R.id.clear_log:
			// Dialog box to confirm the delete log action
			new AlertDialog.Builder(this)
			.setTitle(R.string.clear_log)
			.setMessage("Do you really want to delete all log entries?")
			.setIcon(android.R.drawable.ic_dialog_alert)
			.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

			    public void onClick(DialogInterface dialog, int whichButton) {
			    	clearLog();
			    	Intent it = new Intent(LogActivity.this, MainActivity.class);
                    it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  
                    startActivity(it); 
			    }

				})
			 .setNegativeButton(android.R.string.no, null).show();
			
			
			break;

		case R.id.action_settings:
			break;

		}
		return super.onOptionsItemSelected(item);
	}
	
	private void clearLog() {
		// Blank string that we will write to the File
		String clear = "";
		// Open the file in MODE_PRIVATE which will allow us to overwrite
		// the file

		try {
			// Write the blank string to the file
			FileOutputStream fos = openFileOutput(FILENAME, MODE_PRIVATE);
			fos.write(clear.getBytes());
			fos.close();

			// Notify the user that the log was cleared
			Toast toast = Toast.makeText(getApplicationContext(),
					"Log Cleared", Toast.LENGTH_LONG);
			toast.show();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}

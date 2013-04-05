package com.experiment.jasonev;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class JasonActivity extends Activity {

	private static String filename = "drive1.txt";

	TextView tvSpeed;
	TextView tvAcc;
	TextView tvThrottle;
	TextView tvLoc;
	TextView tvIgn;

	// JSON Node names
	private static final String TAG_SPEED = "speed";
	private static final String TAG_ACCELERATION = "acceleration";
	private static final String TAG_THROTTLE = "throttle";
	private static final String TAG_IGNITION = "ignition";
	private static final String TAG_TIME = "time";
	private static final String TAG_POSITION = "position";
	private static final String TAG_GPS = "gps";
	private static final String TAG_VALUE = "value";
	private static final String TAG_DISTANCE = "distance";

	// contacts JSONArray
	JSONArray speed = null;
	JSONArray acceleration = null;
	JSONArray throttle = null;
	JSONArray ignition = null;
	JSONArray gps = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jason);
		init();
		getJSON();
	}

	public void init() {
		tvSpeed = (TextView) findViewById(R.id.tv_speed);
		tvAcc = (TextView) findViewById(R.id.tv_acc);
		tvThrottle = (TextView) findViewById(R.id.tv_throttle);
		tvLoc = (TextView) findViewById(R.id.tv_loc);
		tvIgn = (TextView) findViewById(R.id.tv_ign);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.jason, menu);
		return true;
	}

	public void getJSON() {
		JSONParser jParser = new JSONParser();
		JSONObject json = jParser.getJSONFromAsset(this, filename);

		try {
			speed = json.getJSONArray(TAG_SPEED);

			for (int i = 0; i < speed.length(); i++) {
				JSONObject c = speed.getJSONObject(i);

				String time = c.getString(TAG_TIME);
				String position = c.getString(TAG_POSITION);
				String value = c.getString(TAG_VALUE);

				if (i == 0) {
					tvSpeed.setText(time + " | " + position + " | " + value);
				} else {
					tvSpeed.setText(tvSpeed.getText() + "\n" + time + " | "
							+ position + " | " + value);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		try {
			acceleration = json.getJSONArray(TAG_ACCELERATION);

			for (int i = 0; i < acceleration.length(); i++) {
				JSONObject c = acceleration.getJSONObject(i);

				String time = c.getString(TAG_TIME);
				String position = c.getString(TAG_POSITION);
				String value = c.getString(TAG_VALUE);

				if (i == 0) {
					tvAcc.setText(time + " | " + position + " | " + value);
				} else {
					tvAcc.setText(tvAcc.getText() + "\n" + time + " | "
							+ position + " | " + value);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		try {
			throttle = json.getJSONArray(TAG_THROTTLE);

			for (int i = 0; i < throttle.length(); i++) {
				JSONObject c = throttle.getJSONObject(i);

				String time = c.getString(TAG_TIME);
				String position = c.getString(TAG_POSITION);
				String value = c.getString(TAG_VALUE);

				if (i == 0) {
					tvThrottle.setText(time + " | " + position + " | " + value);
				} else {
					tvThrottle.setText(tvThrottle.getText() + "\n" + time
							+ " | " + position + " | " + value);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		try {
			gps = json.getJSONArray(TAG_GPS);

			for (int i = 0; i < gps.length(); i++) {
				JSONObject c = gps.getJSONObject(i);

				String time = c.getString(TAG_TIME);
				String value = c.getString(TAG_VALUE);

				if (i == 0) {
					tvLoc.setText(time + " | " + value);
				} else {
					tvLoc.setText(tvLoc.getText() + "\n" + time + " | " + value);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		try {
			ignition = json.getJSONArray(TAG_IGNITION);

			for (int i = 0; i < ignition.length(); i++) {
				JSONObject c = ignition.getJSONObject(i);

				String time = c.getString(TAG_TIME);
				String position = c.getString(TAG_POSITION);
				String value = c.getString(TAG_VALUE);
				String distance = c.getString(TAG_DISTANCE);

				if (i == 0) {
					tvIgn.setText(time + " | " + position + " | " + value
							+ " | " + distance);
				} else {
					tvIgn.setText(tvIgn.getText() + "\n" + time + " | "
							+ position + " | " + value + " | " + distance);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}

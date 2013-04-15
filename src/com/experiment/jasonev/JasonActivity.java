package com.experiment.jasonev;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.widget.TextView;

public class JasonActivity extends Activity {

	private static String filename = "gpslog20.txt";

	static TextView tvTimeStamp;
	static TextView tvPosition;
	static TextView tvAcc;
	static TextView tvSpeed;

	// JSON Node names
	private static final String TAG_DRIVE_DATA = "driveData";
	private static final String TAG_TIME = "time";
	private static final String TAG_GPS_DATA = "gpsData";
	private static final String TAG_ACCEL_DATA = "accelData";
	private static final String TAG_SPEED_DATA = "speedData";

	// contacts JSONArray
	JSONArray driveData = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jason);
		init();
		getJSON();
	}

	static Handler timeHandler = new Handler() {
		@Override
		public void handleMessage(Message timeMsg) {
			String text = (String) timeMsg.obj;
			tvTimeStamp.append(text + "\n");
		}
	};

	static Handler gpsHandler = new Handler() {
		@Override
		public void handleMessage(Message gpsMsg) {
			String text = (String) gpsMsg.obj;
			tvPosition.append(text + "\n");
		}
	};

	static Handler accHandler = new Handler() {
		@Override
		public void handleMessage(Message accMsg) {
			String text = (String) accMsg.obj;
			tvAcc.append(text + "\n");
		}
	};

	static Handler speedHandler = new Handler() {
		@Override
		public void handleMessage(Message speedMsg) {
			String text = (String) speedMsg.obj;
			tvSpeed.append(text + "\n");
		}
	};

	public void init() {
		tvTimeStamp = (TextView) findViewById(R.id.tv_timestamp);
		tvPosition = (TextView) findViewById(R.id.tv_position);
		tvAcc = (TextView) findViewById(R.id.tv_acc);
		tvSpeed = (TextView) findViewById(R.id.tv_speed);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.jason, menu);
		return true;
	}

	public void getJSON() {
		JSONParser jParser = new JSONParser();
		final JSONObject json = jParser.getJSONFromAsset(this, filename);
		try {
			driveData = json.getJSONArray(TAG_DRIVE_DATA);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		final ProgressDialog pdParsing = new ProgressDialog(this);
		pdParsing.setTitle("Parsing " + filename + "...");
		pdParsing.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pdParsing.setProgress(0);
		pdParsing.setMax(driveData.length());
		pdParsing.setCancelable(false);
		pdParsing.show();
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < driveData.length(); i++) {
					try {
						JSONObject c = driveData.getJSONObject(i);
						Message timeMsg = new Message();
						timeMsg.obj = c.getString(TAG_TIME);
						timeHandler.sendMessage(timeMsg);
						Message gpsMsg = new Message();
						gpsMsg.obj = c.getString(TAG_GPS_DATA);
						gpsHandler.sendMessage(gpsMsg);
						Message accMsg = new Message();
						accMsg.obj = c.getString(TAG_ACCEL_DATA);
						accHandler.sendMessage(accMsg);
						Message speedMsg = new Message();
						speedMsg.obj = c.getString(TAG_SPEED_DATA);
						speedHandler.sendMessage(speedMsg);
						pdParsing.incrementProgressBy(1);
						if (pdParsing.getProgress()>=pdParsing.getMax()) {
                              pdParsing.dismiss();
                        }
						Thread.sleep(10);
					} catch (JSONException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
			}
		};
		new Thread(runnable).start();
	}
}

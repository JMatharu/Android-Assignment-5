//Jagdeep Matharu - 300710666

package com.comp.assign5;

import java.util.ArrayList;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;

public class Jagdeep_restaurant extends Activity {

	static final LatLng r1 = new LatLng(43.691209, -79.695421);
	static final LatLng r2 = new LatLng(43.643481, -79.380383);
	static final LatLng r3 = new LatLng(43.668752, -79.387932);
	GoogleMap googleMap;
	MapController mc;
	GeoPoint p;
	LatLng latLng;
	MarkerOptions markerOptions;
	public double latitude;
	public double longitude;
	protected Object geoCoder;

	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);

		setContentView(R.layout.restaurant_matharu1);

		MyAdapter adapter = new MyAdapter(this, generateData());

		ListView listView = (ListView) findViewById(R.id.listView1);

		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				if (position == 0) {
					googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
					googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(r1,
							14.0f));
					@SuppressWarnings("unused")
					Marker TP = googleMap.addMarker(new MarkerOptions()
							.position(r1).title("Bombay Palace")
							.snippet("71 Jarvis St, Toronto, ON"));
					googleMap.setOnMarkerClickListener(new OnMarkerClickListener() {
						
						@Override
						public boolean onMarkerClick(Marker marker) {
							// TODO Auto-generated method stub
							Thread myThread = null;
							Runnable runnable = new CountDownRunner2();
							myThread = new Thread(runnable);
							myThread.start();
							return false;
						}
					});

				} else if (position == 1) {
					googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
					googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(r2,
							14.0f));
					@SuppressWarnings("unused")
					Marker TP = googleMap.addMarker(new MarkerOptions()
							.position(r2).title("Aria Ristorante")
							.snippet("25 York St, Toronto, ON"));
					googleMap.setOnMarkerClickListener(new OnMarkerClickListener() {
						
						@Override
						public boolean onMarkerClick(Marker marker) {
							// TODO Auto-generated method stub
							Thread myThread = null;
							Runnable runnable = new CountDownRunner1();
							myThread = new Thread(runnable);
							myThread.start();
							return false;
						}
					});
				} else if (position == 2) {
					googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
					googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(r3,
							14.0f));
					@SuppressWarnings("unused")
					Marker TP = googleMap.addMarker(new MarkerOptions()
							.position(r3).title("Panorama Lounge")
							.snippet("55 Bloor St W, Toronto, ON"));
					googleMap.setOnMarkerClickListener(new OnMarkerClickListener() {
						
						@Override
						public boolean onMarkerClick(Marker marker) {
							// TODO Auto-generated method stub
							Thread myThread = null;
							Runnable runnable = new CountDownRunner();
							myThread = new Thread(runnable);
							myThread.start();
							return false;
						}
					});
				}
			}
		});

		googleMap = ((MapFragment) getFragmentManager().findFragmentById(
				R.id.map)).getMap();
		googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(43,
				-79), 6.0f));

		TextView txt = (TextView) findViewById(R.id.textView1);
		TextView txt1 = (TextView) findViewById(R.id.textView2);
		txt.setText(R.string.details);
		txt.setTextColor(Color.BLUE);
		txt1.setText(R.string.full_name);

		Button bBack = (Button) findViewById(R.id.button1);
		bBack.setText(R.string.backButton);

	}
	
	public class MyLocationListener implements LocationListener {

		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			latitude = 37.422006;
			longitude = -122.084095;
			String Text = "My current Latitude = " + latitude + " Longitude = "
					+ longitude;
			Toast.makeText(getApplicationContext(), Text, Toast.LENGTH_SHORT)
					.show();

			SendQueryString();
		}

		public void onProviderDisabled(String provider) {
			Toast.makeText(getApplicationContext(), "Gps Disabled",
					Toast.LENGTH_SHORT).show();
		}

		public void onProviderEnabled(String provider) {
			Toast.makeText(getApplicationContext(), "Gps Enabled",
					Toast.LENGTH_SHORT).show();
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
		}

	}

	public void SendQueryString() {
		// TODO Auto-generated method stub
		new Thread() {
			public void run() {

				String url = "http://mywebapp.com/coordinates/create?latitude="
						+ latitude + "&longitude=" + longitude;

				try {
					HttpClient Client = new DefaultHttpClient();
					HttpGet httpget = new HttpGet(url);
					Client.execute(httpget);
				} catch (Exception ex) {
					String fail = "Fail!";
					Toast.makeText(getApplicationContext(), fail,
							Toast.LENGTH_SHORT).show();
				}
			}
		}.start();
	}

	private ArrayList<Model> generateData() {
		ArrayList<Model> models = new ArrayList<Model>();
		// models.add(new Model("Group Title"));
		models.add(new Model(R.drawable.bombaypalace, R.string.res1));
		models.add(new Model(R.drawable.aria, R.string.res2));
		models.add(new Model(R.drawable.panorma, R.string.res3));

		return models;
	}

	public void bBack(View view) {
		Intent activityIntent = new Intent(Jagdeep_restaurant.this,
				JagdeepActivity5.class);
		Jagdeep_restaurant.this.startActivity(activityIntent);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		try {
			AlertDialog diaBox = AskOption();
			diaBox.show();
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(this, R.string.error, Toast.LENGTH_LONG).show();
		}

	}

	private AlertDialog AskOption() {
		// TODO Auto-generated method stub
		AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
				.setTitle(R.string.exit)
				.setMessage(R.string.notification)
				.setPositiveButton(R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								Intent activityIntent = new Intent(
										Jagdeep_restaurant.this,
										JagdeepActivity5.class);
								Jagdeep_restaurant.this
										.startActivity(activityIntent);
							}
						})
				.setNegativeButton(R.string.cancel,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						}).create();
		return myQuittingDialogBox;
	}
	class CountDownRunner implements Runnable {
		// @Override
		public void run() {
			while (!Thread.currentThread().isInterrupted()) {
				try {
					doWork();
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				} catch (Exception e) {
				}
			}
		}
}
	class CountDownRunner1 implements Runnable {
		// @Override
		public void run() {
			while (!Thread.currentThread().isInterrupted()) {
				try {
					doWork1();
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				} catch (Exception e) {
				}
			}
		}
}
	class CountDownRunner2 implements Runnable {
		// @Override
		public void run() {
			while (!Thread.currentThread().isInterrupted()) {
				try {
					doWork2();
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				} catch (Exception e) {
				}
			}
		}
}
	public void doWork() {
		// TODO Auto-generated method stub
		runOnUiThread(new Runnable() {
			public void run() {
				try {
					Uri uri = Uri.parse("http://www.panoramalounge.com");
					Intent intent = new Intent(Intent.ACTION_VIEW, uri);
					startActivity(intent);
				} catch (Exception e) {
				}
			}
		});
	}

	public void doWork2() {
		// TODO Auto-generated method stub
		runOnUiThread(new Runnable() {
			public void run() {
				try {
					Uri uri = Uri.parse("http://www.bombaypalace.ca");
					Intent intent = new Intent(Intent.ACTION_VIEW, uri);
					startActivity(intent);
				} catch (Exception e) {
				}
			}
		});
	}

	public void doWork1() {
		// TODO Auto-generated method stub
		runOnUiThread(new Runnable() {
			public void run() {
				try {
					Uri uri = Uri.parse("http://ariaristorante.ca");
					Intent intent = new Intent(Intent.ACTION_VIEW, uri);
					startActivity(intent);
				} catch (Exception e) {
				}
			}
		});
	}
}

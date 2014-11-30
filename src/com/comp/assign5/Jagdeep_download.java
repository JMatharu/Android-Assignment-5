//Jagdeep Matharu - 300710666

package com.comp.assign5;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Jagdeep_download extends Activity {

	// button to show progress dialog
	Button btnShowProgress;

	// Progress Dialog
	private ProgressDialog pDialog;
	ImageView my_image;
	// Progress dialog type (0 - for Horizontal progress bar)
	public static final int progress_bar_type = 0;

	// File url to download

	private static String url1 = "http://fblog.futurebrand.com/wp-content/uploads/2013/10/android-vs-apple-wallpaper-wallpaper-ranpict.jpg";
	private static String url2 = "http://images.wikia.com/fantendo/images/6/6e/Small-mario.png";
	private static String url3 = "http://www.freegreatdesign.com/files/images/8/3533-crystal-rainbow-apple-icon-png-1.jpg";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.download_matharu4);

		Thread myThread = null;

		Runnable runnable = new CountDownRunner();
		myThread = new Thread(runnable);
		myThread.start();

		View tR = (View) findViewById(R.id.view1);
		tR.setBackgroundColor(Color.BLACK);

		TextView txt = (TextView) findViewById(R.id.textView1);
		txt.setText(R.string.details);
		txt.setTextColor(Color.WHITE);

		// show progress bar button
		btnShowProgress = (Button) findViewById(R.id.btnProgressBar);
		btnShowProgress.setText(R.string.bImage);
		btnShowProgress.setTextColor(Color.WHITE);
		btnShowProgress.setBackgroundColor(Color.rgb(162, 137, 192));
		// Image view to show image after downloading
		my_image = (ImageView) findViewById(R.id.my_image);
		/**
		 * Show Progress bar click event
		 * */
		btnShowProgress.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// starting new Async Task
				File file1 = new File(Environment.getExternalStorageDirectory()
						.toString() + "/downloadedfile.jpg");

				File file2 = new File(Environment.getExternalStorageDirectory()
						.toString() + "/downloadedfil.jpg");

				File file3 = new File(Environment.getExternalStorageDirectory()
						.toString() + "/downloadedfi.jpg");

				new DownloadFileFromURL().execute(url1);
				if (file1.exists()) {
					new DownloadFileFromURL().execute(url2);
				} else if (file1.exists() && file2.exists()) {
					new DownloadFileFromURL().execute(url3);
				} else if (file1.exists() && file2.exists() && file3.exists()) {
					AlertDialog diaBox = notifyf();
					diaBox.show();
				}
			}
		});
	}

	protected AlertDialog notifyf() {
		// TODO Auto-generated method stub

		AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
				.setTitle(R.string.fd)
				.setMessage(R.string.Delete)

				.setPositiveButton(R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								dialog.dismiss();
							}
						})
				.setNegativeButton(R.string.delete,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								File file = new File(Environment.getExternalStorageDirectory()
										.toString() + "/downloadedfile.jpg");
								@SuppressWarnings("unused")
								boolean isSuccess = file.delete();
							}
						}).create();
		return myQuittingDialogBox;

	}

	/**
	 * Showing Dialog
	 * */
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case progress_bar_type:
			pDialog = new ProgressDialog(this);
			pDialog.setMessage("Downloading file. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setMax(100);
			pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			pDialog.setCancelable(true);
			pDialog.show();
			return pDialog;
		default:
			return null;
		}
	}

	/**
	 * Background Async Task to download file
	 * */
	class DownloadFileFromURL extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Bar Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			showDialog(progress_bar_type);
		}

		/**
		 * Downloading file in background thread
		 * */
		@Override
		protected String doInBackground(String... f_url) {
			int count;
			try {
				URL url = new URL(f_url[0]);
				URLConnection conection = url.openConnection();
				conection.connect();
				// getting file length
				int lenghtOfFile = conection.getContentLength();

				// input stream to read file - with 8k buffer
				InputStream input = new BufferedInputStream(url.openStream(),
						8192);

				// Output stream to write file
				OutputStream output = new FileOutputStream(
						"/sdcard/downloadedfile.jpg");

				byte data[] = new byte[1024];

				long total = 0;

				while ((count = input.read(data)) != -1) {
					total += count;
					// publishing the progress....
					// After this onProgressUpdate will be called
					publishProgress("" + (int) ((total * 100) / lenghtOfFile));

					// writing data to file
					output.write(data, 0, count);
				}

				// flushing output
				output.flush();

				// closing streams
				output.close();
				input.close();

			} catch (Exception e) {
				Log.e("Error: ", e.getMessage());
			}

			return null;
		}

		/**
		 * Updating progress bar
		 * */
		protected void onProgressUpdate(String... progress) {
			// setting progress percentage
			pDialog.setProgress(Integer.parseInt(progress[0]));
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		@Override
		protected void onPostExecute(String file_url) {
			// dismiss the dialog after the file was downloaded
			dismissDialog(progress_bar_type);

			// Displaying downloaded image into image view
			// Reading image path from sdcard
			String imagePath = Environment.getExternalStorageDirectory()
					.toString() + "/downloadedfile.jpg";
			// setting downloaded into image view
			my_image.setImageDrawable(Drawable.createFromPath(imagePath));
		}

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
										Jagdeep_download.this,
										JagdeepActivity5.class);
								Jagdeep_download.this
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
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				} catch (Exception e) {
				}
			}
		}

		public void doWork() {
			runOnUiThread(new Runnable() {
				public void run() {
					try {
						TextView txtCurrentTime = (TextView) findViewById(R.id.textView2);
						Calendar c = Calendar.getInstance();
						SimpleDateFormat df = new SimpleDateFormat(
								"yyyy-MM-dd KK:mm:ss", Locale.CANADA);
						String formattedDate = df.format(c.getTime());

						txtCurrentTime.setText(formattedDate);
						txtCurrentTime.setTextColor(Color.WHITE);

						txtCurrentTime.setGravity(Gravity.CENTER);
					} catch (Exception e) {
					}
				}
			});
		}

	}

}
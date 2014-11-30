//Jagdeep Matharu - 300710666

package com.comp.assign5;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class JagdeepActivity5 extends Activity {
	RadioButton rb1, rb2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_matharu5);

		declare();
	}

	private void declare() {
		// TODO Auto-generated method stub
		TextView txtDetails = (TextView) findViewById(R.id.txtDetails);
		txtDetails.setText(R.string.details);

		rb1 = (RadioButton) findViewById(R.id.radio0);
		rb1.setText(R.string.download);

		rb2 = (RadioButton) findViewById(R.id.radio1);
		rb2.setText(R.string.restaurantList);
	}

	public void imgButt(View view) {
		try {
			if (rb1.isChecked() || rb2.isChecked()) {

			} else {
				AlertDialog diaBox = SelectOption();
				diaBox.show();
			}

			if (rb1.isChecked()) {
				Intent activityIntent = new Intent(JagdeepActivity5.this,
						Jagdeep_download.class);
				JagdeepActivity5.this.startActivity(activityIntent);
			} else if (rb2.isChecked()) {
				Intent activityIntent = new Intent(JagdeepActivity5.this,
						Jagdeep_restaurant.class);
				JagdeepActivity5.this.startActivity(activityIntent);
			}
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(this, R.string.error, Toast.LENGTH_LONG).show();
		}
	}

	private AlertDialog SelectOption() {
		// TODO Auto-generated method stub
		AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
				.setTitle(R.string.selectOne)
				.setMessage(R.string.dialogMessage)

				.setPositiveButton(R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								dialog.dismiss();
							}
						}).create();
		return myQuittingDialogBox;
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		AlertDialog diaBox = AskOption();
		diaBox.show();
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
								finish();
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

}

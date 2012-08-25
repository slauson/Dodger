package com.slauson.dasher.menu;

import com.slauson.dasher.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

public class PaidDialogBaseMenu extends Activity {

	/** Paid version dialog **/
	protected static final int DIALOG_PAID_VERSION = -1;

	/** Paid feature for dialog **/
	protected static final String DIALOG_EXTRA_PAID_FEATURE = "paid_feature";
	
	@Override
	public Dialog onCreateDialog(int id, Bundle args) {

		Dialog dialog = null;
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

		switch(id) {
		case DIALOG_PAID_VERSION:
			
			// get args
			int feature = args.getInt(DIALOG_EXTRA_PAID_FEATURE);
			
			if (feature == 0) {
				feature = R.string.menu_paid_default_feature;
			}
			
			alertDialogBuilder
				.setTitle(R.string.menu_paid_title)
				.setMessage(String.format(getString(R.string.menu_paid_message), getString(feature)))
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						removeDialog(DIALOG_PAID_VERSION);
					}
				});
			dialog = alertDialogBuilder.create();
			break;
		default:
			break;
		}
			
		return dialog;
	}
}

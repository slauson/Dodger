package com.slauson.asteroid_dasher.menu;

import com.slauson.asteroid_dasher.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class PaidDialogBaseMenu extends Activity {

	/** Paid version dialog **/
	protected static final int DIALOG_PAID_VERSION = -1;

	@Override
	public Dialog onCreateDialog(int id, Bundle args) {

		Dialog dialog = null;
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

		switch(id) {
		case DIALOG_PAID_VERSION:
			
			alertDialogBuilder
				.setTitle(R.string.menu_paid_title)
				.setMessage(getString(R.string.menu_paid_message))
				.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						removeDialog(DIALOG_PAID_VERSION);
					}
				})
				.setNegativeButton("Buy", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						removeDialog(DIALOG_PAID_VERSION);
						try {
						    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getString(R.string.menu_paid_package_name))));
						} catch (android.content.ActivityNotFoundException anfe) {
						    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + getString(R.string.menu_paid_package_name))));
						}
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

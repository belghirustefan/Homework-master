package com.example.homework.utils;

import android.R;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;

//helper class for displaying various types of dialog
public class PopDialog {

	public static final int TYPE_OK = 1;
	private static AlertDialog mAlertDialog;

	public static void showDialog(Context ctx, String title, String message,
			android.content.DialogInterface.OnClickListener ocl) {
		Builder builder = new AlertDialog.Builder(ctx);
		builder.setMessage(message).setTitle(title)
				.setNeutralButton(ctx.getText(R.string.ok), ocl);
		mAlertDialog = builder.create();
		mAlertDialog.show();
	}

	public static void showDialog(Context ctx, String message,
			OnClickListener yes, OnClickListener no) {
		Builder builder = new AlertDialog.Builder(ctx);
		builder.setMessage(message)
				.setPositiveButton(ctx.getText(R.string.yes), yes)
				.setNegativeButton(ctx.getText(R.string.no), no)
				.setCancelable(true);
		mAlertDialog = builder.create();
		mAlertDialog.show();
	}

	public static void showDialog(Context ctx, String message,
			String positiveMessage, String negativeMessage,
			OnClickListener yes, OnClickListener no) {
		Builder builder = new AlertDialog.Builder(ctx);
		builder.setMessage(message).setPositiveButton(positiveMessage, yes)
				.setNegativeButton(negativeMessage, no).setCancelable(true);
		mAlertDialog = builder.create();
		mAlertDialog.show();
	}

	public static void showDialog(Context ctx, String message,
			DialogInterface.OnClickListener ok) {
		Builder builder = new AlertDialog.Builder(ctx);
		builder.setMessage(message).setNeutralButton(ctx.getText(R.string.ok),
				ok);
		mAlertDialog = builder.create();
		mAlertDialog.show();
	}

	public static void showDialog(Context ctx, String message,
			OnClickListener ok, OnCancelListener onCancelListener) {
		Builder builder = new AlertDialog.Builder(ctx);
		builder.setMessage(message).setNeutralButton(ctx.getText(R.string.ok),
				ok);
		builder.setOnCancelListener(onCancelListener);
		mAlertDialog = builder.create();
		mAlertDialog.show();
	}

	public static void showDialog(Context ctx, String message,
			OnClickListener ok, OnCancelListener onCancelListener,
			OnDismissListener onDismissListener) {
		Builder builder = new AlertDialog.Builder(ctx);
		builder.setMessage(message).setNeutralButton(ctx.getText(R.string.ok),
				ok);
		builder.setOnCancelListener(onCancelListener);
		// builder.setOnDismissListener(onDismissListener);
		mAlertDialog = builder.create();
		mAlertDialog.show();
	}

	public static void cancelAlertDialog() {
		if (mAlertDialog != null)
			mAlertDialog.cancel();
	}
}

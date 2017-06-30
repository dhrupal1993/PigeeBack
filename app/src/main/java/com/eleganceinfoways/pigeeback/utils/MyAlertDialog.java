/**
 * 
 */
package com.eleganceinfoways.pigeeback.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;





public class MyAlertDialog {

	AlertDialog.Builder builder;

	private OnClickListener listener;
	private OnClickListener negative;
//	Languages.LanguageResponse languageResponse;
	Context ctx;

	public interface OnClickListener{
		public void onClick();
	}
	
	public MyAlertDialog(Context context){
		this.ctx = context;
		this.builder = new AlertDialog.Builder(context);
	}
	public MyAlertDialog(Context context,OnClickListener listener){
		this.ctx = context;
		this.builder = new AlertDialog.Builder(context);
		this.listener=listener;
	}
	public MyAlertDialog(Context context,OnClickListener listener,OnClickListener negative){
		this.builder = new AlertDialog.Builder(context);
		this.listener=listener;
		this.negative=negative;
	}
	
	public void show(String btnLable,String title){

		/*if(languageResponse == null)
		{
			languageResponse.server_error = ctx.getResources().getString(R.string.server_error);
			Log.e("nxnc",""+languageResponse.server_error);
		}*/
		if(title!=null && title.length()>0 && !title.equalsIgnoreCase("404 Not Found"))
			 {
			builder.setMessage(title)
					.setCancelable(false)
					.setPositiveButton(btnLable, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							if (listener != null) listener.onClick();
							dialog.dismiss();
						}
					});

			AlertDialog alert = builder.create();
			alert.show();
		}

	}

	public void showForceFully(String btnLable,String title){
		Log.e("Alert","showForceFully done");
		if(title!=null && title.length()>0) {
			builder.setMessage(title)
					.setCancelable(false)
					.setPositiveButton(btnLable, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							if (listener != null) listener.onClick();
							dialog.dismiss();
						}
					});
			Log.e("Alert_2","showForceFully done");
			Log.e("Alert","sdfsd"+builder.create()+"done");
			AlertDialog alert1 = builder.create();

			alert1.show();
			Log.e("Alert","2"+alert1.isShowing());
		}
	}

	public void showCancel(String btnCancel,String btnPositive,String title){
		if(title!=null && title.length()>0) {
			builder.setMessage(title)
					.setCancelable(false)
					.setPositiveButton(btnPositive, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							if (listener != null) listener.onClick();
							dialog.dismiss();
						}
					})
			.setNegativeButton(btnCancel,new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					if (negative != null) negative.onClick();
					dialog.dismiss();
				}
			});


			AlertDialog alert = builder.create();
			alert.show();
		}
	}


	public void showNoInternet(String btnLable,String title){

		/*if(languageResponse == null)
		{
			languageResponse.server_error = ctx.getResources().getString(R.string.server_error);
			Log.e("nxnc",""+languageResponse.server_error);
		}*/
		if(title!=null && title.length()>0)
		{
			builder.setMessage(title)
					.setCancelable(false)
					.setPositiveButton(btnLable, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							if (listener != null) listener.onClick();
							dialog.dismiss();
						}
					});

			AlertDialog alert = builder.create();
			alert.show();
		}

	}
}

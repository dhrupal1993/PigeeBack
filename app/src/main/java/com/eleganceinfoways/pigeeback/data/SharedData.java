package com.eleganceinfoways.pigeeback.data;

import android.content.Context;
import android.content.SharedPreferences;


public class SharedData {
	private Context context;
	private String preferenceName = "pigeeback";

	public SharedData(Context cnt) {
		context = cnt;
	}

	public void putString(String key, String value) {
		SharedPreferences sp = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
		SharedPreferences.Editor spe = sp.edit();
		spe.putString(key, value);
		spe.commit();
	}

	/*public void putDouble(String key, Double value) {
		SharedPreferences sp = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
		SharedPreferences.Editor spe = sp.edit();
		spe.put(key, value);
		spe.commit();
	}*/

	public void putBoolean(String key, boolean value) {
		SharedPreferences sp = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
		SharedPreferences.Editor spe = sp.edit();
		spe.putBoolean(key, value);
		spe.commit();
	}

	public void putInt(String key, int value) {
		SharedPreferences sp = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
		SharedPreferences.Editor spe = sp.edit();
		spe.putInt(key, value);
		spe.commit();
	}

	public String getString(String key, String defaultValue) {
		String value = "";
		SharedPreferences sp = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
		value = sp.getString(key, defaultValue);
		return value;
	}

	public boolean getBoolean(String key, boolean defaultValue) {
		boolean value = false;
		SharedPreferences sp = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
		value = sp.getBoolean(key, defaultValue);
		return value;
	}

	public int getInt(String key, int defaultValue) {
		int value = 0;
		SharedPreferences sp = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
		value = sp.getInt(key, defaultValue);
		return value;
	}

	public void putLong(String key, long value) {
		SharedPreferences sp = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
		SharedPreferences.Editor spe = sp.edit();
		spe.putLong(key, value);
		spe.commit();
	}

	public long getLong(String key, long value) {

		SharedPreferences sp = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
		value = sp.getLong(key, value);
		return value;
	}

	public void clearData() {
		// Clearing all data from Shared Preferences
		SharedPreferences sp = context.getSharedPreferences(preferenceName,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor spe = sp.edit();
		try {

			spe.remove("isLogin");
		}
		catch(Exception e){}

		//spe.remove(getEmail());
		//spe.clear();
		spe.commit();
	}

	public void clearallData() {
		// Clearing all data from Shared Preferences
		SharedPreferences sp = context.getSharedPreferences(preferenceName,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor spe = sp.edit();
		//spe.remove(getEmail());
		spe.clear();
		spe.commit();
	}

}
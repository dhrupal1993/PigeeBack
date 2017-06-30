package com.eleganceinfoways.pigeeback.widget;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.eleganceinfoways.pigeeback.utils.GetFontstyle;

import java.util.List;

/**
 * Created by drindia19 on 9/6/2016.
 */
public  class SpinnerAdapter extends ArrayAdapter<String> {
    // Initialise custom font, for example:
  //MainActivity acty;
    public
    GetFontstyle getFontstyle;

    public SpinnerAdapter(Activity acty, int resource, String[] items) {
        super(acty, resource, items);
        //this.acty=acty;
        getFontstyle = new GetFontstyle(getContext());
    }

    // Affects default (closed) state of the spinner
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView view = (TextView) super.getView(position, convertView, parent);
        view.setTypeface(getFontstyle.CoreSansMedium());
        return view;
    }

    // Affects opened state of the spinner
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView view = (TextView) super.getDropDownView(position, convertView, parent);
        view.setTypeface(getFontstyle.CoreSansMedium());
        return view;
    }
}
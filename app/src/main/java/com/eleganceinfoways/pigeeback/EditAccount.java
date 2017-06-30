package com.eleganceinfoways.pigeeback;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.eleganceinfoways.pigeeback.utils.GetFontstyle;

public class EditAccount extends Fragment {

    EditText edname,edphone,edaddress;
    Button btnupdate,btncancel;
    GetFontstyle getFontstyle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View view =  inflater.inflate(R.layout.fragment_edit_account, container, false);
        edname = (EditText)view.findViewById(R.id.edname);
        edphone = (EditText)view.findViewById(R.id.edphone);
        edaddress = (EditText)view.findViewById(R.id.edaddress);
        btnupdate = (Button)view.findViewById(R.id.btnupdate);
        btncancel = (Button)view.findViewById(R.id.btncancel);
        setFontStyle();
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Edit Account");
    }


    private void setFontStyle() {
        if(getFontstyle != null)
        {
            btnupdate.setTypeface(getFontstyle.CoreSansBold());
            btncancel.setTypeface(getFontstyle.CoreSansBold());
            edname.setTypeface(getFontstyle.CoreSansMedium());
            edphone.setTypeface(getFontstyle.CoreSansMedium());
            edaddress.setTypeface(getFontstyle.CoreSansMedium());

        }
    }
}

package com.eleganceinfoways.pigeeback;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eleganceinfoways.pigeeback.adapter.CustomerRoutesAdapater;

public class CustomerRoutesList extends Fragment {

    CustomerRoutesAdapater customerRoutesAdapater;
    LinearLayoutManager linearLayoutManager;
    RecyclerView rvcustroutes;



    public CustomerRoutesList() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View view =  inflater.inflate(R.layout.fragment_customer_routes, container, false);
        rvcustroutes = (RecyclerView)view.findViewById(R.id.rvcustroutes);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        rvcustroutes.setLayoutManager(linearLayoutManager);
        setAdapter();
        return  view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("My List");
    }
    private void setAdapter()
    {
        customerRoutesAdapater = new CustomerRoutesAdapater(getContext());
        rvcustroutes.setAdapter(customerRoutesAdapater);
    }

}

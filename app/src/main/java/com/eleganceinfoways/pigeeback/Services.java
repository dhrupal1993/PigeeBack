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

import com.eleganceinfoways.pigeeback.adapter.ServiceAdapter;

public class Services extends Fragment {

    RecyclerView rvservices;
    LinearLayoutManager linearLayoutManager;
    ServiceAdapter serviceAdapter;


    public Services() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View view = inflater.inflate(R.layout.fragment_services, container, false);
        rvservices = (RecyclerView)view.findViewById(R.id.rvservices);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        rvservices.setLayoutManager(linearLayoutManager);



        //Setting Adapter
        setAdapter();
        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Services");
    }

    private void setAdapter() {
        //serviceProviderAdapter = new ServiceProviderAdapter(getActivity());
        serviceAdapter = new ServiceAdapter(getContext());
        rvservices.setAdapter(serviceAdapter);
        //rvservicelist.setAdapter(serviceProviderAdapter);
    }
}

package com.eleganceinfoways.pigeeback;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eleganceinfoways.pigeeback.adapter.CustomerOrderAdapter;
import com.eleganceinfoways.pigeeback.adapter.OrderAdapter;
import com.eleganceinfoways.pigeeback.data.SharedData;

/**
 * Created by KHUSHI on 22-Jun-17.
 */

public class Orders extends Fragment {

    OrderAdapter orderAdapter;
    CustomerOrderAdapter customerOrderAdapter;
    RecyclerView rvOrders;
    LinearLayoutManager linearLayoutManager;
    SharedData sharedData;
    boolean isProvider = false;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View view =  inflater.inflate(R.layout.fragment_order, container, false);
        sharedData = new SharedData(getActivity());
        rvOrders = (RecyclerView)view.findViewById(R.id.rvorders);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        rvOrders.setLayoutManager(linearLayoutManager);
        isProvider = sharedData.getBoolean("isProvider",false);

        setAdapter();

        return view;

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Orders");
    }

    private void setAdapter() {

        if(isProvider) {
            orderAdapter = new OrderAdapter(getActivity(),Orders.this);
            rvOrders.setAdapter(orderAdapter);
        }else{
            customerOrderAdapter = new CustomerOrderAdapter(getActivity());
            rvOrders.setAdapter(customerOrderAdapter);
        }
        //rvservicelist.setAdapter(serviceProviderAdapter);
    }

    public void openFragment()
    {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, new PostPickup());
        ft.commit();
    }
}

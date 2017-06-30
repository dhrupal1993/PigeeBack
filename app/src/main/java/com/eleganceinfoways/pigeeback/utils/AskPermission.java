package com.eleganceinfoways.pigeeback.utils;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class AskPermission extends AppCompatActivity {

    public static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION= 103;
    public static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 102;

    private void showRationale(Activity act, String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(act)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setCancelable(false)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        Log.e("onRequestPermissions","onRequestPermissionsResult");
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION:
            {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Log.e("Permission Granted", "Location Permission Granted");
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }

                else {
                    Log.e("Permission Not Granted", "Location Permission Not Granted");

                }
                return;
            }
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("PermissionStorage Grant", "Permission Storage Gran");
                }
                else {
                   Log.e("Permission Storage Not", "Permission Storage Not Granted");
                }
                return;
            }

        }
    }

    public void checkPermission(final Activity context, final int i, final String P)
    {

        if (ContextCompat.checkSelfPermission(context, P) != PackageManager.PERMISSION_GRANTED)
        {
            Log.e("Not Granted","Not Gramted");
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(context, P))
            {
                if (P.equalsIgnoreCase(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    Log.e("Show Explanation", "Show Explanation");
                    showRationale(context, "You need to allow access to Location",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ActivityCompat.requestPermissions(context, new String[]{P}, i);
                                    return;
                                }
                            });
                }

                if (P.equalsIgnoreCase(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    Log.e("Show Explanation", "Show Explanation");
                    showRationale(context, "You need to allow access to Write External Storage",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ActivityCompat.requestPermissions(context, new String[]{P}, i);
                                    return;
                                }
                            });
                }
                //Not Needed To check directly grant it
            }

            else
            {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(context, new String[]{P}, i);
                return;
            }
        }

       else
        {
            Log.e("In Granted", "In Granted");
            switch (i) {
                case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                    Toast.makeText(context, "Permission Location Granted", Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        }
    }

}

//For Multiple Permission


/*****************************************************************************************************************/

//final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;

    /*private void insertDummyContactWrapper() {
        List<String> permissionsNeeded = new ArrayList<String>();

        final List<String> permissionsList = new ArrayList<String>();
        if (!addPermission(permissionsList, Manifest.permission.ACCESS_FINE_LOCATION))
            permissionsNeeded.add("GPS");
        if (!addPermission(permissionsList, Manifest.permission.READ_CONTACTS))
            permissionsNeeded.add("Read Contacts");
        if (!addPermission(permissionsList, Manifest.permission.WRITE_CONTACTS))
            permissionsNeeded.add("Write Contacts");

        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {
                // Need Rationale
                String message = "You need to grant access to " + permissionsNeeded.get(0);
                for (int i = 1; i < permissionsNeeded.size(); i++)
                    message = message + ", " + permissionsNeeded.get(i);
                showMessageOKCancel(message,
                        new DialogInterface.OnClickListener() {
                            @Override

                            public void onClick(DialogInterface dialog, int which) {
                                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                                        REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
                            }
                        });
                return;
            }
            requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                    REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
            return;
        }

        insertDummyContact();
    }*/

    /*private boolean addPermission(List<String> permissionsList, String permission) {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            // Check for Rationale Option
            if (!shouldShowRequestPermissionRationale(permission))
                return false;
        }
        return true;
    }*/


/*****************************************************************************************************************/


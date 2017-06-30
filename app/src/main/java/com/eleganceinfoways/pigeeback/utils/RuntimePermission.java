package com.eleganceinfoways.pigeeback.utils;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;


public class RuntimePermission {

    private  OnRequestPermissionsResultListener listener;
    private String permission[];
    private int requestCode;
    private AppCompatActivity acty;
    String strmsg,strok,strcancel;


    public interface OnRequestPermissionsResultListener{
        public void onGranted(String permission[], int requestCode);
        public void onDenied(String permission[], int requestCode);
    }

    public void ask(AppCompatActivity acty,OnRequestPermissionsResultListener listener,String permission[],int requestCode,
                    String rationalMesaage,String strOk,String strCancel){
        this.acty=acty;this.listener=listener;this.permission=permission;this.requestCode=requestCode;
        this.strmsg = rationalMesaage;this.strok = strOk;this.strcancel = strCancel;

        if(!hasPermissions(permission)){

      //  if (ContextCompat.checkSelfPermission(acty,
       //         permission)
        //        != PackageManager.PERMISSION_GRANTED) {
         /*   if (!ActivityCompat.shouldShowRequestPermissionRationale(acty,
                    permission)) {
                if(listener!=null)listener.onNeverAsked(permission,requestCode);
            *//*    showMessageOKCancel("You need to allow access to GPS",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(RuntimePermission.this.acty,
                                        new String[]{RuntimePermission.this.permission},
                                        RuntimePermission.this.requestCode);
                            }
                        });*//*
                return;
            }*/
            ActivityCompat.requestPermissions(acty,
                    permission,
                    requestCode);
            return;
        }else
        {
            if(listener!=null)listener.onGranted(permission,requestCode);
        }
    }

    public  boolean hasPermissions( String[] permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && acty != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(acty, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }


    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
       if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

           if(listener!=null)listener.onGranted(permission,requestCode);

                } else {
                     if (!ActivityCompat.shouldShowRequestPermissionRationale(acty, permission[0])) {
                         showMessageOKCancel(strmsg,
                                 new DialogInterface.OnClickListener() {
                                     @Override
                                     public void onClick(DialogInterface dialog, int which) {
                                         startInstalledAppDetailsActivity();
                                     }
                                 });
                         }
                     if(listener!=null)listener.onDenied(permission,requestCode);
                }
    }

    public void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(acty)
                .setMessage(message)
                .setPositiveButton(strok, okListener)
                .setNegativeButton(strcancel, null)
                .create()
                .show();
    }

    public  void startInstalledAppDetailsActivity() {
        if (acty == null) {
            return;
        }
        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + acty.getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        acty.startActivity(i);
    }


}

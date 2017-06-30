package com.eleganceinfoways.pigeeback;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.location.Location;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.eleganceinfoways.pigeeback.utils.GetFontstyle;
import com.eleganceinfoways.pigeeback.utils.MyAlertDialog;
import com.eleganceinfoways.pigeeback.utils.RuntimePermission;
import com.eleganceinfoways.pigeeback.utils.ValidationUtil;
import com.eleganceinfoways.pigeeback.widget.SpinnerAdapter;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.maps.android.SphericalUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class PostPickup extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    private Uri mImageCaptureUri;
    public int facing;

    public String filePath = "";
    private File outPutFile = null;
    private static final int CAMERA_CODE = 101, GALLERY_CODE = 201, CROPING_CODE = 301;
    public RuntimePermission permission;
    private static final int PLACE_PICKER_REQUEST = 1;
    private static final int PLACE_PICKER_REQUEST1 = 2;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));

    //private GoogleMap mMap;
    SupportMapFragment mapFragment;
    private boolean isEnable = true;
    private boolean canShow = false;

    //To store longitude and latitude from map
    private double longitude;
    private double latitude;
    private LinearLayout llfilldetails;
    //From -> the first coordinate from where we need to calculate the distance
    private double fromLongitude;
    private double fromLatitude;

    //To -> the second coordinate to where we need to calculate the distance
    private double toLongitude;
    private double toLatitude;

    //Google ApiClient
    private GoogleApiClient googleApiClient;

    //Our buttons
    private Button buttonCalcDistance, buttonSetTo, buttonSetFrom, btnpost, btnimage;
    private EditText edttoDate, edtfromDate, edtdescription, edtremarks, edtweight;
    private Spinner sptl;
    private ImageView ivpickdate, ivedit, ivpicktodate, imgparcel;
    private TextView edtSource, edtDestination, tvtitle;
    DatePickerDialog.OnDateSetListener pickDate;
    DatePickerDialog.OnDateSetListener pickfromDate;
    Calendar myCalendar;
    String strpickdate, strfromdate;
    GetFontstyle getFontstyle;
    SpinnerAdapter adapter;
    MainDrawerActivity acty;
    Bitmap bitmap;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments

        View view = inflater.inflate(R.layout.activity_post_pickup, container, false);
        outPutFile = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");

        getFontstyle = new GetFontstyle(getActivity());
        buttonSetTo = (Button) view.findViewById(R.id.buttonSetTo);
        buttonSetFrom = (Button) view.findViewById(R.id.buttonSetFrom);
        btnimage = (Button) view.findViewById(R.id.btnimage);
        buttonCalcDistance = (Button) view.findViewById(R.id.buttonCalcDistance);
        btnpost = (Button) view.findViewById(R.id.btnpost);
        edttoDate = (EditText) view.findViewById(R.id.edttoDate);
        edtfromDate = (EditText) view.findViewById(R.id.edtfromDate);
        edtSource = (TextView) view.findViewById(R.id.edtSource);
        edtDestination = (TextView) view.findViewById(R.id.edtDestination);
        edtdescription = (EditText) view.findViewById(R.id.edtdesc);
        edtremarks = (EditText) view.findViewById(R.id.edtremarks);
        edtweight = (EditText) view.findViewById(R.id.edtweight);
        ivpickdate = (ImageView) view.findViewById(R.id.ivpickdate);
        ivpicktodate = (ImageView) view.findViewById(R.id.ivpicktodate);
        ivedit = (ImageView) view.findViewById(R.id.ivedit);
        imgparcel = (ImageView) view.findViewById(R.id.imgparcel);
        llfilldetails = (LinearLayout) view.findViewById(R.id.llfilldetails);
        sptl = (Spinner) view.findViewById(R.id.sptl);
        tvtitle = (TextView) view.findViewById(R.id.tvtitle);
        setFontStyle();
        showHideView();
        myCalendar = Calendar.getInstance();
        //   mapFragment = new SupportMapFragment();
        //mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        //mapFragment.getMapAsync(this);

        //Spinner Adapter
        adapter = new SpinnerAdapter(
                getActivity(),
                R.layout.tv,
                getActivity().getResources().getStringArray(R.array.tl)
        );
        sptl.setAdapter(adapter);

        googleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(AppIndex.API).build();

        buttonCalcDistance.setOnClickListener(this);
        buttonSetTo.setOnClickListener(this);
        buttonSetFrom.setOnClickListener(this);
        ivpickdate.setOnClickListener(this);
        ivpicktodate.setOnClickListener(this);
        ivedit.setOnClickListener(this);
        btnpost.setOnClickListener(this);
        btnimage.setOnClickListener(this);

        edtSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
                    intentBuilder.setLatLngBounds(BOUNDS_MOUNTAIN_VIEW);
                    Intent intent = intentBuilder.build(getActivity());
                    startActivityForResult(intent, PLACE_PICKER_REQUEST);

                } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }

            }
        });

        edtDestination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    PlacePicker.IntentBuilder intentBuilder =
                            new PlacePicker.IntentBuilder();
                    intentBuilder.setLatLngBounds(BOUNDS_MOUNTAIN_VIEW);
                    Intent intent = intentBuilder.build(getActivity());
                    startActivityForResult(intent, PLACE_PICKER_REQUEST1);

                } catch (GooglePlayServicesRepairableException
                        | GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }

            }
        });


        //Pick up Date selection
        pickDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd-MM-yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
                strpickdate = sdf.format(myCalendar.getTime());
                edttoDate.setText(strpickdate);
            }
        };


        //Pick up Date selection
        pickfromDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd-MM-yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
                strfromdate = sdf.format(myCalendar.getTime());
                edtfromDate.setText(strfromdate);
            }
        };

        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Post Pickup");
    }


    @Override
    public void onStart() {
        googleApiClient.connect();
        super.onStart();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Maps Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://google.com")
                // TODO: Make sure this auto-generated app deep link URI is correct.
                //Uri.parse(null)
        );
        AppIndex.AppIndexApi.start(googleApiClient, viewAction);
    }

    @Override
    public void onStop() {
        googleApiClient.disconnect();
        super.onStop();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Maps Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://google.com")
                // TODO: Make sure this auto-generated app deep link URI is correct.
                //Uri.parse(null)
        );
        AppIndex.AppIndexApi.end(googleApiClient, viewAction);
    }

    //Getting current location
    private void getCurrentLocation() {
        //mMap.clear();
        //Creating a location object
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (location != null) {
            //Getting longitude and latitude
            longitude = location.getLongitude();
            latitude = location.getLatitude();

            //moving the map to location
            moveMap();
        }
    }

    //Function to move the map
    private void moveMap() {
        //Creating a LatLng Object to store Coordinates
       /* LatLng latLng = new LatLng(latitude, longitude);

        //Adding marker to map
        mMap.addMarker(new MarkerOptions()
                .position(latLng) //setting position
                .draggable(true) //Making the marker draggable
                .title("Current Location")); //Adding a title

        //Moving the camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        //Animating the camera
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));*/
    }

    public String makeURL(double sourcelat, double sourcelog, double destlat, double destlog) {
        StringBuilder urlString = new StringBuilder();
        urlString.append("https://maps.googleapis.com/maps/api/directions/json");
        urlString.append("?origin=");// from
        urlString.append(Double.toString(sourcelat));
        urlString.append(",");
        urlString
                .append(Double.toString(sourcelog));
        urlString.append("&destination=");// to
        urlString
                .append(Double.toString(destlat));
        urlString.append(",");
        urlString.append(Double.toString(destlog));
        urlString.append("&sensor=false&mode=driving&alternatives=true");
        urlString.append("&key=AIzaSyD3HT5f4xCRkJv0MdQPQUnAI5pp6Nl3thE");
        return urlString.toString();
    }

    private void getDirection() {
        //Getting the URL
        String url = makeURL(fromLatitude, fromLongitude, toLatitude, toLongitude);

        //Showing a dialog till we get the route
        final ProgressDialog loading = ProgressDialog.show(getActivity(), "Getting Route", "Please wait...", false, false);

        //Creating a string request
        StringRequest stringRequest = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        //Calling the method drawPath to draw the path
                        drawPath(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                    }
                });

        //Adding the request to request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    //The parameter is the server response
    public void drawPath(String result) {
        //Getting both the coordinates
        LatLng from = new LatLng(fromLatitude, fromLongitude);
        LatLng to = new LatLng(toLatitude, toLongitude);

        //Calculating the distance in meters
        Double distance = SphericalUtil.computeDistanceBetween(from, to);

        //Displaying the distance
        Toast.makeText(getActivity(), String.valueOf(distance + " Meters"), Toast.LENGTH_SHORT).show();


        try {
            //Parsing json
            final JSONObject json = new JSONObject(result);
            JSONArray routeArray = json.getJSONArray("routes");
            JSONObject routes = routeArray.getJSONObject(0);
            JSONObject overviewPolylines = routes.getJSONObject("overview_polyline");
            String encodedString = overviewPolylines.getString("points");
            List<LatLng> list = decodePoly(encodedString);
            /*Polyline line = mMap.addPolyline(new PolylineOptions()
                    .addAll(list)
                    .width(20)
                    .color(Color.RED)
                    .geodesic(true)
            );*/
        } catch (JSONException e) {
        }
    }

    private List<LatLng> decodePoly(String encoded) {
        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }

    public void showAlert(String title, String message, MyAlertDialog.OnClickListener listener) {
        MyAlertDialog myAlertDialog = new MyAlertDialog(getActivity(), listener);
        myAlertDialog.show(getResources().getString(R.string.ok), message);
    }

    @Override
    public void onClick(View v) {
        if (v == buttonSetFrom) {
            Toast.makeText(getActivity(), "From set", Toast.LENGTH_SHORT).show();
        }

        if (v == buttonSetTo) {
            toLatitude = latitude;
            toLongitude = longitude;
            fromLatitude = latitude;
            fromLongitude = longitude;
            Toast.makeText(getActivity(), "To set", Toast.LENGTH_SHORT).show();
        }

        if (v == buttonCalcDistance) {
            getDirection();
        }

        if (v == ivpickdate) {
            DatePickerDialog dialog = new DatePickerDialog(getContext(), pickfromDate, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH));
            dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            dialog.show();
        }

        if (v == ivpicktodate) {
            DatePickerDialog dialog = new DatePickerDialog(getContext(), pickDate, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH));
            dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            dialog.show();
        }

        if (v == btnpost) {
            if ((ValidationUtil.isNULL(edttoDate.getText().toString())) && (ValidationUtil.isNULL(edtfromDate.getText().toString())) && (ValidationUtil.isNULL(edtSource.getText().toString()))
                    && (ValidationUtil.isNULL(edtDestination.getText().toString())) && (ValidationUtil.isNULL(edtweight.getText().toString()))
                    ) {
                showAlert("", getResources().getString(R.string.data_validation), null);
            } else {
                showAlert("", "Posted", null);
            }
        }

        if (v == ivedit) {
            //setEnable(isEnable);
        }

        if (v == btnimage) {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, GALLERY_CODE);
        }
    }

    private void setEnable(boolean isEnable) {
        edtDestination.setEnabled(isEnable);
        edtSource.setEnabled(isEnable);
        edtweight.setEnabled(isEnable);
        edtdescription.setEnabled(isEnable);
        edtremarks.setEnabled(isEnable);
        sptl.setEnabled(isEnable);
        ivpickdate.setEnabled(isEnable);
        ivpicktodate.setEnabled(isEnable);
    }

    private void showHideView() {

        if ((ValidationUtil.isNULL(edttoDate.getText().toString()) || (ValidationUtil.isNULL(edtDestination.getText().toString()))
                || (ValidationUtil.isNULL(edtweight.getText().toString()))
                || (ValidationUtil.isNULL(edtSource.getText().toString())))
                || (ValidationUtil.isNULL(edtdescription.getText().toString()))
                || (ValidationUtil.isNULL(edtremarks.getText().toString()))
                || (ValidationUtil.isNULL(edtfromDate.getText().toString()))
                || (sptl.getSelectedItem().toString().equalsIgnoreCase("FTL/TTL")))
            canShow = false;

        if (canShow) {
            ivedit.setVisibility(View.VISIBLE);
            setEnable(false);
        } else {
            ivedit.setVisibility(View.GONE);
            setEnable(true);
        }
    }

    private void setFontStyle() {
        if (getFontstyle != null) {
            edtDestination.setTypeface(getFontstyle.CoreSansMedium());
            edttoDate.setTypeface(getFontstyle.CoreSansMedium());
            edtfromDate.setTypeface(getFontstyle.CoreSansMedium());
            edtremarks.setTypeface(getFontstyle.CoreSansMedium());
            edtSource.setTypeface(getFontstyle.CoreSansMedium());
            edtdescription.setTypeface(getFontstyle.CoreSansMedium());
            edtweight.setTypeface(getFontstyle.CoreSansMedium());
            edtweight.setTypeface(getFontstyle.CoreSansMedium());
            buttonCalcDistance.setTypeface(getFontstyle.CoreSansMedium());
            buttonSetTo.setTypeface(getFontstyle.CoreSansBold());
            buttonCalcDistance.setTypeface(getFontstyle.CoreSansBold());
            btnpost.setTypeface(getFontstyle.CoreSansBold());
            tvtitle.setTypeface(getFontstyle.CoreSansBold());

        }
    }

    public void getPermissionProfile() {
        acty.asked(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, new RuntimePermission.OnRequestPermissionsResultListener() {
            @Override
            public void onGranted(String[] permission, int requestCode) {
                setProfile();
            }

            @Override
            public void onDenied(String[] permission, int requestCode) {
                Log.e("onDenied", "onDenied");

            }
        }, 100, "ok", "cancel", "App needs to access your External storage Do you want to grant it?");

    }

    public void asked(String permission[], RuntimePermission.OnRequestPermissionsResultListener listener, int requestCode, String ok, String cancel, String permission_msg) {
        this.permission = new RuntimePermission();
        this.permission.ask(acty, listener, permission, requestCode, permission_msg, ok, cancel);
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void setProfile() {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("" + "Choose Image");
            builder.setItems(new CharSequence[]{"Gallery", "Camera"},
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case 0:

                                    // GET IMAGE FROM THE GALLERY
                              /*  Intent gintent = new Intent();
                                gintent.setType("image*//*");
                                gintent.setAction(Intent.ACTION_GET_CONTENT);
*/
                                    Intent gintent = new Intent(
                                            Intent.ACTION_PICK,
                                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);


                                    Intent chooser = Intent.createChooser(gintent, "Choose a Picture");
                                    startActivityForResult(chooser, GALLERY_CODE);
                                    break;

                                case 1:

                                    String fileName = "new-photo-name.jpg";
                                    // create parameters for Intent with filename
                                    ContentValues values = new ContentValues();
                                    values.put(MediaStore.Images.Media.TITLE,
                                            fileName);
                                    values.put(MediaStore.Images.Media.DESCRIPTION,
                                            "Image captured by camera");
                                    // imageUri is the current activity attribute,
                                    // define and save it for later usage (also in
                                    // onSaveInstanceState)
                                    mImageCaptureUri = acty.getContentResolver()
                                            .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                                    values);
                                    // create new Intent
                                    Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    intent1.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
                                    intent1.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                                    startActivityForResult(intent1, CAMERA_CODE);
                                    break;

                                default:
                                    break;
                            }
                        }
                    });

            builder.show();
        } catch (Exception e) {
        }
    }

    //Decode File
    public void decodeFile(String filePath) {
        // Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, o);

        // The new size we want to scale to
        final int REQUIRED_SIZE = 1024;

        // Find the correct scale value. It should be the power of 2.
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE)
                break;
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        // Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        bitmap = BitmapFactory.decodeFile(filePath, o2);

        ExifInterface ei = null;
        try {
            ei = new ExifInterface(filePath);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL);
        int rotate = 0;
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_270:
                rotate = 270;
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                rotate = 180;
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                rotate = 90;
                break;
        }

        Matrix matrix = new Matrix();
        matrix.preRotate(rotate);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
        try {
            File f = new File(filePath);
            if (!f.exists())
                f.createNewFile();

            // Convert bitmap to byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
            byte[] bitmapdata = getBytesFromBitmap(bitmap);

            // write the bytes in file
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(bitmapdata);

        } catch (Exception e) {
            Log.e("", "" + e.toString());
        }
        if (bitmap != null)
            imgparcel.setImageBitmap(bitmap);
        else {
            String title = getResources().getString(R.string.app_name);
            showAlert(title, "Error", null);
        }
    }

    public void setImage(Uri selectedImageUri) {
//        Log.e("selected Image Uri",""+acty.selectedImageUri);
        if (selectedImageUri != null) {
            try {
                String filemanagerstring = null;
                String selectedImagePath = null;

                if (selectedImageUri.toString().startsWith("content://")) {
                    selectedImagePath = getPath(getContext(), selectedImageUri);
                } else
                    filemanagerstring = selectedImageUri.getPath();

                if (selectedImagePath != null) {
                    Log.e("selected Image filePath", "" + filePath);
                    filePath = selectedImagePath;
                } else if (filemanagerstring != null) {
                    filePath = filemanagerstring;
                } else {
                    String title = getResources().getString(R.string.app_name);
                    Log.e(title, "Error");
                    Log.e("Bitmap", "Unknown path");
                }

                if (filePath != null) {
                    // String filepah_rotated = filePath;
                    //byte[] bitmapdata1 = getBytesFromBitmap(bitmap);
                    //encodedImage = Base64.encodeToString(bitmapdata1, Base64.DEFAULT);
                    decodeFile(filePath);

                    // decodeFilePreview(filePath);
                } else {
                    bitmap = null;
                }
            } catch (Exception e) {
                String title = getResources().getString(R.string.app_name);
                Log.e(title, "Error");
            }
        }
    }

    // convert from bitmap to byte array
    public byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        return stream.toByteArray();
    }

    public static String getPath(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public String getPath(Uri uri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(uri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            ;
            int column_index = cursor.getColumnIndexOrThrow(proj[0]);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    public Bitmap getPreview(String fileName) {
        File image = new File(fileName);

        BitmapFactory.Options bounds = new BitmapFactory.Options();
        bounds.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(image.getPath(), bounds);
        if ((bounds.outWidth == -1) || (bounds.outHeight == -1)) {
            return null;
        }
        int originalSize = (bounds.outHeight > bounds.outWidth) ? bounds.outHeight
                : bounds.outWidth;
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = originalSize / 64;
        return BitmapFactory.decodeFile(image.getPath(), opts);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_CODE && resultCode == RESULT_OK && null != data) {

            Uri selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                setImage(selectedImageUri);

            }
        } else if (requestCode == CAMERA_CODE) {

            if (resultCode == Activity.RESULT_OK) {
                // use imageUri here to access the image
                Camera.CameraInfo info = new Camera.CameraInfo();
                facing = info.facing;
                setImage(mImageCaptureUri);
                //isError = false;
            }
        } else if (requestCode == PLACE_PICKER_REQUEST && resultCode == RESULT_OK) {

            final Place place = PlacePicker.getPlace(getActivity(), data);
            final CharSequence name = place.getName();
            final CharSequence address = place.getAddress();
            String attributions = (String) place.getAttributions();
            if (attributions == null) {
                attributions = "";
            }

            edtSource.setText(address);


        } else if (requestCode == PLACE_PICKER_REQUEST1
                && resultCode == RESULT_OK) {

            final Place place = PlacePicker.getPlace(getActivity(), data);
            final CharSequence name = place.getName();
            final CharSequence address = place.getAddress();
            String attributions = (String) place.getAttributions();
            if (attributions == null) {
                attributions = "";
            }

            edtDestination.setText(address);
        }
    }

}
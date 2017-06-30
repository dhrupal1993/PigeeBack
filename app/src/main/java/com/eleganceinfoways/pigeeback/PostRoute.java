package com.eleganceinfoways.pigeeback;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eleganceinfoways.pigeeback.adapter.CropingOptionAdapter;
import com.eleganceinfoways.pigeeback.model.CropingOption;
import com.eleganceinfoways.pigeeback.utils.GetFontstyle;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.facebook.FacebookSdk.getApplicationContext;

public class PostRoute extends Fragment implements View.OnClickListener{


    private final static int REQUEST_PERMISSION_REQ_CODE = 34;
    private static final int CAMERA_CODE = 101, GALLERY_CODE = 201, CROPING_CODE = 301;
    private static final int PLACE_PICKER_REQUEST = 1;
    private static final int PLACE_PICKER_REQUEST1 = 2;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));
    TextView tvSource,tvDestination;
    ImageView imgVehicle,ivPickToDate,ivPickFromDate;
    EditText edtFromDate,edtToDate;
    Button btnvehicleimage,btnpostroute;
    DatePickerDialog.OnDateSetListener pickDate;
    DatePickerDialog.OnDateSetListener pickfromDate;
    Calendar myCalendar;
    String strpickdate, strfromdate;
    GetFontstyle getFontstyle;
    private Uri mImageCaptureUri;
    private File outPutFile = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments

        View view = inflater.inflate(R.layout.post_service, container, false);

        outPutFile = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");

        myCalendar = Calendar.getInstance();
        tvSource = (TextView)view.findViewById(R.id.tvSource);
        tvDestination = (TextView)view.findViewById(R.id.tvDestination);
        imgVehicle = (ImageView)view.findViewById(R.id.imgvehicle);
        ivPickToDate = (ImageView)view.findViewById(R.id.ivPickToDate);
        ivPickFromDate = (ImageView)view.findViewById(R.id.ivPickFromDate);
        edtFromDate = (EditText)view.findViewById(R.id.edtFromDate);
        edtToDate = (EditText)view.findViewById(R.id.edtToDate);
        btnvehicleimage = (Button)view.findViewById(R.id.btnvehicleimage);
        btnpostroute = (Button)view.findViewById(R.id.btnpostroute);


        if(btnpostroute != null) btnpostroute.setOnClickListener(this);
        if(btnvehicleimage != null) btnvehicleimage.setOnClickListener(this);
        if(ivPickToDate != null) ivPickToDate.setOnClickListener(this);
        if(ivPickFromDate != null) ivPickFromDate.setOnClickListener(this);


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
                edtToDate.setText(strpickdate);
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
                edtFromDate.setText(strfromdate);
            }
        };


        tvSource.setOnClickListener(new View.OnClickListener() {
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

        tvDestination.setOnClickListener(new View.OnClickListener() {
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

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_CODE && resultCode == RESULT_OK && null != data) {

            mImageCaptureUri = data.getData();
            System.out.println("Gallery Image URI : "+mImageCaptureUri);
            CropingIMG();

        } else if (requestCode == CAMERA_CODE && resultCode == RESULT_OK) {

            System.out.println("Camera Image URI : "+mImageCaptureUri);
            CropingIMG();
        } else if (requestCode == CROPING_CODE) {

            try {
                if(outPutFile.exists()){
                    Bitmap photo = decodeFile(outPutFile);
                    imgVehicle.setImageBitmap(photo);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Error while save image", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if (requestCode == PLACE_PICKER_REQUEST && resultCode == RESULT_OK) {

            final Place place = PlacePicker.getPlace(getActivity(), data);
            final CharSequence name = place.getName();
            final CharSequence address = place.getAddress();
            String attributions = (String) place.getAttributions();
            if (attributions == null) {
                attributions = "";
            }

            tvSource.setText(address);

        } else  if (requestCode == PLACE_PICKER_REQUEST1 && resultCode == RESULT_OK) {

            final Place place = PlacePicker.getPlace(getActivity(), data);
            final CharSequence name = place.getName();
            final CharSequence address = place.getAddress();
            String attributions = (String) place.getAttributions();
            if (attributions == null) {
                attributions = "";
            }
            tvDestination.setText(address);
        }
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Post Route");
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.ivPickFromDate:
                DatePickerDialog dialog = new DatePickerDialog(getContext(), pickfromDate, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                dialog.show();
                break;

            case R.id.ivPickToDate:
                DatePickerDialog dialognew = new DatePickerDialog(getContext(), pickDate, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                dialognew.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                dialognew.show();
                break;

            case R.id.btnvehicleimage:
                selectImageOption();
                break;

            default:
                break;
        }

    }

    //Select Image
    private void selectImageOption() {
        final CharSequence[] items = { "Capture Photo", "Choose from Gallery", "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Capture Photo")) {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp1.jpg");
                    mImageCaptureUri = Uri.fromFile(f);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
                   startActivityForResult(intent, CAMERA_CODE);

                } else if (items[item].equals("Choose from Gallery")) {

                    Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, GALLERY_CODE);

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    //Image Cropping
    private void CropingIMG() {

        final ArrayList<CropingOption> cropOptions = new ArrayList<CropingOption>();

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setType("image/*");

        List<ResolveInfo> list = getActivity().getPackageManager().queryIntentActivities( intent, 0 );
        int size = list.size();
        if (size == 0) {
            Toast.makeText(getContext(), "Cann't find image croping app", Toast.LENGTH_SHORT).show();
            return;
        } else {
            intent.setData(mImageCaptureUri);
            intent.putExtra("outputX", 512);
            intent.putExtra("outputY", 512);
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("scale", true);

            //TODO: don't use return-data tag because it's not return large image data and crash not given any message
            //intent.putExtra("return-data", true);

            //Create output file here
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outPutFile));

            if (size == 1) {
                Intent i   = new Intent(intent);
                ResolveInfo res = (ResolveInfo) list.get(0);

                i.setComponent( new ComponentName(res.activityInfo.packageName, res.activityInfo.name));

                startActivityForResult(i, CROPING_CODE);
            } else {
                for (ResolveInfo res : list) {
                    final CropingOption co = new CropingOption();

                    co.title  = getActivity().getPackageManager().getApplicationLabel(res.activityInfo.applicationInfo);
                    co.icon  = getActivity().getPackageManager().getApplicationIcon(res.activityInfo.applicationInfo);
                    co.appIntent= new Intent(intent);
                    co.appIntent.setComponent( new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
                    cropOptions.add(co);
                }

                CropingOptionAdapter adapter = new CropingOptionAdapter(getApplicationContext(), cropOptions);

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Choose Croping App");
                builder.setCancelable(false);
                builder.setAdapter( adapter, new DialogInterface.OnClickListener() {
                    public void onClick( DialogInterface dialog, int item ) {
                        startActivityForResult( cropOptions.get(item).appIntent, CROPING_CODE);
                    }
                });

                builder.setOnCancelListener( new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel( DialogInterface dialog ) {

                        if (mImageCaptureUri != null ) {
                            getActivity().getContentResolver().delete(mImageCaptureUri, null, null );
                            mImageCaptureUri = null;
                        }
                    }
                } );

                AlertDialog alert = builder.create();
                alert.show();
            }
        }
    }

    //Decode file
    private Bitmap decodeFile(File f) {
        try {
            // decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            // Find the correct scale value. It should be the power of 2.
            final int REQUIRED_SIZE = 512;
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            // decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {
        }
        return null;
    }
}

package com.eleganceinfoways.pigeeback;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.eleganceinfoways.pigeeback.utils.RuntimePermission;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.app.DatePickerDialog.Builder;
import static android.app.DatePickerDialog.OnClickListener;
import static android.app.DatePickerDialog.OnDateSetListener;

/**
 * Created by drindia19 on 7/27/2016.
 */

public class FragmentAccountSettings extends Fragment implements View.OnClickListener {


    /*Profile of Customer*/
    Context context;
    MainDrawerActivity acty;
    ImageView ivUserName, ivFirstName, ivLastName, ivGender, ivBirthDate, ivStreet, ivHouseNumber, ivZipCode, ivTown, ivPhoneNumber;

    TextView tvTitle, tvChangePhoto;
    EditText edUserName, edFirstName, edLastName, edBirthDate, edStreet, edHouseNumber, edZipCode, edTown, edPhoneNumber,
            edEmail;
    Button btnSubmit, btnCancel, btnChangepass;
    ImageView ivProfile;
    ImageButton imgbtnCaleder;
    Calendar myCalendar;
    String strstartdate;
    OnDateSetListener Birthdate;
    Date strDate;
    Spinner spGendedr;

    public static final int ACTION_REQUEST_GALLERY = 105,
            ACTION_REQUEST_CAMERA = 106;
    public Uri initialURI;
    Bitmap bitmap;
    public String filePath = "";
    public int facing;
    public String strUname, strFname, strLname, strBdate, strGender, strPhoneNumber, strStreet, strHnumber, strZipcode, strTown, strEmail, strPassword = "";
    private ScrollView sv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_account_settings_1, container, false);

        myCalendar = Calendar.getInstance();
        sv = (ScrollView) view.findViewById(R.id.sv);
        ivUserName = (ImageView) view.findViewById(R.id.acc_txt_username);
        ivFirstName = (ImageView) view.findViewById(R.id.acc_iv_fname);
        ivLastName = (ImageView) view.findViewById(R.id.acc_iv_lname);
        ivGender = (ImageView) view.findViewById(R.id.acc_iv_gender);
        ivStreet = (ImageView) view.findViewById(R.id.acc_iv_street);
        ivHouseNumber = (ImageView) view.findViewById(R.id.acc_iv_hnumber);
        ivZipCode = (ImageView) view.findViewById(R.id.acc_iv_zipcode);
        ivTown = (ImageView) view.findViewById(R.id.acc_iv_town);
        ivPhoneNumber = (ImageView) view.findViewById(R.id.acc_iv_phnenumber);
        ivBirthDate = (ImageView) view.findViewById(R.id.acc_iv_bdate);
        ivProfile = (ImageView) view.findViewById(R.id.ivProfile);
        tvTitle = (TextView) view.findViewById(R.id.acc_set_title);
        tvChangePhoto = (TextView) view.findViewById(R.id.tvChangePhoto);
        edBirthDate = (EditText) view.findViewById(R.id.acc_ed_birthdate);
        edEmail = (EditText) view.findViewById(R.id.acc_ed_email);
        edFirstName = (EditText) view.findViewById(R.id.acc_ed_fname);
        edLastName = (EditText) view.findViewById(R.id.acc_ed_lname);
        edUserName = (EditText) view.findViewById(R.id.acc_ed_uname);
        edStreet = (EditText) view.findViewById(R.id.acc_ed_street);
        edHouseNumber = (EditText) view.findViewById(R.id.acc_ed_hnumver);
        edZipCode = (EditText) view.findViewById(R.id.acc_ed_zipcode);
        edTown = (EditText) view.findViewById(R.id.acc_ed_town);
        edPhoneNumber = (EditText) view.findViewById(R.id.acc_ed_phnenumber);
        btnSubmit = (Button) view.findViewById(R.id.acc_btn_sbmit);
        btnCancel = (Button) view.findViewById(R.id.acc_btn_cancel);
        btnChangepass = (Button) view.findViewById(R.id.acc_btn_changepass);
        imgbtnCaleder = (ImageButton) view.findViewById(R.id.imgbtncalendar);
        spGendedr = (Spinner) view.findViewById(R.id.acc_sp_gender);
        imgbtnCaleder.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        btnChangepass.setOnClickListener(this);
        tvChangePhoto.setOnClickListener(this);
        edUserName.requestFocus();





        Birthdate = new OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd-MM-yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
                strstartdate = sdf.format(myCalendar.getTime());
                edBirthDate.setText(strstartdate);
                edBirthDate.setTextColor(acty.getResources().getColor(R.color.black));
            }
        };
        //validation for email


        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                sv.fullScroll(ScrollView.FOCUS_UP);
            }
        }, 200);

       /* edZipCode.addTextChangedListener(new TextWatcher() {
           @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String str = s.toString();
                if(str!=null && str.replace(" ","").length()>5){
                    new GetCityState(str).execute();
                }

            }
        });*/
        return view;
    }

 /*   @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);
    }

    //When Menu is not there
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                acty.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgbtncalendar:
                DatePickerDialog dialog = new DatePickerDialog(getContext(), Birthdate, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                //dialog.getDatePicker().setMaxDate(new Date().getTime());
                dialog.show();
                break;
            case R.id.acc_btn_sbmit:

                break;

            case R.id.acc_btn_cancel:
                acty.onBackPressed();
                break;

            case R.id.tvChangePhoto:
                getPermissionProfile();
                break;

            default:
                break;
        }
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        this.acty = (MainDrawerActivity) context;
    }



    public void setProfile() {
        try {
            Builder builder = new Builder(getContext());
            builder.setTitle("" + "Choose Image");
            builder.setItems(new CharSequence[]{"select_galle" ,"select_camera"},
                    new OnClickListener() {

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
                                    startActivityForResult(chooser, ACTION_REQUEST_GALLERY);
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
                                    initialURI = acty.getContentResolver()
                                            .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                                    values);
                                    // create new Intent
                                    Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    intent1.putExtra(MediaStore.EXTRA_OUTPUT, initialURI);
                                    intent1.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                                    startActivityForResult(intent1, ACTION_REQUEST_CAMERA);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        boolean isError = true;
        Log.e("" + requestCode, "" + resultCode);
        if (resultCode == Activity.RESULT_OK)
            if (requestCode == ACTION_REQUEST_GALLERY) {
                if (resultCode == Activity.RESULT_OK) {
                    Uri selectedImageUri = data.getData();
                    if (selectedImageUri != null) {
                        setImage(selectedImageUri);
                        isError = false;
                    } else Log.e("Selected Uri", "Is Null Maion Activity");
                }
            } else if (requestCode == ACTION_REQUEST_CAMERA) {

                if (resultCode == Activity.RESULT_OK) {
                    // use imageUri here to access the image
                    Camera.CameraInfo info = new Camera.CameraInfo();
                    facing = info.facing;
                    setImage(initialURI);
                    isError = false;
                }
            }
        if (isError) {
            String title = getResources().getString(R.string.app_name);
               Log.e(title,"Error" );
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
                    Log.e(title,"Error" );
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
                Log.e(title,"Error" );
            }
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
            ivProfile.setImageBitmap(bitmap);
        else {
            String title = getResources().getString(R.string.app_name);
            Log.e(title,"Error" );
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

    //Getting Permission for updating profile for marshmallow. (it was in older version of earni)
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




}

package com.eleganceinfoways.pigeeback;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.eleganceinfoways.pigeeback.data.Config;
import com.eleganceinfoways.pigeeback.utils.InternetUtil;
import com.eleganceinfoways.pigeeback.utils.MyAlertDialog;
import com.eleganceinfoways.pigeeback.utils.ValidationUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Registeration extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextUsername;
    private EditText editName;
    private EditText editTextPassword;
    private EditText editTextPhone;
    private EditText editTextConfirmOtp;
    private ValidationUtil validationUtil;
    MyAlertDialog alertDialog;
    InternetUtil internetUtil;

    public boolean isDataValid = false;
    private TextView linkLogin;
    private AppCompatButton buttonRegister;
    private AppCompatButton buttonConfirm;

    //String variables to hold username password and phone
    private String username;
    private String password;
    private String phone;


    //Volley RequestQueue
    private RequestQueue requestQueue;

    public Registeration() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        //Initializing Views
        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        editName = (EditText) findViewById(R.id.editName);
        buttonRegister = (AppCompatButton) findViewById(R.id.buttonRegister);
        linkLogin = (TextView) findViewById(R.id.linkLogin);
        //Initializing the RequestQueue
        requestQueue = Volley.newRequestQueue(this);

        //Validation
        validationUtil = new ValidationUtil();

        //AlertDialog
        alertDialog = new MyAlertDialog(this);

        //Validation
        internetUtil = new InternetUtil(this);

        //Adding a listener to button
        buttonRegister.setOnClickListener(this);
        linkLogin.setOnClickListener(this);
    }

    //This method would confirm the otp
    private void confirmOtp() throws JSONException {
        //Creating a LayoutInflater object for the dialog box
        LayoutInflater li = LayoutInflater.from(this);
        //Creating a view to get the dialog box
        View confirmDialog = li.inflate(R.layout.dialog_confirm, null);

        //Initizliaing confirm button fo dialog box and edittext of dialog box
        buttonConfirm = (AppCompatButton) confirmDialog.findViewById(R.id.buttonConfirm);
        editTextConfirmOtp = (EditText) confirmDialog.findViewById(R.id.editTextOtp);

        //Creating an alertdialog builder
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        //Adding our dialog box to the view of alert dialog
        alert.setView(confirmDialog);

        //Creating an alert dialog
        final AlertDialog alertDialog = alert.create();

        //Displaying the alert dialog
        alertDialog.show();

        //On the click of the confirm button from alert dialog
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Hiding the alert dialog
                alertDialog.dismiss();

                //Displaying a progressbar
                final ProgressDialog loading = ProgressDialog.show(Registeration.this, getResources().getString(R.string.authenticting), "Please wait while we check the entered code", false, false);

                //Getting the user entered otp from edittext
                final String otp = editTextConfirmOtp.getText().toString().trim();

                //Creating an string request
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.CONFIRM_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //if the server response is success
                                if (response.equalsIgnoreCase("success")) {
                                    //dismissing the progressbar
                                    loading.dismiss();

                                    //Starting a new activity
                                    startActivity(new Intent(Registeration.this, MainDrawerActivity.class));
                                } else {
                                    //Displaying a dialog if the otp entered is wrong
                                    showAlert("", getResources().getString(R.string.wrong_otp), new MyAlertDialog.OnClickListener() {
                                        @Override
                                        public void onClick() {
                                            try {
                                                //Asking user to enter otp again
                                                confirmOtp();
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }                                        }
                                    });
                                  /*  Toast.makeText(Registeration.this,, Toast.LENGTH_LONG).show();
                                    try {
                                        //Asking user to enter otp again
                                        confirmOtp();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                */}
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                alertDialog.dismiss();
                                Toast.makeText(Registeration.this, error.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        //Adding the parameters otp and username
                        params.put(Config.KEY_OTP, otp);
                        params.put(Config.KEY_USERNAME, username);
                        return params;
                    }
                };

                //Adding the request to the queue
                requestQueue.add(stringRequest);
            }
        });
    }

    //this method will register the user
    private void register() {

        //Displaying a progress dialog
        final ProgressDialog loading = ProgressDialog.show(this, "Registering", "Please wait...", false, false);

        //Getting user data
        username = editTextUsername.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();
        phone = editTextPhone.getText().toString().trim();

        //Again creating the string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        try {
                            //Creating the json object from the response
                            JSONObject jsonResponse = new JSONObject(response);

                            //If it is success
                            if (jsonResponse.getString(Config.TAG_RESPONSE).equalsIgnoreCase("Success")) {
                                //Asking user to confirm otp
                                confirmOtp();
                            } else {
                                //If not successful user may already have registered
                                Toast.makeText(Registeration.this, "Username or Phone number already registered", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        Toast.makeText(Registeration.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //Adding the parameters to the request
                params.put(Config.KEY_USERNAME, username);
                params.put(Config.KEY_PASSWORD, password);
                params.put(Config.KEY_PHONE, phone);
                return params;
            }
        };

        //Adding request the the queue
        requestQueue.add(stringRequest);
    }

    private boolean checkData() {
        if ((validationUtil.isEmailValid(editTextUsername.getText().toString().trim()))
                && !validationUtil.isNULL(editTextPassword.getText().toString().trim())
                && !validationUtil.isNULL(editTextPhone.getText().toString().trim())
                && !validationUtil.isNULL(editName.getText().toString().trim())
                && validationUtil.isPhoneValid(editTextPhone.getText().toString().trim())) {
            isDataValid = true;
        }
        return isDataValid;
    }

    @Override
    public void onClick(View v) {

        //register();
        if (v.getId() == R.id.buttonRegister) {
            if (checkData()) {
                register();
               /* Intent main = new Intent(Registeration.this, MainDrawerActivity.class);
                startActivity(main);*/
            } else {
                alertDialog.show("Ok", "Please enter valid data to Register.");
            }
        } else if (v.getId() == R.id.linkLogin) {
            Intent login = new Intent(Registeration.this, Login.class);
            startActivity(login);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
            finish();
        }

    }

    public void showAlert(String title, String message, MyAlertDialog.OnClickListener listener) {
        MyAlertDialog myAlertDialog = new MyAlertDialog(this, listener);
        myAlertDialog.show(getResources().getString(R.string.ok), message);

    }

}

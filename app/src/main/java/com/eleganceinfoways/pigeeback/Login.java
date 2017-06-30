package com.eleganceinfoways.pigeeback;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.eleganceinfoways.pigeeback.data.SharedData;
import com.eleganceinfoways.pigeeback.model.FacebookPojo;
import com.eleganceinfoways.pigeeback.utils.InternetUtil;
import com.eleganceinfoways.pigeeback.utils.MyAlertDialog;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.User;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import io.fabric.sdk.android.Fabric;
import retrofit2.Call;

public class Login extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "eDb1VsGda6atjZoDHLL16YZKQ";
    private static final String TWITTER_SECRET = "q3dWYUbELKm060LGdqHAuoHSMSqzEJSmvOcWpQeTG8xOY2NCq4";
    private static final String TAG = "TwitterLogin";
    CallbackManager callbackManager;
    TwitterLoginButton twitterLoginButton;
    GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 9001;
    private static final int RC_TWITTER = 9002;
    AutoCompleteTextView txtemail;
    public boolean isProvider = false;
    ToggleButton toggle;
    // MyAlertDialog alertDialog;
    InternetUtil internetUtil;
    FacebookPojo fbobj;
    TextView tvsignup, txtPassword;
    Button btnlogin, btnfacebook;
    Gson gson;
    SharedData sharedData;
    public boolean isLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedData = new SharedData(this);
        if (sharedData.getBoolean("isLogin", false)) {
            Intent intent = new Intent(Login.this, MainDrawerActivity.class);
            startActivity(intent);
            finish();
        }

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_login);
        tvsignup = (TextView) findViewById(R.id.linkSignup);
        txtPassword = (TextView) findViewById(R.id.txtPassword);
        twitterLoginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
        btnlogin = (Button) findViewById(R.id.btnLogin);
        txtemail = (AutoCompleteTextView) findViewById(R.id.txtemail);
        btnfacebook = (Button) findViewById(R.id.facebook_login_button);
        toggle = (ToggleButton) findViewById(R.id.toggle);
        internetUtil = new InternetUtil(this);

        gson = new Gson();
       /* CommonClass commonClass = new CommonClass();
        commonClass.printKeyHash(this);*/

        if (toggle != null) toggle.setOnClickListener(this);
        tvsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(Login.this, Registeration.class);
                startActivity(register);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (internetUtil.isNetworkAvailableSimple()) {

                    if (toggle.isChecked()) {
                        if (txtemail.getText().toString().equals("admin@pigeeback.com")
                                && (txtPassword.getText().toString().equals("admin")))
                        {

                            Intent main = new Intent(Login.this, MainDrawerActivity.class);
                            main.putExtra("isProvider", isProvider);
                            startActivity(main);
                            sharedData.putBoolean("isLogin", true);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                            finish();
                        }

                    } else if (txtemail.getText().toString().equals("customer@pigeeback.com")
                                && (txtPassword.getText().toString().equals("customer")))
                        {
                            Intent main = new Intent(Login.this, MainDrawerActivity.class);
                            main.putExtra("isProvider", isProvider);
                            startActivity(main);
                            sharedData.putBoolean("isLogin", true);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                            finish();
                        }
                        else{
                        showAlert("Ok", getResources().getString(R.string.data_validation), null);

                    }


                } else {
                    showAlert("Ok", getResources().getString(R.string.no_internet), null);
                }
            }
        });
        //Add callback to the button

        twitterLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                //If login succeeds passing the Calling the login method and passing Result object
                login(result);
            }

            @Override
            public void failure(TwitterException exception) {
                //If failure occurs while login handle it here
                Log.d("TwitterKit", "Login with Twitter failure", exception);
            }
        });

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.eleganceinfoways.pigeeback", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.i("KeyHash:",Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

        findViewById(R.id.btn_google).setOnClickListener(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        //Facebook login
        btnfacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (internetUtil.isNetworkAvailableSimple()) {
                    // isLoginFacebook = true;
                    onFblogin();
                } else {
                    showAlert("", getResources().getString(R.string.no_internet), null);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_google:
                signIn();
                break;

            case R.id.toggle:
                if (toggle.isChecked()) {
                    isProvider = true;
                } else {
                    isProvider = false;
                }
                sharedData.putBoolean("isProvider", isProvider);
                break;
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    //getting permission for fb and get the response of user
    private void onFblogin() {
        //Logout First
        try {
            LoginManager.getInstance().logOut();
        } catch (Exception e) {
        }
        // Set permissions
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "public_profile"));
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        System.out.println("OnSuccess");
                        //Graph Request
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(
                                            JSONObject json,
                                            GraphResponse response) {
                                        // Application code
                                        if (response.getError() != null) {
                                            // handle error
                                            System.out.println("ERROR");
                                        } else {
                                            System.out.println("Success");
                                            try {
                                                String jsonresult = String.valueOf(json);
                                                System.out.println("JSON Result" + jsonresult);

                                                fbobj = gson.fromJson(jsonresult, FacebookPojo.class);
                                            /*  try {
                                                    if (fbobj != null && fbobj.birthday.length() > 0) {
                                                        fbobj.birthday = fbobj.birthday.replaceAll("/", "-");
                                                        SimpleDateFormat dFormat = new SimpleDateFormat("MM-dd-yyyy");
                                                        fbobj.birthday = new SimpleDateFormat("dd-MM-yyyy").format(dFormat.parse(fbobj.birthday));
                                                    }
                                                } catch (Exception e) {
                                                }*/
                                                //   isLoginFacebook = true;
                                                //   Log.e("birthdate",fbobj.birthday);
                                                //new DoLogin().execute();

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,link,email,first_name,last_name,picture.type(large),gender");
                        request.setParameters(parameters);
                        request.executeAsync();

                    }

                    @Override
                    public void onCancel() {
                        Log.d("OnCancel", "On cancel");
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.d("OnError", error.toString());
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        } else if (requestCode == TwitterAuthConfig.DEFAULT_AUTH_REQUEST_CODE) {
            twitterLoginButton.onActivityResult(requestCode, resultCode, data);
        } else
            callbackManager.onActivityResult(requestCode, resultCode, data);

    }

    public void login(Result<TwitterSession> result) {
        //Creating a twitter session with result's data
        TwitterSession session = result.data;

        //Getting the username from session
        final String userName = session.getUserName();


        //Getting the account service of the user logged in
        Call<User> call = Twitter.getApiClient(session).getAccountService()
                .verifyCredentials(true, false);
        call.enqueue(new Callback<User>() {
            @Override
            public void failure(TwitterException e) {
                //If any error occurs handle it here
            }

            @Override
            public void success(Result<User> userResult) {
                //If it succeeds creating a User object from userResult.data
                User user = userResult.data;
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.welcome) + userName, Toast.LENGTH_LONG).show();
                twitterLoginButton.setVisibility(View.GONE);
            }
        });
    }

    private void handleSignInResult(GoogleSignInResult result) {

        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            Toast.makeText(getApplicationContext(), "Logged in as : " + acct.getDisplayName(), Toast.LENGTH_LONG).show();

        } else {
            // Signed out, show unauthenticated UI.
            // updateUI(false);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    public void showAlert(String title, String message, MyAlertDialog.OnClickListener listener) {
        MyAlertDialog myAlertDialog = new MyAlertDialog(this, listener);
        myAlertDialog.show(getResources().getString(R.string.ok), message);

    }

}

package com.eleganceinfoways.pigeeback.data;

/**
 * Created by Dhrupal on 31-01-2017.
 */

public class Config {

    //URLs to register.php and confirm.php file
    public static final String REGISTER_URL = "http://www.pigeeback.com/API/register.php";
    public static final String CONFIRM_URL = "http://www.pigeeback.com/API/confirm.php";

    //Keys to send username, password, phone and otp
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_OTP = "otp";

    //JSON Tag from response from server
    public static final String TAG_RESPONSE= "ErrorMessage";
}

package com.eleganceinfoways.pigeeback.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.net.ParseException;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.widget.EditText;

public class ValidationUtil {
	
	public static boolean isValidEmailId(String emailid){
		Pattern pattern;
	    Matcher matcher;
	    final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	    pattern = Pattern.compile(EMAIL_PATTERN);
	    matcher = pattern.matcher(emailid);
	    return matcher.matches();
	}
	
	public static boolean isIdentical(String str1, String str2){
		return (str1.equals(str2));
	}
	
	public static boolean isEmailValid(CharSequence email) {
	    boolean isValid = false;

	    String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,3}$";

	    Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
	    Matcher matcher = pattern.matcher(email);
	    if (matcher.matches()) {
	        isValid = true;
	    }
	    return isValid;
	}
	
	public static boolean isDateValid(String dateToValidate, String dateFromat){
		  if(dateToValidate == null){
		   return false;
		  }
		  SimpleDateFormat sdf = new SimpleDateFormat(dateFromat);
		  sdf.setLenient(false);
		  try {
		   //if not valid, it will throw ParseException
		   Date date = sdf.parse(dateToValidate);
		   System.out.println(date);
		  } catch (ParseException e) {
		   e.printStackTrace();
		   return false;
		  }catch (Exception ex){
		   ex.printStackTrace();
		   return false;
		  }
		  return true;
		 }
	
	public static boolean isEmptyValid(EditText et) {
		return ! TextUtils.isEmpty(et.getText());
	}
	
	public static boolean isAlNumValid(CharSequence str) {
		boolean isValid = false;

	    String expression = "[a-zA-Z0-9 \\./-]*";
	    Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
	    Matcher matcher = pattern.matcher(str);
	    if (matcher.matches()) {
	        isValid = true;
	    }
	    return isValid;
	}
	
	public static boolean isAlphaValid(CharSequence str) {
		boolean isValid = false;

	    String expression = "[a-zA-Z \\./-]*";
	    Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
	    Matcher matcher = pattern.matcher(str);
	    if (matcher.matches()) {
	        isValid = true;
	    }
	    return isValid;
	}
	
	public static boolean isNumericValid(EditText et) {
		return TextUtils.isDigitsOnly(et.getText());
	}
	
	public static boolean isPhoneValid(CharSequence str) {
		boolean isValid = false;
//		Pattern pattern = null;
//		String expression;
//		
//		if (Build.VERSION.SDK_INT>=8)
//		{
//			expression = "(\\+[0-9]+[\\- \\.]*)?" + "(\\([0-9]+\\)[\\- \\.]*)?" + "([0-9][0-9\\- \\.][0-9\\- \\.]+[0-9])";
//			pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
//		}
//		else
//		{
//			pattern = Patterns.PHONE;
//		}
//		
//	    Matcher matcher = pattern.matcher(str);
//	    
//	    
//	    int len = str.length();
//	    if (matcher.matches() && len >= 10 && len <= 13) {
//	        isValid = true;
//	    }
		
		 String st = String.valueOf(str);
		 int len = st.length();
		if(PhoneNumberUtils.isGlobalPhoneNumber(st) && (len >=10 && len <=13) )
			return true;
		else
			return false;
	}

	//Check this all regular expression special for UK that we have to keep default
	public static boolean isZipValid(CharSequence str) {
		boolean isValid = false;
		String countryCode = "US";
		String expression= "^(GIR|[A-Z]\\d[A-Z\\d]??|[A-Z]{2}\\d[A-Z\\d]??)[ ]??(\\d[A-Z]{2})$";
		//String expression="([A-Z]{1,2}[0-9]{1,2})\\ ([0-9][A-Z]{2}))"; //This is giving some error
		String expression2="(GIR\\ 0AA)$";
		
		if (countryCode.equalsIgnoreCase("US"))
			expression = "^\\d{5}([\\-]?\\d{4})?$";
		else if (countryCode.equalsIgnoreCase("IN"))
			expression = "^\\d{6}";
		else if (countryCode.equalsIgnoreCase("DE"))
			expression = "\\b((?:0[1-46-9]\\d{3})|(?:[1-357-9]\\d{4})|(?:[4][0-24-9]\\d{3})|(?:[6][013-9]\\d{3}))\\b";
		else if (countryCode.equalsIgnoreCase("CA"))
			expression = "^([ABCEGHJKLMNPRSTVXY]\\d[ABCEGHJKLMNPRSTVWXYZ])\\ {0,1}(\\d[ABCEGHJKLMNPRSTVWXYZ]\\d)$";
		else if (countryCode.equalsIgnoreCase("FR"))
			expression ="^(F-)?((2[A|B])|[0-9]{2})[0-9]{3}$";
		else if (countryCode.equalsIgnoreCase("IT"))
			expression = "^(V-|I-)?[0-9]{5}$";
		else if (countryCode.equalsIgnoreCase("AU"))
			expression = "^(0[289][0-9]{2})|([1345689][0-9]{3})|(2[0-8][0-9]{2})|(290[0-9])|(291[0-4])|(7[0-4][0-9]{2})|(7[8-9][0-9]{2})$";
		else if (countryCode.equalsIgnoreCase("NL"))
			expression = "^[1-9][0-9]{3}\\s?([a-zA-Z]{2})?$";
		else if (countryCode.equalsIgnoreCase("ES"))
			expression = "^([1-9]{2}|[0-9][1-9]|[1-9][0-9])[0-9]{3}$";
		else if (countryCode.equalsIgnoreCase("DK"))
			expression = "^([D-d][K-k])?( |-)?[1-9]{1}[0-9]{3}$";
		else if (countryCode.equalsIgnoreCase("SE"))
			expression = "^(s-|S-){0,1}[0-9]{3}\\s?[0-9]{2}$";
		else if (countryCode.equalsIgnoreCase("BE"))
			expression = "^[1-9]{1}[0-9]{3}$";

	    Pattern pattern1 = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
	    Pattern pattern2 = Pattern.compile(expression2, Pattern.CASE_INSENSITIVE);
	    Matcher matcher1 = pattern1.matcher(str);
	    Matcher matcher2 = pattern2.matcher(str);
	    if (matcher1.matches() || matcher2.matches() ) {
	        isValid = true;
	    }
	    return isValid;
	}
	
	public static boolean isSSNValid(CharSequence str) {
		boolean isValid = false;

	    String expression = "^((?!000)(?!666)(?:[0-6]\\d{2}|7[0-2][0-9]|73[0-3]|7[5-6][0-9]|77[0-2]))-((?!00)\\d{2})-((?!0000)\\d{4})$";
	    Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
	    Matcher matcher = pattern.matcher(str);
	    if (matcher.matches()) {
	        isValid = true;
	    }
	    return isValid;
	}

	//string is null or  not..
	public static boolean isNULL(String str){

		if(str == null || str.equalsIgnoreCase("") || str.equalsIgnoreCase(" ") || str.equalsIgnoreCase("null")){
			return true;
		}

		return false;
	}

}

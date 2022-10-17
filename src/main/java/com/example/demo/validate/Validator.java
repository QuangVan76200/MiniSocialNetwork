package com.example.demo.validate;

import java.util.regex.Pattern;

public class Validator {

	private static final Pattern REGEX_EMAIL=Pattern.compile("/^[a-zA-Z0-9_.+-]+@fsoft.com.vn$/");
	
	
	public static Boolean isValidateEmail(String email) {
		return REGEX_EMAIL.matcher(email).matches();
	}
}

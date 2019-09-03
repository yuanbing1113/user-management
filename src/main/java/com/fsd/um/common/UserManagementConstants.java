package com.fsd.um.common;

import java.util.ArrayList;
import java.util.List;

public class UserManagementConstants {
	
	private UserManagementConstants() {
		
	}
	
	public static final List<String> PUBLIC_URLS = new ArrayList<>();
	public static final String SPRING_SECURITY_SAVED_REQUEST = "SPRING_SECURITY_SAVED_REQUEST";
	public static final String URL_USER_ACCOUNT = "/user/account";
	
	static {
		PUBLIC_URLS.add("/captcha");
		PUBLIC_URLS.add("/login");
		PUBLIC_URLS.add("/register");
	}
}

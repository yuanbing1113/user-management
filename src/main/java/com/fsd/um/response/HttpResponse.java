package com.fsd.um.response;

public class HttpResponse {
	public static final int STATUS_OK = 200;
	public static final int STATUS_BAD_REQUEST = 400;
	public static final int STATUS_INTERNAL_ERROR = 500;

	private int code;
	private Object data;
	private Object message;

	public static final HttpResponse OK = new HttpResponse(STATUS_OK);

	public static HttpResponse ok(Object o) {
		return new HttpResponse(STATUS_OK, o);
	}

	public static HttpResponse error(String data) {
		return new HttpResponse(STATUS_BAD_REQUEST, data);
	}

	public static HttpResponse fatal(String data) {
		return new HttpResponse(STATUS_INTERNAL_ERROR, data);
	}

	public HttpResponse(int code) {
	        this(code, "");
	    }

	public HttpResponse(int code, Object data) {
	        this.code = code;
	        if(code == STATUS_OK) {
	            this.data = data;
	            this.message = "success";
	        } else {
	            this.message = data;
	        }
	        
	    }

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Object getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Object getMessage() {
		return message;
	}

	public void setMessage(Object message) {
		this.message = message;
	}

}

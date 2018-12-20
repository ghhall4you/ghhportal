package com.ghh.framework.exception;

/*****************************************************************
 *
 * 用户身份认证异常类
 *
 * @author ghh
 * @date 2018年12月19日下午10:35:09
 * @since v1.0.1
 ****************************************************************/
public class AuthException extends Exception {

	private static final long serialVersionUID = -918297720990500482L;
	Throwable myException;

	public AuthException(String errorMsg) {
		super(errorMsg);
	}

	public AuthException(String errorMsg, Exception e) {
		super(errorMsg);
		this.myException = e;
	}

	public String getDetailMessage() {
		if (myException != null) {
			return myException.getMessage();
		} else {
			return null;
		}
	}

	public String toString() {
		String s = this.getMessage();
		if (myException != null) {
			s = s + "," + myException.getMessage();
		}
		return s;
	}

	public void printStackTrace() {
		// this.printStackTrace();
		if (myException != null) {
			myException.printStackTrace();
		}
	}

	public Throwable getMyException() {
		return myException;
	}

	public void setMyException(Throwable myException) {
		this.myException = myException;
	}

}
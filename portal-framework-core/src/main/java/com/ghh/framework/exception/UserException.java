package com.ghh.framework.exception;

/*****************************************************************
 *
 * 用户异常类
 *
 * @author ghh
 * @date 2018年12月19日下午10:36:00
 * @since v1.0.1
 ****************************************************************/
public class UserException extends Exception {

	private static final long serialVersionUID = 1L;
	Throwable myException;

	public UserException(String errorMsg) {
		super(errorMsg);
	}

	public UserException(String errorMsg, Exception e) {
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

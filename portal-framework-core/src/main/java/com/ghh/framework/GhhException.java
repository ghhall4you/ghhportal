package com.ghh.framework;

/**
 * 应用异常
 * @author zhouzw
 *
 */
public class GhhException extends Exception{

	private static final long serialVersionUID = 1L;
	Throwable myException;
	
	public GhhException(String errorMsg){
		super(errorMsg);
	}
	
	public GhhException(String errorMsg,Exception e){
		super(errorMsg);
		this.myException=e;
	}
	
	public String getDetailMessage(){
		if(myException!=null){
			return myException.getMessage();
		}else{
			return null;
		}
	}
	
	public String toString(){
		String s=this.getMessage();
		if(myException!=null){
			s=s+","+myException.getMessage();
		}
		return s;
	}
	
	public void printStackTrace(){
		//this.printStackTrace();
		if(myException!=null){
			myException.printStackTrace();
		}
	}
	
	/**
	 * 覆盖父类的这个异常输出方法，不显示方法栈运行情况
	 */
	@Override
	public Throwable fillInStackTrace() {
		return this;
	}

	public Throwable getMyException() {
		return myException;
	}

	public void setMyException(Throwable myException) {
		this.myException = myException;
	}
}

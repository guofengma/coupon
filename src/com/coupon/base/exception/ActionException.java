package com.coupon.base.exception;


public class ActionException extends CatchedException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5935167937413542509L;

	public ActionException() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param s
	 * @param e
	 */
	public ActionException(String s, Throwable e) {
		super(s, e);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param s
	 */
	public ActionException(String s) {
		super(s);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param e
	 */
	public ActionException(Throwable e) {
		super(e);
		// TODO Auto-generated constructor stub
	}
	
}

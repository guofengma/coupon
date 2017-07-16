package com.coupon.base.exception;

public class DAOException extends CatchedException {

	private static final long serialVersionUID = -5935167937413542509L;

	public DAOException() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param s
	 * @param e
	 */
	public DAOException(String s, Throwable e) {
		super(s, e);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param s
	 */
	public DAOException(String s) {
		super(s);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param e
	 */
	public DAOException(Throwable e) {
		super(e);
		// TODO Auto-generated constructor stub
	}
}

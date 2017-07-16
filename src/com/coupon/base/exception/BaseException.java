package com.coupon.base.exception;

import org.apache.log4j.Logger;

/**
 * 
 * @author niexiao
 * 异常基类
 */
public class BaseException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1033884159796034858L;
	private Logger logger = Logger.getLogger(getClass());
	private Throwable _rootCause;
	private Object[] errorArgs;

	// private String message;
	/**
	 * 
	 */
	public BaseException() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param s
	 */
	public BaseException(String s) {
		super(s);
		_rootCause = this;
	}

	public BaseException(String s, Throwable e) {
		super(s);
		if (e instanceof BaseException) {
			_rootCause = ((BaseException) e)._rootCause;
		} else {
			logger.info(e.getClass(), e);
			_rootCause = this;
		}
		logger.debug(s, e);
	}

	public BaseException(Throwable e) {
		this(e.getMessage(), e);
	}

	/**
	 * @return
	 */
	public Throwable getRootCause() {
		return _rootCause;
	}

	/**
	 * 
	 * @return
	 */
	public String getDetailMessage() {
		String msg = getMessage();
		Throwable cause = getCause();
		if (cause != null && cause instanceof BaseException) {
			return msg += "\n原因: " + ((BaseException) cause).getDetailMessage();
		}
		return msg;
	}

	/**
	 * 返回错误信息
	 */
	public String getMessage() {
		return super.getMessage();
	}

	public void printStackTrace() {
		printStackTrace(System.err);
	}

}

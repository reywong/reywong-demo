package cn.com.yto56.coresystem.common.stl.framework.db.exception;

/**
 * 错误来源
 * 
 * @author luoyue@cn.ibm.com
 *
 */
public interface ExceptionSource {

	public static int ERR_SOURCE_FRAMEWORK  = 0;
	public static int ERR_SOURCE_APPLICAITON_SERVER  = 1;
	public static int ERR_SOURCE_DATABASE  = 2;
	public static int ERR_SOURCE_DEPENDS_LIB  = 3;
	public static int ERR_SOURCE_APPLICAITON  = 4;
	public static int ERR_SOURCE_DEPLOY  = 5;
	public static int ERR_SOURCE_USER  = 6;
	public static int ERR_SOURCE_BIZ  = 7;
	public static int ERR_SOURCE_INTEGRATION  = 8;
	public static int ERR_SOURCE_PROCESS  = 9;
	public static int ERR_SOURCE_PROCESS_ENGINE  = 10;
	public static int ERR_SOURCE_OPERATE  = 11;
	public static int ERR_SOURCE_CONFIG  = 12;
	public static int ERR_SOURCE_SERVICE  = 13;
	public static int ERR_SOURCE_UNKNOWN  = 99;
}

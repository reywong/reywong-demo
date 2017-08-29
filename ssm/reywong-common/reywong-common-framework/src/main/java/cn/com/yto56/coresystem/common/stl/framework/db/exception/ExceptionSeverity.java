package cn.com.yto56.coresystem.common.stl.framework.db.exception;

/**
 * 错误严重程度
 * 
 * @author luoyue@cn.ibm.com
 *
 */
public interface ExceptionSeverity {
	public static int ERR_SEVERITY_HINT  = 0;
	public static int ERR_SEVERITY_LOW  = 1;
	public static int ERR_SEVERITY_NORMAL  = 2;
	public static int ERR_SEVERITY_HIGH  = 3;
	public static int ERR_SEVERITY_FATAL  = 4;
}

package cn.com.yto56.coresystem.common.stl.framework.db.exception;


public class DaoException extends BaseRuntimeException {
    private static final long serialVersionUID = 2138418113786442626L;

    /**
     * 构造数据持久化操作异常
     *
     * @param code 异常编号
     */
    public DaoException(int code) {
        super(null, null, code, ExceptionSeverity.ERR_SEVERITY_NORMAL, ExceptionSource.ERR_SOURCE_DATABASE);
    }

    /**
     * 构造数据持久化操作异常
     *
     * @param message 原始异常信息
     * @param code    异常编号
     */
    public DaoException(String message, int code) {
        super(message, null, code, ExceptionSeverity.ERR_SEVERITY_NORMAL, ExceptionSource.ERR_SOURCE_DATABASE);
    }

    /**
     * 构造数据持久化操作异常
     *
     * @param cause 原始异常
     * @param code  异常编号
     */
    public DaoException(Throwable cause, int code) {
        super(null, cause, code, ExceptionSeverity.ERR_SEVERITY_NORMAL,ExceptionSource.ERR_SOURCE_DATABASE);
    }

    /**
     * 构造数据持久化操作异常
     *
     * @param code     异常编号
     * @param severity 异常级别, 缺省为
     */
    public DaoException(int code, int severity) {
        super(null, null, code, severity, ExceptionSource.ERR_SOURCE_DATABASE);
    }

    /**
     * 构造数据持久化操作异常
     *
     * @param message  原始异常信息
     * @param code     异常编号
     * @param severity 异常级别
     */
    public DaoException(String message, int code, int severity) {
        super(message, null, code, severity,ExceptionSource.ERR_SOURCE_DATABASE);
    }

    /**
     * 构造数据持久化操作异常
     *
     * @param cause    原始异常
     * @param code     异常编号
     * @param severity 异常级别
     */
    public DaoException(Throwable cause, int code, int severity) {
        super(null, cause, code, severity, ExceptionSource.ERR_SOURCE_DATABASE);
    }
}

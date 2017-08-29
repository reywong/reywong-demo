package cn.com.yto56.coresystem.common.stl.framework.db.dao.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * JdbcDao注解
 * 用于给dao实现层加数据源
 *
 * @author lihui
 *         2016-01-23
 *         <br/>
 *         使用是需在springmvc-plugin.xml中加:</br>
 *         &lt;!-- 扫描JdbcDao注解 --&gt;</br>
 *         &lt;bean class="cn.com.yto56.coresystem.framework.jdbc.dao.DaoScannerConfigurer"/&gt;
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface JdbcDao {

    /**
     * 配置数据源bean的id
     * 如果写成${dataSource} 则在属性文件中找KEY为dataSource对应的value
     * 如果找到则用value作为数据源名去上下文中找数据源,
     * 如果没找到则用${}中的值作为数据源名去找
     *
     * @return
     */
    String dataSource();

}

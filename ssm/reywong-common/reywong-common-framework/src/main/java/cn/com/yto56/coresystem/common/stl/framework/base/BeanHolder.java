package cn.com.yto56.coresystem.common.stl.framework.base;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * 获取spring bean对象
 * @author lihui
 *
 */
public class BeanHolder {

	public static <T> T getBean(String id,Class<T> clazz){
		WebApplicationContext application = 
				ContextLoader.getCurrentWebApplicationContext();
		return application.getBean(id,clazz);
	}
	
	public static Object getBean(String id){
		WebApplicationContext application = 
				ContextLoader.getCurrentWebApplicationContext();
		return application.getBean(id);
	}
	
	public static <T> T getBean(Class<T> clazz){
		String beanName = StringUtils.firstToLowerCase(clazz.getSimpleName());
		return getBean(beanName,clazz);
	}
}

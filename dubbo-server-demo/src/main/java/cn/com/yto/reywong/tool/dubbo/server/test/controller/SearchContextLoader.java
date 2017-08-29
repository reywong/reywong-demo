package cn.com.yto.reywong.tool.dubbo.server.test.controller;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.ContextLoader;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by wangrui on 2017/4/25.
 */
public class SearchContextLoader extends ContextLoader implements ServletContextListener {
    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        String config = arg0.getServletContext().getInitParameter("contextConfigLocationT");
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(config);
        context.start();
    }

}

package cn.com.yto.reywong.tool.dubbo.client.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by wangrui on 2017/4/20.
 */
public class TestClient {
    private final static Logger logger = LoggerFactory.getLogger(TestClient.class);


    public static void main(String[] args) throws InterruptedException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath*:META-INF/spring/applicationContext-dubbo.xml");

        for (int i = 0; i < 100; i++) {
            Thread.sleep(500);
//            HessianProxyFactory factory = new HessianProxyFactory();
//            String url = "http://localhost:8080/demo/demoService";
//            try {
//                DemoService demoService = (DemoService) factory.create(DemoService.class, url);
//                System.out.println(demoService.sayHello("dddd"));
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            }

            cn.com.yto.reywong.tool.dubbo.client.test.DemoService demoService = (cn.com.yto.reywong.tool.dubbo.client.test.DemoService) context.getBean("demoService");
            String hello = demoService.sayHello("reywong");
            System.out.println(hello);


        }
        context.start();
//
//        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath*:META-INF/spring/applicationContext-dubbo.xml");
//
//        DemoService demoService = (DemoService) context.getBean("demoService");
//        String hello = demoService.sayHello("reywong");
//        System.out.println(hello);
//
//        context.start();

    }


}

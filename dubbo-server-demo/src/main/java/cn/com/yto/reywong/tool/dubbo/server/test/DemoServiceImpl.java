package cn.com.yto.reywong.tool.dubbo.server.test;

/**
 * Created by user on 2016/10/8.
 */

import org.springframework.stereotype.Service;

@Service(value = "demoService")
public class DemoServiceImpl implements DemoService {


    @Override
    public String sayHello(String name) {
        return "ggggggggggggggHello =" + name;
    }
}

package cn.com.yto.reywong.tool.java.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class SystemPathUtil {
    public String getCurrentPath() throws IOException {
        StringBuffer sb = new StringBuffer();
        //返回读取指定资源的输入流
        System.out.println(this.getClass().getResource("test.tpl"));
        InputStream is = this.getClass().getResourceAsStream("test.tpl");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String s = "";
        while ((s = br.readLine()) != null) {
            sb.append(s);
        }
        return sb.toString();
    }

    public static void main(String[] args) throws IOException {
        SystemPathUtil systemPathUtil = new SystemPathUtil();
        System.out.println(systemPathUtil.getCurrentPath());
    }
}

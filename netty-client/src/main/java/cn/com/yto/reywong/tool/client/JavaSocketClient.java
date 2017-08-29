package cn.com.yto.reywong.tool.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Created by wangrui on 2017/4/13.
 */
public class JavaSocketClient {


    public static void main(String[] args) throws Exception {

        //向服务器发送请求
        Socket socket = new Socket("127.0.0.1", 88);

        new Thread(new RunnableClient(socket)).start();
        //获取输出流
        PrintStream print = new PrintStream(socket.getOutputStream());
        String line = "";
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        while ((line = read.readLine()) != null) {
            //写入Socket对应的输出流
            print.println(line);
        }
    }
}

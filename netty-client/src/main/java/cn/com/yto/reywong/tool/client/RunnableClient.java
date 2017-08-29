package cn.com.yto.reywong.tool.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by wangrui on 2017/4/13.
 */
public class RunnableClient implements Runnable {
    private Socket socket;
    private BufferedReader read;

    public RunnableClient(Socket socket) throws IOException {
        this.socket = socket;
        this.read = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
    }

    @Override
    public void run() {
        try {
            String line = null;
            while ((line = read.readLine()) != null) {
                System.out.println("返回值："+line);
            }

        } catch (IOException io) {
            io.printStackTrace();
        }

    }
}

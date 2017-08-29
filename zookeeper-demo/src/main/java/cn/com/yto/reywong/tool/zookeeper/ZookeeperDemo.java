package cn.com.yto.reywong.tool.zookeeper;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

import java.util.List;

/**
 * Created by wangrui on 2017/5/2.
 */
public class ZookeeperDemo {
    public static void main(String[] args) {

//        zkServers：Zookeeper服务器地址
//        sessionTimeout：session超时时间
//        connectionTimeout：连接超时时间
//        zkSerializer：序列化器，ZkClient提供2种
//        SerializableSerializer：对象序列化，可转换对象
//        BytesPushThroughSerializer：字节数组序列化

        ZkClient zkClient = new ZkClient("10.129.221.158:2183", 5000,2000,new SerializableSerializer());
        //查询zookeeper
        List<String> list = zkClient.getChildren("/dubbo");
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                System.out.println(list.get(i));
            }
        }
        //添加节点
//        zkClient.create

    }
}

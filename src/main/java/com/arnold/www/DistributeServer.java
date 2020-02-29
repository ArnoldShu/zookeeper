package com.arnold.www;

import org.apache.zookeeper.*;

import java.io.IOException;

/**
 * @ClassName DistributeServer
 * @Description: zookeeper服务端程序
 * @Author Arnold
 * @Date 2020/2/28
 * @Version V2.0
 **/
public class DistributeServer {
    //集群环境通信地址
    private String connectint ="hadoop1:2181,hadoop2:2181,hadoop3:2181";
    //session超时时间
    private int sessionTimeout = 5000;
    //zookeeper客户端
    private static ZooKeeper zooKeeperClient;

    /**
     * @MethodName: getConnection
     * @Description: 链接集群
     * @Param: void
     * @Return: void
     * @Author: Arnold
     * @Date: 2020/2/28
     **/
    public  void getConnection() {
        //初始化一个zookeeper client
        try {
            zooKeeperClient = new ZooKeeper(connectint, 5000, new Watcher() {
                public void process(WatchedEvent watchedEvent) {

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
     /**
      * @MethodName: regist
      * @Description: 注册节点
      * @Param:
      * @Return:
      * @Author: Arnold
      * @Date: 2020/2/29
     **/
    public void regist(String hostname){
        try {
            String path = zooKeeperClient.create("/servers/server",hostname.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL_SEQUENTIAL);
            System.out.println(hostname+"is online !!!");
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
     /**
      * @MethodName: bussiness
      * @Description: 模拟注册成功之后的逻辑
      * @Param: void
      * @Return: void
      * @Author: Arnold
      * @Date: 2020/2/28
     **/
    public void bussiness() {
        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //链接zookeeper 服务端注册到进群众
        DistributeServer server = new DistributeServer();
        //链接集群
        server.getConnection();
        //注册节点
        server.regist(args[0].toString());
        //逻辑处理
        server.bussiness();
    }
}
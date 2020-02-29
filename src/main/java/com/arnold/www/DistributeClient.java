package com.arnold.www;

import org.apache.zookeeper.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName DistributeServer
 * @Description: zookeeper客户端程序
 * @Author Arnold
 * @Date 2020/2/28
 * @Version V2.0
 **/
public class DistributeClient {
    //集群环境通信地址
    private String connection ="hadoop1:2181,hadoop2:2181,hadoop3:2181";
    //session超时时间
    private int sessionTimeout = 5000;
    //zookeeper客户端
    private static ZooKeeper zooKeeperClient;

    /**
     * @MethodName: getChildren
     * @Description: 获取子节点
     * @Param: String hostname 主机名
     * @Return: void
     * @Author: Arnold
     * @Date: 2020/2/28
     **/
    public  void getChildren() {
        try {
            //获取子节点
            List<String> nodeList = zooKeeperClient.getChildren("/servers",true);
            //结果集
            List<String> hosts = new ArrayList<String>();
            for (String str :nodeList) {
                //获取子节点所有数据
               byte[] byteData =    zooKeeperClient.getData("/servers/"+str,false,null);
               hosts.add(new String(byteData));
            }
            System.out.println(hosts);
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
    public   void bussiness() {
        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void getConnection(){
        try {
            //初始化一个zookeeper client
            zooKeeperClient = new ZooKeeper(connection, sessionTimeout, new Watcher() {
                public void process(WatchedEvent watchedEvent) {
                    getChildren ();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        DistributeClient distributeClient = new DistributeClient();
        //获取zookeeper链接
        distributeClient.getConnection();
        //注册监听
        distributeClient.getChildren();
        //业务逻辑处理
        distributeClient.bussiness();
    }
}
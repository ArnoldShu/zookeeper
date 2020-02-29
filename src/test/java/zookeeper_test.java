import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * @ClassName zookeeper_test
 * @Description: zookeeper客户端测试类
 * @Author Arnold
 * @Date 2020/2/28
 * @Version V2.0
 **/
public class zookeeper_test {
    //集群环境通信地址
    private String connectint ="hadoop1:2181,hadoop2:2181,hadoop3:2181";
    //session超时时间
    private int sessionTimeout = 5000;
    //zookeeper客户端
    private  ZooKeeper zooKeeperClient;

    // 初始化一个zookeeper client
    @Before
    public void test() throws IOException {
        //初始化一个zookeeper client
        zooKeeperClient = new ZooKeeper(connectint, 5000, new Watcher() {
            public void process(WatchedEvent watchedEvent) {

            }
        });
    }

    @Test
    public void test1() throws Exception {
        //创建一个持久节点
        String path = zooKeeperClient.create("/javaNode","java测试节点".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(path);
    }

    @Test
    public void test2() throws Exception {
        //获取根节点的子节点并监听其节点变化
        List<String> path = zooKeeperClient.getChildren("/",true);
        for (String str :path) {
            System.out.println("节点:"+str);
        }
    }

    @Test
    public void test3() throws Exception {
        //判断节点是否存在
        Stat stat = zooKeeperClient.exists("/dafdafadf",false);
        System.out.println(stat==null? "/dafdafadf节点不存在" : "/dafdafadf已经存在");
    }

    @Test
    public void test4() throws Exception {
        //判断节点是否存在
        Stat stat = zooKeeperClient.exists("/dafdafadf",false);
        System.out.println(stat==null? "/dafdafadf节点不存在" : "/dafdafadf已经存在");
    }
}
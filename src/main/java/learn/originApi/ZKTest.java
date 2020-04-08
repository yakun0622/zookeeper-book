package learn.originApi;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.List;

/**
 * description:
 *
 * @author wangyakun(yakun0622@gmail.com)
 * @version 1.0
 * @date 2019-05-18 00:02
 */
public class ZKTest {
    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        ZooKeeper zooKeeper = new ZooKeeper(ZkConstant.ZK_HOSTS, ZkConstant.ZK_TIMEOUT, null);
        System.out.println("getState=>" + zooKeeper.getState());
        System.out.println("getSessionId=>" + zooKeeper.getSessionId());
        String path = "/yoffey_test" ;
        zooKeeper.create(path, "aa".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        byte[] data = zooKeeper.getData(path, null, null);
        System.out.println("getData=>" + new String(data));
        List<String> children = zooKeeper.getChildren("/", null);
        System.out.println("list all path=>");
        for (String child : children) {
            System.out.println(child);
        }
        zooKeeper.close();
    }
}

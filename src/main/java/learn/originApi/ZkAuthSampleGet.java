package learn.originApi;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

/**
 * description: 使用没有权限信息的会话访问有权限信息的数据节点
 *
 * @author wangyakun(yakun0622@gmail.com)
 * @version 1.0
 * @date 2019-05-27 15:46
 */
public class ZkAuthSampleGet {
    private static String path = "/zk-book-auth_test";
    public static void main(String[] args) throws Exception {
        ZooKeeper zk = new ZooKeeper(ZkConstant.ZK_HOSTS, ZkConstant.ZK_TIMEOUT, null);
        Thread.sleep(1000);
        zk.addAuthInfo("digest", "foo:true".getBytes());
        zk.create(path, "123".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.EPHEMERAL);
        ZooKeeper zk1 = new ZooKeeper(ZkConstant.ZK_HOSTS, ZkConstant.ZK_TIMEOUT, null);
        Thread.sleep(1000);
        zk1.getData(path, false, null);
    }
}

package learn.originApi;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

/**
 * description:
 *
 * @author wangyakun(yakun0622@gmail.com)
 * @version 1.0
 * @date 2019-05-27 15:41
 */
public class ZkAuthSample {
    private static String path = "/zk-book-auth_test";

    public static void main(String[] args) throws Exception {
        ZooKeeper zk = new ZooKeeper(ZkConstant.ZK_HOSTS, ZkConstant.ZK_TIMEOUT, null);
        zk.addAuthInfo("digest", "foo:true".getBytes());
        zk.create(path, "123".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.EPHEMERAL);
        Thread.sleep(Integer.MAX_VALUE);
    }
}

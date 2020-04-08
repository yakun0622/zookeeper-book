package learn.originApi;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

/**
 * description: 使用错误权限信息的会话访问有权限信息的数据节点
 *
 * @author wangyakun(yakun0622@gmail.com)
 * @version 1.0
 * @date 2019-05-27 15:46
 */
public class ZkAuthSampleGet2 {
    private static String path = "/zk-book-auth_test";
    public static void main(String[] args) throws Exception {
        ZooKeeper zk = new ZooKeeper(ZkConstant.ZK_HOSTS, ZkConstant.ZK_TIMEOUT, null);
        Thread.sleep(5000);
        zk.addAuthInfo("digest", "foo:true".getBytes());
        zk.create(path, "123".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.EPHEMERAL);
        ZooKeeper zk1 = new ZooKeeper(ZkConstant.ZK_HOSTS, ZkConstant.ZK_TIMEOUT, null);
        Thread.sleep(3000);
        zk1.addAuthInfo("digest", "foo:true".getBytes());
        System.out.println(new String(zk1.getData(path, false, null)));;

        ZooKeeper zk2 = new ZooKeeper(ZkConstant.ZK_HOSTS, ZkConstant.ZK_TIMEOUT, null);
        Thread.sleep(3000);
        zk2.addAuthInfo("digest", "foo:false".getBytes());
        zk2.getData(path, false, null);
    }
}

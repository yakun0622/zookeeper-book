package learn.originApi;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

/**
 * description: 对数据节点添加权限信息后，可以删除该节点，但是删除子节点需要权限
 *
 * @author wangyakun(yakun0622@gmail.com)
 * @version 1.0
 * @date 2019-05-27 15:46
 */
public class ZkAuthSampleDelete {
    private static String path = "/zk-book-auth_test";
    private static String path2 = "/zk-book-auth_test/c1";
    public static void main(String[] args) throws Exception {
        ZooKeeper zk = new ZooKeeper(ZkConstant.ZK_HOSTS, ZkConstant.ZK_TIMEOUT, null);
        Thread.sleep(5000);
        zk.addAuthInfo("digest", "foo:true".getBytes());
        zk.create(path, "123".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);
        zk.create(path2, "".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.EPHEMERAL);

        try {
            ZooKeeper zk1 = new ZooKeeper(ZkConstant.ZK_HOSTS, ZkConstant.ZK_TIMEOUT, null);
            Thread.sleep(3000);
            zk1.delete(path2, -1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        ZooKeeper zk2 = new ZooKeeper(ZkConstant.ZK_HOSTS, ZkConstant.ZK_TIMEOUT, null);
        Thread.sleep(3000);
        zk2.addAuthInfo("digest", "foo:true".getBytes());
        zk2.delete(path2, -1);
        System.out.println("成功删除节点2");

        ZooKeeper zk3 = new ZooKeeper(ZkConstant.ZK_HOSTS, ZkConstant.ZK_TIMEOUT, null);
        Thread.sleep(3000);
        zk3.delete(path, -1);
        System.out.println("成功删除节点1");
    }
}

package learn.originApi;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * description:
 *
 * @author wangyakun(yakun0622@gmail.com)
 * @version 1.0
 * @date 2019-05-23 15:00
 */
public class ZkCreateNodeSyncDemo implements Watcher {
    private static CountDownLatch latch = new CountDownLatch(1);

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        ZooKeeper zk = new ZooKeeper(ZkConstant.ZK_HOSTS, ZkConstant.ZK_TIMEOUT, new ZkCreateNodeSyncDemo());
        latch.await();
        String node1 = zk.create("/zk-test-ephemeral", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println("success create node=>" + node1);
        String node2 = zk.create("/zk-test-ephemeral-sequ", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println("success create node=>" + node2);
    }

    @Override
    public void process(WatchedEvent event) {
        System.out.println("get event=>" + event);
        if (Event.KeeperState.SyncConnected == event.getState()) {
            latch.countDown();
        }
    }
}

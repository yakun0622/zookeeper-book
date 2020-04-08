package learn.originApi;

import org.apache.zookeeper.AsyncCallback;
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
 * @date 2019-05-24 10:15
 */
public class ZkDeleteNodeDemo implements Watcher {
    private static CountDownLatch latch = new CountDownLatch(1);

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        ZooKeeper zk = new ZooKeeper(ZkConstant.ZK_HOSTS, ZkConstant.ZK_TIMEOUT, new ZkDeleteNodeDemo());
        latch.await();
        String node1 = zk.create("/zk-test-need-delete", new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        String node2 = zk.create("/zk-test-need-delete2", new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(node1 + "==" + node2);
        zk.delete("/zk-test-need-delete", 0);
        zk.delete("/zk-test-need-delete2", 0, new IVoidCallback(), "i am context");
        Thread.sleep(Integer.MAX_VALUE);
    }
    @Override
    public void process(WatchedEvent event) {
        if (Event.KeeperState.SyncConnected == event.getState()) {
            latch.countDown();
        }
    }
}

class IVoidCallback implements AsyncCallback.VoidCallback {
    @Override
    public void processResult(int rc, String path, Object ctx) {
        System.out.println(String.format("rc[%s]==path[%s]==ctx[%s]", rc, path, ctx));
    }
}

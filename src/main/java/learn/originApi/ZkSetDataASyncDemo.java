package learn.originApi;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;

/**
 * description:
 *
 * @author wangyakun(yakun0622@gmail.com)
 * @version 1.0
 * @date 2019-05-27 14:12
 */
public class ZkSetDataASyncDemo implements Watcher {
    private static CountDownLatch latch = new CountDownLatch(1);
    private static ZooKeeper zk = null;

    public static void main(String[] args) throws Exception {
        zk = new ZooKeeper(ZkConstant.ZK_HOSTS, ZkConstant.ZK_TIMEOUT, new ZkSetDataASyncDemo());
        latch.await();
        String path = "/zk-book";
        zk.create(path, "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        zk.setData(path, "456".getBytes(), -1, new IStatCallback(), null);
        Thread.sleep(Integer.MAX_VALUE);
    }
    @Override
    public void process(WatchedEvent event) {
        if (Event.KeeperState.SyncConnected == event.getState()) {
            if (Event.EventType.None == event.getType() && event.getPath() == null) {
                latch.countDown();
            }
        }
    }
}

class IStatCallback implements AsyncCallback.StatCallback {

    @Override
    public void processResult(int rc, String path, Object ctx, Stat stat) {
        System.out.println(String.format("rc[%s], path[%s], ctx[%s]", rc, path, ctx));
        System.out.println(String.format("czxid[%s], mzxid[%s], version[%s]", stat.getCzxid(), stat.getMzxid(), stat.getVersion()));
    }
}
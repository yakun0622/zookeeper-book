package learn.originApi;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
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
 * @date 2019-05-27 13:45
 */
public class ZkSetDataSyncDemo implements Watcher {
    private static CountDownLatch latch = new CountDownLatch(1);
    private static ZooKeeper zk = null;

    public static void main(String[] args) throws Exception {
        zk = new ZooKeeper(ZkConstant.ZK_HOSTS, ZkConstant.ZK_TIMEOUT, new ZkSetDataSyncDemo());
        latch.await();
        String path = "/zk-book";
        zk.create(path, "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        zk.getData(path, true, null);
        Stat stat = zk.setData(path, "456".getBytes(), -1);
        System.out.println(String.format("stat==>czxid[%s],mzxid[%s],version[%s]",
                stat.getCzxid(), stat.getMzxid(), stat.getVersion()));
        Stat stat1 = zk.setData(path, "789".getBytes(), stat.getVersion());
        System.out.println(String.format("stat1==>czxid[%s],mzxid[%s],version[%s]",
                stat1.getCzxid(), stat1.getMzxid(), stat1.getVersion()));
        try {
            zk.setData(path, "999".getBytes(), stat.getVersion());
        } catch (KeeperException e) {
            System.out.println("Error:" + e.code() + ", " + e.getMessage());
        }
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

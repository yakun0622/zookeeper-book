package learn.originApi;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * description:
 *
 * @author wangyakun(yakun0622@gmail.com)
 * @version 1.0
 * @date 2019-05-23 12:50
 */
public class ZkConstructorDemo implements Watcher {
    private static CountDownLatch latch = new CountDownLatch(1);

    public static void main(String[] args) throws IOException {
        ZooKeeper zk = new ZooKeeper(ZkConstant.ZK_HOSTS, ZkConstant.ZK_TIMEOUT, new ZkConstructorDemo());
        System.out.println("state=>" + zk.getState());
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("zk connected success!");
    }

    @Override
    public void process(WatchedEvent event) {
        System.out.println("get event=>" + event);
        if (Event.KeeperState.SyncConnected == event.getState()) {
            latch.countDown();
        }
    }
}

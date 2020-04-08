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
 * @date 2019-05-23 13:56
 */
public class ZkConstructorWithSidPasswdDemo implements Watcher {
    private static CountDownLatch count = new CountDownLatch(1);

    public static void main(String[] args) throws IOException, InterruptedException {
        ZooKeeper zk = new ZooKeeper(ZkConstant.ZK_HOSTS, ZkConstant.ZK_TIMEOUT, new ZkConstructorWithSidPasswdDemo());
        count.await();
        long sessionId = zk.getSessionId();
        byte[] sessionPasswd = zk.getSessionPasswd();
        zk = new ZooKeeper(ZkConstant.ZK_HOSTS, ZkConstant.ZK_TIMEOUT, new ZkConstructorWithSidPasswdDemo()
                , 1L, "test".getBytes());
        zk = new ZooKeeper(ZkConstant.ZK_HOSTS, ZkConstant.ZK_TIMEOUT, new ZkConstructorWithSidPasswdDemo()
                , sessionId, sessionPasswd);
        Thread.sleep(Integer.MAX_VALUE);
    }
    @Override
    public void process(WatchedEvent event) {
        System.out.println("get event=>" + event);
        if (Event.KeeperState.SyncConnected == event.getState()) {
            count.countDown();
        }
    }
}

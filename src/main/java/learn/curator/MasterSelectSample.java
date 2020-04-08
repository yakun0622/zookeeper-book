package learn.curator;

import learn.originApi.ZkConstant;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.CountDownLatch;

/**
 * description:
 *
 * @author wangyakun(yakun0622@gmail.com)
 * @version 1.0
 * @date 2020-04-08 14:27
 */
public class MasterSelectSample {
    static String masterPath = "/zkcurator-master_path";
    static CuratorFramework client =
            CuratorFrameworkFactory.builder()
                    .connectString(ZkConstant.ZK_HOSTS)
                    .sessionTimeoutMs(ZkConstant.ZK_SESSION_TIMEOUT)
                    .connectionTimeoutMs(ZkConstant.ZK_TIMEOUT)
                    .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                    .build();
    static CuratorFramework client2 =
            CuratorFrameworkFactory.builder()
                    .connectString(ZkConstant.ZK_HOSTS)
                    .sessionTimeoutMs(ZkConstant.ZK_SESSION_TIMEOUT)
                    .connectionTimeoutMs(ZkConstant.ZK_TIMEOUT)
                    .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                    .build();
    static CountDownLatch latch = new CountDownLatch(2);

    public static void main(String[] args) throws InterruptedException {
        client.start();
        client2.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                LeaderSelector selector = new LeaderSelector(client, masterPath, new LeaderSelectorListenerAdapter() {
                    @Override
                    public void takeLeadership(CuratorFramework client) throws Exception {
                        System.out.println("client1选举成为master");
                        Thread.sleep(3000);
                        System.out.println("完成操作，释放master权利");
                        latch.countDown();
                    }
                });
                selector.autoRequeue();
                selector.start();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                LeaderSelector selector = new LeaderSelector(client2, masterPath, new LeaderSelectorListenerAdapter() {
                    @Override
                    public void takeLeadership(CuratorFramework client) throws Exception {
                        System.out.println("client2选举成为master");
                        Thread.sleep(3000);
                        System.out.println("完成操作，释放master权利");
                        latch.countDown();
                    }
                });
                selector.autoRequeue();
                selector.start();
            }
        }).start();
        latch.await();
    }
}

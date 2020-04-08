package learn.curator;

import learn.originApi.ZkConstant;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.barriers.DistributedBarrier;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * description:
 *
 * @author wangyakun(yakun0622@gmail.com)
 * @version 1.0
 * @date 2020-04-08 15:36
 */
public class DistributedCyclicBarrierSample {
    static String path = "/zkcurator-cyclic_barrier";
    static DistributedBarrier barrier;

    public static void main(String[] args) throws Exception {
        final int length = 5;
        for (int i = 0; i < length; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    CuratorFramework client =
                            CuratorFrameworkFactory.builder()
                                    .connectString(ZkConstant.ZK_HOSTS)
                                    .sessionTimeoutMs(ZkConstant.ZK_SESSION_TIMEOUT)
                                    .connectionTimeoutMs(ZkConstant.ZK_TIMEOUT)
                                    .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                                    .build();
                    client.start();
                    barrier = new DistributedBarrier(client, path);
                    System.out.println(Thread.currentThread().getName() + "设置barrier");
                    try {
                        barrier.setBarrier();
                        barrier.waitOnBarrier();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "启动");
                }
            }).start();
        }
        Thread.sleep(2000);
        barrier.removeBarrier();
    }
}

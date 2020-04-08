package learn.curator;

import learn.originApi.ZkConstant;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.barriers.DistributedBarrier;
import org.apache.curator.framework.recipes.barriers.DistributedDoubleBarrier;
import org.apache.curator.retry.ExponentialBackoffRetry;

import static learn.curator.DistributedCyclicBarrierSample.barrier;

/**
 * description:
 *
 * @author wangyakun(yakun0622@gmail.com)
 * @version 1.0
 * @date 2020-04-08 15:36
 */
public class DistributedCyclicBarrier2Sample {
    static String path = "/zkcurator-cyclic_barrier2";

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
                    DistributedDoubleBarrier barrier = new DistributedDoubleBarrier(client, path, length);
                    try {
                        Thread.sleep(Math.round(Math.random() * 3000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "进入barrier");
                    try {
                        barrier.enter();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "启动");
                    try {
                        Thread.sleep(Math.round(Math.random() * 3000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        barrier.leave();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "退出");
                }
            }).start();
        }
    }
}

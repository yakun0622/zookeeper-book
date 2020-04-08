package learn.curator;

import learn.originApi.ZkConstant;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * description:
 *
 * @author wangyakun(yakun0622@gmail.com)
 * @version 1.0
 * @date 2020-04-08 11:32
 */
public class CreateNodeBackGroundSample {
    static String path = "/zkcurator";
    static CuratorFramework client =
            CuratorFrameworkFactory.builder()
                    .connectString(ZkConstant.ZK_HOSTS)
                    .sessionTimeoutMs(ZkConstant.ZK_SESSION_TIMEOUT)
                    .connectionTimeoutMs(ZkConstant.ZK_TIMEOUT)
                    .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                    .build();
    static final int THREAD_SIZE = 2;
    static ExecutorService tp = new ThreadPoolExecutor(THREAD_SIZE, THREAD_SIZE,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>());
    static CountDownLatch latch = new CountDownLatch(2);

    public static void main(String[] args) throws Exception {
        client.start();
        System.out.println("Main thread:" + Thread.currentThread().getName());
        // 用自定义的线程池处理异步通知事件
        client.create().withMode(CreateMode.EPHEMERAL).inBackground(new BackgroundCallback() {
            @Override
            public void processResult(CuratorFramework curatorFramework, CuratorEvent curatorEvent) throws Exception {
                System.out.println("code:" + curatorEvent.getResultCode() + ", type:" + curatorEvent.getType());
                System.out.println("Thread of processResult:" + Thread.currentThread().getName());
                latch.countDown();
            }
        }, tp).forPath(path, "init".getBytes());
        // 用默认的EventThread处理异步通知事件
        client.create().withMode(CreateMode.EPHEMERAL).inBackground(new BackgroundCallback() {
            @Override
            public void processResult(CuratorFramework curatorFramework, CuratorEvent curatorEvent) throws Exception {
                System.out.println("code:" + curatorEvent.getResultCode() + ", type:" + curatorEvent.getType());
                System.out.println("Thread of processResult:" + Thread.currentThread().getName());
                latch.countDown();
            }
        }).forPath(path, "init".getBytes());
        latch.await();
        tp.shutdown();
    }
}

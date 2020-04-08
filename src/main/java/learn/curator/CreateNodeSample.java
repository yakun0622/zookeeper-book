package learn.curator;

import learn.originApi.ZkConstant;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

/**
 * description:
 *
 * @author wangyakun(yakun0622@gmail.com)
 * @version 1.0
 * @date 2020-04-08 09:36
 */
public class CreateNodeSample {
    static String path = "/zkcurator";
    static CuratorFramework client =
            CuratorFrameworkFactory.builder()
                    .connectString(ZkConstant.ZK_HOSTS)
                    .sessionTimeoutMs(ZkConstant.ZK_SESSION_TIMEOUT)
                    .connectionTimeoutMs(ZkConstant.ZK_TIMEOUT)
                    .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                    .build();

    public static void main(String[] args) throws Exception {
        client.start();
//        client.create().forPath(path);
//        client.create().forPath(path, "init".getBytes());
//        client.create().withMode(CreateMode.EPHEMERAL).forPath(path);
        client.create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.EPHEMERAL)
                .forPath("/zkcurator/c1");
        Thread.sleep(Integer.MAX_VALUE);
    }
}

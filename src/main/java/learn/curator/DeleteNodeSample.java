package learn.curator;

import learn.originApi.ZkConstant;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

/**
 * description:
 *
 * @author wangyakun(yakun0622@gmail.com)
 * @version 1.0
 * @date 2020-04-08 10:02
 */
public class DeleteNodeSample {
    static String path = "/zkcurator/c1";
    static CuratorFramework client =
            CuratorFrameworkFactory.builder()
                    .connectString(ZkConstant.ZK_HOSTS)
                    .sessionTimeoutMs(ZkConstant.ZK_SESSION_TIMEOUT)
                    .connectionTimeoutMs(ZkConstant.ZK_TIMEOUT)
                    .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                    .build();

    public static void main(String[] args) throws Exception {
        client.start();
//        client.delete().forPath(path);
//        client.delete().deletingChildrenIfNeeded().forPath(path);
//        client.delete().guaranteed().forPath(path);

        client.create().creatingParentsIfNeeded()
                .withMode(CreateMode.EPHEMERAL)
                .forPath(path);
        Stat stat = new Stat();
        client.getData().storingStatIn(stat).forPath(path);
        client.delete().deletingChildrenIfNeeded().withVersion(stat.getVersion()).forPath("/zkcurator");
    }
}

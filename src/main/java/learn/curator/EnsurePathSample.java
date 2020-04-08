package learn.curator;

import learn.originApi.ZkConstant;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.EnsurePath;

/**
 * description: 没有就创建，有不报错
 *
 * @author wangyakun(yakun0622@gmail.com)
 * @version 1.0
 * @date 2020-04-08 16:21
 */
public class EnsurePathSample {
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
        client.usingNamespace("zkcurator");

        EnsurePath ensurePath = new EnsurePath(path);
        ensurePath.ensure(client.getZookeeperClient());
        ensurePath.ensure(client.getZookeeperClient());

        EnsurePath ensurePath1 = client.newNamespaceAwareEnsurePath("/c1");
        ensurePath1.ensure(client.getZookeeperClient());
    }
}

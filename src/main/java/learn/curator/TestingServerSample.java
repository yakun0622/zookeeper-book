package learn.curator;

import learn.originApi.ZkConstant;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.test.TestingServer;
import org.apache.zookeeper.CreateMode;

import java.io.File;

/**
 * description:
 *
 * @author wangyakun(yakun0622@gmail.com)
 * @version 1.0
 * @date 2020-04-08 16:29
 */
public class TestingServerSample {
    static String path = "/zookeeper";

    public static void main(String[] args) throws Exception {
        TestingServer server = new TestingServer(5181, new File("/Users/Yoffey/dev/curator-zk-data"));
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(server.getConnectString())
                .sessionTimeoutMs(ZkConstant.ZK_SESSION_TIMEOUT)
                .connectionTimeoutMs(ZkConstant.ZK_TIMEOUT)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .build();
        client.start();
        System.out.println(client.getChildren().forPath(path));
        client.create().withMode(CreateMode.EPHEMERAL).forPath("/aaa", "123".getBytes());
        System.out.println(new String(client.getData().forPath("/aaa")));
        server.close();
    }
}

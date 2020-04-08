package learn.curator;

import learn.originApi.ZkConstant;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * description: 用fluent风格创建客户端
 *
 * @author wangyakun(yakun0622@gmail.com)
 * @version 1.0
 * @date 2020-04-08 09:20
 */
public class CreateSessionSampleFluent {
    public static void main(String[] args) throws InterruptedException {
        RetryPolicy policy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(ZkConstant.ZK_HOSTS)
                .sessionTimeoutMs(ZkConstant.ZK_SESSION_TIMEOUT)
                .connectionTimeoutMs(ZkConstant.ZK_TIMEOUT)
                .retryPolicy(policy)
                .namespace("base") // 命名空间为/base
                .build();
        client.start();
        Thread.sleep(Integer.MAX_VALUE);
    }
}

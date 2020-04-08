package learn.curator;

import learn.originApi.ZkConstant;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;


/**
 * description:
 *
 * @author wangyakun(yakun0622@gmail.com)
 * @version 1.0
 * @date 2020-04-08 09:06
 */
public class CreateSessionSample {
    public static void main(String[] args) throws InterruptedException {
        RetryPolicy policy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient(ZkConstant.ZK_HOSTS,
                ZkConstant.ZK_SESSION_TIMEOUT,
                ZkConstant.ZK_TIMEOUT,
                policy);
        client.start();
        Thread.sleep(Integer.MAX_VALUE);
    }
}

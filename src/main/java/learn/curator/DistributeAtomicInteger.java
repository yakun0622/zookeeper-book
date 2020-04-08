package learn.curator;

import learn.originApi.ZkConstant;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicInteger;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;

/**
 * description:
 *
 * @author wangyakun(yakun0622@gmail.com)
 * @version 1.0
 * @date 2020-04-08 15:15
 */
public class DistributeAtomicInteger {
    static String path = "/zkcurator-atomic_integer";
    static CuratorFramework client =
            CuratorFrameworkFactory.builder()
                    .connectString(ZkConstant.ZK_HOSTS)
                    .sessionTimeoutMs(ZkConstant.ZK_SESSION_TIMEOUT)
                    .connectionTimeoutMs(ZkConstant.ZK_TIMEOUT)
                    .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                    .build();

    public static void main(String[] args) {
        client.start();
        DistributedAtomicInteger atomicInteger =
                new DistributedAtomicInteger(client, path, new RetryNTimes(3, 1000));
        try {
            AtomicValue<Integer> atomicValue = atomicInteger.add(8);
            System.out.println("result:" + atomicValue.succeeded());
            System.out.println(atomicValue.postValue() + ", " + atomicValue.preValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

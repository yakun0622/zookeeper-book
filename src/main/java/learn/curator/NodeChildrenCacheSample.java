package learn.curator;

import learn.originApi.ZkConstant;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

/**
 * description:
 *
 * @author wangyakun(yakun0622@gmail.com)
 * @version 1.0
 * @date 2020-04-08 13:50
 */
public class NodeChildrenCacheSample {
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
        PathChildrenCache cache = new PathChildrenCache(client, path, true);
        cache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
        cache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                switch (event.getType()) {
                    case CHILD_ADDED:
                        System.out.println(String.format("child add, path:%s", event.getData().getPath()));
                        break;
                    case CHILD_UPDATED:
                        System.out.println(String.format("child update, path:%s, data:%s",
                                event.getData().getPath(), new String(event.getData().getData())));
                        break;
                    case CHILD_REMOVED:
                        System.out.println(String.format("child remove, path:%s", event.getData().getPath()));
                        break;
                    default:
                        System.out.println(String.format("other change, path:%s", event.getData().getPath()));
                        break;
                }
            }
        });
        client.create().withMode(CreateMode.PERSISTENT).forPath(path);
        waitTime(1000);
        client.create().withMode(CreateMode.EPHEMERAL).forPath(path + "/c1");
        waitTime(1000);
        client.setData().forPath(path + "/c1", "aa".getBytes());
        waitTime(1000);
        client.delete().forPath(path + "/c1");
        waitTime(1000);
        client.delete().forPath(path);
    }

    private static void waitTime(int mills) throws InterruptedException {
        Thread.sleep(mills);
    }
}

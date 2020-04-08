package learn.zkclient;

import learn.originApi.ZkConstant;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

/**
 * description:
 *
 * @author wangyakun(yakun0622@gmail.com)
 * @version 1.0
 * @date 2019-05-27 17:55
 */
public class GetDataSample {
    public static void main(String[] args) throws InterruptedException {
        ZkClient zkClient = new ZkClient(ZkConstant.ZK_HOSTS, ZkConstant.ZK_TIMEOUT);
        String path = "/zkclient";
        zkClient.createEphemeral(path, "123");
        zkClient.subscribeDataChanges(path, new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {
                System.out.println(String.format("path[%s]'s data changes, new data is %s", s, o));
            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                System.out.println(String.format("path[%s]'s data deleted", s));
            }
        });
        System.out.println(zkClient.readData(path));
        zkClient.writeData(path, "456");
        Thread.sleep(1000);
        zkClient.delete(path);
        Thread.sleep(Integer.MAX_VALUE);
    }
}

package learn.zkclient;

import learn.originApi.ZkConstant;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.List;

/**
 * description:
 *
 * @author wangyakun(yakun0622@gmail.com)
 * @version 1.0
 * @date 2019-05-27 17:43
 */
public class GetChildrenSample {
    public static void main(String[] args) throws Exception {
        ZkClient zkClient = new ZkClient(ZkConstant.ZK_HOSTS, ZkConstant.ZK_TIMEOUT);
        String path = "/zkclient";
        zkClient.subscribeChildChanges(path, new IZkChildListener() {
            @Override
            public void handleChildChange(String s, List<String> list) throws Exception {
                System.out.println(String.format("path[%s]'s children changes, current chilren:%s", s, list));
            }
        });
        zkClient.createPersistent(path);
        Thread.sleep(1000);
        zkClient.createEphemeral(path + "/c1");
        Thread.sleep(1000);
        zkClient.delete(path + "/c1");
        Thread.sleep(1000);
        zkClient.delete(path);
        Thread.sleep(Integer.MAX_VALUE);
    }
}

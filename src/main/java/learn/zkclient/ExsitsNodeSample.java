package learn.zkclient;

import learn.originApi.ZkConstant;
import org.I0Itec.zkclient.ZkClient;

/**
 * description:
 *
 * @author wangyakun(yakun0622@gmail.com)
 * @version 1.0
 * @date 2019-05-27 18:05
 */
public class ExsitsNodeSample {
    public static void main(String[] args) {
        ZkClient zkClient = new ZkClient(ZkConstant.ZK_HOSTS, ZkConstant.ZK_TIMEOUT);
        String path = "/zkclient";
        System.out.println(zkClient.exists(path));
        zkClient.createEphemeral(path);
        System.out.println(zkClient.exists(path));
    }
}

package learn.zkclient;

import learn.originApi.ZkConstant;
import org.I0Itec.zkclient.ZkClient;

/**
 * description:
 *
 * @author wangyakun(yakun0622@gmail.com)
 * @version 1.0
 * @date 2019-05-27 18:02
 */
public class SetDataSample {
    public static void main(String[] args) {
        ZkClient zkClient = new ZkClient(ZkConstant.ZK_HOSTS, ZkConstant.ZK_TIMEOUT);
        String path = "/zkclient";
        zkClient.createEphemeral(path);
        zkClient.writeData(path, "123");
        System.out.println(zkClient.readData(path));
    }
}

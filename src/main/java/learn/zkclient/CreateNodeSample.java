package learn.zkclient;

import learn.originApi.ZkConstant;
import org.I0Itec.zkclient.ZkClient;

/**
 * description:
 *
 * @author wangyakun(yakun0622@gmail.com)
 * @version 1.0
 * @date 2019-05-27 17:22
 */
public class CreateNodeSample {
    public static void main(String[] args) {
        ZkClient zkClient = new ZkClient(ZkConstant.ZK_HOSTS, ZkConstant.ZK_TIMEOUT);
        String path = "/zkclient/c1";
        zkClient.createPersistent(path, true);
        System.out.println("创建节点成功");
    }
}

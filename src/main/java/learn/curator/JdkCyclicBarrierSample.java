package learn.curator;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * description:
 *
 * @author wangyakun(yakun0622@gmail.com)
 * @version 1.0
 * @date 2020-04-08 15:30
 */
public class JdkCyclicBarrierSample {
    public static CyclicBarrier barrier = new CyclicBarrier(3);

    public static void main(String[] args) {
        int threadPoolSize = 3;
        ExecutorService tp = new ThreadPoolExecutor(threadPoolSize, threadPoolSize,
                0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        tp.submit(new Thread(new Runner("1号选手")));
        tp.submit(new Thread(new Runner("2号选手")));
        tp.submit(new Thread(new Runner("3号选手")));
        tp.shutdown();
    }
}

class Runner implements Runnable {
    private String name;

    public Runner(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println(this.name + "准备好了！");
        try {
            JdkCyclicBarrierSample.barrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println(this.name + "起跑！");
    }
}

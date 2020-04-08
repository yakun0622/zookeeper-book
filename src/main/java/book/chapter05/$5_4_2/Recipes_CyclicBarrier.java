package book.chapter05.$5_4_2;
import java.io.IOException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Recipes_CyclicBarrier {

	public static CyclicBarrier barrier = new CyclicBarrier( 3 );
	public static void main( String[] args ) throws IOException, InterruptedException {
		ExecutorService executor = Executors.newFixedThreadPool( 3 );
		executor.submit( new Thread( new Runner( "1号选手" ) ) );
		executor.submit( new Thread( new Runner( "2号选手" ) ) );
		executor.submit( new Thread( new Runner( "3号选手" ) ) );
		executor.shutdown();
	}
}
class Runner implements Runnable {
	private String name;
	public Runner( String name ) {
		this.name = name;
	}
	public void run() {
		System.out.println( name + " 准备好了." );
		try {
			Recipes_CyclicBarrier.barrier.await();
		} catch ( Exception e ) {}
		System.out.println( name + " 起跑!" );
	}
}
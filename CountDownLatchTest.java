package concurrency;

import java.util.concurrent.CountDownLatch;


/**
 * 对于countdownlatch的测试是闭锁的一种
 * @author hou
 *
 */
public class CountDownLatchTest {
public static  long timetask(int num, final Runnable task) throws InterruptedException{
	final CountDownLatch start=new CountDownLatch(1);
	final CountDownLatch end=new CountDownLatch(num);
	for(int i=0;i<num;i++){
		Thread t=new Thread(){
			public void run(){
				try {
					start.await();
					task.run();
					end.countDown();
				} catch (InterruptedException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
		};
		t.start();
	}
	Long Time=System.nanoTime();
	start.countDown();
	end.await();
	Time=System.nanoTime()-Time;
	return Time;
}
public static void main(String[] args) throws InterruptedException {
	
	System.out.println(CountDownLatchTest.timetask(5,new  Runnable() {
		public void run() {
			System.out.println("test");
		}
	}));
}
}

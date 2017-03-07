package com.hbyd.parks.attendancesys.future;

import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * 测试 JDK 1.5 的新功能：Callable
 *
 * @see <a href="http://www.javacodegeeks.com/2014/10/how-to-use-callable-and-futuretask.html">JavaCodeGeeks</a>
 */
public class CallableTest {

/*
    Differences Between Callable and Runnable:

    Callable is what Runnable hoped to become. Callable’s only method is "T call()"
    What makes it so neat is that it returns something. This is a step above having
    to create a getter for the answer to a task.  While this is cool, there needs to
    be a way to get at the returned value.

    The Future is here:
    Future has a way to get the value out when the Callable is done.
    The function is get() or get(long timeout, TimeUnit unit).
    This is the equivalent of calling
        Thread thead = new Thread(runnable);
        thread.join();//Waits for this thread to die.
        runnable.getXXX();//get the result of runnable
    at the same time.
*/

    public static final long BEGIN = 0;

    public static final long END = 100000;

    @Test
    public void test(){
        //A FutureTask can be used to wrap a Callable or Runnable object.
        //Because FutureTask implements Runnable, a FutureTask can be
        //submitted to an Executor for execution.

//      1. 创建任务：使用 FutureTask 包裹 Callable 和 Runnable 的子类
        FutureTask<SumTimeAnswer> task = new FutureTask<>(new CounterCallable(BEGIN, END));
        FutureTask<SumTimeAnswer> firstHalf = new FutureTask<>(new CounterCallable(0, END / 2));
        FutureTask<SumTimeAnswer> secondHalf = new FutureTask<>(new CounterCallable(END / 2 + 1, END));

//      2. 创建线程池，用于执行任务
//        ExecutorService pool = Executors.newSingleThreadExecutor();//单线程
        ExecutorService pool = Executors.newFixedThreadPool(10);//创建容量为 10 的线程池

//      3. 向线程池提交任务，可以提交 Runnable 和 Callable 的子类
        pool.submit(task);
        pool.submit(firstHalf);
        pool.submit(secondHalf);

        try {
//          4. 获取任务的执行结果
            SumTimeAnswer answer = task.get();// get(): a blocking call on the response to be available.
            SumTimeAnswer firstAnswer = firstHalf.get();
            SumTimeAnswer secondAnswer = secondHalf.get();

            System.out.println("time to finish: " + answer.getTimeToFinish() + ", sum: " + answer.getSum());
            System.out.println(
                    "time to finish: " + (firstAnswer.getTimeToFinish() + secondAnswer.getTimeToFinish()) +
                    ", sum: " + (firstAnswer.getSum() + secondAnswer.getSum())
            );
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        pool.shutdown();
    }
}

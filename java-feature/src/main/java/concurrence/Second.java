package concurrence;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by Moriatry on 15/10/18.
 */
public class Second {
    public static void main_1(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<Future<String>> resultList = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            Future<String> future = executorService.submit(new TaskWithResult(i));
            resultList.add(future);
        }

        for (Future<String> fs : resultList) {
            try {
                while(!fs.isDone());
                    System.out.println(fs.get());
            } catch(InterruptedException e) {
                e.printStackTrace();
            } catch(ExecutionException e) {
                e.printStackTrace();
            } finally {
                executorService.shutdown();
            }
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<Future<String>> resultList = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
//            Future<String> future = executorService.submit(new Callable<String>() {
//                @Override
//                public String call() throws Exception {
//                    System.out.println("call() method is called, " + Thread.currentThread().getName());
//                    return "The result is " + Thread.currentThread().getName();
//                }
//            });
//            resultList.add(future);

            Future<String> future = executorService.submit(()->{
                System.out.println("call() method is called, " + Thread.currentThread().getName());
                return "The result is " + Thread.currentThread().getName();
            });
            resultList.add(future);
        }

        for(Future<String> fs: resultList) {
            try {
                System.out.println(fs.get());
            } catch(InterruptedException e) {
                    e.printStackTrace();
            } catch(ExecutionException e) {
                    e.printStackTrace();
            } finally {
                executorService.shutdown();
            }
        }

        func();
    }

    public static void func() {
        ExecutorService executorService = new ThreadPoolExecutor(3, 6, 50, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10));
        List<Future<String>> futureList = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            Future<String> future = executorService.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    System.out.println("###Thread's name is " + Thread.currentThread().getName());
                    Thread.sleep(10);
                    return "###The result is " + Thread.currentThread().getName();
                }
            });
            futureList.add(future);
        }

        for(Future<String> item: futureList) {
            try {
                System.out.println(item.get());
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                executorService.shutdown();
            }
        }

    }
}

class TaskWithResult implements Callable<String>{
    private int id;

    public TaskWithResult(int id){
        this.id = id;
    }

    public String call() throws Exception {
        System.out.println("call() method is called, " + Thread.currentThread().getName());
        return "The result is " + id + " " + Thread.currentThread().getName();
    }
}

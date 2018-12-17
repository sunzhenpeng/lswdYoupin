package com.lswd.youpin.common.util;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class JavaTest {

    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName());
        Callable<Integer> callable = new Callable<Integer>() {
            public Integer call() throws Exception {
                System.out.println(Thread.currentThread().getName());
                return new Random().nextInt(100);
            }
        };
        FutureTask<Integer> future = new FutureTask<Integer>(callable);
        new Thread(future).start();
        try {
            for (int  i = 0;i<10;i++){
                System.out.println(Thread.currentThread().getName() + "我正在执行for循环" + i);
            }
            System.out.println(Thread.currentThread().getName() + "我是callable的返回值"+future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}

package com.kangjun.threadpool.impl;

import com.kangjun.threadpool.ThreadPool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 线程池实现类
 * @param <Job>
 */
public class DefaultThreadPool<Job extends Runnable> implements ThreadPool<Job> {

    // 线程池最大限制数    maximumPoolSize(最大线程数)
    private static final int MAX_WORKER_NUMBERS = 10;
    // 线程池默认的数量   corePoolSize(核心线程数)
    private static final int DEFAULT_WORKER_NUMBERS = 5;
    
    // 维护一个工作列表,将会向里边插入工作(阻塞队列 - 有界无界)
    private final LinkedList<Job> jobs = new LinkedList<Job>();
    // 工作者列表   
    private final List<Worker> workers = Collections.synchronizedList(new ArrayList<Worker>());
    // 工作者线程的数量
    private int workerNum = DEFAULT_WORKER_NUMBERS;

    //生成线程池
    public DefaultThreadPool() {
        this.workerNum = DEFAULT_WORKER_NUMBERS;
        initializeWorkers(this.workerNum);
    }

    public DefaultThreadPool(int num) {
        if (num > MAX_WORKER_NUMBERS) {
            this.workerNum =DEFAULT_WORKER_NUMBERS;
        } else {
            this.workerNum = num;
        }
        initializeWorkers(this.workerNum);
    }
    //初始化线程工作者
    private void initializeWorkers(int num) {
        for (int i = 0; i < num; i++) {
            Worker worker = new Worker();
            //添加到工作者线程的列表    
            //prestartAllCoreThreads 预创建所有线程
            workers.add(worker);
            //启动工作者线程
            Thread thread = new Thread(worker);
            thread.start();
        }
    }

    //定义工作者线程，负责消费任务
  	class Worker implements Runnable {
          // 表示是否运行该worker
          private volatile boolean running = true;

          public void run() {
              while (running) {
                  Job job = null;
                  //线程的等待/通知机制
                  synchronized (jobs) {
                      if (jobs.isEmpty()) {
                          try {
                              jobs.wait();//线程等待唤醒   所有的任务都预先放在任务队列里(简化版)
                          } catch (InterruptedException e) {
                              //感知到外部对该线程的中断操作，返回
                              Thread.currentThread().interrupt();
                              return;
                          }
                      }
                      // 取出一个job  poll take
                      job = jobs.removeFirst();
                  }
                  //执行job
                  if (job != null) {
                      job.run();
                  }
              }
          }

          // 终止该线程
          public void shutdown() {
              running = false;
          }
      }
    
    public void execute(Job job) {
        if (job != null) {
        	//根据线程的"等待/通知机制"这里必须对jobs加锁
	        synchronized (jobs) {
	            jobs.addLast(job);
	            jobs.notify();
	        }
        }
    }
    //关闭线程池即关闭每个工作者线程
     public void shutdown() {
        for (Worker w : workers) {
            w.shutdown();
            System.out.println("工作线程" + w + "被关闭啦！！！");
        }
    }
    //增加工作者线程
    public void addWorkers(int num) {
        //加锁，防止该线程还没增加完成而下个线程继续增加导致工作者线程超过最大值
    	synchronized (jobs) {
            if (num + this.workerNum > MAX_WORKER_NUMBERS) {
                num = MAX_WORKER_NUMBERS - this.workerNum;
            }
            initializeWorkers(num);
            this.workerNum += num; //MAX
        }
    }
    //减少工作者线程
    public void removeWorker(int num) {
        synchronized (jobs) {
        if(num >= this.workerNum){
        	throw new IllegalArgumentException("超过了已有的线程数量");  //默认饱和策略
        }
        for (int i = 0; i < num; i++) {
            Worker worker = workers.get(i);
            if (worker != null) {
                //关闭该线程并从列表中移除
                worker.shutdown();
                workers.remove(i);
            }
        }
        this.workerNum -= num;
        }
    }

	public int getJobSize() {
        return jobs.size();
    }
	
}

package com.kangjun.threadpool;

	/**
	 * 
	 * Function:简单的线程池接口定义
	 * 
	 * 定义一些基本方法：
	 * 1、执行任务  submit execute
	 * 2、关闭线程池 shutdown(SHUTDOWN状态) shutdownNow(STOP状态)
	 * 3、添加工作者线程  一个任务对应一个线程
	 * 4、减少工作者线程  任务执行完毕 超过一定时间线程会被销毁(超过core线程数的线程)
	 * 5、得到正在等待执行的任务数量 阻塞队列的个数
	 * 
	 */
	public interface ThreadPool<Job extends Runnable>{
	  //执行一个任务(Job),这个Job必须实现Runnable
	  void execute(Job job);
	  //关闭线程池
	  void shutdown();
	  //增加工作者线程
	  void addWorkers(int num);
	  //减少工作者线程
	  void removeWorker(int num);
	  //得到正在等待执行的任务数量
	  int getJobSize();
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
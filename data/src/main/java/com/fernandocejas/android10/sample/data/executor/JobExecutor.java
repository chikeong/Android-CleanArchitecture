/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.data.executor;

import com.fernandocejas.android10.sample.domain.executor.ThreadExecutor;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Decorated {@link java.util.concurrent.ThreadPoolExecutor} Singleton class based on
 * 'Initialization on Demand Holder' pattern.
 */
public class JobExecutor implements ThreadExecutor {

  private static class LazyHolder {
    private static final JobExecutor INSTANCE = new JobExecutor();
  }

  public static JobExecutor getInstance() {
    return LazyHolder.INSTANCE;
  }

  private static final int INITIAL_POOL_SIZE = 3;
  private static final int MAX_POOL_SIZE = 5;

  // Sets the amount of time an idle thread waits before terminating
  private static final int KEEP_ALIVE_TIME = 10;

  // Sets the Time Unit to seconds
  private static final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;

  private final BlockingQueue<Runnable> workQueue;

  private final ThreadPoolExecutor threadPoolExecutor;

  private JobExecutor() {
    this.workQueue = new LinkedBlockingQueue<Runnable>();
    this.threadPoolExecutor = new ThreadPoolExecutor(INITIAL_POOL_SIZE, MAX_POOL_SIZE,
        KEEP_ALIVE_TIME, KEEP_ALIVE_TIME_UNIT, this.workQueue);
  }

  /**
   * {@inheritDoc}
   *
   * @param runnable The class that implements {@link Runnable} interface.
   */
  @Override public void execute(Runnable runnable) {
    if (runnable == null) {
      throw new IllegalArgumentException("Runnable to execute cannot be null");
    }
    this.threadPoolExecutor.execute(runnable);
  }
}
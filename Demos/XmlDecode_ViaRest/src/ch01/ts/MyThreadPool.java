package ch01.ts;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class MyThreadPool extends ThreadPoolExecutor {
    private static final int pool_size = 10;
    private boolean is_paused;
    private ReentrantLock pause_lock = new ReentrantLock();
    private Condition unpaused = pause_lock.newCondition();
    
    public MyThreadPool(){
       super(pool_size,        // core pool size
             pool_size,        // maximum pool size
             0L,               // keep-alive time for idle thread
             TimeUnit.SECONDS, // time unit for keep-alive setting
             new LinkedBlockingQueue<Runnable>(pool_size)); // work queue 
    }

    // some overrides
    protected void beforeExecute(Thread t, Runnable r) {
       super.beforeExecute(t, r);
       pause_lock.lock();
       try {
          while (is_paused) unpaused.await();
       } 
       catch (InterruptedException e) { t.interrupt(); } 
       finally { pause_lock.unlock(); }
    }
    
    public void pause() {
       pause_lock.lock();
       try {
          is_paused = true;
       } 
       finally { pause_lock.unlock(); }
    }
    
    public void resume() {
       pause_lock.lock();
       try {
          is_paused = false;
          unpaused.signalAll();
       } 
       finally { pause_lock.unlock(); }
    }
}

package len.com.k3query.utils;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
public class ThreadManager {

    private ThreadPoolProxy threadPoolProxy;

    private ThreadManager() {
    }

    private static ThreadManager th = new ThreadManager();

    public static ThreadManager getInstance() {
        return th;
    }

public synchronized ThreadPoolProxy create(){
    if (threadPoolProxy==null){
        threadPoolProxy = new ThreadPoolProxy(3, 3);
    }
    return threadPoolProxy;
}

   public class ThreadPoolProxy {
        public  ThreadPoolExecutor poolExecutor;

       private int poolsize;
       private int  maxpoolsize;

        public ThreadPoolProxy(int poolsize ,int maxpoolsize) {
            this.poolsize=poolsize;
            this.maxpoolsize=maxpoolsize;
        }


        public void excute(Runnable runnable) {

            if (poolExecutor==null){

                poolExecutor=new ThreadPoolExecutor(poolsize,maxpoolsize,5000, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>(10));
            }
            poolExecutor.execute(runnable);

        }

        public  void  cancel(Runnable runnable){
            if (poolExecutor!=null&&!poolExecutor.isShutdown()&&!poolExecutor.isTerminating()){
                poolExecutor.remove(runnable);
            }
        }


    }


}

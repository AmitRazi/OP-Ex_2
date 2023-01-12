package Part_2;

import java.util.concurrent.*;
import static java.lang.Thread.sleep;

public class CustomExecutor {
    private final ThreadPoolExecutor executor;
    private final int priorityArray[] = new int[11];
    private boolean stopped = false;

    public CustomExecutor() {
        PriorityBlockingQueue<Runnable> queue = new PriorityBlockingQueue<>();
        executor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors() / 2, Runtime.getRuntime().availableProcessors() - 1,
                300L, TimeUnit.MILLISECONDS, queue) {
            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                Task task = (Task) r;
                decerementPriority(task.getPriority());
            }
        };
        //executor.allowsCoreThreadTimeOut();
    }


    public <T> CustomFuture<T> submit(Task task) {
        // if(executor.getQueue().isEmpty() == false && executor.getPoolSize() < (Runtime.getRuntime().availableProcessors() -1)) executor.setCorePoolSize(executor.getPoolSize()+1);
        // if(executor.getQueue().isEmpty() && executor.getPoolSize() > Runtime.getRuntime().availableProcessors() / 2) executor.setCorePoolSize(executor.getPoolSize()-1);
        if (stopped == false) {
            incrementPriority(task.getPriority());
            executor.execute(task);
            return new CustomFuture<>(task);
        }
        return null;
    }

    public <T> CustomFuture<T> submit(Callable<T> op) {
        return submit(Task.createTask(op));
    }

    public <T> CustomFuture<T> submit(Callable<T> op, TaskType type) {
        return submit(Task.createTask(op, type));
    }

    public <T> CustomFuture<T> submit(Runnable op) {
        return submit(Task.createTask(Executors.callable(op)));
    }

    public <T> CustomFuture<T> submit(Runnable op, TaskType type) {
        return submit(Task.createTask(Executors.callable(op), type));
    }

    private void incrementPriority(int priority){
        synchronized (priorityArray){
            priorityArray[priority]++;
        }
    }

    private void decerementPriority(int priority){
        synchronized (priorityArray){
            priorityArray[priority]--;
        }
    }

    public int getCurrentMaxPriority() {
        for(int i = 0 ; i < priorityArray.length ; i++){
            if(priorityArray[i] > 0) return i;
        }
        return 0;
    }

    public void gracefullyTerminate() {
        stopped = true;
        while (executor.getActiveCount() > 0) {
            try {
                sleep(20);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        executor.shutdown();
    }


    @Override
    public String toString() {
        return "CustomExecutor{" +
                "executor=" + "[" + (executor.isTerminated() ? "TERMINATED" : "ACTIVE") +
                ", pool size = " + executor.getPoolSize() +
                ", active threads = " + executor.getActiveCount() +
                ", queued tasks = " + executor.getQueue().size() +
                ", completed tasks = " + executor.getCompletedTaskCount() +
                "]" +
                ", currentMaxPriority=" + getCurrentMaxPriority()+
                ", stopped=" + stopped +
                '}';
    }
}
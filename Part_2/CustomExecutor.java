package Part_2;

import java.util.concurrent.*;
import static java.lang.Thread.sleep;

/**
 * A custom ThreadPoolExecutor that schedules tasks according to their priority.
 */
public class CustomExecutor {
    private final ThreadPoolExecutor executor;
    private final int priorityArray[] = new int[11];
    private boolean stopped = false;

    /**
     * Parametrized Constructor
     * Initilized the ThreadPoolExecutor with following paramaters:
     * CorePoolSize - half of the available processors
     * maximumPoolSize - The available processors minus 1
     * keepAliveTime - 300 miliseconds
     * queue - A priority blocking queue.
     */
    public CustomExecutor() {
        PriorityBlockingQueue<Runnable> queue = new PriorityBlockingQueue<>();
        executor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors() / 2, Runtime.getRuntime().availableProcessors() - 1,
                300L, TimeUnit.MILLISECONDS, queue) {
            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                Task task = (Task) r;
                   decrementPriority(task.getPriority());
            }
        };
        //executor.allowsCoreThreadTimeOut();
    }

    /**
     * Submits a task to the executor
     * @param task - a new task to be executed
     * @return a CustomFuture that will return the result of the task.
     * @param <T> - the results type
     */
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

    /**
     * Creates a task from a Callable and sumbits it.
     * @param op - A callable operation.
     * @return a CustomFuture that will return the result of the task.
     * @param <T> - the results type
     */
    public <T> CustomFuture<T> submit(Callable<T> op) {
        return submit(Task.createTask(op));
    }

    /**
     * Creates a task from a Callable and sumbits it.
     * @param op - A callable operation.
     * @param type - A TaskType, holds the type of the task and its priority.
     * @return a CustomFuture that will return the result of the task.
     * @param <T> - the results type
     */
    public <T> CustomFuture<T> submit(Callable<T> op, TaskType type) {
        return submit(Task.createTask(op, type));
    }

    /**
     * Create a task from a Runnable operation that does not return a value.
     * @param op - The Runnable operation
     */
    public void  submit(Runnable op) {
        submit(Task.createTask(Executors.callable(op)));
    }

    /**
     * Create a task from a Runnable operation and a TaskType
     * @param op - The Runnable operation
     * @param type - The TaskType, holds the tasks type and priority.
     */
    public void submit(Runnable op, TaskType type) {
        submit(Task.createTask(Executors.callable(op), type));
    }

    /**
     * @param priority - The priority to be incremented, the arrays index.
     */
    private void incrementPriority(int priority){
        synchronized (priorityArray){
            priorityArray[priority]++;
        }
    }

    /**
     * @param priority - the priority to be decremented, the arrays index.
     */
    private void decrementPriority(int priority){
        synchronized (priorityArray){
            priorityArray[priority]--;
        }
    }

    /**
     * @return The maximum priority in the queue. If the queue is empty, return 0.
     */
    public int getCurrentMaxPriority() {
       for(int i = 0 ; i < priorityArray.length ; i++){
           if(priorityArray[i] > 0) return i;
       }
       return 0;
    }

    /**
     * Waits for all the tasks to finished, then and only then, shuts down the executor.
     */
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

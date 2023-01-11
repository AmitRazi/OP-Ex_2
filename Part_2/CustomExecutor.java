package Part_2;

import java.util.concurrent.*;

public class CustomExecutor {
    private  final ThreadPoolExecutor executor;
    private int currentMaxPriority;
    private boolean stopped = false;
    private final  PriorityBlockingQueue<Runnable> queue;

    public CustomExecutor() {
        queue = new PriorityBlockingQueue<>();
        executor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors() / 2, Runtime.getRuntime().availableProcessors() - 1,
                300L, TimeUnit.MILLISECONDS, queue) {
            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                Task task = (Task) queue.peek();
                if (task != null) {
                    currentMaxPriority = task.getPriority();
                } else {
                    currentMaxPriority = 0;
                }
            }
        };
    }


    public <T> CustomFuture<T> submit(Task task) {
        if (stopped == false) {
            executor.execute(task);
            return new CustomFuture<>(task, task.getLatch());
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

    public int getCurrentMaxPriority() {
        return currentMaxPriority;
    }

    public void gracefullyTerminate() {
        stopped = true;
        executor.shutdown();
    }


}

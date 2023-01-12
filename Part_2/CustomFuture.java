package Part_2;

import java.util.concurrent.*;

/**
 * a custom Future interface implementation specifically designed for the Task class.
 * @param <T> The value to be returned.
 */

public class CustomFuture<T> implements Future<T> {
    private final Task task;
    private final CountDownLatch latch;
    private boolean cancel = false;

    /**
     * Parametrized Constructor
     * @param task - The associated task the instance. The CustomFuture instance will retrieve the result of this task.
     */
    public CustomFuture(Task task) {
        this.latch = task.getLatch();
        this.task = task;
    }


    /**
     * Cancel the instance. Takes a boolean argument that indicates whether to interrupt the thread running the task.
     * @param mayInterruptIfRunning {@code true} if the thread
     * executing this task should be interrupted (if the thread is
     * known to the implementation); otherwise, in-progress tasks are
     * allowed to complete
     * @return true if the task hasn't been performed yet, false otherwise.
     */
    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        cancel = true;
        if (isDone() == false) {
            task.cancel();
            return true;
        }
        return false;
    }

    /**
     * @return true if the task and future had been cancelled, false otherwise.
     */
    @Override
    public boolean isCancelled() {
        return cancel;
    }

    /**
     * @return true if the task has finished, false otherwise.
     */
    public boolean isDone() {
        return task.isFinished();
    }

    /**
     * Tries to retireve the result of the associated task. If the task has been cancelled, the method will return null.
     * Uses the CountDownLatch to wait for the task to be finished.
     * @return The result of the task if the task hasn't been cancelled, otherwise, null.
     * @throws InterruptedException if the current thread was interrupted while waiting
     * @throws ExecutionException if the computation threw an exception
     */
    @Override
    public T get() throws InterruptedException, ExecutionException {
        if (isCancelled() == true) return null;
        latch.await();
        return (T) task.getRes();
    }

    /**
     * Tries to retireve the result of the associated task. If the task has been cancelled, the method will return null.
     * Uses the CountDownLatch to wait for a specified maximum amount of time for the task to finish.
     * @param timeout the maximum time to wait
     * @param unit the time unit of the timeout argument
     * @return The result of the task if the task hasn't been cancelled, otherwise, null.
     * @throws InterruptedException if the current thread was interrupted while waiting
     * @throws ExecutionException if the computation threw an exception
     */
    @Override
    public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException{
        if (isCancelled() == true) return null;
        if (latch.await(timeout, unit)) {
            return (T) task.getRes();
        } else {
            System.err.println("The task has timed out after more then " + timeout + " " + unit);
            return null;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomFuture<?> that)) return false;

        if (cancel != that.cancel) return false;
        if (!task.equals(((CustomFuture<?>) o).task)) return false;
        return task.getRes().equals((((CustomFuture<?>) o).task).getRes());
    }

    @Override
    public int hashCode() {
        int result = task != null ? task.hashCode() : 0;
        result = 31 * result + (latch != null ? latch.hashCode() : 0);
        result = 31 * result + (cancel ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CustomFuture{" +
                "task=" + task +
                ", cancel=" + cancel +
                '}';
    }
}
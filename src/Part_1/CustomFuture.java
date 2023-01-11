package Part_1;

import java.util.concurrent.*;

public class CustomFuture<T> implements Future<T> {
    private final Task task;
    private final CountDownLatch latch;
    private boolean cancel = false;

    public CustomFuture(Task task, CountDownLatch latch) {
        this.latch = latch;
        this.task = task;
    }


    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        cancel = true;
        if(isDone() == false){
            task.cancel();
            return true;
        }
        return false;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    public boolean isDone() {
        return task.isFinished();
    }

    @Override
    public T get() throws InterruptedException, ExecutionException {
        if(isCancelled() == true) return null;
        latch.await();
        return (T) task.getRes();
    }

    @Override
    public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        if(isCancelled() == true) return null;
        if (latch.await(timeout, unit)) {
            return (T) task.getRes();
        } else {
            throw new TimeoutException("The task has timed out after more then " + timeout + " " + unit.toString());
        }
    }

    @Override
    public String toString() {
        return task.toString();
    }
}

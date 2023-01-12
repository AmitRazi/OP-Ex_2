package Part_2;

import java.util.concurrent.*;

/**
 * This class Represents a task with a TaskType that may return a value of some type.
 * @param <T> - The type of result to be returned
 */
public class Task<T> implements Runnable, Comparable<Task> {
    private final Callable<T> op;
    private final TaskType type;
    private final CountDownLatch latch = new CountDownLatch(1);
    private boolean cancelled = false;
    private boolean finished = false;
    private T res;


    /**
     * Parametrized Constructor
     *
     * @param op   - A Callable, holds the operation to be performed and the type to be returned.
     * @param type - The task type and the priority of the task to be performed.
     */
    private Task(Callable<T> op, TaskType type) {
        this.op = op;
        this.type = type;
    }

    /**
     * Factory method for safe creation of a task. Receives both a Callable and a TaskType.
     *
     * @param op   - A Callable, holds the operation to be performed and the type to be returned.
     * @param type - The task type and the priority of the task to be performed.
     * @param <T>  - The type of result to be returned.
     * @return A Task instance.
     */
    public static <T> Task createTask(Callable<T> op, TaskType type) {
        if (op != null)
            return new Task(op, type);
        return null;
    }

    /**
     * Factory method for safe creation of a task. Receives a Callable, add a default TaskType.
     *
     * @param op  - A Callable, holds the operation to be performed and the type to be returned.
     * @param <T> The type of result to be returned.
     * @return A Task instance.
     */
    public static <T> Task createTask(Callable<T> op) {
        return createTask(op, TaskType.OTHER);
    }

    /**
     * Factory method for safe creation of a task. Receives a Runnable that represents a task that does not return a value.
     * The method wraps the Runnable in a Callable and adds a default TaskType.
     *
     * @param op - A Runnable, holds the operation to be performed.
     * @return A Task instance
     */
    public static Task createTask(Runnable op) {
        return createTask(Executors.callable(op), TaskType.OTHER);
    }

    /**
     * @param op   - Factory method for safe creation of a task. Receives a Runnable that represents a task that does not return a value, and a TaskType.
     *             The method wraps the Runnable in a Callable.
     * @param type - The task type and the priority of the task to be performed.
     * @return A Task instance.
     */
    public static Task createTask(Runnable op, TaskType type) {
        return createTask(Executors.callable(op), type);
    }

    /**
     * @return The priority of the task.
     */
    public int getPriority() {
        return type.getTypePriority();
    }

    /**
     * @return true if the task has completed, false otherwise.
     */
    public boolean isFinished() {
        return finished;
    }

    /**
     * Used for the associated CustomFuture get method.
     *
     * @return returns the CountDownLatch instance.
     */
    public CountDownLatch getLatch() {
        return this.latch;
    }

    /**
     * Cancels th task.
     */
    public void cancel() {
        if (isFinished() == false)
            cancelled = true;
    }

    /**
     * @return If the task has returned a value, returns the result, otherwise, return null.
     */
    public T getRes() {
        if (res != null) {
            return res;
        }
        return null;
    }

    /**
     * Implementation of the Runnable interface, runs the Callable operation. Once the operation has finished, the latch countdown mathod is invoked
     * and the associated CustomFuture will be able to retrieve the result.
     */
    @Override
    public void run() {
        if (cancelled == false) {
            try {
                res = op.call();
                latch.countDown();
                finished = true;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public int compareTo(Task o) {
        return Integer.compare(this.getPriority(), o.getPriority());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task<?> task)) return false;
        if (finished != task.finished) return false;
        if (type.getType() != task.type.getType()) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = op != null ? op.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (latch != null ? latch.hashCode() : 0);
        result = 31 * result + (finished ? 1 : 0);
        result = 31 * result + (res != null ? res.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Part_1.Task{" +
                "Type=" + type +
                ", Finished=" + finished +
                ", Result=" + res +
                '}';
    }
}
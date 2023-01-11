package Part_2;

import java.util.concurrent.*;

public class Task<T> implements Runnable, Comparable<Task> {
    private final Callable<T> op;
    private final TaskType type;
    private final CountDownLatch latch = new CountDownLatch(1);
    private boolean cancelled = false;
    private boolean finished = false;
    private T res;


    private Task(Callable<T> op, TaskType type) {
        this.op = op;
        this.type = type;
    }

    public static <T> Task createTask(Callable<T> op, TaskType type) {
        return new Task(op, type);
    }

    public static <T> Task createTask(Callable<T> op) {
        return new Task(op, TaskType.OTHER);
    }

    public int getPriority() {
        return type.getTypePriority();
    }


    public boolean isFinished() {
        return finished;
    }

    public CountDownLatch getLatch() {
        return this.latch;
    }

    public void cancel(){
        if(isFinished() == false)
            cancelled = true;
    }
    public T getRes() {
        return res;
    }

    @Override
    public void run() {
        if(cancelled == false) {
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

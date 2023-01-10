import java.util.Objects;
import java.util.concurrent.*;

public class Task<T> implements Runnable, Comparable<Task>{
    private Callable<T> op;
    private TaskType type;
    private final CountDownLatch latch = new CountDownLatch(1);
    private boolean finished = false;
    private boolean cancel = false;
    T res;


    private Task(Callable<T> op, TaskType type){
        this.op = op;
        this.type = type;
    }

    public static <T> Task createTask(Callable<T> op, TaskType type){
        return new Task(op,type);
    }
    public static <T> Task createTask(Callable<T> op){
        return new Task(op,TaskType.OTHER);
    }
    public int getPriority(){
        return type.getTypePriority();
    }

    @Override
    public int compareTo(Task o) {
        if(this.getPriority() > o.getPriority()) return 1;
        else if(this.getPriority() < o.getPriority()) return 0;
        return 0;
    }



    @Override
    public void run() {
        try {
            res = op.call();
            latch.countDown();
            finished = true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task<?> task)) return false;

        if (finished != task.finished) return false;
        if (cancel != task.cancel) return false;
        if (type.getType() != task.type.getType()) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = op != null ? op.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (latch != null ? latch.hashCode() : 0);
        result = 31 * result + (finished ? 1 : 0);
        result = 31 * result + (cancel ? 1 : 0);
        result = 31 * result + (res != null ? res.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Task{" +
                "Type=" + type +
                ", Finished=" + finished +
                ", Canceled=" + cancel +
                ", Result=" + res +
                '}';
    }
}

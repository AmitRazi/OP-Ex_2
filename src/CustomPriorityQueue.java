import java.util.concurrent.PriorityBlockingQueue;

public class CustomPriorityQueue extends PriorityBlockingQueue<Runnable> {
    private PriorityBlockingQueue<Task> queue;

    public CustomPriorityQueue(){
        queue = new PriorityBlockingQueue<>();
    }
}


import java.sql.SQLOutput;
import java.util.concurrent.*;

public class CustomExecutor{
    private ThreadPoolExecutor executor;
    private int currentMaxPriority;
    private boolean stopped = false;
    private CustomPriorityQueue queue;

    public CustomExecutor(){
        queue = new CustomPriorityQueue();
       executor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors()/2, Runtime.getRuntime().availableProcessors()-1,
                300L, TimeUnit.MILLISECONDS, queue){
           @Override
           protected void beforeExecute(Thread t, Runnable r) {
              Task task = (Task) queue.peek();
              if(task != null){
              currentMaxPriority = task.getPriority();}
              else{
                  currentMaxPriority = 0;
              }
           }
       };
    }


    public <T> Future<T> submit(Task task){
        if(stopped == false) {
            executor.execute(task);
            System.out.println(executor.getCorePoolSize());
            return task;
        }
        return null;
    }

    public <T> Future<T> submit(Callable<T> op){
        return submit(Task.createTask(op));
    }

    public <T> Future<T> submit(Callable<T> op,TaskType type){
        return submit(Task.createTask(op, type));
    }

    public int getCurrentMaxPriority(){
        return currentMaxPriority;
    }
    public void shutdown(){
        stopped = true;
        executor.shutdown();
    }




}

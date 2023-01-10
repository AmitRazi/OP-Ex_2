import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CustomFuture<T> implements Future<T> {
    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        cancel = mayInterruptIfRunning;
        return cancel;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    public boolean isDone(){
        return finished;
    }

    @Override
    public T get() throws InterruptedException, ExecutionException {
        latch.await();
        return res;
    }

    @Override
    public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        if(latch.await(timeout,unit)){
            return res;
        }else{
            throw new TimeoutException("The task has timed out after more then "+timeout+" "+unit.toString());
        }
    }

}

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) {
        Task task1 = Task.createTask(() -> "Amit is king");
        CustomExecutor service = new CustomExecutor();
        CustomFuture<Integer> task = service.submit(() -> {
            int sum = 0;
            for (int i = 0; i < 100; i++) {
                sum += i;
            }
            return sum;
        });
        Future<String> task2 = service.submit(() -> {
            sleep(200);
            System.out.println("sleep task");
            return "sleep task";
        });
        Future<String> task3 = service.submit(() -> {
            sleep(200);
            System.out.println("sleep task");
            return "sleep task";
        });
        Future<String> task4 = service.submit(() -> {
            sleep(200);
            System.out.println("sleep task");
            return "sleep task";
        });
        Future<String> task25 = service.submit(() -> {
            sleep(200);
            System.out.println("sleep task");
            return "sleep task";
        });
        Future<String> task24 = service.submit(() -> {
            sleep(200);
            System.out.println("sleep task");
            return "sleep task";
        });
        Future<String> task23 = service.submit(() -> {
            sleep(200);
            System.out.println("sleep task");
            return "sleep task";
        });
        Future<String> task22 = service.submit(() -> {
            sleep(200);
            System.out.println("sleep task");
            return "sleep task";
        });
        Future<String> task21 = service.submit(() -> {
            sleep(200);
            System.out.println("sleep task");
            return "sleep task";
        });
        Future<String> task20 = service.submit(() -> {
            sleep(200);
            System.out.println("sleep task");
            return "sleep task";
        });
        Future<String> task5 = service.submit(() -> {
            sleep(200);
            System.out.println("sleep task");
            return "sleep task";
        });
        Future<String> task6 = service.submit(() -> {
            sleep(200);
            System.out.println("sleep task");
            return "sleep task";
        });

        Future<String> task8 = service.submit(() -> {
            sleep(200);
            System.out.println("sleep task");
            return "sleep task";
        });
        Future<String> task9 = service.submit(() -> {
            sleep(200);
            System.out.println("sleep task");
            return "sleep task";
        });
        Future<String> task10 = service.submit(() -> {
            sleep(200);
            System.out.println("sleep task");
            return "sleep task";
        });
        Future<String> task11 = service.submit(() -> {
            sleep(200);
            System.out.println("sleep task");
            return "sleep task";
        });
        Future<String> task12 = service.submit(() -> {
            sleep(200);
            System.out.println("sleep task");
            return "sleep task";
        });
        try {
            sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        TaskType.IO.setTypePriority(10);
        service.submit(() -> System.out.println("hello from runnable"));
        Future<String> task7 = service.submit(() -> {
            sleep(200);
            System.out.println("special sleep task");
            return "special sleep task";
        }, TaskType.IO);
        service.submit(task1);
        service.shutdown();
        try {
            String str = task7.get();
            int test = task.get();
            System.out.println(str+" this is");
            System.out.println(test + " hey");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        System.out.println(task.toString());

    }
}